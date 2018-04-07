package com.egen.foodserver;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author cdov
 */
@RunWith(SpringRunner.class)
@DataJpaTest
public class FoodEntityTest {
    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void testInsert(){
        Food food = testEntityManager.persistFlushFind(new Food(null, "Rice", "White Rice"));
        assertThat(food.getName()).isEqualTo("Rice");
        assertThat(food.getDescription()).isEqualTo("White Rice");
    }
}
