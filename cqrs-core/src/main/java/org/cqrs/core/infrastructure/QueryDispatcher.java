package org.cqrs.core.infrastructure;

import org.cqrs.core.domain.BaseEntity;
import org.cqrs.core.queries.BaseQuery;
import org.cqrs.core.query.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {
    <T extends BaseQuery> void registerHandler(Class<T> queryType, QueryHandlerMethod<T> handler);

    <U extends BaseEntity> List<U> send(BaseQuery query);
}
