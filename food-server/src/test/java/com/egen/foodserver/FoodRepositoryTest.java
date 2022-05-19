package com.egen.foodserver;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * @author cdov
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
public class FoodRepositoryTest {

    @Autowired
    FoodRepository foodRepository;
    @Autowired
    TestEntityManager testEntityManager;

    @Test
    public void testFindAll(){
        Food food = testEntityManager.persistFlushFind(new Food(null, "Rice", "White Rice"));
        assertThat(food).isNotNull();
        List<Food> foods = foodRepository.findAll();
        assertThat(foods.size()).isEqualTo(1);
        assertThat(foods.get(0).getName()).isEqualTo("Rice");
        assertThat(foods.get(0).getDescription()).isEqualTo("White Rice");
    }
}
