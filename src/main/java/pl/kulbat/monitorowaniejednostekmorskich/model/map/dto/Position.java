package pl.kulbat.monitorowaniejednostekmorskich.model.map.dto;

public record Position(
        String timeStamp,
        Double sog,
        Double rot,
        Integer navstat,
        Integer mmsi,
        Double cog,
        Geometry geometry,
        Integer shipType,
        String name,
        String imo,
        String callsign,
        String country,
        String eta,
        String destination,
        Boolean isSurvey,
        Integer heading,
        Double draught,
        Integer a,
        Integer b,
        Integer c,
        Integer d) {
}
