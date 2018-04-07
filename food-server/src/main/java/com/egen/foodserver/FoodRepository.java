package com.egen.foodserver;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author cdov
 */
public interface FoodRepository extends JpaRepository<Food, Long> {

}
