package com.revolut.rest.v1.controller;

import com.revolut.domain.Account;
import com.revolut.rest.v1.service.AccountServiceImpl;

import java.util.List;
import java.util.Optional;

public class AccountControllerImpl implements AccountController {

    private AccountServiceImpl accountService;

    public AccountControllerImpl() {
        this.accountService = new AccountServiceImpl();
    }

    public Long create(String name) {
        return accountService.create(name);
    }

    public List<Account> getAllAccount() {
        return accountService.getAllAccount();
    }

    public Optional<Account> getAccountById(String id) {
        return accountService.getAccountById(id);
    }
}
