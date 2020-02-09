package com.revolut.domain;

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
    private BigDecimal amount;
    private long from;
    private long to;
}
