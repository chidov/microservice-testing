package com.egen.foodserver;

import com.egen.foodserver.verified.base.FoodBase;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author cdov
 */

public class FoodControllerTest extends FoodBase {

    @Test
    public void testGetAllFoods() throws Exception {
        this.mockMvc
                .perform(get("/foods"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("@.[0].name").value("Rice"));
    }
}
