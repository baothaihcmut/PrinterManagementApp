package com.printerapp.infrastructure.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.printer_employee.value_objects.PrinterEmployeeId;
import com.printerapp.domain.aggregates.user.User;
import com.printerapp.domain.aggregates.user.value_objects.Email;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.aggregates.user.value_objects.UserName;
import com.printerapp.domain.enums.Role;
import com.printerapp.domain.records.printer_employee.EmployeeRecord;
import com.printerapp.domain.records.printer_employee.PrinterEmployeeRecord;
import com.printerapp.domain.records.printer_employee.PrinterRecord;
import com.printerapp.domain.repositories.PrinterEmployeeRepository;
import com.printerapp.infrastructure.persistence.mappers.InfraPrinterEmployeeMapper;
import com.printerapp.infrastructure.persistence.models.Employee;
import com.printerapp.infrastructure.persistence.models.Printer;
import com.printerapp.infrastructure.persistence.models.PrinterEmployee;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaPrinterEmployeeRepository implements PrinterEmployeeRepository {
        @PersistenceContext
        private EntityManager entityManager;

        private final InfraPrinterEmployeeMapper printerEmployeeMapper;

        @Override
        public void save(com.printerapp.domain.aggregates.printer_employee.PrinterEmployee printerEmployee) {
                PrinterEmployee model = this.printerEmployeeMapper.toPersistence(printerEmployee);
                System.out.println(model);
                model.setEmployee(
                                this.entityManager.getReference(Employee.class,
                                                printerEmployee.getId().getEmployeeId().getValue()));
                model.setPrinter(
                                this.entityManager.getReference(Printer.class,
                                                printerEmployee.getId().getPrinterId().getValue()));
                this.entityManager.merge(model);
        }

        @Override
        public Optional<com.printerapp.domain.aggregates.printer_employee.PrinterEmployee> findById(
                        PrinterEmployeeId printerEmployeeId) {
                CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
                CriteriaQuery<PrinterEmployee> cq = cb.createQuery(PrinterEmployee.class);
                Root<PrinterEmployee> root = cq.from(PrinterEmployee.class);
                Join<PrinterEmployee, Printer> joinPrinter = root.join("printer");
                cq.select(
                                cb.construct(PrinterEmployee.class,
                                                root.get("id"),
                                                root.get("employee").get("id"),
                                                joinPrinter.get("id"),
                                                joinPrinter.get("name"),
                                                joinPrinter.get("location"),
                                                joinPrinter.get("code"),
                                                joinPrinter.get("status"),
                                                root.get("isManager"),
                                                root.get("numOfTransactionProcess"),
                                                root.get("numOfTransactionDone")));
                cq.where(cb.and(cb.equal(root.get("printer").get("id"), printerEmployeeId.getPrinterId().getValue()),
                                cb.equal(root.get("employee").get("id"),
                                                printerEmployeeId.getEmployeeId().getValue())));
                try {
                        PrinterEmployee res = this.entityManager.createQuery(cq).getSingleResult();
                        return Optional.of(this.printerEmployeeMapper.toDomain(res));
                } catch (NoResultException e) {
                        return Optional.empty();
                }

        }

        @Override
        public List<PrinterEmployeeRecord> findByIdEmployeeId(
                        UserId employeeId) {
                CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
                CriteriaQuery<PrinterEmployee> cq = cb.createQuery(PrinterEmployee.class);
                Root<PrinterEmployee> root = cq.from(PrinterEmployee.class);
                Join<PrinterEmployee, Printer> joinPrinter = root.join("printer", JoinType.INNER);
                Join<PrinterEmployee, Employee> joinEmployee = root.join("employee", JoinType.INNER);
                Join<Employee, User> joinUser = joinEmployee.join("user", JoinType.LEFT);
                cq.select(
                                cb.construct(PrinterEmployee.class,
                                                root.get("id"),
                                                joinEmployee.get("id"),
                                                joinUser.get("firstName"),
                                                joinUser.get("lastName"),
                                                joinUser.get("email"),
                                                joinPrinter.get("id"),
                                                joinPrinter.get("name"),
                                                joinPrinter.get("location"),
                                                joinPrinter.get("code"),
                                                joinPrinter.get("status"),
                                                root.get("isManager"),
                                                root.get("numOfTransactionProcess"),
                                                root.get("numOfTransactionDone")));
                cq.where(cb.equal(joinEmployee.get("user").get("id"), employeeId.getValue()));
                List<PrinterEmployee> res = this.entityManager.createQuery(cq).getResultList();
                return res.stream().map((entity) -> new PrinterEmployeeRecord(
                                new PrinterRecord(
                                                new PrinterId(entity.getPrinter().getId()),
                                                new Printer().getName(),
                                                entity.getPrinter().getLocation(),
                                                entity.getPrinter().getCode(),
                                                entity.getPrinter().getStatus()),
                                new EmployeeRecord(new UserId(entity.getEmployee().getId()),
                                                new UserName(entity.getEmployee().getUser().getFirstName(),
                                                                entity.getEmployee().getUser().getLastName()),
                                                new Email(entity.getEmployee().getUser().getEmail(), Role.EMPLOYEE)),
                                this.printerEmployeeMapper.toDomain(entity))).toList();

        }

        @Override
        public List<PrinterEmployeeRecord> findByPrinterId(
                        PrinterId printerId) {
                CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
                CriteriaQuery<PrinterEmployee> cq = cb.createQuery(PrinterEmployee.class);
                Root<PrinterEmployee> root = cq.from(PrinterEmployee.class);
                Join<PrinterEmployee, Printer> joinPrinter = root.join("printer", JoinType.INNER);
                Join<PrinterEmployee, Employee> joinEmployee = root.join("employee", JoinType.INNER);
                Join<Employee, User> joinUser = joinEmployee.join("user", JoinType.LEFT);
                cq.select(
                                cb.construct(PrinterEmployee.class,
                                                root.get("id"),
                                                joinEmployee.get("id"),
                                                joinEmployee.get("employeeId"),
                                                joinEmployee.get("isOnWork"),
                                                joinEmployee.get("startWorkDate"),
                                                joinUser.get("firstName"),
                                                joinUser.get("lastName"),
                                                joinUser.get("email"),
                                                joinUser.get("phoneNumber"),
                                                joinUser.get("role"),
                                                joinUser.get("isActive"),
                                                joinPrinter.get("id"),
                                                joinPrinter.get("name"),
                                                joinPrinter.get("location"),
                                                joinPrinter.get("code"),
                                                joinPrinter.get("status"),
                                                root.get("isManager"),
                                                root.get("numOfTransactionProcess"),
                                                root.get("numOfTransactionDone")));
                cq.where(cb.equal(joinPrinter.get("id"), printerId.getValue()));
                List<PrinterEmployee> res = this.entityManager.createQuery(cq).getResultList();
                return res.stream().map((entity) -> new PrinterEmployeeRecord(
                                new PrinterRecord(
                                                new PrinterId(entity.getPrinter().getId()),
                                                new Printer().getName(),
                                                entity.getPrinter().getLocation(),
                                                entity.getPrinter().getCode(),
                                                entity.getPrinter().getStatus()),
                                new EmployeeRecord(new UserId(entity.getEmployee().getId()),
                                                new UserName(entity.getEmployee().getUser().getFirstName(),
                                                                entity.getEmployee().getUser().getLastName()),
                                                new Email(entity.getEmployee().getUser().getEmail(), Role.EMPLOYEE)),
                                this.printerEmployeeMapper.toDomain(entity))).toList();
        }

}
