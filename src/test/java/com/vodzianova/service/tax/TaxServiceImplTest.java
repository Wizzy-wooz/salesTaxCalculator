package com.vodzianova.service.tax;

import com.vodzianova.TestUtilsCommon;
import com.vodzianova.model.Category;
import com.vodzianova.model.Product;
import com.vodzianova.service.validation.ValidationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Collection;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

/**
 * Created by elena on 1/25/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class TaxServiceImplTest {

    @InjectMocks
    private TaxService taxService = new TaxServiceImpl(new BigDecimal("10.00"), new BigDecimal("5.00"), "0.05");

    @Mock
    private ValidationService validationService;

    @Test
    public void roundSalesTaxShouldRoundAmountToTheNearest0_05() {
        assertEquals(new BigDecimal("1.15"), taxService.roundSalesTax(new BigDecimal(1.12), "0.05"));
    }

    @Test
    public void roundSalesTaxShouldRoundAmountToTheNearest0_1() {
        assertEquals(new BigDecimal("1.2"), taxService.roundSalesTax(new BigDecimal(1.12), "0.1"));
    }

    @Test
    public void roundSalesTaxShouldRoundAmountToTheNearest0_005() {
        assertEquals(new BigDecimal("1.125"), taxService.roundSalesTax(new BigDecimal(1.12), "0.005"));
    }

    @Test
    public void calculateTaxForNotImportedNotTaxableProduct() {
        Product product = TestUtilsCommon.createProduct("Book", 1, new BigDecimal(10.00), false, Category.BOOKS);
        assertEquals(new BigDecimal("0.00"), taxService.calculateTax(product));
    }

    @Test
    public void calculateTaxForImportedNotTaxableProduct() {
        Product product = TestUtilsCommon.createProduct("Book", 1, new BigDecimal(10.00), true, Category.BOOKS);
        assertEquals(new BigDecimal("0.50"), taxService.calculateTax(product));
    }

    @Test
    public void calculateTaxForNotImportedTaxableProduct() {
        Product product = TestUtilsCommon.createProduct("Book", 1, new BigDecimal(10.00), false, Category.OTHER_PRODUCTS);
        assertEquals(new BigDecimal("1.00"), taxService.calculateTax(product));
    }

    @Test
    public void calculateTaxForImportedTaxableProduct() {
        Product product = TestUtilsCommon.createProduct("Book", 1, new BigDecimal(10.00), true, Category.OTHER_PRODUCTS);
        assertEquals(new BigDecimal("1.50"), taxService.calculateTax(product));
    }

    @Test
    public void calculateSalesTaxForProductList() {
        Product product1 = TestUtilsCommon.createProduct("Book", 1, new BigDecimal(12.49), false, Category.BOOKS);
        Product product2 = TestUtilsCommon.createProduct("Chocolate Bar", 1, new BigDecimal(0.85), false, Category.FOOD);
        Product product3 = TestUtilsCommon.createProduct("Music CD", 1, new BigDecimal(14.99), false, Category.OTHER_PRODUCTS);
        assertEquals(new BigDecimal("1.50"), taxService.calculateSalesTax(createProductList(product1, product2, product3)).getSalesTax());
    }

    @Test
    public void calculateSalesTaxForProductListWithSeveralItems() {
        Product product1 = TestUtilsCommon.createProduct("Book", 10, new BigDecimal(12.49), false, Category.BOOKS);
        Product product2 = TestUtilsCommon.createProduct("Chocolate Bar", 10, new BigDecimal(0.85), false, Category.FOOD);
        Product product3 = TestUtilsCommon.createProduct("Music CD", 10, new BigDecimal(14.99), false, Category.OTHER_PRODUCTS);
        assertEquals(new BigDecimal("15.00"), taxService.calculateSalesTax(createProductList(product1, product2, product3)).getSalesTax());
    }

    private Collection<Product> createProductList(Product product1, Product product2, Product product3) {
        return asList(product1, product2, product3);
    }
}