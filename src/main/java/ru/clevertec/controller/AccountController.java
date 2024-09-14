package ru.clevertec.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.common.AccountType;
import ru.clevertec.domain.Account;
import ru.clevertec.exception.AccountNotFoundByIdException;
import ru.clevertec.exception.AccountNotFoundByNumException;
import ru.clevertec.service.AccountService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accounts")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @ExceptionHandler({AccountNotFoundByNumException.class, AccountNotFoundByIdException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleAccountNotFoundException(RuntimeException ex) {
        return ex.getMessage();
    }

    @GetMapping()
    public ResponseEntity<List<Account>> findAll() {
        List<Account> accounts = accountService.getAccounts();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accounts);
    }

    @GetMapping("/{num}")
    public ResponseEntity<Account> findByAccountNum(@PathVariable("num") long num) {
        Account account = accountService.getAccountByNum(num);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(account);
    }

    @GetMapping("/type/{accountType}")
    public ResponseEntity<List<Account>> findByAccountType(@PathVariable("accountType") AccountType accountType) {
        System.out.println(accountType);
        List<Account> accounts = accountService.getAccountsByType(accountType);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(accounts);
    }

    @PostMapping
    public ResponseEntity<Account> create(@RequestBody Account account) {
        Account createdAccount = accountService.create(account);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(createdAccount);
    }

    @PutMapping("/{uuid}")
    public ResponseEntity<Account> update(@PathVariable("uuid") UUID uuid, @RequestBody Account account) {
        Account updatedAccount = accountService.update(uuid, account);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(updatedAccount);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEntity<Void> delete(@PathVariable("uuid") UUID uuid) {
        accountService.delete(uuid);
        return ResponseEntity.noContent().build();
    }

}
