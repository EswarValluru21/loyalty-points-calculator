package com.example.loyalty.controller;

import com.example.loyalty.dto.CustomerResponse;
import com.example.loyalty.service.LoyaltyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class LoyaltyController {

    private final LoyaltyService loyaltyService;

    @GetMapping(value = "/points")
    public ResponseEntity<List<CustomerResponse>> getPointsDetails() {
        log.info("Customer points being calculated.");
        return ResponseEntity.ok().body(loyaltyService.getIndividualCustomerPoints());
    }

}
