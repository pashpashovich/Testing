package ru.clevertec.util;

import ru.clevertec.common.AccountType;
import ru.clevertec.common.MyCurrency;
import ru.clevertec.common.supplier.DateSupplier;
import ru.clevertec.domain.Account;
import ru.clevertec.entity.AccountEntity;

import java.util.UUID;

public class TestData {

    private static final DateSupplier DATE_SUPPLIER = new DateSupplierTest();

    public static AccountEntity generateEntity() {
        return new AccountEntity()
                .setId(UUID.randomUUID())
                .setNumber(12345678)
                .setActive(true)
                .setBalance(300)
                .setCurrency(MyCurrency.USD)
                .setOpenDate(DATE_SUPPLIER.getCurrentDateTime().minusDays(1))
                .setAccountType(AccountType.CHECKING);
    }

    public static Account generateDomain() {
        return new Account()
                .setId(UUID.randomUUID())
                .setNumber(12345678)
                .setActive(true)
                .setBalance(300)
                .setCurrency(MyCurrency.USD)
                .setOpenDate(DATE_SUPPLIER.getCurrentDateTime().minusDays(1))
                .setAccountType(AccountType.CHECKING);
    }
}
