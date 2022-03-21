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

    @GetMapping(value = "/attractionIndex")
    public String index() {
        return "Greetings from microservice Attraction !";
    }

    @GetMapping(value = "/createAttractions")
    public List<Attraction> createAttractions(){
        logger.debug("Retrieving list of attractions");
        return attractionService.createAttractions();
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

    @GetMapping(value = "/isWithinAttractionProximity")
    public boolean isWithinAttractionProximity(@RequestParam double attractionLatitude,
                                               @RequestParam double attractionLongitude,
                                               @RequestParam double locationLatitude,
                                               @RequestParam double locationLongitude){
        logger.debug("Checking if attraction is in proximity");
        return attractionService.isWithinAttractionProximity(attractionLatitude, attractionLongitude, locationLatitude, locationLongitude);
    }

    @GetMapping(value = "/getAttractionProximityRange")
    public int getAttractionProximityRange(){
        return attractionService.getAttractionProximityRange();
    }

    @GetMapping(value = "/newAttractionProximityRange")
    public void newAttractionProximityRange(int proximityRange){
        attractionService.setAttractionProximityRange(proximityRange);
    }

    @GetMapping(value = "/defaultAttractionProximityRange")
    public void defaultAttractionProximityRange(){
        attractionService.setDefaultAttractionProximityRange();
    }
}
