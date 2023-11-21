package com.sungjun.dto.repository;

import com.sungjun.dto.entity.CreditBankCard;
import com.sungjun.dto.entity.DebitBankCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeditBankCardRepository extends JpaRepository<DebitBankCard, Long> {
}
