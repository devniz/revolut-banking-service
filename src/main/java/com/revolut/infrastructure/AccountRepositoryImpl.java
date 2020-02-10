package com.revolut.infrastructure;

import com.revolut.app.Database;
import com.revolut.domain.Account;
import com.revolut.domain.mapper.AccountMapper;
import com.revolut.domain.AccountRequest;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.Handle;

import java.util.List;
import java.util.Optional;

@Slf4j
public class AccountRepositoryImpl {

    private Database database;

    public AccountRepositoryImpl() {
        this.database = new Database();
        this.database.init();
    }

    public Long create(AccountRequest accountRequest) {
        this.database.getJdbi().useHandle(handle -> handle.execute(
                "INSERT INTO account(id, name, balance) VALUES (?, ?, ?)",
                accountRequest.getId(),
                accountRequest.getName(),
                accountRequest.getBalance()
        ));
        return accountRequest.getId();
    }

    public List<Account> getAllAccount() {
        return this.database.getJdbi().withHandle(handle -> handle.createQuery("SELECT * FROM account")
                .map(new AccountMapper())
                .list());
    }

    public Optional<Account> getAccountById(String id) {
        try (Handle handle = this.database.getJdbi().open()) {
            return handle.createQuery("SELECT * FROM account WHERE id = " + id)
                    .map(new AccountMapper())
                    .findFirst();
        }
    }
}
