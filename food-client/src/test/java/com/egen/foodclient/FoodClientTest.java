package com.egen.foodclient;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cdov
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
//@AutoConfigureWireMock
@AutoConfigureStubRunner(ids = {"com.egen:food-server:+:stubs:8080"}, stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class FoodClientTest {

    @Autowired
    private FoodClient foodClient;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() throws Exception {
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
        assertThat(foodList).filteredOn(food -> food.getName().equals("Rice")).hasSize(1);
        assertThat(foodList).extracting("id", "name", "description").containsExactly(
                new Tuple(1L, "Rice", "White Rice"),
                new Tuple(2L, "Fried Rice", "Premium Rice")
        );
    }
}
