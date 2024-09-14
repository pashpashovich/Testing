package ru.clevertec.service;

import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.common.AccountType;
import ru.clevertec.common.supplier.DateSupplier;
import ru.clevertec.domain.Account;
import ru.clevertec.entity.AccountEntity;
import ru.clevertec.exception.AccountNotFoundByNumException;
import ru.clevertec.exception.AccountNotFoundByTypeException;
import ru.clevertec.mapper.AccountMapper;
import ru.clevertec.repository.AccountRepository;
import ru.clevertec.util.DateSupplierTest;
import ru.clevertec.util.TestData;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {

    @Mock
    private AccountMapper accountMapper;
    @Mock
    private AccountRepository accountRepository;

    @Spy
    private DateSupplier dateSupplier = new DateSupplierTest();

    @InjectMocks
    private AccountService accountService;

    @Test
    void shouldGetAccounts() {
        //given
        List<AccountEntity> accountEntities = List.of(TestData.generateEntity());
        List<Account> accounts = List.of(TestData.generateDomain());
        when(accountRepository.getAccounts())
                .thenReturn(accountEntities);
        when(accountMapper.toDomains(accountEntities))
                .thenReturn(accounts);

        //when
        List<Account> actualAccounts = accountService.getAccounts();

        //then
        assertFalse(actualAccounts.isEmpty());

    }

    @Test
    void shouldGetAccountByNum() {
        // given
        double number = Math.random() * 1000000;
        AccountEntity accountEntity = TestData.generateEntity();
        Account account = TestData.generateDomain();
        account.setNumber((long) number);
        when(accountRepository.getAccountByNum((long) number))
                .thenReturn(Optional.of(accountEntity));
        when(accountMapper.toDomain(accountEntity))
                .thenReturn(account);
        //when
        Account actualAccount = accountService.getAccountByNum((long) number);

        //then
        assertEquals(account.getNumber(), actualAccount.getNumber());
        verify(accountRepository, times(1)).getAccountByNum((long) number);
    }

    @Test
    void shouldThrowAccountNotFoundByNumException_whenAccountNotFound() {
        // given
        double number = Math.random() * 1000000;
        when(accountRepository.getAccountByNum((long) number))
                .thenReturn(Optional.empty());
        //when,then
        assertThrows(AccountNotFoundByNumException.class,
                () -> accountService.getAccountByNum((long) number));
        verifyNoInteractions(accountMapper);
    }

    @ParameterizedTest
    @EnumSource(AccountType.class)
    void shouldGetAccountByType(AccountType accountType) {
        // given
        List<AccountEntity> accountEntities = List.of(TestData.generateEntity());
        List<Account> accounts = List.of(TestData.generateDomain());
        accounts.forEach(account -> account.setAccountType(accountType));
        when(accountRepository.getAccountsByType(accountType))
                .thenReturn(Optional.of(accountEntities));
        when(accountMapper.toDomains(accountEntities))
                .thenReturn(accounts);
        //when
        List<Account> actualAccount = accountService.getAccountsByType(accountType);

        //then
        assertThat(actualAccount.getFirst().getAccountType()).isEqualTo(accounts.getFirst().getAccountType());
        verify(accountRepository, times(1)).getAccountsByType(accountType);
    }

    @ParameterizedTest
    @EnumSource(AccountType.class)
    void shouldThrowAccountNotFoundByTypeException_whenAccountNotFound(AccountType accountType) {
        // given
        when(accountRepository.getAccountsByType(accountType))
                .thenReturn(Optional.empty());

        // when, then
        assertThrows(AccountNotFoundByTypeException.class,
                () -> accountService.getAccountsByType(accountType));
        verifyNoInteractions(accountMapper);
    }


    @Test
    void shouldAddAccountToDB() {
        // given
        Account account = TestData.generateDomain();
        AccountEntity accountEntity = TestData.generateEntity();
        AccountEntity createdAccountEntity = TestData.generateEntity();
        when(accountMapper.toEntity(account)).thenReturn(accountEntity);
        when(accountRepository.create(accountEntity)).thenReturn(createdAccountEntity);
        when(accountMapper.toDomain(createdAccountEntity)).thenReturn(account);

        // when
        Account actualAccount = accountService.create(account);

        // then
        assertEquals(account, actualAccount);
        verify(accountRepository, times(1)).create(accountEntity);
        verify(accountMapper, times(1)).toEntity(account);
        verify(accountMapper, times(1)).toDomain(createdAccountEntity);
    }

    @Test
    void shouldUpdateAccount() {
        // given
        UUID uuid = UUID.randomUUID();
        Account newAccount = TestData.generateDomain();
        AccountEntity accountEntity = TestData.generateEntity();
        AccountEntity updatedAccountEntity = TestData.generateEntity();
        when(accountMapper.toEntity(newAccount)).thenReturn(accountEntity);
        when(accountRepository.update(uuid, accountEntity)).thenReturn(updatedAccountEntity);
        when(accountMapper.toDomain(updatedAccountEntity)).thenReturn(newAccount);

        // when
        Account actualAccount = accountService.update(uuid, newAccount);

        // then
        assertEquals(newAccount, actualAccount);
        verify(accountRepository, times(1)).update(uuid, accountEntity);
        verify(accountMapper, times(1)).toEntity(newAccount);
        verify(accountMapper, times(1)).toDomain(updatedAccountEntity);
    }

    @Test
    void shouldDeleteByNum() {
        // given
        UUID uuid = UUID.randomUUID();

        //when
        accountService.delete(uuid);

        //then
        verify(accountRepository, times(1)).delete(uuid);
    }
}