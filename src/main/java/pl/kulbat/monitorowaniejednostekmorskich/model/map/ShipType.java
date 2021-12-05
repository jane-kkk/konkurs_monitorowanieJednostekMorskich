package pl.kulbat.monitorowaniejednostekmorskich.model.map;

import java.util.Arrays;
import java.util.List;

public enum ShipType {
    DEFAULT(0),
    WING_IN_GROUND(20, 21, 22, 23, 24, 25, 26, 27, 28, 29),
    FISHING(30),
    TOWING(31, 32),
    DREDGING_OPS(33),
    DIVING_OPS(34),
    MILITARY_OPS(35),
    SAILING(36),
    PLEASURE_CRAFT(37),
    HIGH_SPEED_CRAFT(40, 41, 42, 43, 44, 45, 46, 47, 48, 49),
    PILOT(50),
    SEARCH_AND_RESCUE(51),
    TUG(52),
    PORT_TENDER(53),
    ANTI_POLLUTION_EQ(54),
    LAW_ENFORCEMENT(55),
    SPARE(56, 57),
    MEDICAL_TRANSPORT(58),
    NONCOMBATANT_SHIP(59),
    PASSENGER(60, 61, 62, 63, 64, 65, 66, 67, 68, 69),
    CARGO(70, 71, 72, 73, 74, 75, 76, 77, 78, 79),
    TANKER(80, 81, 82, 83, 84, 85, 86, 87, 88, 89),
    OTHER_TYPE(90, 91, 92, 93, 94, 95, 96, 97, 98, 99);

    private final List<Integer> types;

    ShipType(Integer... types) {
        this.types = Arrays.asList(types);
    }

    public static ShipType getById(Integer id) {
        return Arrays.stream(values()).filter(shipType -> shipType.types.contains(id)).findFirst().orElse(DEFAULT);
    }
}
