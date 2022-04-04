package microserviceAttraction.services;

import com.google.common.util.concurrent.RateLimiter;
import microserviceAttraction.models.Attraction;
import microserviceAttraction.utilities.SleepUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class AttractionService {

    //proximity in miles
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
    private final int defaultAttractionProximityRange = 200;
    private int attractionProximityRange = 10000;

    private final static Logger logger = LoggerFactory.getLogger(AttractionService.class);
    private static RateLimiter rateLimiter = RateLimiter.create(100000);
    private final SleepUtilities sleepUtilities = new SleepUtilities();

    private List<Attraction> attractions = createAttractions();

    public List<Attraction> findAll(){
        return attractions;
    }

    /**
     * At first, this method gather all information from 5 attractions near a location
     * using a latitude and a longitude. In a second time, this method create a list
     * of information concerning the attraction, the location, the reward points and
     * the distance between them
     * @param latitude
     * @param longitude
     * @return a list of information from 5 attractions
     */

    public List<List<String>> findFiveClosestTouristAttractions(double latitude, double longitude){
        List<Attraction> fiveClosestAttractions = findFiveNearByAttractions(latitude, longitude);
        List<List<String>> attractionsInformation = createAttractionInformation(fiveClosestAttractions, latitude, longitude);

        return attractionsInformation;
    }

    /**
     * This method is called to create a random reward value
     * Each time this method is called, sleep during 2 milliseconds
     * @return a random integer
     */

    public int createAttractionRewardPoints() {
        sleepUtilities.sleepLighter();

        int randomInt = ThreadLocalRandom.current().nextInt(1, 1000);

        logger.debug("RandomInt = " + randomInt);
        return randomInt;
    }

    /**
     * This method verify if the distance between attraction and a location is inferior
     * to the attraction proximity range
     * @param attractionLatitude
     * @param attractionLongitude
     * @param locationLatitude
     * @param locationLongitude
     * @return a boolean
     */

    public boolean isWithinAttractionProximity(double attractionLatitude, double attractionLongitude, double locationLatitude, double locationLongitude) {
        logger.debug("Actual attraction proximity range is " + attractionProximityRange);
        return getDistanceBetweenAttractionAndLocation(attractionLatitude, attractionLongitude, locationLatitude, locationLongitude) < attractionProximityRange;
    }

    public int getAttractionProximityRange() {
        return attractionProximityRange;
    }

    public void setAttractionProximityRange(int proximityRange) {
        this.attractionProximityRange = proximityRange;
    }

    public void setDefaultAttractionProximityRange() {
        attractionProximityRange = defaultAttractionProximityRange;
    }

    /**
     * Given an attraction and a location with their own latitude and longitude,
     * this method calculate the angle using these attributes. After that,
     * it translate the result into miles.
     * @param attractionLatitude
     * @param attractionLongitude
     * @param locationLatitude
     * @param locationLongitude
     * @return a distance in miles
     */

    private double getDistanceBetweenAttractionAndLocation(double attractionLatitude, double attractionLongitude, double locationLatitude, double locationLongitude) {

        double mathAttractionLatitude = Math.toRadians(attractionLatitude);
        double mathAttractionLongitude = Math.toRadians(attractionLongitude);
        double mathLocationLatitude = Math.toRadians(locationLatitude);
        double mathLocationLongitude = Math.toRadians(locationLongitude);

        double angle = Math.acos(Math.sin(mathAttractionLatitude) * Math.sin(mathLocationLatitude)
                + Math.cos(mathAttractionLatitude) * Math.cos(mathLocationLatitude) * Math.cos(mathAttractionLongitude - mathLocationLongitude));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        return statuteMiles;
    }

    /**
     * Given a list of attractions and a latitude and longitude from a location,
     * create a list containing all information as list of string.
     * @param attractionList
     * @param latitude
     * @param longitude
     * @return a list containing all the attraction information and the distance
     * between a location and this attraction
     */

    private List<List<String>> createAttractionInformation(List<Attraction> attractionList, double latitude, double longitude){
        List<List<String>> allAttractionsInformationGathered = new ArrayList<>();
        double locationLatitude = latitude;
        double locationLongitude = longitude;

        for(Attraction attraction : attractionList){
            List<String> attractionsInformation = new ArrayList<>();

            attractionsInformation.add(attraction.getAttractionName());
            attractionsInformation.add(String.valueOf(attraction.getLatitude()));
            attractionsInformation.add(String.valueOf(attraction.getLongitude()));
            attractionsInformation.add(String.valueOf(locationLatitude));
            attractionsInformation.add(String.valueOf(locationLongitude));
            attractionsInformation.add(String.valueOf(getDistanceBetweenAttractionAndLocation(attraction.getLatitude(),attraction.getLongitude(), locationLatitude, locationLongitude)));
            attractionsInformation.add(String.valueOf(createAttractionRewardPoints()));

            allAttractionsInformationGathered.add(attractionsInformation);
        }

        return allAttractionsInformationGathered;
    }

    /**
     * This method iterate over the attraction List two times
     * First, it will add all the attraction in the attraction proximity range
     * In second, it will add any attractions if the list has less than 5 attractions
     * and if the attraction doesn't exist already
     * @param latitude
     * @param longitude
     * @return a list of attraction
     */

    private List<Attraction> findFiveNearByAttractions(double latitude, double longitude) {
        List<Attraction> nearbyAttractions = new ArrayList<>();
        for(Attraction attraction : findAll()) {
            if(isWithinAttractionProximity(attraction.getLatitude(),attraction.getLongitude(), latitude, longitude) & nearbyAttractions.size() < 5) {
                nearbyAttractions.add(attraction);
            }
        }

        for(Attraction attraction : findAll()){
            if(!findAll().stream().toList().contains(attraction.getAttractionName()) & nearbyAttractions.size() < 5){
                nearbyAttractions.add(attraction);
            }
        }

        return nearbyAttractions;
    }

    /**
     * Create a list of attractions with parameters : name, city, state, latitude, longitude
     * Each time this method is called, sleep during 2 milliseconds
     * @return a list of attractions
     */

    private List<Attraction> createAttractions() {
        rateLimiter.acquire();
        this.sleepUtilities.sleepLighter();
        List<Attraction> attractions = new ArrayList<>();
        attractions.add(new Attraction("Disneyland", "Anaheim", "CA", 33.817595D, -117.922008D));
        attractions.add(new Attraction("Jackson Hole", "Jackson Hole", "WY", 43.582767D, -110.821999D));
        attractions.add(new Attraction("Mojave National Preserve", "Kelso", "CA", 35.141689D, -115.510399D));
        attractions.add(new Attraction("Joshua Tree National Park", "Joshua Tree National Park", "CA", 33.881866D, -115.90065D));
        attractions.add(new Attraction("Buffalo National River", "St Joe", "AR", 35.985512D, -92.757652D));
        attractions.add(new Attraction("Hot Springs National Park", "Hot Springs", "AR", 34.52153D, -93.042267D));
        attractions.add(new Attraction("Kartchner Caverns State Park", "Benson", "AZ", 31.837551D, -110.347382D));
        attractions.add(new Attraction("Legend Valley", "Thornville", "OH", 39.937778D, -82.40667D));
        attractions.add(new Attraction("Flowers Bakery of London", "Flowers Bakery of London", "KY", 37.131527D, -84.07486D));
        attractions.add(new Attraction("McKinley Tower", "Anchorage", "AK", 61.218887D, -149.877502D));
        attractions.add(new Attraction("Flatiron Building", "New York City", "NY", 40.741112D, -73.989723D));
        attractions.add(new Attraction("Fallingwater", "Mill Run", "PA", 39.906113D, -79.468056D));
        attractions.add(new Attraction("Union Station", "Washington D.C.", "CA", 38.897095D, -77.006332D));
        attractions.add(new Attraction("Roger Dean Stadium", "Jupiter", "FL", 26.890959D, -80.116577D));
        attractions.add(new Attraction("Texas Memorial Stadium", "Austin", "TX", 30.283682D, -97.732536D));
        attractions.add(new Attraction("Bryant-Denny Stadium", "Tuscaloosa", "AL", 33.208973D, -87.550438D));
        attractions.add(new Attraction("Tiger Stadium", "Baton Rouge", "LA", 30.412035D, -91.183815D));
        attractions.add(new Attraction("Neyland Stadium", "Knoxville", "TN", 35.955013D, -83.925011D));
        attractions.add(new Attraction("Kyle Field", "College Station", "TX", 30.61025D, -96.339844D));
        attractions.add(new Attraction("San Diego Zoo", "San Diego", "CA", 32.735317D, -117.149048D));
        attractions.add(new Attraction("Zoo Tampa at Lowry Park", "Tampa", "FL", 28.012804D, -82.469269D));
        attractions.add(new Attraction("Franklin Park Zoo", "Boston", "MA", 42.302601D, -71.086731D));
        attractions.add(new Attraction("El Paso Zoo", "El Paso", "TX", 31.769125D, -106.44487D));
        attractions.add(new Attraction("Kansas City Zoo", "Kansas City", "MO", 39.007504D, -94.529625D));
        attractions.add(new Attraction("Bronx Zoo", "Bronx", "NY", 40.852905D, -73.872971D));
        attractions.add(new Attraction("Cinderella Castle", "Orlando", "FL", 28.419411D, -81.5812D));
        logger.debug("Creating a list of attractions");
        return attractions;
    }
}
