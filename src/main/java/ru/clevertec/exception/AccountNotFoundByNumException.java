package ru.clevertec.exception;

public class AccountNotFoundByNumException extends RuntimeException {
    private AccountNotFoundByNumException(String message) {
        super(message);
    }

    public static AccountNotFoundByNumException byAccountNum(long num) {
        return new AccountNotFoundByNumException(
                String.format("Account not found by name %d", num)
        );
    }
}
