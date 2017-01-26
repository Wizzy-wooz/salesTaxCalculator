package com.vodzianova.model;

/**
 * Created by elena on 1/24/17.
 */
public enum Category {
    BOOKS(false), FOOD(false), MEDICAL_PRODUCTS(false), CLOTHING(true), COMPUTERS(true), OTHER_PRODUCTS(true);

    private boolean taxable;

    Category(boolean taxable) {
        this.taxable = taxable;
    }

    public boolean isTaxable() {
        return this.taxable;
    }
}
