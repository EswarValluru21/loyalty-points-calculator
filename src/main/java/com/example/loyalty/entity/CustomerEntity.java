package com.example.loyalty.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "CUSTOMER")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CustomerEntity {

    @Id
    @Column(name = "CUSTOMER_ID")
    private String id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "TOTAL_POINTS")
    private int loyaltyPoints;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<TransactionEntity> transactions;

}
