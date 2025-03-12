package org.cqrs.account.query.infrastructure.handlers;

import lombok.RequiredArgsConstructor;
import org.cqrs.account.query.api.queries.*;
import org.cqrs.core.infrastructure.QueryDispatcher;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class QueryHandlerDispatcherConfig {

    private final QueryDispatcher queryDispatcher;
    private final QueryHandler queryHandler;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            queryDispatcher.registerHandler(FindAllAccountsQuery.class, queryHandler::handle);
            queryDispatcher.registerHandler(FindAccountByIdQuery.class, queryHandler::handle);
            queryDispatcher.registerHandler(FindAccountByHolderQuery.class, queryHandler::handle);
            queryDispatcher.registerHandler(FindAccountsWithBalanceQuery.class, queryHandler::handle);
        };
    }
}
