package com.revolut.rest.v1.service;

import java.math.BigDecimal;

public interface TransferService {
    void transfer(long from, long to, BigDecimal amount) throws InterruptedException;
}
