package com.printerapp.application.queries.users;

import com.printerapp.domain.common.pagination.PaginatedParam;
import com.printerapp.domain.common.sort.SortParam;
import com.printerapp.domain.enums.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FindUserQuery {
    Role role;
    SortParam sortParam;
    PaginatedParam paginatedParam;

}
