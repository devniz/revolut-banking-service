package com.revolut.di;

import com.google.inject.AbstractModule;
import com.revolut.rest.v1.controller.AccountControllerImpl;
import org.jdbi.v3.core.Jdbi;

public class AppModule extends AbstractModule {
    protected void configure() {
        bind(AccountControllerImpl.class);
        bind(Jdbi.class).toInstance(Jdbi.create("jdbc:h2:mem:revolut;DB_CLOSE_DELAY=-1"));
    }
}
