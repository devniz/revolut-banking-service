package com.revolut.infrastructure;

import com.revolut.app.Database;
import com.revolut.domain.Account;
import lombok.extern.slf4j.Slf4j;
import org.jdbi.v3.core.transaction.SerializableTransactionRunner;
import org.jdbi.v3.core.transaction.TransactionIsolationLevel;

@Slf4j
public class TransferRepositoryImpl {

    private Database database;

    public TransferRepositoryImpl() {
        this.database = new Database();
        this.database.getJdbi().setTransactionHandler(new SerializableTransactionRunner());
    }

    public void transfer(Account account, String type) {
        this.database.getJdbi().inTransaction(TransactionIsolationLevel.SERIALIZABLE, h -> h.execute("UPDATE account SET balance = ? WHERE id = ?", account.getBalance(), account.getId()));
        log.info(type + ": " + account.getName() + " your balance is now " + account.getBalance());
    }
}

