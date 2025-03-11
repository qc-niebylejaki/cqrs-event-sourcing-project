package org.cqrs.core.infrastructure;

import org.cqrs.core.commands.BaseCommand;
import org.cqrs.core.commands.CommandHandlerMethod;

public interface CommandDispatcher {

    <T extends BaseCommand> void registerHandler(Class<T> type, CommandHandlerMethod<T> handler);

    void send(BaseCommand command);
}
