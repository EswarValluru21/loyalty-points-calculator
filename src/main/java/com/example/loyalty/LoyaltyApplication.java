package com.example.loyalty;

import com.example.loyalty.entity.CustomerEntity;
import com.example.loyalty.entity.TransactionEntity;
import com.example.loyalty.repository.CustomerRepository;
import com.example.loyalty.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Calendar;
import java.util.Date;

@SpringBootApplication
@RequiredArgsConstructor
public class LoyaltyApplication {

    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public static void main(String[] args) {
        SpringApplication.run(LoyaltyApplication.class, args);
    }

    @Bean
    public void loadData() {
        CustomerEntity customerEntity1 = new CustomerEntity("1", "Bruce Banner", 0, null);
        CustomerEntity customerEntity2 = new CustomerEntity("2", "Stephen Strange", 0, null);
        CustomerEntity customerEntity3 = new CustomerEntity("3", "Baron Strucker", 0, null);
        customerRepository.save(customerEntity1);
        customerRepository.save(customerEntity2);
        customerRepository.save(customerEntity3);

        transactionRepository.save(new TransactionEntity("1", 20, new Date(2021, Calendar.JANUARY, 10), customerEntity1, 0));
        transactionRepository.save(new TransactionEntity("2", 120, new Date(2021, Calendar.JANUARY, 25), customerEntity1, 0));
        transactionRepository.save(new TransactionEntity("3", 90, new Date(2021, Calendar.MARCH, 1), customerEntity1, 0));
        transactionRepository.save(new TransactionEntity("4", 150, new Date(2021, Calendar.FEBRUARY, 5), customerEntity2, 0));
        transactionRepository.save(new TransactionEntity("5", 80, new Date(2021, Calendar.JANUARY, 12), customerEntity3, 0));

    }

}
