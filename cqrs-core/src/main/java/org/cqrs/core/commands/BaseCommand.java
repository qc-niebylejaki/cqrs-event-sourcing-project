package org.cqrs.core.commands;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.cqrs.core.messages.Message;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public abstract class BaseCommand extends Message {

    public BaseCommand(String id) {
        super(id);
    }
}
