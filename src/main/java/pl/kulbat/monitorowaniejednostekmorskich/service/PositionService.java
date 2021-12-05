package pl.kulbat.monitorowaniejednostekmorskich.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import pl.kulbat.monitorowaniejednostekmorskich.model.map.ShipEntity;
import pl.kulbat.monitorowaniejednostekmorskich.model.map.dto.Area;
import pl.kulbat.monitorowaniejednostekmorskich.model.map.dto.Position;
import pl.kulbat.monitorowaniejednostekmorskich.model.map.dto.Ship;
import pl.kulbat.monitorowaniejednostekmorskich.service.mapper.PositionMapper;
import pl.kulbat.monitorowaniejednostekmorskich.service.repository.DestinationEntityRepository;
import pl.kulbat.monitorowaniejednostekmorskich.service.repository.ShipEntityRepository;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static pl.kulbat.monitorowaniejednostekmorskich.model.map.NavigationalStatus.getStatusById;
import static pl.kulbat.monitorowaniejednostekmorskich.model.map.ShipType.getById;
import static pl.kulbat.monitorowaniejednostekmorskich.service.IconUtils.getDefaultIconWhenCustomIsNotAvailable;

@Service
@RequiredArgsConstructor
public class PositionService {

    private static final String POSITIONS_URL = "https://www.barentswatch.no/bwapi/v2/geodata/ais/openpositions?";
    private final RestTemplate restTemplate = new RestTemplate();
    private final TokenService tokenService;
    private final PositionMapper positionMapper;
    private final ShipEntityRepository shipRepository;
    private final DestinationService destinationService;

    public List<Ship> getShips(Area area) {
        Position[] positionList = getPositionList(area).getBody();

        return Stream.of(positionList).map(
                position -> new Ship(position.geometry().coordinates().get(0),
                        position.geometry().coordinates().get(1),
                        position.name(),
                        destinationService.getDestinationPointByName(position.destination()),
                        position.sog(),
                        position.eta(),
                        getStatusById(position.navstat()),
                        getDefaultIconWhenCustomIsNotAvailable(getById(position.shipType()))
                )).collect(toList());
    }

    public List<Ship> findShipsByDestinationCountry(String country) {
        return positionMapper.map(shipRepository.findAllByDestinationCountry(country));
    }

    public void saveShipsToEntity(Area area) {
        Position[] positionList = getPositionList(area).getBody();
        List<ShipEntity> shipEntities = Arrays.stream(positionList)
                .map(position -> positionMapper.map(position, destinationService.getDestinationPointByName(position.name())))
                .collect(toList());
        shipRepository.saveAll(shipEntities);
    }

    private ResponseEntity<Position[]> getPositionList(Area area) {
        final String finalPositionUrl = POSITIONS_URL + "Xmin=" + area.xMin() + "&Xmax=" + area.xMax() + "&Ymin=" + area.yMin() + "&Ymax=" + area.yMax();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + tokenService.getAccessToken());
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        return restTemplate.exchange(finalPositionUrl, HttpMethod.GET, httpEntity, Position[].class);
    }
}
