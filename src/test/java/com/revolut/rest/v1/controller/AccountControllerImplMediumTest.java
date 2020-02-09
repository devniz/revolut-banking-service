package com.revolut.rest.v1.controller;

import com.revolut.app.Application;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import spark.Spark;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.notNullValue;

public class AccountControllerImplMediumTest {

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
    @DisplayName("/POST account return HTTP 200")
    void itShouldReturn200WhenCreatingNewAccount() {
        given().log().all()
                .contentType(JSON)
                .body(addBody())
                .when()
                .post("/account")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body(notNullValue());
    }

    @Test
    @DisplayName("/GET all account return HTTP 200")
    void itShouldReturn200WhenGettingAllAccounts() {
        given().log().all()
                .contentType(JSON)
                .when()
                .get("/account")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body(notNullValue());
    }

    @Test
    @DisplayName("/GET account by ID return HTTP 200")
    void itShouldReturn200WhenGettingAccountById() {
        given().log().all()
                .contentType(JSON)
                .when()
                .get("/account/123")
                .then().log().all()
                .assertThat().statusCode(200)
                .assertThat().body(notNullValue());
    }

    private String addBody() {
        return "{\n" +
                "\t\"name\": \"Joe\"\n" +
                "}";
    }
}
