package com.revolut.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@EqualsAndHashCode
@ToString
public class Transaction {
    private long id;
    private LocalDate timestamp;
    private long fromId;
    private long toId;
    private BigDecimal amount;
}
