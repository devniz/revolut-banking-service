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
public class Transfer {
    private long id;
    private LocalDate timestamp;
    private Account from;
    private Account to;
    private BigDecimal amount;
}
