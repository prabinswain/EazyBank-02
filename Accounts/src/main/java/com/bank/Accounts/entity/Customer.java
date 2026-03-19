package com.bank.Accounts.entity;

import jakarta.persistence.*;
import lombok.*;



@Entity
@Table(name = "customer")
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Customer extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
//    @GenericGenerator(name = "native", strategy = "native")
    private Long customerId;
    @Column(name = "name")
    private String name;
    @Column(name = "email")
    private String email;
    @Column(name = "mobile_number")
    private String mobileNumber;


}
