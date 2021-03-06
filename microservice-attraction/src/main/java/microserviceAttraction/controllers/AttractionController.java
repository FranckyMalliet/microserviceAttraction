package microserviceAttraction.controllers;

import microserviceAttraction.models.Attraction;
import microserviceAttraction.services.AttractionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AttractionController {

    private final static Logger logger = LoggerFactory.getLogger(AttractionController.class);
    private AttractionService attractionService;

    public AttractionController(AttractionService attractionService){
        this.attractionService = attractionService;
    }

    @GetMapping(value = "/Attractions")
    public List<Attraction> getAttractions(){
        logger.debug("Retrieving list of attractions");
        return attractionService.findAll();
    }

    @GetMapping(value = "/fiveClosestTouristAttractions")
    public List<List<String>> fiveClosestTouristAttractions(@RequestParam("latitude") double latitude,
                                                            @RequestParam("longitude") double longitude){
        logger.debug("Retrieving five nearby attractions");
        return attractionService.findFiveClosestTouristAttractions(latitude, longitude);
    }

    @GetMapping(value = "/createAttractionRewardPoints")
    public int createAttractionRewardPoints(){
        return attractionService.createAttractionRewardPoints();
    }

    @GetMapping(value = "/getAttractionProximityRange")
    public int getAttractionProximityRange(){
        return attractionService.getAttractionProximityRange();
    }

    @GetMapping(value = "/newAttractionProximityRange")
    public void newAttractionProximityRange(@RequestParam int proximityRange){
        attractionService.setAttractionProximityRange(proximityRange);
    }

    @GetMapping(value = "/defaultAttractionProximityRange")
    public void defaultAttractionProximityRange(){
        attractionService.setDefaultAttractionProximityRange();
    }
}
