package com.revolut.rest.v1.controller;

import com.revolut.infrastructure.exception.UnknownAccountException;

public interface TransferController {
    String transfer(String payload) throws UnknownAccountException;
}
