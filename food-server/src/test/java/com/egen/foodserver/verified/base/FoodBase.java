package com.egen.foodserver.verified.base;

import static org.mockito.BDDMockito.given;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.egen.foodserver.Food;
import com.egen.foodserver.FoodController;
import com.egen.foodserver.FoodRepository;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

/**
 * @author cdov
 */
@ExtendWith(SpringExtension.class)
@WebMvcTest(FoodController.class)
public class FoodBase {

    @Autowired
    protected MockMvc mockMvc;

    @MockBean
    protected FoodRepository foodRepository;

    @BeforeEach
    void setup() {
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

