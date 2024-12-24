package com.printerapp.application.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.printerapp.application.commands.printers.AddEmployeeToPrinterCommand;
import com.printerapp.application.commands.printers.CreatePrinterCommand;
import com.printerapp.application.commands.printers.FindPrinterByStatusCommand;
import com.printerapp.application.commands.printers.UpdatePrinterCommand;
import com.printerapp.application.exceptions.NotFoundException;
import com.printerapp.application.mappers.ApplicationPrinterMapper;
import com.printerapp.application.queries.printers.FindAllTransactionOfPrinterQuery;
import com.printerapp.application.queries.printers.FindPrinterQuery;
import com.printerapp.application.queries.printers.SearchPrinterQuery;
import com.printerapp.application.results.printers.PrinterEmployeeResult;
import com.printerapp.application.results.printers.PrinterResult;
import com.printerapp.domain.aggregates.printer.Printer;
import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.printer_employee.PrinterEmployee;
import com.printerapp.domain.common.filter.FilterParam;
import com.printerapp.domain.common.filter.Operator;
import com.printerapp.domain.common.pagination.PaginatedResult;
import com.printerapp.domain.records.transactions.TransactionRecord;
import com.printerapp.domain.repositories.PrinterEmployeeRepository;
import com.printerapp.domain.repositories.PrinterRepository;
import com.printerapp.domain.repositories.TransactionRepositoy;
import com.printerapp.domain.repositories.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PrinterService {
        private final PrinterRepository printerRepository;
        private final TransactionRepositoy transactionRepositoy;
        private final ApplicationPrinterMapper applicationPrinterMapper;
        private final PrinterEmployeeRepository printerEmployeeRepository;
        private final UserRepository employeeRepository;

        @Transactional
        public PrinterResult createPrinter(CreatePrinterCommand createPrinterCommand) {
                Printer newPrinter = new Printer(
                                createPrinterCommand.getName(), createPrinterCommand.getCode(),
                                createPrinterCommand.getLocation(), createPrinterCommand.getStatus());
                this.printerRepository.save(newPrinter);
                return this.applicationPrinterMapper.toResult(newPrinter);
        }

        public PrinterResult findById(PrinterId printerId) {
                return this.applicationPrinterMapper.toResult(this.printerRepository.findById(printerId)
                                .orElseThrow(() -> new NotFoundException("Printer not exist")));
        }

        public PaginatedResult<PrinterResult> findAllPrinter(FindPrinterByStatusCommand command) {
                PaginatedResult<Printer> printers = this.printerRepository
                                .findAll(command.getStatus(), command.getPaginatedParam());
                List<PrinterResult> res = printers.getData().stream()
                                .map((printer) -> this.applicationPrinterMapper.toResult(printer))
                                .collect(Collectors.toList());
                return PaginatedResult.<PrinterResult>of(res, printers.getPage(), printers.getSize(),
                                printers.getTotalElements());
        }

        public PaginatedResult<TransactionRecord> findAllTransactionOfPrinter(
                        FindAllTransactionOfPrinterQuery query) {
                // filter for query
                List<FilterParam<?>> filters = new ArrayList<>();
                filters.add(FilterParam.builder().field("printer.id").operator(Operator.EQUAL)
                                .value(query.getPrinterId().getValue()).build());
                if (query.getStatus() != null) {
                        filters.add(FilterParam.builder().field("status").operator(Operator.EQUAL)
                                        .value(query.getStatus())
                                        .build());
                }
                // find in db
                return this.transactionRepositoy
                                .findByCriteria(
                                                filters,
                                                query.getSortParam() == null ? new ArrayList<>()
                                                                : List.of(query.getSortParam()),
                                                query.getPaginatedParam());

        }

        @Transactional
        public void addEmployeeToPrinter(AddEmployeeToPrinterCommand command) {
                Printer printer = this.printerRepository.findById(command.getPrinterId())
                                .orElseThrow(() -> new NotFoundException("Printer not exist"));
                PrinterEmployee printerEmployee = new PrinterEmployee(command.getEmployeeId(), command.getPrinterId(),
                                command.getIsManager(), printer.getName(), printer.getCode(), printer.getLocation(),
                                printer.getStatus());
                this.printerEmployeeRepository.save(printerEmployee);
        }

        public PaginatedResult<PrinterResult> searchPrinter(SearchPrinterQuery search) {
                return null;
        }

        public PaginatedResult<PrinterResult> findPrinter(FindPrinterQuery query) {
                List<FilterParam<?>> filters = new ArrayList<>();
                if (query.getStatus() != null) {
                        filters.add(FilterParam.builder().field("status").operator(Operator.EQUAL)
                                        .value(query.getStatus()).build());
                }
                PaginatedResult<Printer> res = this.printerRepository.findByCriteria(filters,
                                query.getSortParam() == null ? new ArrayList<>() : List.of(query.getSortParam()),
                                query.getPaginatedParam());
                return PaginatedResult.of(res.getData().stream()
                                .map((printer) -> this.applicationPrinterMapper.toResult(printer))
                                .toList(),
                                res.getPage(), res.getSize(), res.getTotalElements());
        }

        public List<PrinterEmployeeResult> findAllEmployeeOfPrinter(PrinterId printerId) {
                return this.employeeRepository.findAllEmployeeOfPrinter(printerId).stream()
                                .map((employee) -> new PrinterEmployeeResult(employee.getId(),
                                                employee.getName().getFirstName(), employee.getName().getLastName(),
                                                employee.getEmail()))
                                .toList();
        }

        @Transactional
        public PrinterResult updatePrinter(UpdatePrinterCommand command) {
                Printer printer = this.printerRepository.findById(command.getId())
                                .orElseThrow(() -> new NotFoundException("Printer not found"));
                // update
                if (command.getName() != null) {
                        printer.setName(command.getName());
                }
                if (command.getLocation() != null) {
                        printer.setLocation(command.getLocation());
                }
                if (command.getStatus() != null) {
                        printer.setStatus(command.getStatus());
                }
                // save
                this.printerRepository.save(printer);
                return this.applicationPrinterMapper.toResult(printer);
        }
}
