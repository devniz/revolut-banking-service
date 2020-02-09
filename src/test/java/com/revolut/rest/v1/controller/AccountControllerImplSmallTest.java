package com.revolut.rest.v1.controller;

import com.revolut.domain.Account;
import com.revolut.rest.v1.service.AccountServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountControllerImplSmallTest {

    @InjectMocks
    private AccountControllerImpl accountController;

    @Mock
    private AccountServiceImpl accountService;

    @Test
    @DisplayName("Verify creation of a new bank account")
    void itShouldCreateNewAccountAndReturnLongId() {
        when(accountService.create("joe")).thenReturn(131342343L);

        var resp = accountController.create("joe");

        verify(accountService).create("joe");
        assertNotNull(resp);
        assertEquals(131342343L, resp);
    }

    @Test
    @DisplayName("Verify get all accounts")
    void itShouldGetAllCreatedAccounts() {
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account(123, "joe", new BigDecimal(100.00)));

        when(accountService.getAllAccount()).thenReturn(accountList);

        var resp = accountController.getAllAccount();

        verify(accountService).getAllAccount();
        assertNotNull(resp);
        assertEquals(1, resp.size());
        assertEquals(123, resp.get(0).getId());
        assertEquals("joe", resp.get(0).getName());
        assertEquals(new BigDecimal(100.00), resp.get(0).getBalance());
    }

    @Test
    @DisplayName("Verify get account by ID")
    void itShouldGetAccountGivenValidId() {
        when(accountService.getAccountById("123")).thenReturn(Optional.of(new Account(123, "joe", new BigDecimal(100.00))));

        var resp = accountController.getAccountById("123");

        verify(accountService).getAccountById("123");
        assertNotNull(resp);
        assertEquals(123, resp.get().getId());
        assertEquals("joe", resp.get().getName());
        assertEquals(new BigDecimal(100.00), resp.get().getBalance());
    }

}
