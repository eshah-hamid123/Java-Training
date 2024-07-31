package com.assignment.BankingApp;

import com.assignment.BankingApp.account.AccountController;
import com.assignment.BankingApp.account.AccountService;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
@ExtendWith(MockitoExtension.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private AccountService accountService;

    @InjectMocks
    private AccountController accountController;

    @Test
    public void testCreateAccount() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"admin\",\"password\":\"123456\"}"))
                                .andExpect(status().isOk())
                                        .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String authToken = JsonPath.read(responseBody, "$.jwtToken");
        mockMvc.perform(MockMvcRequestBuilders.post("/v1/accounts/create-account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"ssss\",\"password\":\"123456\",\"email\":\"stestuser23@example.com\",\"address\":\"lhr pak\",\"balance\":1000,\"accountNumber\":\"1125675443\"}")
                .with(csrf())
                        .header("Authorization", "Bearer " + authToken))
            .andExpect(status().isCreated())

                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("ssss"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("stestuser23@example.com"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.address").value("lhr pak"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(1000))
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").value("1125675443"));
    }

    @Test
    void testUpdateAccount() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"admin\",\"password\":\"123456\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String authToken = JsonPath.read(responseBody, "$.jwtToken");

        mockMvc.perform(MockMvcRequestBuilders.put("/v1/accounts/edit-account/32")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"testUser323\",\"password\":\"123456\",\"email\":\"saniaaa@example.com\",\"address\":\"lhr pak\",\"balance\":1000,\"accountNumber\":\"12567544\"}")
                        .with(csrf())
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("testUser323"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("saniaaa@example.com"));
    }

    @Test
    void testGetAllAccounts() throws Exception {

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"admin\",\"password\":\"123456\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String authToken = JsonPath.read(responseBody, "$.jwtToken");

        mockMvc.perform(MockMvcRequestBuilders.get("/v1/accounts/all-accounts")
                        .with(csrf())
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk());
    }

    @Test
    void testGetAccountById() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"eisha\",\"password\":\"eisha123\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String authToken = JsonPath.read(responseBody, "$.jwtToken");
        mockMvc.perform(MockMvcRequestBuilders.get("/v1/accounts/get-account/19")
                        .with(csrf())
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("eisha"));
    }

    @Test
    void testDeleteAccountSuccess() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"username\":\"admin\",\"password\":\"123456\"}"))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        String authToken = JsonPath.read(responseBody, "$.jwtToken");
        mockMvc.perform(MockMvcRequestBuilders.delete("/v1/accounts/delete-account/32")
                        .with(csrf())
                        .header("Authorization", "Bearer " + authToken))
                .andExpect(status().isNoContent());
    }




}
