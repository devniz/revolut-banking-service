package com.revolut.rest.v1.controller;

import com.revolut.domain.Account;
import com.revolut.rest.v1.service.AccountServiceImpl;

import java.util.List;
import java.util.Optional;

public class AccountControllerImpl implements AccountController {

    private AccountServiceImpl accountServiceImpl;

    public AccountControllerImpl() {
        this.accountServiceImpl = new AccountServiceImpl();
    }

    public Long create(String name) {
        return accountServiceImpl.create(name);
    }

    public List<Account> getAllAccount() {
        return accountServiceImpl.getAllAccount();
    }

    public Optional<Account> getAccountById(String id) {
        return accountServiceImpl.getAccountById(id);
    }
}
