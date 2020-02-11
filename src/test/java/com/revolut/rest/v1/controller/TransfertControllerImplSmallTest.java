package com.revolut.rest.v1.controller;

import com.revolut.infrastructure.AccountRepositoryImpl;
import com.revolut.infrastructure.exception.InsufficientFundException;
import com.revolut.infrastructure.exception.UnauthorisedTransaction;
import com.revolut.infrastructure.exception.UnknownAccountException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TransfertControllerImplSmallTest {

    @Test
    @DisplayName("Verify concurrent transfers with no race condition")
    void itShouldMakeMultipleConcurrentTransfersSuccessfully() throws InterruptedException, ExecutionException {
        List<Long> accountIds = this.createMultipleAccount();
        TransferControllerImpl t = new TransferControllerImpl();

        ExecutorService executor = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(2);

        Callable<Void> transfer = () -> {
            latch.countDown();
            latch.await();
            t.transfer(addBody(accountIds.get(0), accountIds.get(1)));
            return null;
        };

        Future<Void> t1 = executor.submit(transfer);
        Future<Void> t2 = executor.submit(transfer);

        t1.get();
        t2.get();
        executor.shutdown();

        AccountRepositoryImpl accountRepository = new AccountRepositoryImpl();
        assertEquals(new BigDecimal(80).setScale(2, RoundingMode.HALF_EVEN), accountRepository.getAccountById(Long.toString(accountIds.get(0))).get().getBalance().setScale(2, RoundingMode.HALF_EVEN));
        assertEquals(new BigDecimal(120).setScale(2, RoundingMode.HALF_EVEN), accountRepository.getAccountById(Long.toString(accountIds.get(1))).get().getBalance().setScale(2, RoundingMode.HALF_EVEN));
    }

    @Test
    @DisplayName("Verify throwing InsufficientFundsException")
    public void itShouldThrowInsufficientFundsExceptionIfTransferAmountIsGreaterThanAccountBalance() {
        List<Long> accountIds = this.createMultipleAccount();
        TransferControllerImpl t = new TransferControllerImpl();
        assertThrows(InsufficientFundException.class, () -> t.transfer(addInsufficientFundsBody(accountIds.get(0), accountIds.get(1))));
    }

    @Test
    @DisplayName("Verify throwing InsufficientFundsException")
    public void itShouldThrowUnauthorisedTransactionExceptionIfTransferTargetSameAccount() {
        List<Long> accountIds = this.createMultipleAccount();
        TransferControllerImpl t = new TransferControllerImpl();
        assertThrows(UnauthorisedTransaction.class, () -> t.transfer(addUnauthorisedTransactionBody(accountIds.get(0), accountIds.get(1))));
    }

    @Test
    @DisplayName("Verify throwing UnknowsAccountException")
    public void itShouldThrowUnknownAccountExceptionIfPayerOrReceiverIdDoesNotexist() {
        TransferControllerImpl t = new TransferControllerImpl();
        assertThrows(UnknownAccountException.class, () -> t.transfer(addUnknownAccountBody()));
    }

    private List<Long> createMultipleAccount() {
        AccountControllerImpl accountController = new AccountControllerImpl();
        long joe_id = accountController.create("Joe");
        long bob_id = accountController.create("Bob");

        List<Long> ids = new ArrayList<>();
        ids.add(joe_id);
        ids.add(bob_id);
        return ids;
    }

    private String addBody(Long payerId, Long receiverId) {
        return "{\n" +
                "\t\"from\":" + payerId + ",\n" +
                "\t\"to\":" + receiverId + ",\n" +
                "\t\"amount\": 10\n" +
                "}";
    }

    private String addInsufficientFundsBody(Long payerId, Long receiverId) {
        return "{\n" +
                "\t\"from\":" + payerId + ",\n" +
                "\t\"to\":" + receiverId + ",\n" +
                "\t\"amount\": 10000\n" +
                "}";
    }

    private String addUnauthorisedTransactionBody(Long payerId, Long receiverId) {
        return "{\n" +
                "\t\"from\":" + payerId + ",\n" +
                "\t\"to\":" + payerId + ",\n" +
                "\t\"amount\": 10\n" +
                "}";
    }

    private String addUnknownAccountBody() {
        return "{\n" +
                "\t\"from\": 423423434 ,\n" +
                "\t\"to\": 234234123424 ,\n" +
                "\t\"amount\": 10\n" +
                "}";
    }
}
