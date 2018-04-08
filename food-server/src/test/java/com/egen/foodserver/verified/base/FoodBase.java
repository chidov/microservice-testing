package com.egen.foodserver.verified.base;

import com.egen.foodserver.Food;
import com.egen.foodserver.FoodController;
import com.egen.foodserver.FoodRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;

/**
 * @author cdov
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FoodBase {

    @Autowired
    private FoodController foodController;

    @MockBean
    private FoodRepository foodRepository;

    @Before
    public void setup() {
        mockFoodRepositoryFindAll();
        RestAssuredMockMvc.standaloneSetup(foodController);
    }

    private void mockFoodRepositoryFindAll(){
        given(this.foodRepository.findAll())
                .willReturn(Arrays.asList(
                        new Food(1L, "Rice", "White Rice"),
                        new Food(2L, "Fried Rice", "Premium Rice")
                        )
                );
    }

}

