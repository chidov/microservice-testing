package com.egen.foodclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * @author cdov
 */
@FeignClient(name = "food", url= "${food.ribbon.listOfServers}")
public interface FoodFeignClient {

    @RequestMapping(method = RequestMethod.GET, value = "/foods")
    List<Food> getFoods();
}
