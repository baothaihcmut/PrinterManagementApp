package com.printerapp.infrastructure.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.common.filter.FilterParam;
import com.printerapp.domain.common.pagination.PaginatedParam;
import com.printerapp.domain.common.pagination.PaginatedResult;
import com.printerapp.domain.common.sort.SortParam;
import com.printerapp.domain.enums.PrinterStatus;
import com.printerapp.domain.repositories.PrinterRepository;
import com.printerapp.infrastructure.persistence.mappers.InfraPrinterMapper;
import com.printerapp.infrastructure.persistence.models.Printer;
import com.printerapp.infrastructure.persistence.models.PrinterEmployee;
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
public class JpaPrinterRepository implements PrinterRepository {
        @PersistenceContext
        private EntityManager entityManager;
        private final InfraPrinterMapper printerMapper;

        @Override
        public void save(com.printerapp.domain.aggregates.printer.Printer printer) {
                Printer printerModel = this.printerMapper.toPersistence(printer);
                this.entityManager.merge(printerModel);

        }

        @Override
        public Optional<com.printerapp.domain.aggregates.printer.Printer> findById(PrinterId id) {
                CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
                CriteriaQuery<Printer> cq = cb.createQuery(Printer.class);
                Root<Printer> root = cq.from(Printer.class);
                cq.select(
                                cb.construct(Printer.class,
                                                root.get("id"),
                                                root.get("name"),
                                                root.get("code"),
                                                root.get("location"),
                                                root.get("status")))
                                .where(cb.equal(root.get("id"), id.getValue()));
                try {
                        // find printer
                        Printer res = this.entityManager.createQuery(cq).getSingleResult();
                        // map res to Printer domain
                        com.printerapp.domain.aggregates.printer.Printer printerDomain = this.printerMapper
                                        .toDomain(res);
                        // find transaction array of id
                        // find employee Id
                        return Optional.of(printerDomain);
                } catch (NoResultException e) {
                        return Optional.empty();
                }
        }

        @Override
        public PaginatedResult<com.printerapp.domain.aggregates.printer.Printer> findAll(PrinterStatus status,
                        PaginatedParam paginatedParam) {
                CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
                CriteriaQuery<Printer> cq = cb.createQuery(Printer.class);
                Root<Printer> root = cq.from(Printer.class);
                cq.select(
                                cb.construct(Printer.class,
                                                root.get("id"),
                                                root.get("name"),
                                                root.get("code"),
                                                root.get("location"),
                                                root.get("status")));
                if (status != null) {
                        cq.where(cb.equal(root.get("status"), status));
                }
                Integer offset = (paginatedParam.getPage() - 1) * paginatedParam.getSize();
                List<com.printerapp.domain.aggregates.printer.Printer> res = this.entityManager.createQuery(cq)
                                .setFirstResult(offset)
                                .setMaxResults(paginatedParam.getSize()).getResultList().stream()
                                .map((printer) -> this.printerMapper.toDomain(printer)).toList();
                CriteriaQuery<Long> cqTotal = cb.createQuery(Long.class);
                Root<Printer> countRoot = cqTotal.from(Printer.class);
                cqTotal.select(cb.count(countRoot));
                if (status != null) {
                        cqTotal.where(cb.equal(countRoot.get("status"), status));
                }
                Long totalElement = this.entityManager.createQuery(cqTotal).getSingleResult();
                return PaginatedResult.<com.printerapp.domain.aggregates.printer.Printer>of(res,
                                paginatedParam.getPage(),
                                paginatedParam.getSize(), totalElement);
        }

        public List<com.printerapp.domain.aggregates.printer.Printer> findAllPrinterOfEmployee(UserId employeeId) {
                CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
                CriteriaQuery<Printer> cq = cb.createQuery(Printer.class);
                Root<Printer> root = cq.from(Printer.class);
                Join<Printer, PrinterEmployee> employeeJoin = root.join("employees", JoinType.LEFT);
                cq.select(
                                cb.construct(Printer.class,
                                                root.get("id"),
                                                root.get("name"),
                                                root.get("code"),
                                                root.get("location"),
                                                root.get("status")))
                                .where(cb.equal(employeeJoin.get("employee").get("id"), employeeId.getValue()))
                                .distinct(true);
                return this.entityManager.createQuery(cq).getResultList().stream()
                                .map((printer) -> this.printerMapper.toDomain(printer)).toList();
        }

        @Override
        public PaginatedResult<com.printerapp.domain.aggregates.printer.Printer> findByCriteria(
                        List<FilterParam<?>> filters, List<SortParam> sorts, PaginatedParam paginated) {
                CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
                CriteriaQuery<Printer> cq = cb.createQuery(Printer.class);
                Root<Printer> root = cq.from(Printer.class);
                cq.select(
                                cb.construct(Printer.class,
                                                root.get("id"),
                                                root.get("name"),
                                                root.get("code"),
                                                root.get("location"),
                                                root.get("status")));
                // filter
                cq.where(cb.and(Util.buidFilter(cb, filters, root).toArray(new Predicate[0])));

                // sort
                cq.orderBy(Util.buidSort(cb, sorts, root));
                int offset = (paginated.getPage() - 1) * paginated.getSize();
                List<com.printerapp.domain.aggregates.printer.Printer> printers = this.entityManager.createQuery(cq)
                                .setFirstResult(offset)
                                .setMaxResults(paginated.getSize()).getResultList()
                                .stream()
                                .map((printer) -> this.printerMapper.toDomain(printer)).toList();
                long count = Util.<Printer>countByCriteria(entityManager, cb, null, filters, Printer.class);
                return PaginatedResult.of(printers, paginated.getPage(), paginated.getSize(), count);

        }

        public PaginatedResult<com.printerapp.domain.aggregates.printer.Printer> search(
                        List<FilterParam<?>> search,
                        List<FilterParam<?>> criteria, List<SortParam> sorts, PaginatedParam paginated) {
                CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
                CriteriaQuery<Printer> cq = cb.createQuery(Printer.class);
                Root<Printer> root = cq.from(Printer.class);
                cq.select(
                                cb.construct(Printer.class,
                                                root.get("id"),
                                                root.get("name"),
                                                root.get("code"),
                                                root.get("location"),
                                                root.get("status")));
                // filter
                cq.where(cb.and(
                                cb.or(Util.buidFilter(cb, search, root).toArray(new Predicate[0])),
                                cb.and(Util.buidFilter(cb, criteria, root).toArray(new Predicate[0]))));

                // sort
                cq.orderBy(Util.buidSort(cb, sorts, root));
                int offset = (paginated.getPage() - 1) * paginated.getSize();
                List<com.printerapp.domain.aggregates.printer.Printer> printers = this.entityManager.createQuery(cq)
                                .setFirstResult(offset)
                                .setMaxResults(paginated.getSize()).getResultList()
                                .stream()
                                .map((printer) -> this.printerMapper.toDomain(printer)).toList();
                long count = Util.<Printer>countByCriteria(entityManager, cb, search, criteria, Printer.class);
                return PaginatedResult.of(printers, paginated.getPage(), paginated.getSize(), count);
        }

}
