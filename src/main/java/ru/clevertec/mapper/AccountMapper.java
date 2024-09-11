package ru.clevertec.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.domain.Account;
import ru.clevertec.entity.AccountEntity;

import java.util.List;

@Mapper
public interface AccountMapper {
    List<Account> toDomains(List<AccountEntity> accountEntities);
    Account toDomain(AccountEntity accountEntity);
    AccountEntity toEntity(Account account);
}
