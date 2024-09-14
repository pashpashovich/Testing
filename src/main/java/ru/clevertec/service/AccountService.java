package ru.clevertec.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.clevertec.common.AccountType;
import ru.clevertec.common.supplier.DateSupplier;
import ru.clevertec.domain.Account;
import ru.clevertec.entity.AccountEntity;
import ru.clevertec.exception.AccountNotFoundByNumException;
import ru.clevertec.exception.AccountNotFoundByTypeException;
import ru.clevertec.mapper.AccountMapper;
import ru.clevertec.repository.AccountRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    private final DateSupplier dateSupplier;

    public List<Account> getAccounts() {
        List<AccountEntity> accounts = accountRepository.getAccounts()
                .stream()
                .filter(accountEntity -> accountEntity.getOpenDate().isBefore(dateSupplier.getCurrentDateTime()))
                .toList();
        return accountMapper.toDomains(accounts);
    }

    public Account getAccountByNum(long num) {
        AccountEntity account = accountRepository.getAccountByNum(num)
                .orElseThrow(() -> AccountNotFoundByNumException.byAccountNum(num));
        return accountMapper.toDomain(account);
    }

    public List<Account> getAccountsByType(AccountType accountType) {
        List<AccountEntity> accounts = accountRepository.getAccountsByType(accountType)
                .orElseThrow(() -> AccountNotFoundByTypeException.byAccountType(accountType));
        return accountMapper.toDomains(accounts);
    }

    public Account create(Account account) {
        AccountEntity accountEntity = accountMapper.toEntity(account);
        AccountEntity createdAccountEntity = accountRepository.create(accountEntity);
        return accountMapper.toDomain(createdAccountEntity);
    }

    public Account update(UUID uuid, Account newAccount) {
        AccountEntity accountEntity = accountMapper.toEntity(newAccount);
        AccountEntity updatedAccountEntity = accountRepository.update(uuid, accountEntity);
        return accountMapper.toDomain(updatedAccountEntity);
    }

    public void delete(UUID uuid) {
        accountRepository.delete(uuid);
    }
}
