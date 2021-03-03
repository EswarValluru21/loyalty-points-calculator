package com.example.loyalty.service;

import com.example.loyalty.dto.CustomerResponse;
import com.example.loyalty.entity.CustomerEntity;
import com.example.loyalty.entity.TransactionEntity;
import com.example.loyalty.repository.CustomerRepository;
import com.example.loyalty.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class LoyaltyService {

    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    Map<String, Map<String, Integer>> customerMonthlyPoints = new HashMap<>();

    Map<String, Integer> customerTotalPoints = new HashMap<>();

    public List<CustomerResponse> getIndividualCustomerPoints() {

        List<TransactionEntity> transactions = (List<TransactionEntity>) transactionRepository.findAll();
        calculatePointsForAllTransactions(transactions);
        calculateMonthlyPointsForEachCustomer();

        return customerMonthlyPoints.keySet().stream().map(customerId -> CustomerResponse.builder()
                .monthlyPointsMap(customerMonthlyPoints.get(customerId))
                .totalPoints(customerTotalPoints.get(customerId))
                .customerId(customerId)
                .build()).collect(Collectors.toList());
    }

    private void calculateMonthlyPointsForEachCustomer() {
        List<CustomerEntity> customers = (List<CustomerEntity>) customerRepository.findAll();
        for (CustomerEntity customerEntity : customers) {
            if (!customerMonthlyPoints.containsKey(customerEntity.getId())) {
                customerMonthlyPoints.put(customerEntity.getId(), new HashMap<>());
            }
            Map<String, Integer> monthlyPointsMap = customerMonthlyPoints.get(customerEntity.getId());
            int totalPoints = 0;
            for (TransactionEntity transaction : customerEntity.getTransactions()) {
                Date transactionDate = transaction.getDate();
                LocalDate localDate = transactionDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                String monthAndYear = localDate.getMonth() + "-" + localDate.getYear();
                totalPoints += applyPointsToCustomer(monthAndYear, transaction, monthlyPointsMap);
            }
            customerEntity.setLoyaltyPoints(totalPoints);
            customerRepository.save(customerEntity);
            customerTotalPoints.put(customerEntity.getId(), totalPoints);
        }
    }

    private int applyPointsToCustomer(String monthAndYear, TransactionEntity transaction, Map<String, Integer> monthlyPointsMap) {
        if (monthlyPointsMap.containsKey(monthAndYear)) {
            monthlyPointsMap.put(monthAndYear, monthlyPointsMap.get(monthAndYear) + transaction.getPoints());
        } else {
            monthlyPointsMap.put(monthAndYear, transaction.getPoints());
        }
        return transaction.getPoints();
    }

    private void calculatePointsForAllTransactions(List<TransactionEntity> transactions) {
        for (TransactionEntity transaction : transactions) {
            transaction.setPoints(calculatePoints(transaction));
            transactionRepository.save(transaction);
        }
    }

    private int calculatePoints(TransactionEntity transaction) {
        int amount = transaction.getAmount();
        if (amount > 100) {
            return (amount - 100) * 2 + 50;
        } else if (amount > 50) {
            return amount - 50;
        } else {
            return 0;
        }
    }


}
