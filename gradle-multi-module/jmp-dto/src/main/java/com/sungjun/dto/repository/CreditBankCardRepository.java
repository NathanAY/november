package com.sungjun.dto.repository;

import com.sungjun.dto.entity.CreditBankCard;
import com.sungjun.dto.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditBankCardRepository extends JpaRepository<CreditBankCard, Long> {
}
