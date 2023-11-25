import com.sungjun.bank.api.BankApi;
import com.sungjun.cloud.bank.impl.CloudBankServiceImpl;

module jmp.bank.impl {
  requires static lombok;
  requires jmp.bank.api;
  requires jmp.dto;
  requires spring.context;
  provides BankApi with CloudBankServiceImpl;
}