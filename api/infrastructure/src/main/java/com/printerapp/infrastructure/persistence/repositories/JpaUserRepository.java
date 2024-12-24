package com.printerapp.infrastructure.persistence.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.printerapp.domain.aggregates.printer.value_objects.PrinterId;
import com.printerapp.domain.aggregates.printer_employee.PrinterEmployee;
import com.printerapp.domain.aggregates.user.value_objects.Email;
import com.printerapp.domain.aggregates.user.value_objects.UserId;
import com.printerapp.domain.common.filter.FilterParam;
import com.printerapp.domain.common.pagination.PaginatedParam;
import com.printerapp.domain.common.pagination.PaginatedResult;
import com.printerapp.domain.common.sort.SortParam;
import com.printerapp.domain.enums.Role;
import com.printerapp.domain.repositories.UserRepository;
import com.printerapp.infrastructure.persistence.mappers.InfraCustomerMapper;
import com.printerapp.infrastructure.persistence.mappers.InfraEmployeeMapper;
import com.printerapp.infrastructure.persistence.mappers.InfraUserMapper;
import com.printerapp.infrastructure.persistence.models.Customer;
import com.printerapp.infrastructure.persistence.models.Employee;
import com.printerapp.infrastructure.persistence.models.User;
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
public class JpaUserRepository implements UserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private final InfraUserMapper userMapper;
    private final InfraCustomerMapper customerMapper;
    private final InfraEmployeeMapper employeeMapper;

    @Override
    public void save(com.printerapp.domain.aggregates.user.User userDomain) {
        User user = this.userMapper.toPersistenct(userDomain);
        user = this.entityManager.merge(user);
        if (userDomain.getRole().equals(Role.CUSTOMER)) {
            Customer customerModel = this.customerMapper.toPersistence(userDomain.getCustomer());
            customerModel.setUser(user);
            customerModel.setId(user.getId());
            this.entityManager.merge(customerModel);
        } else if (userDomain.getRole().equals(Role.EMPLOYEE)) {
            Employee employeeModel = this.employeeMapper.toPersistence(userDomain.getEmployee());
            employeeModel.setId(user.getId());
            employeeModel.setUser(user);
            this.entityManager.merge(employeeModel);
        }
    }

    @Override
    public Optional<com.printerapp.domain.aggregates.user.User> findById(UserId userId) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Join<User, Customer> customerJoin = root.join("customer", JoinType.LEFT);
        Join<User, Employee> employeeJoin = root.join("employee", JoinType.LEFT);
        cq.select(
                cb.construct(
                        User.class,
                        root.get("id"),
                        root.get("firstName"),
                        root.get("lastName"),
                        root.get("email"),
                        root.get("phoneNumber"),
                        root.get("role"),
                        root.get("isActive"),
                        customerJoin.get("customerId"),
                        customerJoin.get("customerType"),
                        customerJoin.get("numOfPapersA3"),
                        customerJoin.get("numOfPapersA4"),
                        customerJoin.get("numOfPapersA5"),
                        employeeJoin.get("employeeId"),
                        employeeJoin.get("isOnWork"),
                        employeeJoin.get("startWorkDate")));
        cq.where(cb.equal(root.get("id"), userId.getValue()));
        try {
            User res = this.entityManager.createQuery(cq).getSingleResult();
            return Optional.of(this.userMapper.toDomain(res));
        } catch (NoResultException e) {
            return Optional.empty();
        }

    }

    @Override
    public Optional<com.printerapp.domain.aggregates.user.User> findByEmail(Email email) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Join<User, Customer> customerJoin = root.join("customer", JoinType.LEFT);
        Join<User, Employee> employeeJoin = root.join("employee", JoinType.LEFT);
        cq.select(
                cb.construct(
                        User.class,
                        root.get("id"),
                        root.get("firstName"),
                        root.get("lastName"),
                        root.get("email"),
                        root.get("phoneNumber"),
                        root.get("role"),
                        root.get("isActive"),
                        customerJoin.get("customerId"),
                        customerJoin.get("customerType"),
                        customerJoin.get("numOfPapersA3"),
                        customerJoin.get("numOfPapersA4"),
                        customerJoin.get("numOfPapersA5"),
                        employeeJoin.get("employeeId"),
                        employeeJoin.get("isOnWork"),
                        employeeJoin.get("startWorkDate")));
        cq.where(cb.equal(root.get("email"), email.getValue()));
        try {
            User res = this.entityManager.createQuery(cq).getSingleResult();
            return Optional.of(this.userMapper.toDomain(res));
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    public PaginatedResult<com.printerapp.domain.aggregates.user.User> findByCriteria(List<FilterParam<?>> filters,
            List<SortParam> sorts,
            PaginatedParam paginatedParam) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Join<User, Customer> customerJoin = root.join("customer", JoinType.LEFT);
        Join<User, Employee> employeeJoin = root.join("employee", JoinType.LEFT);
        cq.select(
                cb.construct(
                        User.class,
                        root.get("id"),
                        root.get("firstName"),
                        root.get("lastName"),
                        root.get("email"),
                        root.get("phoneNumber"),
                        root.get("role"),
                        root.get("isActive"),
                        customerJoin.get("customerId"),
                        customerJoin.get("customerType"),
                        customerJoin.get("numOfPapersA3"),
                        customerJoin.get("numOfPapersA4"),
                        customerJoin.get("numOfPapersA5"),
                        employeeJoin.get("employeeId"),
                        employeeJoin.get("isOnWork"),
                        employeeJoin.get("startWorkDate")));
        // filter
        cq.where(cb.and(Util.buidFilter(cb, filters, root).toArray(new Predicate[0])));

        // sort
        cq.orderBy(Util.buidSort(cb, sorts, root));
        int offset = (paginatedParam.getPage() - 1) * paginatedParam.getSize();
        List<com.printerapp.domain.aggregates.user.User> res = this.entityManager.createQuery(cq).setFirstResult(offset)
                .setMaxResults(paginatedParam.getSize()).getResultList().stream()
                .map((user) -> this.userMapper.toDomain(user)).toList();
        long count = Util.countByCriteria(entityManager, cb, null, filters, User.class);
        return PaginatedResult.of(res, paginatedParam.getPage(), paginatedParam.getSize(), count);
    }

    @Override
    public List<com.printerapp.domain.aggregates.user.User> findAllEmployeeOfPrinter(PrinterId printerId) {
        CriteriaBuilder cb = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);
        Join<User, Employee> employeeJoin = root.join("employee", JoinType.INNER);
        Join<Employee, PrinterEmployee> printerEmployeeJoin = employeeJoin.join("printers", JoinType.INNER);
        cq.select(
                cb.construct(
                        User.class,
                        root.get("id"),
                        root.get("firstName"),
                        root.get("lastName"),
                        root.get("email"),
                        root.get("phoneNumber"),
                        root.get("role"),
                        root.get("isActive"),
                        employeeJoin.get("employeeId"),
                        employeeJoin.get("isOnWork"),
                        employeeJoin.get("startWorkDate")));
        cq.where(cb.equal(printerEmployeeJoin.get("printer").get("id"), printerId.getValue()));
        return this.entityManager.createQuery(cq).getResultList().stream()
                .map((entity) -> this.userMapper.toDomain(entity)).toList();
    }
}
