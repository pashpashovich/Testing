package ru.clevertec.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.domain.Account;
import ru.clevertec.entity.AccountEntity;
import ru.clevertec.mapper.AccountMapper;
import ru.clevertec.repository.AccountRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountMapper accountMapper;
    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @Test
    void shouldGetAccounts() {
        //given
        AccountEntity accountEntity = new AccountEntity();
        List<AccountEntity> accountEntities=List.of(accountEntity);
        List<Account> accounts = List.of(new Account());
        Mockito.when(accountRepository.getAccounts())
                .thenReturn(accountEntities);
        Mockito.when(accountMapper.toDomains(accountEntities))
                .thenReturn(accounts);

        //when
        List<Account> actualAccounts = accountService.getAccounts();

        //then
        assertFalse(actualAccounts.isEmpty());

    }

    @Test
    void getAccountByNum() {
        // given

        //when

        //then
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}