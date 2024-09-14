package ru.clevertec.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import ru.clevertec.common.AccountType;
import ru.clevertec.common.MyCurrency;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private UUID id;
    private long number;
    private double balance;
    private MyCurrency currency;
    private OffsetDateTime openDate;
    private AccountType accountType;
    private boolean isActive;
}
