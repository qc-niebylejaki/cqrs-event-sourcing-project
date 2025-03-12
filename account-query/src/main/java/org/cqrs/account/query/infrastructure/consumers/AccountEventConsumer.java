package org.cqrs.account.query.infrastructure.consumers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqrs.account.common.events.AccountClosedEvent;
import org.cqrs.account.common.events.AccountOpenedEvent;
import org.cqrs.account.common.events.FundsDepositEvent;
import org.cqrs.account.common.events.FundsWithdrawEvent;
import org.cqrs.account.query.infrastructure.handlers.EventHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountEventConsumer implements EventConsumer {

    private final EventHandler eventHandler;

    @Override
    @KafkaListener(topics = "AccountOpenedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(AccountOpenedEvent event, Acknowledgment ack) {
        log.info("Received event {}", event);
        eventHandler.on(event);
        ack.acknowledge();
    }

    @Override
    @KafkaListener(topics = "FundsDepositEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(FundsDepositEvent event, Acknowledgment ack) {
        log.info("Received event {}", event);
        eventHandler.on(event);
        ack.acknowledge();
    }

    @Override
    @KafkaListener(topics = "FundsWithdrawEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(FundsWithdrawEvent event, Acknowledgment ack) {
        log.info("Received event {}", event);
        eventHandler.on(event);
        ack.acknowledge();
    }

    @Override
    @KafkaListener(topics = "AccountClosedEvent", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(AccountClosedEvent event, Acknowledgment ack) {
        log.info("Received event {}", event);
        eventHandler.on(event);
        ack.acknowledge();
    }
}
