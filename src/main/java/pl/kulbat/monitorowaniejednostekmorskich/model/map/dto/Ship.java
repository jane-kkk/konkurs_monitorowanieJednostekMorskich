package pl.kulbat.monitorowaniejednostekmorskich.model.map.dto;

import pl.kulbat.monitorowaniejednostekmorskich.model.map.ShipType;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public record Ship(Double x, Double y, String name, DestinationPoint destinationPoint, Double sog,
                   String eta, String navstat, ShipType shipType) {

    public Ship(String name, DestinationPoint destinationPoint, ShipType shipType) {
        this(0.0, 0.0, name, destinationPoint, 0.0, EMPTY, EMPTY, shipType);
    }
}
