package org.cqrs.account.query.api.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.cqrs.account.query.api.dto.EqualityType;
import org.cqrs.core.queries.BaseQuery;

@Data
@AllArgsConstructor
public class FindAccountsWithBalanceQuery extends BaseQuery {
    private EqualityType equalityType;
    private double balance;
}
