package org.cqrs.core.handlers;

import org.cqrs.core.domain.AggregateRoot;

public interface EventSourcingHandler<T extends AggregateRoot> {

    void save(AggregateRoot aggregate);

    T getById(String aggregateId);
}
