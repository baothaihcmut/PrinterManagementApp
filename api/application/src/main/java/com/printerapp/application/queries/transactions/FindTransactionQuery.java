package com.printerapp.application.queries.transactions;

import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.common.pagination.PaginatedParam;
import com.printerapp.domain.common.sort.SortParam;
import com.printerapp.domain.enums.PrintTransactionStatus;
import com.printerapp.domain.enums.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindTransactionQuery {
    private PrintTransactionStatus status;
    private PaginatedParam paginatedParam;
    private SortParam sortParam;
    @Builder.Default
    private UserId userId = null;
    @Builder.Default
    private Role role = null;
}
