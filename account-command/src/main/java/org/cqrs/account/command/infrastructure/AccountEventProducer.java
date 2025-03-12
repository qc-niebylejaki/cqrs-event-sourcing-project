package org.cqrs.account.command.infrastructure;

import lombok.RequiredArgsConstructor;
import org.cqrs.core.events.BaseEvent;
import org.cqrs.core.producers.EventProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountEventProducer implements EventProducer {

    private final KafkaTemplate<String, BaseEvent> kafkaTemplate;

    @Override
    public void produce(String topic, BaseEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
