package pl.kulbat.monitorowaniejednostekmorskich.service;

import org.springframework.util.ResourceUtils;
import pl.kulbat.monitorowaniejednostekmorskich.model.map.ShipType;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class IconUtils {

    public static ShipType getDefaultIconWhenCustomIsNotAvailable(ShipType type) {
        try {
            String shipType = Arrays.stream(ResourceUtils.getFile("classpath:static/shipIcons").list())
                    .map(s -> s.substring(0, s.lastIndexOf('.')))
                    .filter(s -> type.name().equals(s)).findFirst().orElse(ShipType.DEFAULT.name());
            return ShipType.valueOf(shipType);
        } catch (FileNotFoundException ex) {
            return ShipType.DEFAULT;
        }
    }

}
