package com.revolut.app;

import com.revolut.rest.v1.controller.AccountControllerImpl;

import static spark.Spark.post;
import static spark.Spark.get;

public class Application {
    public static void main(String[] args) {
        AccountControllerImpl accountControllerImpl = new AccountControllerImpl();
        post("/account", (req, res) -> accountControllerImpl.create(req.body()));
        get("/account/:id", (req, res) -> accountControllerImpl.getAccountById(req.params(":id")));
        get("/account", (req, res) -> accountControllerImpl.getAllAccount());
    }
}
