package infrastructure;

import com.revolut.rest.v1.controller.AccountControllerImpl;
import com.revolut.rest.v1.service.AccountServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


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
}
