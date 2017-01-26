package com.vodzianova.model;

import java.math.BigDecimal;

/**
 * Created by elena on 1/24/17.
 */
public class Receipt {

    private BigDecimal salesTax;

    public Receipt(BigDecimal salesTax) {
        this.salesTax = salesTax;
    }

    public BigDecimal getSalesTax() {
        return salesTax;
    }

    @Override
    public String toString() {
        return "Receipt{" +
                "salesTax=" + salesTax +
                '}';
    }
}
