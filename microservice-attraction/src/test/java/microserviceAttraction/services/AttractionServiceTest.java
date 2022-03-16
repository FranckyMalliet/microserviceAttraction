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
        double longitude = -110;
        List<List<String>> attractionListInformation = attractionService.findFiveClosestTouristAttractions(latitude, longitude);

        //THEN
        Assertions.assertNotNull(attractionListInformation);
        Assertions.assertEquals(5, attractionListInformation.size());
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
        //Assertions.assertTrue(attractionService.isWithinAttractionProximity(attraction, specificPointLatitude, specificPointLongitude));
    }

    @Test
    public void getProximityBufferTest() {
        //GIVEN
        int proximityBuffer = attractionService.getProximityBuffer();

        //THEN
        Assertions.assertEquals(proximityBuffer, 10);
        Assertions.assertNotNull(proximityBuffer);
    }

    @Test
    public void setProximityBufferTest() {
        //GIVEN
        int newProximityBuffer = 300;

        //WHEN
        attractionService.setProximityBuffer(newProximityBuffer);

        //THEN
        Assertions.assertEquals(attractionService.getProximityBuffer(), 300);
    }

    @Test
    public void setDefaultProximityBufferTest() {
        //WHEN
        attractionService.setDefaultProximityBuffer();
        int proximityBuffer = attractionService.getProximityBuffer();

        //THEN
        Assertions.assertEquals(proximityBuffer, 10);
    }
}
