package com.vodzianova.service.validation;

import com.vodzianova.TestUtilsCommon;
import com.vodzianova.model.Category;
import com.vodzianova.model.Product;
import org.junit.Before;
import org.junit.Test;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;

/**
 * Created by elena on 1/25/17.
 */
public class ValidationServiceImplTest {

    private ValidationService validationService;

    @Before
    public void setUp() {
        validationService = new ValidationServiceImpl();
    }

    @Test
    public void validateProductShouldNotThrowExceptionIfProductIsValid() {
        validationService.validate(TestUtilsCommon.createProduct("Book", 1, new BigDecimal("12.49"), false, Category.BOOKS));
    }

    @Test(expected = ConstraintViolationException.class)
    public void validateProductShouldThrowExceptionIfProductWasNotSet() {
        validationService.validate(null);
    }

    @Test(expected = ConstraintViolationException.class)
    public void validateProductShouldThrowExceptionIfProductDescriptionIsNull() {
        validationService.validate(TestUtilsCommon.createProduct(null, 1, new BigDecimal("12.49"), false, Category.BOOKS));
    }

    @Test(expected = ConstraintViolationException.class)
    public void validateProductShouldThrowExceptionIfProductCountIsNull() {
        validationService.validate(createEmptyProduct());
    }

    @Test(expected = ConstraintViolationException.class)
    public void validateProductShouldThrowExceptionIfProductCountIsNegative() {
        validationService.validate(TestUtilsCommon.createProduct("Book", -1, new BigDecimal("12.49"), false, Category.BOOKS));
    }

    @Test(expected = ConstraintViolationException.class)
    public void validateProductShouldThrowExceptionIfProductUnitPriceIsNull() {
        validationService.validate(createEmptyProduct());
    }

    @Test(expected = ConstraintViolationException.class)
    public void validateProductShouldThrowExceptionIfProductUnitPriceIsNegative() {
        validationService.validate(TestUtilsCommon.createProduct("Book", 1, new BigDecimal("-12.49"), false, Category.BOOKS));
    }

    @Test(expected = ConstraintViolationException.class)
    public void validateProductShouldThrowExceptionIfProductImportedIsNull() {
        validationService.validate(createEmptyProduct());
    }

    @Test(expected = ConstraintViolationException.class)
    public void validateProductShouldThrowExceptionIfProductDescriptionIsEmpty() {
        validationService.validate(TestUtilsCommon.createProduct("", 1, new BigDecimal("12.49"), false, Category.BOOKS));
    }

    private Product createEmptyProduct() {
        return new Product();
    }
}