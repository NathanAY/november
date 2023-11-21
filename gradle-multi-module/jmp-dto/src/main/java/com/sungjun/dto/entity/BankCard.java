package com.sungjun.dto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class BankCard {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String number;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
