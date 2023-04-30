package com.udacity.pricing.api;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class PricingControllerTest {


    @Autowired
    public MockMvc mockMvc;

    @Test
    public void test_get_price_is_not_found() throws Exception {
        mockMvc.perform(get("/services/price?vehicleId=30")
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertEquals("Cannot find price for Vehicle 30",
                        result.getResolvedException().getCause().getMessage()));
    }

    @Test
    public void test_get_price_is_correct() throws Exception {
        mockMvc.perform(get("/services/price?vehicleId=1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(jsonPath("$.vehicleId").value(1))
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.currency").value("USD"));
    }

    @Test
    public void get_get_price_bad_request() throws Exception {
        mockMvc.perform(get("/services/price"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
