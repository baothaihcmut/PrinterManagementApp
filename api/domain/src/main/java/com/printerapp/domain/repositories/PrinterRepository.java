package com.printerapp.domain.repositories;

import java.util.List;
import java.util.Optional;

import com.printerapp.domain.aggregates.printer.Printer;
import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.common.filter.FilterParam;
import com.printerapp.domain.common.pagination.PaginatedParam;
import com.printerapp.domain.common.pagination.PaginatedResult;
import com.printerapp.domain.common.sort.SortParam;
import com.printerapp.domain.enums.PrinterStatus;

public interface PrinterRepository {
        void save(Printer printer);

        Optional<Printer> findById(PrinterId id);

        PaginatedResult<Printer> findAll(PrinterStatus status, PaginatedParam param);

        List<Printer> findAllPrinterOfEmployee(UserId employeeId);

        PaginatedResult<Printer> findByCriteria(List<FilterParam<?>> filters, List<SortParam> sorts,
                        PaginatedParam paginated);
}
