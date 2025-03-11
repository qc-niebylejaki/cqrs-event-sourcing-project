package org.cqrs.account.common.events;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.cqrs.core.events.BaseEvent;

@Data
@SuperBuilder
public class AccountClosedEvent extends BaseEvent {
}
