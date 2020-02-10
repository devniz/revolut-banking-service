package com.revolut.rest.v1.controller;

import com.revolut.infrastructure.AccountRepositoryImpl;
import com.revolut.infrastructure.exception.InsufficientFundException;
import com.revolut.infrastructure.exception.UnauthorisedTransaction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerImplSmallTest {

    @Test
    @DisplayName("Verify concurrent transfers with no race condition")
    void itShouldMakeMultipleConcurrentTransfersWithoutAnyRaceConcurrencyIssue() throws InterruptedException, ExecutionException {
        List<Long> accountIds = this.createMultipleAccount();
        Map<String, Long> ids = this.findPayerAndReceiverAccountId(accountIds);
        TransferControllerImpl t = new TransferControllerImpl();

        ExecutorService executor = Executors.newCachedThreadPool();
        CountDownLatch latch = new CountDownLatch(2);

        Callable<Void> transfer = () -> {
            latch.countDown();
            latch.await();
            t.transfer(addBody(ids.get("payerId"), ids.get("receiverId")));
            return null;
        };

        Future<Void> t1 = executor.submit(transfer);
        Future<Void> t2 = executor.submit(transfer);

        t1.get();
        t2.get();
        executor.shutdown();

        AccountRepositoryImpl accountRepository = new AccountRepositoryImpl();
        assertEquals(new BigDecimal(80).setScale(2, RoundingMode.HALF_EVEN), accountRepository.getAccountById(Long.toString(ids.get("payerId"))).get().getBalance().setScale(2, RoundingMode.HALF_EVEN));
        assertEquals(new BigDecimal(120).setScale(2, RoundingMode.HALF_EVEN), accountRepository.getAccountById(Long.toString(ids.get("receiverId"))).get().getBalance().setScale(2, RoundingMode.HALF_EVEN));
    }

    @Test
    @DisplayName("Verify throwing InsufficientFundsException")
    public void itShouldThrowInsufficientFundsExceptionIfTransferAmountIsGreaterThanAccountBalance() throws InterruptedException {
        List<Long> accountIds = this.createMultipleAccount();
        Map<String, Long> ids = this.findPayerAndReceiverAccountId(accountIds);
        TransferControllerImpl t = new TransferControllerImpl();
        assertThrows(InsufficientFundException.class, () -> t.transfer(addInsufficientFundsBody(ids.get("payerId"), ids.get("receiverId"))));
    }

    @Test
    @DisplayName("Verify throwing InsufficientFundsException")
    public void itShouldThrowUnauthorisedTransactionIfTransferTargetSameAccount() throws InterruptedException {
        List<Long> accountIds = this.createMultipleAccount();
        Map<String, Long> ids = this.findPayerAndReceiverAccountId(accountIds);
        TransferControllerImpl t = new TransferControllerImpl();
        assertThrows(UnauthorisedTransaction.class, () -> t.transfer(addUnauthorisedTransaction(ids.get("payerId"), ids.get("receiverId"))));
    }

    private Map<String, Long> findPayerAndReceiverAccountId(List<Long> accountIds) {
        Long payerId = getRandomBankAccount(accountIds);
        Long receiverId = getRandomBankAccount(accountIds);
        Map<String, Long> ids = new HashMap<>();
        ids.put("payerId", payerId);
        ids.put("receiverId", receiverId);
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

    private String addUnauthorisedTransaction(Long payerId, Long receiverId) {
        return "{\n" +
                "\t\"from\":" + payerId + ",\n" +
                "\t\"to\":" + payerId + ",\n" +
                "\t\"amount\": 10\n" +
                "}";
    }

    private List<Long> createMultipleAccount() {
        AccountControllerImpl accountController = new AccountControllerImpl();
        long joe_id = accountController.create("Joe");
        long bob_id = accountController.create("Bob");
        long sarah_id = accountController.create("Sarah");
        long alex_id = accountController.create("Alex");
        long scott_id = accountController.create("Scott");

        List<Long> ids = new ArrayList<>();
        ids.add(joe_id);
        ids.add(bob_id);
        ids.add(sarah_id);
        ids.add(alex_id);
        ids.add(scott_id);
        return ids;
    }

    private Long getRandomBankAccount(List<Long> accounts) {
        Random rand = new Random();
        return accounts.get(rand.nextInt(accounts.size()));
    }
}
