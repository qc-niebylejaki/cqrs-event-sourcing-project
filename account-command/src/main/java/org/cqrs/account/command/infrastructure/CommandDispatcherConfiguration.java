package org.cqrs.account.command.infrastructure;

import lombok.RequiredArgsConstructor;
import org.cqrs.account.command.api.commands.*;
import org.cqrs.core.infrastructure.CommandDispatcher;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CommandDispatcherConfiguration {

    private final CommandDispatcher commandDispatcher;
    private final CommandHandler commandHandler;

    @Bean
    public ApplicationRunner applicationRunner() {
        return args -> {
            commandDispatcher.registerHandler(OpenAccountCommand.class, commandHandler::handle);
            commandDispatcher.registerHandler(DepositFundsCommand.class, commandHandler::handle);
            commandDispatcher.registerHandler(WithdrawFundsCommand.class, commandHandler::handle);
            commandDispatcher.registerHandler(CloseAccountCommand.class, commandHandler::handle);
            commandDispatcher.registerHandler(RestoreReadDbCommand.class, commandHandler::handle);
        };
    }
}
