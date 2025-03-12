package org.cqrs.account.query.infrastructure.handlers;

import org.cqrs.account.common.events.AccountClosedEvent;
import org.cqrs.account.common.events.AccountOpenedEvent;
import org.cqrs.account.common.events.FundsDepositEvent;
import org.cqrs.account.common.events.FundsWithdrawEvent;

public interface EventHandler {

    void on(AccountOpenedEvent event);

    void on(FundsDepositEvent event);

    void on(FundsWithdrawEvent event);

    void on(AccountClosedEvent event);
}
