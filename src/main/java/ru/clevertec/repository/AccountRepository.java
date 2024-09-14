package ru.clevertec.repository;

import org.springframework.stereotype.Repository;
import ru.clevertec.common.AccountType;
import ru.clevertec.common.MyCurrency;
import ru.clevertec.entity.AccountEntity;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class AccountRepository {

    private static final List<AccountEntity> db = List.of(
            new AccountEntity(UUID.randomUUID(), 12345678, 324.6, MyCurrency.BYN, OffsetDateTime.now().minus(Duration.ofDays(2)), AccountType.SAVINGS, true),
            new AccountEntity(UUID.randomUUID(), 16438964, 1532, MyCurrency.USD, OffsetDateTime.now().minus(Duration.ofDays(100)), AccountType.CHECKING, true),
            new AccountEntity(UUID.randomUUID(), 16438634, 57800, MyCurrency.RUB, OffsetDateTime.now().minus(Duration.ofDays(456)), AccountType.SOCIAL, false),
            new AccountEntity(UUID.randomUUID(), 16481054, 1000, MyCurrency.EUR, OffsetDateTime.now().minus(Duration.ofHours(5)), AccountType.CREDIT, true)
    );

    public List<AccountEntity> getAccounts() {
        return db;
    }

    public Optional<AccountEntity> getAccountByNum(long num) {
        return db.stream()
                .filter(accountEntity -> accountEntity.getNumber() == num)
                .findFirst();
    }

    public Optional<List<AccountEntity>> getAccountsByType(AccountType accountType) {
        return Optional.of(db.stream()
                .filter(accountEntity -> accountEntity.getAccountType() == accountType)
                .toList());
    }

    public AccountEntity create(AccountEntity accountEntity) {
        return accountEntity;
    }

    public AccountEntity update(UUID uuid, AccountEntity newAccountEntity) {
        return newAccountEntity.setId(uuid);
    }

    public void delete(UUID uuid) {
        // without body
    }


}
