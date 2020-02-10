package com.revolut.domain;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class TransferRequest {
    private long from;
    private long to;
    private BigDecimal amount;

    public static TransferRequest from(String json) {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(json, TransferRequest.class);
    }
}
