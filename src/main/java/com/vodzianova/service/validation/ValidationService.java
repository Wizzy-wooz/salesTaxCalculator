package com.vodzianova.service.validation;

import com.vodzianova.model.Product;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * Created by elena on 1/24/17.
 */
@Validated
@Service
public interface ValidationService {

    void validate(@Valid Product product);
}
