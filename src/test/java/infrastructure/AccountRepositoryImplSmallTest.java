package infrastructure;

import com.revolut.domain.Account;
import com.revolut.rest.v1.controller.AccountControllerImpl;
import com.revolut.rest.v1.service.AccountServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountRepositoryImplSmallTest {
    @InjectMocks
    private AccountControllerImpl accountController;

    @Mock
    private AccountServiceImpl accountService;

    @Test
    @DisplayName("Verify create account with success")
    void itShouldCreateAccountSuccessfully() {
        when(accountService.create("Joe")).thenReturn(123L);
        var resp = accountController.create("Joe");

        assertNotNull(resp);
        assertEquals(123L, resp);
    }

    @Test
    @DisplayName("Verify get account is successful")
    void itShouldGetAccountByIdSuccessfully() {
        when(accountService.getAccountById("123")).thenReturn(Optional.of(new Account(123L, "Joe", new BigDecimal(100.00))));
        var resp = accountController.getAccountById("123");

        assertNotNull(resp);
        assertEquals(Optional.of(new Account(123L, "Joe", new BigDecimal(100.00))), resp);
    }

    @Test
    @DisplayName("Verify get all account is successful")
    void itShouldGetAllAccountSuccessfully() {
        List<Account> accountList = new ArrayList<>();
        accountList.add(new Account(123L, "Joe", new BigDecimal(100.00)));
        accountList.add(new Account(123L, "Mark", new BigDecimal(1500.00)));
        accountList.add(new Account(123L, "Sarah", new BigDecimal(600.00)));
        when(accountService.getAllAccount()).thenReturn(accountList);

        var resp = accountController.getAllAccount();

        assertNotNull(resp);
        assertEquals(accountList, resp);
    }
}
