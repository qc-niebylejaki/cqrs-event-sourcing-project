package org.cqrs.account.query.infrastructure;

import org.cqrs.core.domain.BaseEntity;
import org.cqrs.core.infrastructure.QueryDispatcher;
import org.cqrs.core.queries.BaseQuery;
import org.cqrs.core.query.QueryHandlerMethod;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
public class AccountQueryDispatcher implements QueryDispatcher {

    private final Map<Class<? extends BaseQuery>, List<QueryHandlerMethod>> routes = new HashMap<>();

    @Override
    public <T extends BaseQuery> void registerHandler(Class<T> queryType, QueryHandlerMethod<T> handler) {
        var handlers = routes.computeIfAbsent(queryType, c -> new LinkedList<>());
        handlers.add(handler);
    }

    @Override
    public <U extends BaseEntity> List<U> send(BaseQuery query) {
        var handlers = routes.get(query.getClass());
        if (handlers == null || handlers.isEmpty()) {
            throw new RuntimeException("No handler for " + query.getClass());
        }
        if (handlers.size() > 1) {
            throw new RuntimeException("More than one handler for " + query.getClass());
        }
        return handlers.get(0).handle(query);
    }
}
