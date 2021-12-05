package pl.kulbat.monitorowaniejednostekmorskich.model.map.dto;

import pl.kulbat.monitorowaniejednostekmorskich.model.map.ShipType;

public record Ship(Double x, Double y, String name, DestinationPoint destinationPoint, Double sog,
                   String eta, String navstat, ShipType shipType) {
}
