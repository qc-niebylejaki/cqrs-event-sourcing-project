package org.cqrs.account.query.infrastructure.handlers;

import lombok.RequiredArgsConstructor;
import org.cqrs.account.common.events.AccountClosedEvent;
import org.cqrs.account.common.events.AccountOpenedEvent;
import org.cqrs.account.common.events.FundsDepositEvent;
import org.cqrs.account.common.events.FundsWithdrawEvent;
import org.cqrs.account.query.domain.AccountRepository;
import org.cqrs.account.query.domain.BankAccount;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountEventHandler implements EventHandler {

    private final AccountRepository accountRepository;

    @Override
    public void on(AccountOpenedEvent event) {
        var bankAccount = BankAccount.builder()
                .id(event.getId())
                .accountHolder(event.getAccountHolder())
                .creationDate(event.getCreatedDate())
                .accountType(event.getAccountType())
                .balance(event.getOpeningBalance())
                .build();
        accountRepository.save(bankAccount);
    }

    @Override
    public void on(FundsDepositEvent event) {
        var bankAccount = accountRepository.findById(event.getId());
        if (bankAccount.isEmpty()) {
            return;
        }
        var currentBalance = bankAccount.get().getBalance();
        var lastBalance = currentBalance + event.getAmount();
        bankAccount.get().setBalance(lastBalance);
        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(FundsWithdrawEvent event) {
        var bankAccount = accountRepository.findById(event.getId());
        if (bankAccount.isEmpty()) {
            return;
        }
        var currentBalance = bankAccount.get().getBalance();
        var lastBalance = currentBalance - event.getAmount();
        bankAccount.get().setBalance(lastBalance);
        accountRepository.save(bankAccount.get());
    }

    @Override
    public void on(AccountClosedEvent event) {
        accountRepository.deleteById(event.getId());
    }
}
