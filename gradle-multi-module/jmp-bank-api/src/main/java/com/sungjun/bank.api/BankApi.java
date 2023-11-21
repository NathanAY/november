package com.sungjun.bank.api;

import com.sungjun.dto.entity.BankCard;
import com.sungjun.dto.entity.BankCardType;
import com.sungjun.dto.entity.UserEntity;

public interface BankApi {

  BankCard createBankCard(UserEntity user, BankCardType bankCardType);

}
