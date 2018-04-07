package com.egen.foodclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cdov
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@AutoConfigureWireMock
@AutoConfigureStubRunner(ids = {"com.egen:food-server:+:stubs:8080"}, stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class FoodClientTest {

    @Autowired
    private FoodClient foodClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {
//        String json = this.objectMapper
//                .writeValueAsString(Arrays.asList(new Food(1L, "Rice", "White Rice"),
//                        new Food(2L, "Fried Rice", "Premium Rice")));
//        WireMock.stubFor(WireMock.get("/foods")
//                .willReturn((WireMock.aResponse()
//                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
//                        .withStatus(200)
//                        .withBody(json))));
    }

    @Test
    public void whenGetFoods_thenReturnFoods(){
        List<Food> foodList = foodClient.getFoods();
        assertThat(foodList).isNotEmpty();
        assertThat(foodList.stream().filter(food -> food.getName().equals("Rice")).count()).isEqualTo(1);
    }
}
