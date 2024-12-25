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

import com.printerapp.application.queries.customers.FindDocumentQuery;
import com.printerapp.application.queries.transactions.FindTransactionQuery;
import com.printerapp.application.results.cusomers.CustomerDocumentResult;
import com.printerapp.application.services.CustomerService;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.common.pagination.PaginatedParam;
import com.printerapp.domain.common.pagination.PaginatedResult;
import com.printerapp.domain.common.sort.SortParam;
import com.printerapp.domain.enums.PrintTransactionStatus;
import com.printerapp.domain.records.transactions.TransactionRecord;
import com.printerapp.interfaces.rest.common.response.AppResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {
        private final CustomerService customerService;

        @GetMapping("/documents")
        public ResponseEntity<AppResponse> findAllDocumentOfCustomer() {
                List<CustomerDocumentResult> customerDocumentResult = this.customerService.findDocumentOfUser();
                return AppResponse.initRespose(HttpStatus.OK, true, "Get document of user success",
                                customerDocumentResult);
        }

        @GetMapping("/{id}/documents")
        @PreAuthorize("hasRole('ROLE_admin')")
        public ResponseEntity<AppResponse> findDocumentOfCustomer(
                        @PathVariable("id") UUID id) {
                List<CustomerDocumentResult> customerDocumentResults = this.customerService.findDocumentOfUser(
                                FindDocumentQuery.builder().customerId(new UserId(id)).build());
                return AppResponse.initRespose(HttpStatus.OK, true, "Get document of customer success",
                                customerDocumentResults);
        }

        @GetMapping("/transactions")
        @PreAuthorize("hasAnyRole('ROLE_customer')")
        public ResponseEntity<AppResponse> findAllTransctionOfCustomer(
                        @RequestParam(value = "status", required = false) PrintTransactionStatus status,
                        @RequestParam(value = "sort", required = false) SortParam sortParam,
                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "size", defaultValue = "10") Integer size) {
                PaginatedResult<TransactionRecord> res = this.customerService
                                .findTractionOfCustomer(FindTransactionQuery.builder()
                                                .status(status)
                                                .sortParam(sortParam)
                                                .paginatedParam(PaginatedParam.builder().page(page).size(size).build())
                                                .build());
                return AppResponse.initRespose(HttpStatus.OK, true, "Get transaction of customer success", res);
        }

}
