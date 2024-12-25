package com.printerapp.interfaces.rest.controllers;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.printerapp.application.commands.transactions.UpdateTransactionStatusCommand;
import com.printerapp.application.queries.transactions.FindPrinterOfTransactionQuery;
import com.printerapp.application.queries.transactions.FindTransactionByIdQuery;
import com.printerapp.application.queries.transactions.FindTransactionQuery;
import com.printerapp.application.queries.transactions.FindUserOfTransactionQuery;
import com.printerapp.application.results.transactions.TransactionPrinterResult;
import com.printerapp.application.results.transactions.TransactionResult;
import com.printerapp.application.results.transactions.TransactionUserResult;
import com.printerapp.application.services.TransactionService;
import com.printerapp.domain.aggregates.transaction.value_objecs.TransactionId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.common.pagination.PaginatedParam;
import com.printerapp.domain.common.pagination.PaginatedResult;
import com.printerapp.domain.common.sort.SortParam;
import com.printerapp.domain.enums.PrintTransactionStatus;
import com.printerapp.domain.enums.Role;
import com.printerapp.domain.records.transactions.TransactionRecord;
import com.printerapp.interfaces.rest.common.response.AppResponse;
import com.printerapp.interfaces.rest.dtos.transactions.CreateTransactionDTO;
import com.printerapp.interfaces.rest.mappers.RestTransactionMapper;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transactions")
@RequiredArgsConstructor
public class TransactionController {
        private final TransactionService transactionService;
        private final RestTransactionMapper transactionMapper;

        @PostMapping("/create")
        public ResponseEntity<AppResponse> createTransaction(
                        @RequestBody CreateTransactionDTO createTransactionDTO) throws Exception {
                TransactionResult res = this.transactionService
                                .createTransaction(this.transactionMapper.toCommand(createTransactionDTO));
                return AppResponse.initRespose(HttpStatus.CREATED, true, "Create Transaction success", res);
        }

        @GetMapping("/{id}")
        public ResponseEntity<AppResponse> getTransactionById(
                        @PathVariable("id") UUID id) throws Exception {
                TransactionResult res = this.transactionService
                                .findById(FindTransactionByIdQuery.builder().transactionId(new TransactionId(id))
                                                .build());
                return AppResponse.initRespose(HttpStatus.OK, true, "Get transaction by id success", res);
        }

        @GetMapping("/{id}/customer")
        public ResponseEntity<AppResponse> getCustomerOfTransaction(@PathVariable("id") UUID id) {
                TransactionUserResult res = this.transactionService.findUserOfTransaction(
                                FindUserOfTransactionQuery.builder().transactionId(new TransactionId(id))
                                                .role(Role.CUSTOMER).build());
                return AppResponse.initRespose(HttpStatus.OK, true, "Get customer of transaction success", res);
        }

        @GetMapping("/{id}/employee")
        public ResponseEntity<AppResponse> getEmployeeOfTransaction(@PathVariable("id") UUID id) {
                TransactionUserResult res = this.transactionService.findUserOfTransaction(
                                FindUserOfTransactionQuery.builder().transactionId(new TransactionId(id))
                                                .role(Role.EMPLOYEE).build());
                return AppResponse.initRespose(HttpStatus.OK, true, "Get employee of transaction success", res);
        }

        @GetMapping("/{id}/printer")
        public ResponseEntity<AppResponse> getPrinterOfTransaction(@PathVariable("id") UUID id) {
                TransactionPrinterResult res = this.transactionService.findPrinterOfTransaction(
                                FindPrinterOfTransactionQuery.builder().transactionId(new TransactionId(id)).build());
                return AppResponse.initRespose(HttpStatus.OK, true, "Get printer of transaction success", res);
        }

        @PatchMapping("/{id}/{status:accept|done}")
        @PreAuthorize("hasAnyRole('ROLE_employee')")
        public ResponseEntity<AppResponse> getTransactionByStatus(
                        @PathVariable UUID id,
                        @PathVariable String status) {
                TransactionResult transactionResult = this.transactionService.updateTransactionStatus(
                                UpdateTransactionStatusCommand.builder().transactionId(new TransactionId(id))
                                                .status(status.equals("accept") ? PrintTransactionStatus.PROCESS
                                                                : PrintTransactionStatus.DONE)
                                                .build());
                return AppResponse.initRespose(HttpStatus.CREATED, true, "Update transaction status success",
                                transactionResult);
        }

        @GetMapping("")
        @PreAuthorize("hasAnyRole('ROLE_admin')")
        public ResponseEntity<AppResponse> findTransaction(
                        @RequestParam(value = "status", required = false) PrintTransactionStatus status,
                        @RequestParam(value = "sort", required = false) SortParam sortParam,
                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "size", defaultValue = "10") Integer size) {
                PaginatedResult<TransactionRecord> res = this.transactionService
                                .findTransaction(FindTransactionQuery.builder().status(status).sortParam(sortParam)
                                                .paginatedParam(PaginatedParam.builder().page(page).size(size).build())
                                                .build());
                return AppResponse.initRespose(HttpStatus.OK, true, "Get all transaction success", res);
        }


        @GetMapping("/{role:customer|employee}/{id}")
        @PreAuthorize("hasRole('ROLE_admin')")
        public ResponseEntity<AppResponse> findTransactionOfUser(
                        @PathVariable("id") UUID userId,
                        @PathVariable("role") String role,
                        @RequestParam(value = "sort", required = false) SortParam sortParam,
                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "size", defaultValue = "10") Integer size) {
                PaginatedResult<TransactionRecord> res = this.transactionService
                                .findTransaction(
                                                FindTransactionQuery.builder()
                                                                .userId(new UserId(userId))
                                                                .role(role.equals("customer") ? Role.CUSTOMER
                                                                                : Role.EMPLOYEE)
                                                                .sortParam(sortParam)
                                                                .paginatedParam(PaginatedParam.builder().page(page)
                                                                                .size(size).build())
                                                                .build());
                return AppResponse.initRespose(HttpStatus.OK, true, "Get transaction of user success", res);
        }
}
