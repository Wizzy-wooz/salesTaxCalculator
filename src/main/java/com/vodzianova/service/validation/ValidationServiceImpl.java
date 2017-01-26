package com.vodzianova.service.validation;

import com.vodzianova.model.Product;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.logging.Logger;

/**
 * Created by elena on 1/24/17.
 */
@Component
public class ValidationServiceImpl implements ValidationService {

    private static Logger logger = Logger.getLogger(ValidationServiceImpl.class.getName());

    @Override
    public void validate(Product product) {

        checkThatProductIsNotNull(product);

        checkThatProductPropertiesAreDefined(product);

        checkThatPriceIsNotNegative(product);

        checkThatProductQuantityIsNotNegative(product);

        checkThatProductDescriptionIsNotEmpty(product);
    }

    private void checkThatProductDescriptionIsNotEmpty(Product product) {
        if (product.getDescription().isEmpty()) {
            logger.warning("Validation failed as product's description is empty.");
            throw new ConstraintViolationException("Can't proceed with calculation of tax as product's description is empty." +
                    "Please check field description in JSON file once again.", Collections.emptySet());
        }
    }

    private void checkThatProductQuantityIsNotNegative(Product product) {
        if (product.getCount() < 0) {
            logger.warning("Validation failed as product's count is negative.");
            throw new ConstraintViolationException("Can't proceed with calculation of tax as product count can't be negative. " +
                    "Please check field count in JSON file once again.", Collections.emptySet());
        }
    }

    private void checkThatPriceIsNotNegative(Product product) {
        if (product.getUnitPrice().compareTo(BigDecimal.ZERO) < 0) {
            logger.warning("Validation failed as product's unit price is negative.");
            throw new ConstraintViolationException("Can't proceed with calculation of tax as product's unit price can't be negative. " +
                    "Please check field unitPrice in JSON file once again.", Collections.emptySet());
        }
    }

    private void checkThatProductPropertiesAreDefined(Product product) {
        if (product.getDescription() == null || product.getUnitPrice() == null || product.getCount() == null || product.isImported() == null) {
            logger.warning("Product properties can't be null");
            throw new ConstraintViolationException("Can't proceed with calculation of tax as product properties are set to null", Collections.emptySet());
        }
    }

    private void checkThatProductIsNotNull(Product product) {
        if (product == null) {
            logger.warning("Product can't be null");
            throw new ConstraintViolationException("Can't proceed with calculation of tax as one of the products is null", Collections.emptySet());
        }
    }
}
