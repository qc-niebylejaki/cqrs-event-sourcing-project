package org.cqrs.core.query;

import org.cqrs.core.domain.BaseEntity;
import org.cqrs.core.queries.BaseQuery;

import java.util.List;

@FunctionalInterface
public interface QueryHandlerMethod<T extends BaseQuery> {
    List<BaseEntity> handle(T query);
}
