package ru.clevertec.service;

import ru.clevertec.domain.Account;
import ru.clevertec.entity.AccountEntity;
import ru.clevertec.mapper.AccountMapper;
import ru.clevertec.mapper.AccountMapperImpl;
import ru.clevertec.repository.AccountRepository;

import java.util.List;
import java.util.UUID;

public class AccountService {
    private final AccountRepository accountRepository = new AccountRepository();

    private final AccountMapper accountMapper = new AccountMapperImpl();

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
