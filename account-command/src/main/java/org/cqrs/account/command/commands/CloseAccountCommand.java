package org.cqrs.account.command.commands;

import lombok.Data;
import org.cqrs.core.commands.BaseCommand;

@Data
public class CloseAccountCommand extends BaseCommand {

    public CloseAccountCommand(String id) {
        super(id);
    }
}
