package pl.kulbat.monitorowaniejednostekmorskich.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import pl.kulbat.monitorowaniejednostekmorskich.model.map.dto.DestinationPoint;
import pl.kulbat.monitorowaniejednostekmorskich.model.map.dto.Position;
import pl.kulbat.monitorowaniejednostekmorskich.model.map.dto.Ship;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static pl.kulbat.monitorowaniejednostekmorskich.model.map.NavigationalStatus.getStatusById;
import static pl.kulbat.monitorowaniejednostekmorskich.model.map.ShipType.getById;
import static pl.kulbat.monitorowaniejednostekmorskich.service.IconUtils.getDefaultIconWhenCustomIsNotAvailable;

@Service
@RequiredArgsConstructor
public class PositionService {

    private static final String POSITIONS_URL = "https://www.barentswatch.no/bwapi/v2/geodata/ais/openpositions?Xmin=10.09094&Xmax=10.67047&Ymin=63.3989&Ymax=63.58645";
    private static final String GEOCODING_URL = "http://api.positionstack.com/v1/forward?access_key=1c28dd66fdeca61da2b6a574ebfa8919&query=";
    private final RestTemplate restTemplate = new RestTemplate();
    private final TokenService tokenService;

    public List<Ship> getShips() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + tokenService.getAccessToken());
        HttpEntity httpEntity = new HttpEntity(httpHeaders);

        ResponseEntity<Position[]> positionsResponse = restTemplate.exchange(POSITIONS_URL, HttpMethod.GET, httpEntity, Position[].class);
        return Stream.of(positionsResponse.getBody()).map(
                position -> new Ship(position.geometry().coordinates().get(0),
                        position.geometry().coordinates().get(1),
                        position.name(),
                        getDestinationPointByName(position.destination()),
                        position.sog(),
                        position.eta(),
                        getStatusById(position.navstat()),
                        getDefaultIconWhenCustomIsNotAvailable(getById(position.shipType()))
                )).collect(toList());
    }


    private DestinationPoint getDestinationPointByName(final String name) {
        if (StringUtils.hasText(name) && name.length() > 3) {
            final String finalUrl = GEOCODING_URL + name + "&limit=1";
            JsonNode data = restTemplate.getForObject(finalUrl, JsonNode.class).get("data");
            if (!data.isEmpty()) {
                return new DestinationPoint(data.get(0).get("latitude").asDouble(), data.get(0).get("longitude").asDouble(), data.get(0).get("name").asText());
            }
        }
        return new DestinationPoint(0.0, 0.0, name);
    }
}
