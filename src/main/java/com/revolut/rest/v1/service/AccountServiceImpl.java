package com.revolut.rest.v1.service;

import com.revolut.app.Utils;
import com.revolut.domain.Account;
import com.revolut.domain.AccountRequest;
import com.revolut.infrastructure.AccountRepositoryImpl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class AccountServiceImpl implements AccountService {

    private AccountRepositoryImpl accountRepository;

    public AccountServiceImpl() {
        this.accountRepository = new AccountRepositoryImpl();
    }

    public Long create(String name) {
        AccountRequest accountRequest = new AccountRequest(Utils.generateUid(), name, new BigDecimal(100.00));
        return accountRepository.create(accountRequest);
    }

    public List<Account> getAllAccount() {
        return accountRepository.getAllAccount();
    }

    public Optional<Account> getAccountById(String id) {
        return accountRepository.getAccountById(id);
    }
}
