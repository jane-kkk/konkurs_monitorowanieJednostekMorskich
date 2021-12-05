package pl.kulbat.monitorowaniejednostekmorskich.model.map.dto;

import static org.apache.commons.lang3.StringUtils.EMPTY;

public record DestinationPoint(Double latitude, Double longitude, String name, String country, String countryCode,
                               String continent) {

    public DestinationPoint(Double latitude, Double longitude, String name) {
        this(latitude, longitude, name, EMPTY, EMPTY, EMPTY);
    }
}
