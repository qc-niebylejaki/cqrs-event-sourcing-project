package org.cqrs.account.command.api.commands;

import lombok.Data;
import org.cqrs.core.commands.BaseCommand;

@Data
public class DepositFundsCommand extends BaseCommand {

    private double amount;
}
