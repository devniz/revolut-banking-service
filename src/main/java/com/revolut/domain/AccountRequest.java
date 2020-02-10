package com.revolut.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class AccountRequest {
    private long id;
    private String name;
    private BigDecimal balance;

    public static AccountRequest from(String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, AccountRequest.class);
    }
}
