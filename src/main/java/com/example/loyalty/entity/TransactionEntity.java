package com.example.loyalty.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "TRANSACTION")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionEntity {

    @Id
    @Column(name = "TRANSACTION_ID")
    private String id;

    @Column(name = "AMOUNT")
    private int amount;

    @Column(name = "DATE")
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID", nullable = false)
    private CustomerEntity customer;

    @Column(name = "POINTS")
    private int points;


}
