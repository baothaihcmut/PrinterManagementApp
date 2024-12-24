package com.printerapp.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.user.User;
import com.printerapp.domain.aggregates.user.value_objects.Email;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.common.filter.FilterParam;
import com.printerapp.domain.common.pagination.PaginatedParam;
import com.printerapp.domain.common.pagination.PaginatedResult;
import com.printerapp.domain.common.sort.SortParam;

public interface UserRepository {
        void save(User user);

        Optional<User> findById(UserId userId);

        Optional<User> findByEmail(Email email);

        PaginatedResult<User> findByCriteria(List<FilterParam<?>> filters, List<SortParam> sorts,
                        PaginatedParam paginatedParam);

        List<User> findAllEmployeeOfPrinter(PrinterId printerId);

}
