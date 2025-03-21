package org.cqrs.account.command.infrastructure;

import lombok.RequiredArgsConstructor;
import org.cqrs.account.command.domain.AccountAggregate;
import org.cqrs.account.command.domain.EventStoreRepository;
import org.cqrs.core.events.BaseEvent;
import org.cqrs.core.events.EventModel;
import org.cqrs.core.exceptions.AggregateNotFoundException;
import org.cqrs.core.exceptions.ConcurrencyException;
import org.cqrs.core.infrastructure.EventStore;
import org.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountEventStore implements EventStore {

    @Value("${spring.kafka.topic}")
    private String topic;
    
    private final EventStoreRepository eventStoreRepository;
    private final EventProducer eventProducer;
    private final AccountEventProducer accountEventProducer;

    @Override
    public void saveEvents(String aggregateId, Iterable<BaseEvent> events, int expectedVersion) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (expectedVersion != -1 && eventStream.get(eventStream.size() - 1).getVersion() != expectedVersion) {
            throw new ConcurrencyException();
        }
        var version = expectedVersion;
        for (var event : events) {
            version++;
            event.setVersion(version);
            var eventModel = EventModel.builder()
                    .timestamp(LocalDate.now())
                    .aggregateIdentifier(aggregateId)
                    .aggregateType(AccountAggregate.class.getTypeName())
                    .version(version)
                    .eventType(event.getClass().getTypeName())
                    .eventData(event)
                    .build();
            var persistedEvent = eventStoreRepository.save(eventModel);
            if (!persistedEvent.getId().isEmpty()) {
                accountEventProducer.produce(topic, event);
            }
        }
    }

    @Override
    public List<BaseEvent> getEvents(String aggregateId) {
        var eventStream = eventStoreRepository.findByAggregateIdentifier(aggregateId);
        if (eventStream == null || eventStream.isEmpty()) {
            throw new AggregateNotFoundException("Incorrect account ID provided!");
        }
        return eventStream.stream()
                .map(EventModel::getEventData)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAggregateIds() {
        var eventStream = eventStoreRepository.findAll();
        if (eventStream == null || eventStream.isEmpty()) {
            throw new IllegalStateException("Could not retrieve events from the event store!");
        }
        return eventStream.stream()
                .map(EventModel::getAggregateIdentifier)
                .toList();
    }
}
