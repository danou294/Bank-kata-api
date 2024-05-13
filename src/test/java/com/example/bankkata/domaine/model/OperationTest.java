package com.example.bankkata.domaine.model;

import com.example.bankkata.domain.model.Operation;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class OperationTest {

    @Test
    public void testOperationCreation() {
        LocalDateTime now = LocalDateTime.now();
        Operation operation = new Operation(now, "DEPOSIT", 100.0, 200.0);

        assertNotNull(operation);
        assertEquals(now, operation.getDate());
        assertEquals("DEPOSIT", operation.getType());
        assertEquals(100.0, operation.getAmount(), 0.01);
        assertEquals(200.0, operation.getBalanceAfterOperation(), 0.01);
    }

    @Test
    public void testOperationCreationWithWithdraw() {
        LocalDateTime now = LocalDateTime.now();
        Operation operation = new Operation(now, "WITHDRAW", 50.0, 150.0);

        assertNotNull(operation);
        assertEquals(now, operation.getDate());
        assertEquals("WITHDRAW", operation.getType());
        assertEquals(50.0, operation.getAmount(), 0.01);
        assertEquals(150.0, operation.getBalanceAfterOperation(), 0.01);
    }
}
