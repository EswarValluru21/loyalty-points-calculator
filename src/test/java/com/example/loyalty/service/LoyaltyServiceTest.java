package com.example.loyalty.service;

import com.example.loyalty.dto.CustomerResponse;
import com.example.loyalty.entity.CustomerEntity;
import com.example.loyalty.entity.TransactionEntity;
import com.example.loyalty.repository.CustomerRepository;
import com.example.loyalty.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LoyaltyServiceTest {

    @Mock
    CustomerRepository customerRepository;
    
    @Mock
    TransactionRepository transactionRepository;
    
    @InjectMocks
    private LoyaltyService loyaltyService;

    List<CustomerEntity> customerEntities = new ArrayList<>();
    List<TransactionEntity> transactionEntities = new ArrayList<>();

    @BeforeEach
    void setUp() {

        CustomerEntity customerEntity1 = new CustomerEntity("1","Bruce Banner",0, null);
        CustomerEntity customerEntity2 = new CustomerEntity("2","Stephen Strange",0, null);
        CustomerEntity customerEntity3 = new CustomerEntity("3","Baron Strucker",0, null);

        TransactionEntity transactionEntity1 = new TransactionEntity("1", 20, new Date(2021, Calendar.JANUARY,10), customerEntity1,0);
        TransactionEntity transactionEntity2 = new TransactionEntity("2", 120, new Date(2021, Calendar.JANUARY,25), customerEntity1,0);
        TransactionEntity transactionEntity3 = new TransactionEntity("3", 90, new Date(2021, Calendar.MARCH,1), customerEntity1,0);
        TransactionEntity transactionEntity4 = new TransactionEntity("4", 150, new Date(2021, Calendar.FEBRUARY,5), customerEntity2,0);
        TransactionEntity transactionEntity5 = new TransactionEntity("5", 80, new Date(2021, Calendar.JANUARY,12), customerEntity3,0);

        customerEntity1.setTransactions(Arrays.asList(transactionEntity1, transactionEntity2, transactionEntity3));
        customerEntity2.setTransactions(Collections.singletonList(transactionEntity4));
        customerEntity3.setTransactions(Collections.singletonList(transactionEntity5));

        customerEntities.addAll(Arrays.asList(customerEntity1, customerEntity2, customerEntity3));
        transactionEntities.addAll(Arrays.asList(transactionEntity1, transactionEntity2, transactionEntity3, transactionEntity4, transactionEntity5));

    }

    @Test
    void getIndividualCustomerPoints() {
        when(customerRepository.findAll()).thenReturn(customerEntities);
        when(transactionRepository.findAll()).thenReturn(transactionEntities);
        List<CustomerResponse> customerResponses = loyaltyService.getIndividualCustomerPoints();
        assertThat(customerResponses).isNotNull();
        assertThat(customerResponses.size()).isEqualTo(3);
        assertThat(customerResponses.stream().anyMatch(customerResponse -> customerResponse.getCustomerId().equals("1") && customerResponse.getTotalPoints() == 130)).isTrue();
        assertThat(customerResponses.stream().anyMatch(customerResponse -> customerResponse.getCustomerId().equals("2") && customerResponse.getTotalPoints() == 150)).isTrue();
        assertThat(customerResponses.stream().anyMatch(customerResponse -> customerResponse.getCustomerId().equals("3") && customerResponse.getTotalPoints() == 30)).isTrue();
    }
}