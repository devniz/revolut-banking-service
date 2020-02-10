package com.revolut.domain;

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
}
