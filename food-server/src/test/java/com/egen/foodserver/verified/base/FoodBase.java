package com.egen.foodserver.verified.base;

import com.egen.foodserver.Food;
import com.egen.foodserver.FoodController;
import com.egen.foodserver.FoodRepository;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

/**
 * @author cdov
 */
@RunWith(SpringRunner.class)
@WebMvcTest(FoodController.class)
public class FoodBase {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected FoodRepository foodRepository;

    @Before
    public void setup() {
        mockFoodRepositoryFindAll();
        RestAssuredMockMvc.mockMvc(mockMvc);
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

