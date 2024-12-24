package com.printerapp.application.queries.employees;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.common.pagination.PaginatedParam;
import com.printerapp.domain.common.sort.SortParam;
import com.printerapp.domain.enums.PrintTransactionStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindTransactionOfEmployeeQuery {
    UserId employeeId;
    PrinterId printerId;
    PrintTransactionStatus status;
    PaginatedParam paginatedParam;
    SortParam sortParam;
}
