package pl.kulbat.monitorowaniejednostekmorskich.model.map;

import java.util.Arrays;

public enum NavigationalStatus {

    UNDER_WAY_ENGINE(0, "Under way using engine"),
    AT_ANCHOR(1, "At anchor"),
    NOT_UNDER_COMMAND(2, "Not under command"),
    RESTRICTED_(3, "Restricted manoeuverability"),
    CONSTRAINED(4, "Constrained by her draught"),
    MOORED(5, "Moored"),
    AGROUND(6, "Aground"),
    ENGAGED(7, "Engaged in Fishing"),
    UNDER_WAY_SAILING(8, "Under way sailing"),
    RESERVED_HSC(9, "Reserved for future amendment of Navigational Status for HSC"),
    RESERVED_WIG(10, "Reserved for future amendment of Navigational Status for WIG"),
    RESERVED_11(11, "Reserved for future use"),
    RESERVED_12(12, "Reserved for future use"),
    RESERVED_13(13, "Reserved for future use"),
    AIS_START(14, "AIS-SART is active"),
    DEFAULT(15, "Not defined (default)");

    private final Integer id;
    private final String status;

    NavigationalStatus(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    public static String getStatusById(Integer id){
        return Arrays.stream(values()).filter(navStatus -> navStatus.id.equals(id)).findFirst().orElse(DEFAULT).status;
    }

}
