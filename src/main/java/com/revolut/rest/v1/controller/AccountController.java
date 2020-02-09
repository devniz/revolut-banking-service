package com.revolut.rest.v1.controller;

import com.revolut.domain.Account;

import java.util.List;
import java.util.Optional;

public interface AccountController {
    Long create(String name);
    List<Account> getAllAccount();
    Optional<Account> getAccountById(String id);
}
