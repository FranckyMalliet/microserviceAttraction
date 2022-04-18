package microserviceAttraction.controllers;

import microserviceAttraction.models.Attraction;
import microserviceAttraction.services.AttractionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AttractionControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private static MockMvc mockMvc;

    @Autowired
    private AttractionService attractionService;

    @BeforeEach
    public void setupMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void givenAnUrl_ReturnAListOfAttractions() throws Exception {
        //WHEN
        List<Attraction> attractionList = attractionService.findAll();
        Assertions.assertNotNull(attractionList);

        //THEN
        mockMvc.perform(get("/Attractions"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAnUrl_ReturnFiveAttractionsCloseToATouristLocation() throws Exception {
        //GIVEN
        double latitude = 35;
        double longitude = -110;

        //THEN
        mockMvc.perform(get("/fiveClosestTouristAttractions")
                        .param("latitude", String.valueOf(latitude))
                        .param("longitude", String.valueOf(longitude)))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAnUrl_ReturnRandomRewardPoints() throws Exception {
        //WHEN
        int randomReward = attractionService.createAttractionRewardPoints();
        Assertions.assertNotNull(randomReward);

        //THEN
        mockMvc.perform(get("/createAttractionRewardPoints"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAnUrl_ReturnTheActualAttractionProximityRange() throws Exception {
        //WHEN
        int attractionProximityRange = attractionService.getAttractionProximityRange();
        Assertions.assertNotNull(attractionProximityRange);

        //THEN
        mockMvc.perform(get("/getAttractionProximityRange"))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAnInteger_ModifyTheActualAttractionProximityRange() throws Exception {
        //WHEN
        int newAttractionProximityRange = 500;

        //THEN
        mockMvc.perform(get("/newAttractionProximityRange")
                        .param("proximityRange", String.valueOf(newAttractionProximityRange)))
                .andExpect(status().isOk());
    }

    @Test
    public void givenAnUrl_ResetToDefaultAttractionProximityRange() throws Exception {
        //WHEN
        attractionService.setDefaultAttractionProximityRange();
        Assertions.assertEquals(200, attractionService.getAttractionProximityRange());

        //THEN
        mockMvc.perform(get("/defaultAttractionProximityRange"))
                .andExpect(status().isOk());
    }
}
