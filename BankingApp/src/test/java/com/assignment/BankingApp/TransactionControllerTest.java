package com.assignment.BankingApp;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testCreateTransaction() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"eisha\",\"password\":\"eisha123\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String authToken = JsonPath.read(responseBody, "$.jwtToken");
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/transactions/transfer-money")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"recieverAccountNumber\":\"12345679\",\"amount\":50, \"description\":\"testing\"}")
                        .with(csrf())
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isCreated())

                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }


    @Test
    void testGetAllTransactionsSuccess() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"admin\",\"password\":\"123456\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String authToken = JsonPath.read(responseBody, "$.jwtToken");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/transactions/all-transactions")
                        .with(csrf())
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk());
    }

}
