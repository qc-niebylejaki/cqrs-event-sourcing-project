package org.cqrs.account.query.api.queries;

import lombok.RequiredArgsConstructor;
import org.cqrs.account.query.api.dto.EqualityType;
import org.cqrs.account.query.domain.AccountRepository;
import org.cqrs.core.domain.BaseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountQueryHandler implements QueryHandler {

    private final AccountRepository accountRepository;

    @Override
    public List<BaseEntity> handle(FindAllAccountsQuery query) {
        return accountRepository.findAll()
                .stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<BaseEntity> handle(FindAccountByIdQuery query) {
        var bankAccount = accountRepository.findById(query.getId());
        return bankAccount.<List<BaseEntity>>map(List::of)
                .orElse(Collections.emptyList());
    }

    @Override
    public List<BaseEntity> handle(FindAccountByHolderQuery query) {
        return accountRepository.findByAccountHolder(query.getAccountHolder())
                .stream()
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<BaseEntity> handle(FindAccountsWithBalanceQuery query) {
        return (query.getEqualityType() == EqualityType.GREATER_THAN)
                ? accountRepository.findByBalanceGreaterThan(query.getBalance())
                : accountRepository.findByBalanceLessThan(query.getBalance());
    }
}
