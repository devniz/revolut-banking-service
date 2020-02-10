package com.revolut.rest.v1.controller;

public interface TransferController {
    String transfer(String payload) throws InterruptedException;
}
