package com.sungjun.dto.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String name;


    @Column
    private String surname;

    @Column
    private LocalDate birthday;

    @OneToMany(mappedBy = "user")
    private List<DebitBankCard> debitCards;

    @OneToMany(mappedBy = "user")
    private List<CreditBankCard> creditCards;
}
