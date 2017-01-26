package com.vodzianova.controller;

import com.vodzianova.model.Product;
import com.vodzianova.model.Receipt;
import com.vodzianova.service.tax.TaxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.logging.Logger;

/**
 * Created by elena on 1/23/17.
 */
@RestController
public class TaxCalculatorController {

    private static Logger logger = Logger.getLogger(TaxCalculatorController.class.getName());
    private static final String MEDIA_TYPE = "application/json";

    @Autowired
    private TaxService taxService;

    @RequestMapping(value = {"/taxcalculator"}, method = {RequestMethod.POST},
            consumes = {MEDIA_TYPE}, produces = {MEDIA_TYPE})
    public Receipt calculateTax(@RequestBody Collection<Product> products) {
        logger.info("Sales Tax calculation started.");
        return taxService.calculateSalesTax(products);
    }
}
