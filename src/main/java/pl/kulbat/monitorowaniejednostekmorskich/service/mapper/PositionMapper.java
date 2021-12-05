package pl.kulbat.monitorowaniejednostekmorskich.service.mapper;

import org.springframework.stereotype.Service;
import pl.kulbat.monitorowaniejednostekmorskich.model.map.DestinationEntity;
import pl.kulbat.monitorowaniejednostekmorskich.model.map.ShipEntity;
import pl.kulbat.monitorowaniejednostekmorskich.model.map.ShipType;
import pl.kulbat.monitorowaniejednostekmorskich.model.map.dto.DestinationPoint;
import pl.kulbat.monitorowaniejednostekmorskich.model.map.dto.Position;
import pl.kulbat.monitorowaniejednostekmorskich.model.map.dto.Ship;

import java.time.ZonedDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class PositionMapper {

    public ShipEntity map(Position position, DestinationPoint destinationPoint) {
        ZonedDateTime timestamp = ZonedDateTime.parse(position.timeStamp());

        return ShipEntity.builder()
                .mmsi(position.mmsi())
                .timeStamp(timestamp)
                .name(position.name())
                .imo(position.imo())
                .country(position.country())
                .destination(map(destinationPoint))
                .shipType(ShipType.getById(position.shipType()))
                .build();
    }

    public Ship map(ShipEntity entity) {
        return new Ship(entity.getName(), map(entity.getDestination()), entity.getShipType());
    }

    public DestinationEntity map(DestinationPoint destinationPoint) {
        return DestinationEntity.builder()
                .name(destinationPoint.name())
                .country(destinationPoint.country())
                .countryCode(destinationPoint.countryCode())
                .continent(destinationPoint.continent())
                .build();
    }

    public DestinationPoint map(DestinationEntity entity) {
        return new DestinationPoint(entity.getLatitude(),
                entity.getLongitude(), entity.getName(),
                entity.getCountry(), entity.getCountryCode(),
                entity.getContinent());
    }


    public List<Ship> map(List<ShipEntity> shipEntities) {
        return shipEntities.stream().map(this::map).collect(toList());
    }
}
