package ru.clevertec.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.common.AccountType;
import ru.clevertec.domain.Account;
import ru.clevertec.exception.AccountNotFoundByIdException;
import ru.clevertec.exception.AccountNotFoundByNumException;
import ru.clevertec.service.AccountService;
import ru.clevertec.util.TestData;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
class AccountConrollerTest {

    @MockBean
    private AccountService accountService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldFindAllAccounts() throws Exception {
        // given
        when(accountService.getAccounts())
                .thenReturn(List.of(TestData.generateDomain()));


        //when,then
        mockMvc.perform(get("/api/v1/accounts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void shouldFindByNumAccount() throws Exception {
        // given
        long number = (long) (Math.random() * 1000000);
        when(accountService.getAccountByNum(number))
                .thenReturn(TestData.generateDomain());

        //when,then
        mockMvc.perform(get(String.format("/api/v1/accounts/%d", number)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(7));
    }

    @Test
    void shouldNotFindByNumAccount() throws Exception {
        // given
        long number = (long) (Math.random() * 1000000);
        when(accountService.getAccountByNum(number))
                .thenThrow(AccountNotFoundByNumException.byAccountNum(number));

        //when,then
        mockMvc.perform(get(String.format("/api/v1/accounts/%d", number)))
                .andExpect(status().is4xxClientError());
    }

    @ParameterizedTest
    @EnumSource(AccountType.class)
    void shouldFindByAccountType(AccountType accountType) throws Exception {
        // given
        when(accountService.getAccountsByType(accountType))
                .thenReturn(List.of(TestData.generateDomain()));

        //when,then
        mockMvc.perform(get(String.format("/api/v1/accounts/type/%s", accountType)))
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    void shouldCreateAccount() throws Exception {
        // given
        Account account = TestData.generateDomain();
        when(accountService.create(any())).thenReturn(account);

        String requestBody = """
                    {
                      "number": 12345678,
                      "balance": 300.0,
                      "currency": "USD",
                      "openDate": "2024-09-14T10:00:00Z",
                      "accountType": "CHECKING",
                      "isActive": true
                    }
                """;

        // when, then
        mockMvc.perform(post("/api/v1/accounts")
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.number").value(12345678))
                .andExpect(jsonPath("$.balance").value(300));
    }

    @Test
    void shouldUpdateAccount() throws Exception {
        // given
        var account = TestData.generateDomain();
        UUID accountId = account.getId();
        when(accountService.update(any(UUID.class), any())).thenReturn(account);
        String requestBody = """
                    {
                      "number": 12345678,
                      "balance": 300.0,
                      "currency": "USD",
                      "openDate": "2024-09-14T10:00:00Z",
                      "accountType": "CHECKING",
                      "isActive": true
                    }
                """;

        // when, then
        mockMvc.perform(put("/api/v1/accounts/{id}", accountId)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(300));
    }

    @Test
    void shouldDeleteAccount() throws Exception {
        // given
        UUID accountId = UUID.randomUUID();
        doNothing().when(accountService).delete(accountId);

        // when, then
        mockMvc.perform(delete("/api/v1/accounts/{id}", accountId))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnNotFoundWhenDeletingNonExistingAccount() throws Exception {
        // given
        UUID accountId = UUID.randomUUID();
        doThrow(AccountNotFoundByIdException.byAccountId(accountId)).when(accountService).delete(accountId);

        // when, then
        mockMvc.perform(delete("/api/v1/accounts/{id}", accountId))
                .andExpect(status().is4xxClientError());
    }


}