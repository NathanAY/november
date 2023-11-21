package com.sungjun.dto.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.Data;

@Entity
@Data
public class Subscription {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String bankcard;

    @Column
    private LocalDate startDate;

}
