package com.revolut.app;

import com.revolut.rest.v1.controller.AccountControllerImpl;
import com.revolut.rest.v1.controller.TransferControllerImpl;

public class Application {
    public static void main(String[] args) {
        Routes routes = new Routes();
        AccountControllerImpl accountControllerImpl = new AccountControllerImpl();
        TransferControllerImpl transferControllerImpl = new TransferControllerImpl();
        routes.handle(accountControllerImpl, transferControllerImpl);
    }
}
