package infrastructure;

import com.revolut.rest.v1.controller.TransferControllerImpl;
import com.revolut.rest.v1.service.TransferServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransferRepositoryImplSmallTest {

    @InjectMocks
    private TransferControllerImpl transferController;

    @Mock
    private TransferServiceImpl transferService;

    @Test
    @DisplayName("Verify transfer is successful")
    void itShouldTransferSuccessfully() {
        when(transferService.transfer(123L, 234L, new BigDecimal(10.00))).thenReturn("Transfer success");

        var resp = transferController.transfer(addBody(123L, 234L));

        assertNotNull(resp);
        assertEquals("transaction is done", resp);
    }

    private String addBody(Long payerId, Long receiverId) {
        return "{\n" +
                "\t\"from\":" + payerId + ",\n" +
                "\t\"to\":" + receiverId + ",\n" +
                "\t\"amount\": 10\n" +
                "}";
    }
}
