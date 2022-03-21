package microserviceAttraction.services;

import microserviceAttraction.models.Attraction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AttractionServiceTest {

    @Autowired
    private AttractionService attractionService;

    @Test
    public void createAttractionsTest () {
        //GIVEN
        List<Attraction> attractionList = attractionService.createAttractions();

        //THEN
        Assertions.assertNotNull(attractionList);
    }

    @Test
    public void findFiveClosestTouristAttractionsTest() {
        //GIVEN
        double latitude = 35;
        double longitude = -115;
        List<List<String>> attractionListInformation = attractionService.findFiveClosestTouristAttractions(latitude, longitude);

        //THEN
        Assertions.assertNotNull(attractionListInformation);
        Assertions.assertEquals(attractionListInformation.size(), 5);
    }

    @Test
    public void createAttractionRewardPointsTest() {
        //GIVEN
        int rewardPoints = attractionService.createAttractionRewardPoints();

        //THEN
        Assertions.assertNotNull(rewardPoints);
    }

    @Test
    public void isWithinAttractionProximityTest() {
        //GIVEN
        double specificPointLatitude = 50;
        double specificPointLongitude = 5;
        Attraction attraction = new Attraction("Tour Eiffel", "Paris", "France", 48.858370, 2.294481);

        //THEN
        Assertions.assertTrue(attractionService.isWithinAttractionProximity(attraction.getLatitude(), attraction.getLongitude(), specificPointLatitude, specificPointLongitude));
    }

    @Test
    public void getAttractionProximityRangeTest() {
        //GIVEN
        attractionService.setAttractionProximityRange(10000);
        int attractionProximityRange = attractionService.getAttractionProximityRange();

        //THEN
        Assertions.assertEquals(attractionProximityRange, 10000);
        Assertions.assertNotNull(attractionProximityRange);
    }

    @Test
    public void setAttractionProximityRangeTest() {
        //GIVEN
        int newAttractionProximityRange = 300;

        //WHEN
        attractionService.setAttractionProximityRange(newAttractionProximityRange);

        //THEN
        Assertions.assertEquals(attractionService.getAttractionProximityRange(), 300);
    }

    @Test
    public void setDefaultAttractionProximityRangeTest() {
        //WHEN
        attractionService.setDefaultAttractionProximityRange();
        int proximityBuffer = attractionService.getAttractionProximityRange();

        //THEN
        Assertions.assertEquals(proximityBuffer, 200);
    }
}
