package ru.clevertec.exception;

import ru.clevertec.common.AccountType;

public class AccountNotFoundByTypeException extends RuntimeException {
    private AccountNotFoundByTypeException(String message) {
        super(message);
    }

    public static AccountNotFoundByTypeException byAccountType(AccountType accountType) {
        return new AccountNotFoundByTypeException(
                String.format("Account not found by name %s", accountType.toString())
        );
    }
}