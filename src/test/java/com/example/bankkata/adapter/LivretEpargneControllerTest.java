package com.example.bankkata.adapter;

import com.example.bankkata.domain.adapter.LivretEpargneController;
import com.example.bankkata.domain.service.LivretEpargne.LivretEpargneService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LivretEpargneControllerTest {

    private MockMvc mockMvc;

    @Mock
    private LivretEpargneService livretEpargneService;

    @InjectMocks
    private LivretEpargneController livretEpargneController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(livretEpargneController).build();
    }

    @Test
    void testDeposit() throws Exception {
        String accountNumber = "1";
        double amount = 100.0;

        mockMvc.perform(post("/livret-epargne/{accountNumber}/deposit", accountNumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.valueOf(amount)))
                .andExpect(status().isOk());

        verify(livretEpargneService).deposit(accountNumber, amount);
    }

    @Test
    void testGetBalance() throws Exception {
        String accountNumber = "1";
        double expectedBalance = 100.0;

        when(livretEpargneService.getBalance(accountNumber)).thenReturn(expectedBalance);

        mockMvc.perform(get("/livret-epargne/{accountNumber}/balance", accountNumber)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(expectedBalance)));

        verify(livretEpargneService).getBalance(accountNumber);
    }
}
