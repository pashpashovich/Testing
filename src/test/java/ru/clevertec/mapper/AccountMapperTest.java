package ru.clevertec.mapper;

import org.jeasy.random.EasyRandom;
import org.junit.jupiter.api.Test;
import ru.clevertec.domain.Account;
import ru.clevertec.entity.AccountEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountMapperTest {

    private final AccountMapper accountMapper = new AccountMapperImpl();
    private final EasyRandom easyRandom = new EasyRandom();


    @Test
    void testToDomain() {
        // given
        AccountEntity accountEntity = easyRandom.nextObject(AccountEntity.class);
        //when
        Account account = accountMapper.toDomain(accountEntity);

        //then
        assertEquals(accountEntity.getId(), account.getId());
        assertEquals(accountEntity.getNumber(), account.getNumber());
        assertEquals(accountEntity.getBalance(), account.getBalance());
        assertEquals(accountEntity.getCurrency(), account.getCurrency());
        assertEquals(accountEntity.getOpenDate(), account.getOpenDate());
        assertEquals(accountEntity.isActive(), account.isActive());
    }

    @Test
    void testToEntity() {
        // given
        Account account = easyRandom.nextObject(Account.class);

        //when
        AccountEntity accountEntity = accountMapper.toEntity(account);

        //then
        assertEquals(account.getId(), accountEntity.getId());
        assertEquals(account.getNumber(), accountEntity.getNumber());
        assertEquals(account.getBalance(), accountEntity.getBalance());
        assertEquals(account.getCurrency(), accountEntity.getCurrency());
        assertEquals(account.getOpenDate(), accountEntity.getOpenDate());
        assertEquals(account.isActive(), accountEntity.isActive());
    }

    @Test
    void testToDomains() {
        // given
        List<AccountEntity> accountEntities = easyRandom.objects(AccountEntity.class, 5).toList();

        //when
        List<Account> accounts = accountMapper.toDomains(accountEntities);

        //then

        assertEquals(accountEntities.size(), accounts.size());
        for (int i = 0; i < accountEntities.size(); i++) {
            assertEquals(accountEntities.get(i).getId(), accounts.get(i).getId());
            assertEquals(accountEntities.get(i).getNumber(), accounts.get(i).getNumber());
            assertEquals(accountEntities.get(i).getBalance(), accounts.get(i).getBalance());
            assertEquals(accountEntities.get(i).getCurrency(), accounts.get(i).getCurrency());
            assertEquals(accountEntities.get(i).getOpenDate(), accounts.get(i).getOpenDate());
            assertEquals(accountEntities.get(i).isActive(), accounts.get(i).isActive());
        }
    }
}
