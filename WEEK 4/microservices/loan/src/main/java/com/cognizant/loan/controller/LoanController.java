package com.cognizant.loan.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class LoanController {

    private static final Logger logger = LoggerFactory.getLogger(LoanController.class);

    @GetMapping("/loans/{number}")
    public Map<String, Object> getLoan(@PathVariable String number) {
        logger.info("Start getLoan");

        Map<String, Object> loan = new HashMap<>();
        loan.put("number", number);
        loan.put("type", "car");
        loan.put("loan", 400000);
        loan.put("emi", 3258);
        loan.put("tenure", 18);

        logger.info("End getLoan");
        return loan;
    }

}
