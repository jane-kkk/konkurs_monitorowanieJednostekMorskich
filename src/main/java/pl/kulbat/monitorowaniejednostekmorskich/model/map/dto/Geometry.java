package pl.kulbat.monitorowaniejednostekmorskich.model.map.dto;

import java.util.List;

public record Geometry(String type, List<Double> coordinates) {
}
