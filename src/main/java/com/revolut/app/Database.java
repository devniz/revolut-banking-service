package com.revolut.app;

import lombok.Getter;
import org.jdbi.v3.core.Jdbi;

public class Database {

    @Getter
    private Jdbi jdbi;

    public Database() {
        this.jdbi = Jdbi.create("jdbc:h2:mem:revolut;DB_CLOSE_DELAY=-1");
    }

    public void init() {
        jdbi.withHandle(handle -> handle.execute("CREATE TABLE IF NOT EXISTS account (id LONG PRIMARY KEY, name VARCHAR, balance FLOAT)"));
    }
}
