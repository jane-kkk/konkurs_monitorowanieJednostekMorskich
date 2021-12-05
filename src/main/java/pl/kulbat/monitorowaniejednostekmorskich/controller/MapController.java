package pl.kulbat.monitorowaniejednostekmorskich.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import pl.kulbat.monitorowaniejednostekmorskich.service.PositionService;

@Controller
public class MapController {

    private final PositionService positionService;

    public MapController(PositionService positionService) {
        this.positionService = positionService;
    }

    @GetMapping
    public String getMap(Model model) {
        model.addAttribute("ships", positionService.getShips());
        model.addAttribute("polygon", positionService.getPolygon());
        return "map";
    }

}
