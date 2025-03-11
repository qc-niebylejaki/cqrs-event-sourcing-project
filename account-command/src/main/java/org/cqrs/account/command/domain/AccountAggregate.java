package org.cqrs.account.command.domain;

import lombok.NoArgsConstructor;
import org.cqrs.account.command.api.commands.OpenAccountCommand;
import org.cqrs.account.common.events.AccountClosedEvent;
import org.cqrs.account.common.events.AccountOpenedEvent;
import org.cqrs.account.common.events.FundsDepositEvent;
import org.cqrs.account.common.events.FundsWithdrawEvent;
import org.cqrs.core.domain.AggregateRoot;

import java.time.LocalDate;

@NoArgsConstructor
public class AccountAggregate extends AggregateRoot {
    private Boolean active;
    private double balance;

    public AccountAggregate(OpenAccountCommand command) {
        raiseEvent(AccountOpenedEvent.builder()
                .id(command.getId())
                .accountHolder(command.getAccountHolder())
                .accountType(command.getAccountType())
                .openingBalance(command.getOpeningBalance())
                .createdDate(LocalDate.now())
                .build());
    }

    public void apply(AccountOpenedEvent event) {
        this.id = event.getId();
        this.active = true;
        this.balance = event.getOpeningBalance();
    }

    public void depositFund(double amount) {
        if (!active) {
            throw new IllegalStateException("Cannot deposit fund when account is not active");
        } else if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
        }
        raiseEvent(FundsDepositEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsDepositEvent event) {
        this.id = event.getId();
        this.balance += event.getAmount();
    }

    public void withdrawFunds(double amount) {
        if (!this.active) {
            throw new IllegalStateException("Cannot withdraw funds when account is not active");
        }
        raiseEvent(FundsWithdrawEvent.builder()
                .id(this.id)
                .amount(amount)
                .build());
    }

    public void apply(FundsWithdrawEvent event) {
        this.id = event.getId();
        this.balance -= event.getAmount();
    }

    public void closeAccount() {
        if (!active) {
            throw new IllegalStateException("Cannot close account when account is not active");
        }
        raiseEvent(AccountClosedEvent.builder()
                .id(this.id)
                .build());
    }

    public void apply(AccountClosedEvent event) {
        this.id = event.getId();
        this.active = false;
    }
}
