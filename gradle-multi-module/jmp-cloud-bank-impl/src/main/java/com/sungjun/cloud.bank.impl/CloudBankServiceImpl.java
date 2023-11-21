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
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CloudBankServiceImpl implements BankApi {

  private final DeditBankCardRepository deditBankCardRepository;
  private final CreditBankCardRepository creditBankCardRepository;

  @Override
  public BankCard createBankCard(UserEntity user, BankCardType bankCardType) {
    if (BankCardType.DEBIT.equals(bankCardType)) {
      BankCard creditBankCard = getBankCard(user, bankCardType);
      DebitBankCard card = deditBankCardRepository.save((DebitBankCard) creditBankCard);
      return card;
    } else if (BankCardType.CREDIT.equals(bankCardType)) {
      BankCard creditBankCard = getBankCard(user, bankCardType);
      CreditBankCard card = creditBankCardRepository.save((CreditBankCard) creditBankCard);
      return card;
    }
    return null;
  }

  private BankCard getBankCard(UserEntity user, BankCardType bankCardType) {
    BankCard creditBankCard;
    if (BankCardType.CREDIT.equals(bankCardType)) {
      creditBankCard = new CreditBankCard();
    } else {
      creditBankCard = new DebitBankCard();
    }
    creditBankCard.setUser(user);
    creditBankCard.setNumber(generateCardNumber());
    return creditBankCard;
  }


  private String generateCardNumber() {
    Random random = new Random();
    StringBuilder builder = new StringBuilder();
    int groupLength = 4;

    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < groupLength; j++) {
        int randomDigit = random.nextInt(10);
        builder.append(randomDigit);
      }

      if (i < 3) {
        builder.append(" ");
      }
    }

    return builder.toString();
  }
}
