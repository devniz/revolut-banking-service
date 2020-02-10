package com.revolut.rest.v1.controller;

import com.revolut.domain.TransferRequest;
import com.revolut.infrastructure.exception.UnknownAccountException;
import com.revolut.rest.v1.service.TransferServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TransferControllerImpl implements TransferController {

    private TransferServiceImpl transferService;

    public TransferControllerImpl() {
        this.transferService = new TransferServiceImpl();
    }

    @Override
    public synchronized String transfer(String payload) {
        TransferRequest req = TransferRequest.from(payload);

        try {
            transferService.transfer(
                    req.getFrom(),
                    req.getTo(),
                    req.getAmount()
            );
        } catch (UnknownAccountException uae) {
            log.error("Payer or receiver account does not exist");
            throw new UnknownAccountException("Payer or receiver account does not exist");
        }
        return "transaction is done";
    }
}
