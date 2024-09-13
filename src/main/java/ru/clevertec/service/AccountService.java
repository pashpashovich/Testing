package ru.clevertec.service;

import lombok.AllArgsConstructor;
import ru.clevertec.domain.Account;
import ru.clevertec.entity.AccountEntity;
import ru.clevertec.mapper.AccountMapper;
import ru.clevertec.mapper.AccountMapperImpl;
import ru.clevertec.repository.AccountRepository;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    private final AccountMapper accountMapper;

    public List<Account> getAccounts() {
        List<AccountEntity> accounts = accountRepository.getAccounts();
        return accountMapper.toDomains(accounts);
    }

    public Account getAccountByNum(long num) {
        AccountEntity account = accountRepository.getAccountByNum(num);
        return accountMapper.toDomain(account);
    }

    public Account create(Account account) {
        AccountEntity accountEntity = accountMapper.toEntity(account);
        AccountEntity createdAccountEntity = accountRepository.create(accountEntity);
        return accountMapper.toDomain(createdAccountEntity);
    }

    public Account update (UUID uuid, Account newAccount) {
        AccountEntity accountEntity = accountMapper.toEntity(newAccount);
        AccountEntity updatedAccountEntity = accountRepository.update(uuid,accountEntity);
        return accountMapper.toDomain(updatedAccountEntity);
    }

    public void delete(UUID uuid) {
        accountRepository.delete(uuid);
    }
}
