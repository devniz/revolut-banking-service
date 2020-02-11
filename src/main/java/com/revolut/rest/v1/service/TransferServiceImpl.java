package com.revolut.rest.v1.service;

import com.revolut.domain.Account;
import com.revolut.infrastructure.AccountRepositoryImpl;
import com.revolut.infrastructure.TransferRepositoryImpl;
import com.revolut.infrastructure.exception.InsufficientFundException;
import com.revolut.infrastructure.exception.UnauthorisedTransaction;
import com.revolut.infrastructure.exception.UnknownAccountException;

import java.math.BigDecimal;

public class TransferServiceImpl implements TransferService {

    private AccountRepositoryImpl accountRepository;
    private TransferRepositoryImpl transferRepository;
    private static final String PAYER_ACCCOUNT_TYPE = "PAY";
    private static final String RECEIVER_ACCCOUNT_TYPE = "RECEIVE";

    public TransferServiceImpl() {
        this.accountRepository = new AccountRepositoryImpl();
        this.transferRepository = new TransferRepositoryImpl();
    }

    @Override
    public String transfer(long from, long to, BigDecimal amount) throws UnknownAccountException {
        if (accountRepository.getAccountById(Long.toString(from)).isPresent() && accountRepository.getAccountById(Long.toString(to)).isPresent()) {
            Account payerAccount = accountRepository.getAccountById(Long.toString(from)).get();
            Account receiverAccount = accountRepository.getAccountById(Long.toString(to)).get();

            if (Long.toString(payerAccount.getId()).equals(Long.toString(receiverAccount.getId()))) {
                throw new UnauthorisedTransaction("Can't transfer to your own account.");
            }

            if (amount.compareTo(payerAccount.getBalance()) > 0) {
                throw new InsufficientFundException("Not enough funds to do this transfer.");
            }

            BigDecimal updateBalanceForPayer = payerAccount.getBalance().subtract(amount);
            payerAccount.setBalance(updateBalanceForPayer);

            BigDecimal updateBalanceForReceiver = receiverAccount.getBalance().add(amount);
            receiverAccount.setBalance(updateBalanceForReceiver);

            transferRepository.transfer(payerAccount, PAYER_ACCCOUNT_TYPE);
            transferRepository.transfer(receiverAccount, RECEIVER_ACCCOUNT_TYPE);
        } else {
            throw new UnknownAccountException("Payer or receiver account does not exist");
        }
        return "Transaction success";
    }
}
