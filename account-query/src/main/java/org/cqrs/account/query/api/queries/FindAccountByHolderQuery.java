package org.cqrs.account.query.api.queries;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.cqrs.core.queries.BaseQuery;

@Data
@AllArgsConstructor
public class FindAccountByHolderQuery extends BaseQuery {
    private String accountHolder;
}
