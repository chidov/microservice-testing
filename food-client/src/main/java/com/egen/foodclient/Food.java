package com.egen.foodclient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author cdov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Food {
    private long id;
    private String name;
    private String description;
}
