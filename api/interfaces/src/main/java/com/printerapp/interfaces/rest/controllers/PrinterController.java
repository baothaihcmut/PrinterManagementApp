package com.printerapp.interfaces.rest.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.printerapp.application.queries.printers.FindAllTransactionOfPrinterQuery;
import com.printerapp.application.queries.printers.FindPrinterQuery;
import com.printerapp.application.results.printers.PrinterEmployeeResult;
import com.printerapp.application.results.printers.PrinterResult;
import com.printerapp.application.services.PrinterService;
import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.common.pagination.PaginatedParam;
import com.printerapp.domain.common.pagination.PaginatedResult;
import com.printerapp.domain.common.sort.SortParam;
import com.printerapp.domain.enums.PrintTransactionStatus;
import com.printerapp.domain.enums.PrinterStatus;
import com.printerapp.domain.records.transactions.TransactionRecord;
import com.printerapp.interfaces.rest.common.response.AppResponse;
import com.printerapp.interfaces.rest.dtos.printers.AddEmployeeToPrinterDTO;
import com.printerapp.interfaces.rest.dtos.printers.CreatePrinterDTO;
import com.printerapp.interfaces.rest.dtos.printers.UpdatePrinterDTO;
import com.printerapp.interfaces.rest.mappers.RestPrinterMapper;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/printers")
@RequiredArgsConstructor
public class PrinterController {
    private final PrinterService printerService;
    private final RestPrinterMapper restPrinterMapper;

    @PostMapping("/create")
    @PreAuthorize("hasAnyRole('ROLE_admin')")
    public ResponseEntity<AppResponse> createPrinter(@RequestBody @Valid CreatePrinterDTO dto) {
        PrinterResult result = this.printerService.createPrinter(this.restPrinterMapper.toCommand(dto));
        return AppResponse.initRespose(HttpStatus.CREATED, true, "Create printer success", result);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ROLE_admin')")
    public ResponseEntity<AppResponse> updatePrinter(@PathVariable("id") UUID printerId,
            @RequestBody @Valid UpdatePrinterDTO dto) {
        PrinterResult res = this.printerService.updatePrinter(this.restPrinterMapper.toCommand(printerId, dto));
        return AppResponse.initRespose(HttpStatus.CREATED, true, "Update printer success", res);
    }

    @GetMapping("/{id}/transactions")
    // @PreAuthorize("hasRole('ROLE_admin') or (hasRole('ROLE_employee') and #status
    // == 'PENDING')")
    public ResponseEntity<AppResponse> getAllTransactions(
            @RequestParam(value = "status", required = false) PrintTransactionStatus status,
            @RequestParam(value = "sort", required = false) SortParam sortParam,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size, @PathVariable("id") UUID id) {
        PaginatedResult<TransactionRecord> res = this.printerService.findAllTransactionOfPrinter(
                FindAllTransactionOfPrinterQuery.builder().printerId(new PrinterId(id)).sortParam(sortParam)
                        .paginatedParam(PaginatedParam.builder().page(page).size(size).build()).status(status).build());
        return AppResponse.initRespose(HttpStatus.OK, true, "Get all transaction of printer success", res);
    }

    @PostMapping("/{id}/employees/add")
    @PreAuthorize("hasAnyRole('ROLE_admin')")
    public ResponseEntity<AppResponse> addEmployee(@PathVariable("id") UUID printerId,
            @RequestBody @Valid AddEmployeeToPrinterDTO addEmployeeToPrinterDTO) {
        this.printerService.addEmployeeToPrinter(
                this.restPrinterMapper.toAddEmployeeToPrinterCommand(addEmployeeToPrinterDTO, printerId));
        return AppResponse.initRespose(HttpStatus.CREATED, true, "Add employee to printer success", null);
    }

    @GetMapping("")
    // @PreAuthorize("hasAnyRole('ROLE_admin')")
    public ResponseEntity<AppResponse> findPrinter(
            @RequestParam(value = "status", required = false) PrinterStatus status,
            @RequestParam(value = "sort", required = false) SortParam sortParam,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size) {
        PaginatedResult<PrinterResult> res = this.printerService.findPrinter(FindPrinterQuery.builder().status(status)
                .sortParam(sortParam).paginatedParam(PaginatedParam.builder().size(size).page(page).build()).build());
        return AppResponse.initRespose(HttpStatus.OK, true, "Get printer sucess", res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppResponse> findById(@PathVariable("id") UUID id) {
        PrinterResult printerResult = this.printerService.findById(new PrinterId(id));
        return AppResponse.initRespose(HttpStatus.OK, true, "Get printer success", printerResult);
    }


    @GetMapping("/{id}/employees")
    public ResponseEntity<AppResponse> getAllEmployeeOfPrinter(@PathVariable("id") UUID printerId) {
        List<PrinterEmployeeResult> res = this.printerService.findAllEmployeeOfPrinter(new PrinterId(printerId));
        return AppResponse.initRespose(HttpStatus.OK, true, "Get all employee of printer success", res);
    }

}
