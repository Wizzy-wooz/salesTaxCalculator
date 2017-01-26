package com.vodzianova.service.tax;

import com.vodzianova.model.Product;
import com.vodzianova.model.Receipt;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * Created by elena on 1/24/17.
 */
@Service
public interface TaxService {

    Receipt calculateSalesTax(Collection<Product> products);

    BigDecimal calculateTax(Product product);

    BigDecimal roundSalesTax(BigDecimal salesTaxAmount, String roundingScale);
}