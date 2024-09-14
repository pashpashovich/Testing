package ru.clevertec.exception;

import java.util.UUID;

public class AccountNotFoundByIdException extends RuntimeException {
    private AccountNotFoundByIdException(String message) {
        super(message);
    }

    public static AccountNotFoundByIdException byAccountId(UUID uuid) {
        return new AccountNotFoundByIdException(
                String.format("Account not found by name %s", uuid)
        );
    }
}