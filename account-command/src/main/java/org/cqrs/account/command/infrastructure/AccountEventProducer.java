package org.cqrs.account.command.infrastructure;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.cqrs.core.events.BaseEvent;
import org.cqrs.core.producers.EventProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountEventProducer implements EventProducer {

    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;

    @Override
    public void produce(String topic, BaseEvent event) {
        log.info("Sending event {} to Kafka", event);
        kafkaTemplate.send(topic, event);
    }
}
