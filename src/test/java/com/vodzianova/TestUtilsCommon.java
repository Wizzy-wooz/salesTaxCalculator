package com.vodzianova;

import com.vodzianova.model.Category;
import com.vodzianova.model.Product;

import java.math.BigDecimal;

/**
 * Created by elena on 1/25/17.
 */
public class TestUtilsCommon {

    public static Product createProduct(String description, Integer count, BigDecimal unitPrice, Boolean imported, Category category) {
        return new Product(description, count, unitPrice, imported, category);
    }
}
