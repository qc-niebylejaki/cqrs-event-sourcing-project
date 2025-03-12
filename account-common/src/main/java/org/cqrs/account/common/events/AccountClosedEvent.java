package org.cqrs.account.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.cqrs.core.events.BaseEvent;

@Data
@AllArgsConstructor
@SuperBuilder
public class AccountClosedEvent extends BaseEvent {
}
