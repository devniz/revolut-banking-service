package com.revolut.app;

import com.revolut.rest.v1.controller.AccountControllerImpl;
import com.revolut.rest.v1.controller.TransferControllerImpl;

import static com.revolut.app.Utils.json;
import static spark.Spark.after;
import static spark.Spark.post;
import static spark.Spark.get;

public class Application {
    public static void main(String[] args) {
        AccountControllerImpl accountControllerImpl = new AccountControllerImpl();
        TransferControllerImpl transferControllerImpl = new TransferControllerImpl();
        post("/account", (req, res) -> accountControllerImpl.create(req.body()), json());
        get("/account/:id", (req, res) -> accountControllerImpl.getAccountById(req.params(":id")), json());
        get("/account", (req, res) -> accountControllerImpl.getAllAccount(), json());
        post("/transfer", (req, res) -> transferControllerImpl.transfer(req.body()), json());
        after((req, res) -> {res.type("application/json");});
    }

}
