package com.revolut.rest.v1.controller;

import com.revolut.domain.TransferRequest;
import com.revolut.rest.v1.service.TransferServiceImpl;

public class TransferControllerImpl implements TransferController {

    private TransferServiceImpl transferService;

    public TransferControllerImpl() {
        this.transferService = new TransferServiceImpl();
    }

    @Override
    public synchronized String transfer(String payload) throws InterruptedException {
        TransferRequest req = TransferRequest.from(payload);

        transferService.transfer(
                req.getFrom(),
                req.getTo(),
                req.getAmount()
        );
        return "transaction is done";
    }
}
