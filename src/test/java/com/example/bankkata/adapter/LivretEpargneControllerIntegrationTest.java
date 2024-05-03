package com.example.bankkata.adapter;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class LivretEpargneControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void createLivretEpargne_ShouldReturnCreatedLivretEpargne() throws Exception {
        double plafondDepot = 1000.0;
        mockMvc.perform(MockMvcRequestBuilders.post("/api/livrets-epargne/create")
                .contentType(MediaType.APPLICATION_JSON)
                .param("plafondDepot", String.valueOf(plafondDepot)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.plafondDepot").value(plafondDepot))
                .andDo(print());
    }

    @Test
    void getLivretEpargneById_ExistingId_ShouldReturnLivretEpargne() throws Exception {
        long existingLivretEpargneId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.get("/api/livrets-epargne/{id}", existingLivretEpargneId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(print());
    }

    @Test
    void getLivretEpargneById_NonExistingId_ShouldReturnNotFound() throws Exception {
        long nonExistingLivretEpargneId = 999L;
        mockMvc.perform(MockMvcRequestBuilders.get("/api/livrets-epargne/{id}", nonExistingLivretEpargneId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound())
                .andDo(print());
    }

    @Test
    void updatePlafondDepot_ExistingId_ShouldReturnUpdatedLivretEpargne() throws Exception {
        long livretEpargneId = 1L;
        double nouveauPlafond = 3000.0;
        mockMvc.perform(MockMvcRequestBuilders.put("/api/livrets-epargne/{id}", livretEpargneId)
                .contentType(MediaType.APPLICATION_JSON)
                .param("nouveauPlafond", String.valueOf(nouveauPlafond)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.plafondDepot").value(nouveauPlafond))
                .andDo(print());
    }

    @Test
    void deleteLivretEpargne_ExistingId_ShouldReturnNoContent() throws Exception {
        long livretEpargneId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/livrets-epargne/{id}", livretEpargneId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent())
                .andDo(print());
    }
}
