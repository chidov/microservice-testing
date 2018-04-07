package com.egen.foodserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author cdov
 */
@RestController
public class FoodController {

    @Autowired
    private FoodRepository foodRepository;

//    public FoodController(FoodRepository foodRepository) {
//        this.foodRepository = foodRepository;
//    }

    @GetMapping("/foods")
    public List<Food> getAllFoods(){
        return foodRepository.findAll();
    }
}
