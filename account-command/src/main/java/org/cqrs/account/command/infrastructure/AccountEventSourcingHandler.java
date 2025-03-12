package org.cqrs.account.command.infrastructure;

import lombok.RequiredArgsConstructor;
import org.cqrs.account.command.domain.AccountAggregate;
import org.cqrs.core.domain.AggregateRoot;
import org.cqrs.core.events.BaseEvent;
import org.cqrs.core.handlers.EventSourcingHandler;
import org.cqrs.core.infrastructure.EventStore;
import org.cqrs.core.producers.EventProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
@RequiredArgsConstructor
public class AccountEventSourcingHandler implements EventSourcingHandler<AccountAggregate> {

    @Value("${spring.kafka.topic}")
    private String topic;

    private final EventStore eventStore;
    private final EventProducer eventProducer;

    @Override
    public void save(AggregateRoot aggregate) {
        eventStore.saveEvents(aggregate.getId(), aggregate.getUncommittedChanges(), aggregate.getVersion());
        aggregate.markChangesAsCommitted();
    }

    @Override
    public AccountAggregate getById(String aggregateId) {
        var aggregate = new AccountAggregate();
        var events = eventStore.getEvents(aggregateId);
        if (events != null && !events.isEmpty()) {
            aggregate.replayEvents(events);
            var latestVersion = events.stream()
                    .map(BaseEvent::getVersion)
                    .max(Comparator.naturalOrder());
            aggregate.setVersion(latestVersion.get());
        }
        return aggregate;
    }

    @Override
    public void republishEvents() {
        var aggregateIds = eventStore.getAggregateIds();
        for (var aggregateId : aggregateIds) {
            var aggregate = getById(aggregateId);
            if (aggregate == null || !aggregate.getActive()) continue;

            var events = eventStore.getEvents(aggregateId);
            for (var event : events) {
                eventProducer.produce(topic, event);
            }
        }
    }
}
