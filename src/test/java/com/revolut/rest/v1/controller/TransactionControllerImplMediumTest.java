package com.revolut.rest.v1.controller;

import com.revolut.app.Application;
import com.revolut.app.Utils;
import com.revolut.domain.AccountRequest;
import com.revolut.infrastructure.AccountRepositoryImpl;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spark.Spark;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.notNullValue;

public class TransactionControllerImplMediumTest {

    @BeforeAll
    static void setUp() {
        RestAssured.port = 4567;
        RestAssured.basePath = "/";
        Application.main(null);
    }

    @AfterAll
    static void tearDown() {
        Spark.stop();
    }

    @Test
    @DisplayName("/POST transfer return HTTP 200")
    void itShouldCallTransferWithSuccess() {
        given().log().all()
                .contentType(JSON)
                .body(buildPayload())
                .when()
                .post("/transfer")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body(notNullValue());
    }

    @Test
    @DisplayName("/POST transfer return 402 because of InsufficientFundException")
    void itShouldTriggerInsufficientException() {
        given().log().all()
                .contentType(JSON)
                .body(buildPayloadForInsufficientFundsException())
                .when()
                .post("/transfer")
                .then().log().all()
                .assertThat().statusCode(402);
    }

    @Test
    @DisplayName("/POST transfer return 401 because of UnauthorisedTransactionException")
    void itShouldTriggerUnauthorisedTransactionException() {
        given().log().all()
                .contentType(JSON)
                .body(buildPayloadForUnauthorisedTransactionException())
                .when()
                .post("/transfer")
                .then().log().all()
                .assertThat().statusCode(401);
    }

    private String buildPayload() {
        AccountRepositoryImpl accountRepository = new AccountRepositoryImpl();
        Long payerId = accountRepository.create(new AccountRequest(Utils.generateUid(), "John", new BigDecimal(100.00)));
        Long receiverId = accountRepository.create(new AccountRequest(Utils.generateUid(), "Max", new BigDecimal(100.00)));
        return "{\n" +
                "\t\"from\":" + payerId + ",\n" +
                "\t\"to\":" + receiverId + ",\n" +
                "\t\"amount\": 10 \n" +
                "}";
    }

    private String buildPayloadForInsufficientFundsException() {
        AccountRepositoryImpl accountRepository = new AccountRepositoryImpl();
        Long payerId = accountRepository.create(new AccountRequest(Utils.generateUid(), "John", new BigDecimal(100.00)));
        Long receiverId = accountRepository.create(new AccountRequest(Utils.generateUid(), "Max", new BigDecimal(100.00)));
        return "{\n" +
                "\t\"from\":" + payerId + ",\n" +
                "\t\"to\":" + receiverId + ",\n" +
                "\t\"amount\": 10000 \n" +
                "}";
    }

    private String buildPayloadForUnauthorisedTransactionException() {
        AccountRepositoryImpl accountRepository = new AccountRepositoryImpl();
        Long payerId = accountRepository.create(new AccountRequest(Utils.generateUid(), "John", new BigDecimal(100.00)));
        return "{\n" +
                "\t\"from\":" + payerId + ",\n" +
                "\t\"to\":" + payerId + ",\n" +
                "\t\"amount\": 10 \n" +
                "}";
    }
}
