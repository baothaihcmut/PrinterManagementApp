package com.printerapp.infrastructure.persistence.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.printerapp.domain.aggregates.transaction.Transaction;
import com.printerapp.domain.aggregates.transaction.value_objecs.TransactionId;
import com.printerapp.domain.aggregates.user.User;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.aggregates.user.value_objects.UserName;
import com.printerapp.domain.common.filter.FilterParam;
import com.printerapp.domain.common.pagination.PaginatedParam;
import com.printerapp.domain.common.pagination.PaginatedResult;
import com.printerapp.domain.common.sort.SortParam;
import com.printerapp.domain.enums.Role;
import com.printerapp.domain.records.transactions.TransactionRecord;
import com.printerapp.domain.records.transactions.UserRecord;
import com.printerapp.domain.repositories.TransactionRepositoy;
import com.printerapp.infrastructure.persistence.mappers.InfraTransactionMapper;
import com.printerapp.infrastructure.persistence.models.Customer;
import com.printerapp.infrastructure.persistence.models.Document;
import com.printerapp.infrastructure.persistence.models.Employee;
import com.printerapp.infrastructure.persistence.models.PrintTransaction;
import com.printerapp.infrastructure.persistence.models.Printer;
import com.printerapp.infrastructure.persistence.models.TransactionDocument;
import com.printerapp.infrastructure.persistence.utils.Util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class JpaTransactionRepository implements TransactionRepositoy {

        @PersistenceContext
        private EntityManager entityManager;

        private final InfraTransactionMapper transactionMapper;

        private List<TransactionDocument> findDocuments(UUID transactionId) {
                CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
                CriteriaQuery<TransactionDocument> cq = cb.createQuery(TransactionDocument.class);
                Root<TransactionDocument> root = cq.from(TransactionDocument.class);
                Join<TransactionDocument, Document> joinDocument = root.join("document", JoinType.LEFT);
                cq.select(
                                cb.construct(
                                                TransactionDocument.class,
                                                root.get("id"),
                                                joinDocument.get("id"),
                                                joinDocument.get("link"),
                                                joinDocument.get("name"),
                                                root.get("paperType"),
                                                root.get("numOfCopies"),
                                                root.get("isLandscape"),
                                                root.get("fromPage"),
                                                root.get("toPage"),
                                                root.get("leftSide"),
                                                root.get("rightSide"),
                                                root.get("topSide"),
                                                root.get("bottomSide"),
                                                root.get("isOneSide"),
                                                root.get("numOfPageOneSide")))
                                .where(cb.equal(root.get("transaction").get("id"), transactionId));
                return this.entityManager.createQuery(cq).getResultList();
        }

        @Override
        public void save(Transaction transaction) {
                PrintTransaction transactionModel = this.transactionMapper.toPersistence(transaction);
                // Get ref of transaction
                Customer customerRef = this.entityManager.getReference(Customer.class,
                                transactionModel.getCustomer().getId());
                Employee employeeRef = transactionModel.getEmployee().getId() != null
                                ? this.entityManager.getReference(Employee.class,
                                                transactionModel.getEmployee().getId())
                                : null;
                Printer printerRef = this.entityManager.getReference(Printer.class,
                                transactionModel.getPrinter().getId());
                // set ref for transaction
                transactionModel.setCustomer(customerRef);
                transactionModel.setEmployee(employeeRef);
                transactionModel.setPrinter(printerRef);

                // Get ref for transaction document
                transactionModel.getDocuments().forEach((document) -> {
                        document.setDocument(this.entityManager.getReference(Document.class,
                                        document.getDocument().getId()));
                        document.setTransaction(transactionModel);
                });
                // save
                this.entityManager.merge(transactionModel);

        }

        @Override
        public List<Transaction> findAll() {
                return null;
        }

        @Override
        public Optional<Transaction> findById(TransactionId transactionId) {
                CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
                CriteriaQuery<PrintTransaction> cq = cb.createQuery(PrintTransaction.class);
                Root<PrintTransaction> root = cq.from(PrintTransaction.class);
                Join<PrintTransaction, Customer> customerJoin = root.join("customer", JoinType.LEFT);
                Join<Customer, User> customerUserJoin = customerJoin.join("user", JoinType.LEFT);
                Join<PrintTransaction, Employee> employeeJoin = root.join("employee", JoinType.LEFT);
                Join<Employee, User> employeeUserJoin = employeeJoin.join("user", JoinType.LEFT);

                cq.select(
                                cb.construct(PrintTransaction.class,
                                                root.get("id"),
                                                customerJoin.get("id"),
                                                customerUserJoin.get("firstName"),
                                                customerUserJoin.get("lastName"),
                                                employeeJoin.get("id"),
                                                employeeUserJoin.get("firstName"),
                                                employeeUserJoin.get("lastName"),
                                                root.get("printer").get("id"),
                                                root.get("name"),
                                                root.get("status"),
                                                root.get("transactionId"),
                                                root.get("totalNumOfPaperA3"),
                                                root.get("totalNumOfPaperA4"),
                                                root.get("totalNumOfPaperA5"),
                                                root.get("createdAt"),
                                                root.get("updatedAt"),
                                                root.get("acceptedAt"),
                                                root.get("doneAt")))
                                .where(cb.equal(root.get("id"), transactionId.getValue()));
                try {
                        PrintTransaction transaction = this.entityManager.createQuery(cq).setFirstResult(0)
                                        .getSingleResult();
                        Transaction domain = this.transactionMapper.toDomain(transaction);
                        domain.setTransactionDocuments(this.findDocuments(transaction.getId()).stream()
                                        .map((document) -> this.transactionMapper.toTransactionDocumentDomain(document))
                                        .toList());
                        System.out.println(domain.getTransactionDocuments().size());
                        return Optional.of(domain);

                } catch (NoResultException e) {
                        return Optional.empty();
                }

        }

        @Override
        public PaginatedResult<TransactionRecord> findByCriteria(List<FilterParam<?>> filters, List<SortParam> sorts,
                        PaginatedParam pagniate) {
                CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
                CriteriaQuery<PrintTransaction> cq = cb.createQuery(PrintTransaction.class);
                Root<PrintTransaction> root = cq.from(PrintTransaction.class);
                Join<PrintTransaction, Customer> customerJoin = root.join("customer", JoinType.LEFT);
                Join<Customer, User> customerUserJoin = customerJoin.join("user", JoinType.LEFT);
                Join<PrintTransaction, Employee> employeeJoin = root.join("employee", JoinType.LEFT);
                Join<Employee, User> employeeUserJoin = employeeJoin.join("user", JoinType.LEFT);

                cq.select(
                                cb.construct(PrintTransaction.class,
                                                root.get("id"),
                                                customerJoin.get("id"),
                                                customerUserJoin.get("firstName"),
                                                customerUserJoin.get("lastName"),
                                                employeeJoin.get("id"),
                                                employeeUserJoin.get("firstName"),
                                                employeeUserJoin.get("lastName"),
                                                root.get("printer").get("id"),
                                                root.get("name"),
                                                root.get("status"),
                                                root.get("transactionId"),
                                                root.get("totalNumOfPaperA3"),
                                                root.get("totalNumOfPaperA4"),
                                                root.get("totalNumOfPaperA5"),
                                                root.get("createdAt"),
                                                root.get("updatedAt"),
                                                root.get("acceptedAt"),
                                                root.get("doneAt")));
                // filter
                cq.where(cb.and(Util.buidFilter(cb, filters, root).toArray(new Predicate[0])));
                // sort
                cq.orderBy(Util.buidSort(cb, sorts, root));
                int offset = (pagniate.getPage() - 1) * pagniate.getSize();
                List<TransactionRecord> transactions = this.entityManager.createQuery(cq).setFirstResult(offset)
                                .setMaxResults(pagniate.getSize()).getResultList()
                                .stream()
                                .map((transaction) -> new TransactionRecord(
                                                this.transactionMapper.toDomain(transaction),
                                                transaction.getEmployee() != null
                                                                ? new UserRecord(
                                                                                new UserId(transaction.getEmployee()
                                                                                                .getId()),
                                                                                new UserName(transaction.getEmployee()
                                                                                                .getUser()
                                                                                                .getFirstName(),
                                                                                                transaction.getEmployee()
                                                                                                                .getUser()
                                                                                                                .getLastName()),
                                                                                Role.EMPLOYEE)
                                                                : null,
                                                new UserRecord(
                                                                new UserId(transaction.getCustomer().getId()),
                                                                new UserName(transaction.getCustomer().getUser()
                                                                                .getFirstName(),
                                                                                transaction.getEmployee().getUser()
                                                                                                .getLastName()),
                                                                Role.CUSTOMER)))
                                .toList();
                long count = Util.<PrintTransaction>countByCriteria(entityManager, cb, null, filters,
                                PrintTransaction.class);
                return PaginatedResult.of(transactions, pagniate.getPage(), pagniate.getSize(), count);

        }

}
