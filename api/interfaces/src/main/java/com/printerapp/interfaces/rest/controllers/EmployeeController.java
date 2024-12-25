package com.printerapp.interfaces.rest.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.printerapp.application.queries.employees.FindTransactionOfEmployeeQuery;
import com.printerapp.application.results.printers.PrinterResult;
import com.printerapp.application.services.EmployeeService;
import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.common.pagination.PaginatedParam;
import com.printerapp.domain.common.pagination.PaginatedResult;
import com.printerapp.domain.common.sort.SortParam;
import com.printerapp.domain.enums.PrintTransactionStatus;
import com.printerapp.domain.records.printer_employee.PrinterEmployeeRecord;
import com.printerapp.domain.records.transactions.TransactionRecord;
import com.printerapp.interfaces.rest.common.response.AppResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/printers")
    @PreAuthorize("hasAnyRole('ROLE_employee')")
    public ResponseEntity<AppResponse> getAllPrinterOfEmployee() {
        List<PrinterEmployeeRecord> res = this.employeeService.findAllPrinterOfEmployee();
        return AppResponse.initRespose(HttpStatus.OK, true, "Get all printer of employee success", res);
    }

    @GetMapping("/transactions")
    @PreAuthorize("hasAnyRole('ROLE_employee')")
    public ResponseEntity<AppResponse> getAllTransactionOfEmployeeByPPrinter(
            @RequestParam(value = "printer", required = false) UUID printerId,
            @RequestParam(value = "status", required = false) PrintTransactionStatus status,
            @RequestParam("sort") SortParam sortParam,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PaginatedResult<TransactionRecord> res = this.employeeService.findAllTransactionOfEmployee(
                FindTransactionOfEmployeeQuery.builder()
                        .printerId(new PrinterId(printerId))
                        .status(status)
                        .paginatedParam(PaginatedParam.builder().size(size).page(page).build()).sortParam(sortParam)
                        .build());
        System.out.println(res);
        return AppResponse.initRespose(HttpStatus.OK, true, "Get all transaction of employee by printer success", res);
    }


    @GetMapping("/{id}/printers")
    public ResponseEntity<AppResponse> findAllPrinterOfEmployee(@PathVariable("id") UUID userId) {
        List<PrinterResult> res = this.employeeService.findAllPrinterOfEmployeeById(new UserId(userId));
        return AppResponse.initRespose(HttpStatus.OK, true, "Get all printer of employee sucess", res);
    }

    @GetMapping("/{id}/transactions")
    public ResponseEntity<AppResponse> findAllTransactionOfEmployee(@PathVariable("id") UUID userId,
            @RequestParam("sort") SortParam sortParam,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PaginatedResult<TransactionRecord> res = this.employeeService
                .findAllTransactionOfEmployee(FindTransactionOfEmployeeQuery.builder().sortParam(sortParam)
                        .paginatedParam(PaginatedParam.builder().page(page).size(size).build())
                        .employeeId(new UserId(userId)).build());
        return AppResponse.initRespose(HttpStatus.OK, true, "Get all transaction of employee sucess", res);
    }
}
