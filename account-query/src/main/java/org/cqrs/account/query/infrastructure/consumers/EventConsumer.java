package org.cqrs.account.query.infrastructure.consumers;

import org.cqrs.account.common.events.AccountClosedEvent;
import org.cqrs.account.common.events.AccountOpenedEvent;
import org.cqrs.account.common.events.FundsDepositEvent;
import org.cqrs.account.common.events.FundsWithdrawEvent;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;

public interface EventConsumer {

    void consume(@Payload AccountOpenedEvent event, Acknowledgment ack);

    void consume(@Payload FundsDepositEvent event, Acknowledgment ack);

    void consume(@Payload FundsWithdrawEvent event, Acknowledgment ack);

    void consume(@Payload AccountClosedEvent event, Acknowledgment ack);
}
