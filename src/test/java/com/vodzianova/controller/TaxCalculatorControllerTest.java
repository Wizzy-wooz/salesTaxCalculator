package com.vodzianova.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.vodzianova.exceptionHandler.RestExceptionHandler;
import com.vodzianova.model.Category;
import com.vodzianova.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by elena on 1/24/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TaxCalculatorControllerTest {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    private MockMvc mockMvc;
    private List<Product> productList;

    @Autowired
    private TaxCalculatorController taxCalculatorController;

    @Before
    public void setUp() {
        this.mockMvc = standaloneSetup(taxCalculatorController)
                .setControllerAdvice(new RestExceptionHandler())
                .setMessageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }

    @Test
    public void calculateTaxRestCallShouldBeSuccessful() throws Exception {
        productList = new ArrayList<>();
        productList.add(new Product("Book", 1, new BigDecimal(12.49), false, Category.BOOKS));
        productList.add(new Product("Chocolate Bar", 1, new BigDecimal(0.85), false, Category.FOOD));
        productList.add(new Product("Music CD", 1, new BigDecimal(14.99), false, Category.OTHER_PRODUCTS));

        String requestJson = prepareRequest();

        MvcResult result = mockMvc.perform(post("/taxcalculator").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson).accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals("{\"salesTax\":1.50}", result.getResponse().getContentAsString());

    }

    @Test
    public void calculateTaxRestCallShouldFailIfProductIsInvalid() throws Exception {
        createProductList(new Product("Book", -1, new BigDecimal(-12.49), false, Category.BOOKS));
        MvcResult result = sendRequest();

        assertEquals("{\"code\":400,\"message\":\"Can't proceed with calculation of tax as product's unit price can't be negative. Please check field unitPrice in JSON file once again.\"}", result.getResponse().getContentAsString());
    }

    @Test
    public void calculateTaxRestCallShouldFailIfProductIsEmpty() throws Exception {
        createProductList(new Product());
        sendRequest();
    }

    @Test(expected = IllegalArgumentException.class)
    public void calculateTaxRestCallShouldFailIfProductCategoryIsMissing() throws Exception {
        createProductList(new Product("Book", 1, new BigDecimal(12.49), false, Category.valueOf("UNKNOWN")));
        sendRequest();
    }

    private void createProductList(Product product) {
        productList = new ArrayList<>();
        productList.add(product);
    }

    private MvcResult sendRequest() throws Exception {
        String requestJson = prepareRequest();

        return mockMvc.perform(post("/taxcalculator").contentType(APPLICATION_JSON_UTF8)
                .content(requestJson).accept(APPLICATION_JSON_UTF8))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    private String prepareRequest() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(productList);
    }
}