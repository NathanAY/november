package com.sungjun.cloud.bank.impl;

import com.sungjun.bank.api.BankApi;
import com.sungjun.dto.entity.BankCard;
import com.sungjun.dto.entity.BankCardType;
import com.sungjun.dto.entity.CreditBankCard;
import com.sungjun.dto.entity.DebitBankCard;
import com.sungjun.dto.entity.UserEntity;
import com.sungjun.dto.repository.CreditBankCardRepository;
import com.sungjun.dto.repository.DeditBankCardRepository;
import java.util.Random;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class CloudBankServiceImpl implements BankApi {

  private DeditBankCardRepository deditBankCardRepository;
  private CreditBankCardRepository creditBankCardRepository;

  @Override
  public BankCard createBankCard(UserEntity user, BankCardType bankCardType) {
    switch (bankCardType) {
      case DEBIT -> {
        var creditBankCard = getBankCard(user, bankCardType);
        var savedCard = deditBankCardRepository.save((DebitBankCard) creditBankCard);
        return savedCard;
      }
      case CREDIT -> {
        var creditBankCard = getBankCard(user, bankCardType);
        var savedCard = creditBankCardRepository.save((CreditBankCard) creditBankCard);
        return savedCard;
      }
    }
    return null;
  }

  private BankCard getBankCard(UserEntity user, BankCardType bankCardType) {
    BankCard creditBankCard = switch (bankCardType) {
      case CREDIT -> new CreditBankCard();
      case DEBIT -> new DebitBankCard();
    };
    creditBankCard.setUser(user);
    creditBankCard.setNumber(generateCardNumber());
    return creditBankCard;
  }


  private String generateCardNumber() {
    Random random = new Random();
    var cardNumber = new StringBuilder();
    int groupLength = 4;

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < groupLength; j++) {
        int randomDigit = random.nextInt(10);
        cardNumber.append(randomDigit);
      }

      if (i < 3) {
        cardNumber.append(" ");
      }
    }

    return cardNumber.toString();
  }
}
