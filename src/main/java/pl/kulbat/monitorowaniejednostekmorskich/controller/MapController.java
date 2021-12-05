package pl.kulbat.monitorowaniejednostekmorskich.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.kulbat.monitorowaniejednostekmorskich.model.map.dto.Area;
import pl.kulbat.monitorowaniejednostekmorskich.model.map.dto.DestinationPoint;
import pl.kulbat.monitorowaniejednostekmorskich.model.map.dto.Ship;
import pl.kulbat.monitorowaniejednostekmorskich.service.DestinationService;
import pl.kulbat.monitorowaniejednostekmorskich.service.PositionService;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Controller
@RequiredArgsConstructor
public class MapController {

    private static final Double XMIN = 10.09094;
    private static final Double XMAX = 10.67047;
    private static final Double YMIN = 63.39894;
    private static final Double YMAX = 63.58645;

    private final PositionService positionService;
    private final DestinationService destinationService;

    @GetMapping
    public String getMap(@RequestParam(required = false) Double xMin,
                         @RequestParam(required = false) Double xMax,
                         @RequestParam(required = false) Double yMin,
                         @RequestParam(required = false) Double yMax,
                         Model model) {
        Area area = new Area(xMin, xMax, yMin, yMax);
        if (xMin == null || xMax == null || yMin == null || yMax == null) {
            area = new Area(XMIN, XMAX, YMIN, YMAX);
        }
        model.addAttribute("ships", positionService.getShips(area));
        model.addAttribute("area", area);
        return "map";
    }

    @PostMapping
    public String saveShips(@ModelAttribute Area area, Model model) {
        model.addAttribute("area", area);
        positionService.saveShipsToEntity(area);
        return "map";
    }

    @GetMapping(path = "/ships")
    public String getShips(@RequestParam String country, Model model) {
        List<Ship> ships = positionService.findShipsByDestinationCountry(country);
        model.addAttribute("ships", ships);
        return "ships";
    }

    @GetMapping(path = "/destinationCountries")
    public String getAllDestinationCountries(Model model) {
        List<String> countries =  destinationService.findAllDestinations().stream()
                .map(DestinationPoint::country)
                .distinct()
                .filter(s -> !s.equalsIgnoreCase("null"))
                .collect(toList());
        model.addAttribute("countries", countries);
        return "countries";
    }

}
