package com.printerapp.infrastructure.persistence.utils;

import java.util.ArrayList;
import java.util.List;

import com.printerapp.domain.common.filter.FilterParam;
import com.printerapp.domain.common.sort.Direction;
import com.printerapp.domain.common.sort.SortParam;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Order;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public class Util {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static Predicate createPredicate(CriteriaBuilder cb, Path<?> fieldPath, FilterParam<?> filter) {
        switch (filter.getOperator()) {
            case EQUAL:
                return cb.equal(fieldPath, filter.getValue());
            case NOTEQUAL:
                return cb.like(fieldPath.as(String.class), "%" + filter.getValue() + "%");
            case GREATERTHAN:
                return cb.greaterThan(fieldPath.as(Comparable.class), (Comparable) filter.getValue());
            case LESSTHAN:
                return cb.lessThan(fieldPath.as(Comparable.class), (Comparable) filter.getValue());
            case LIKE:
                return cb.like(fieldPath.as(String.class), "%" + filter.getValue() + "%");
            default:
                throw new IllegalArgumentException("Unsupported operator: " + filter.getOperator());
        }
    }

    public static List<Predicate> buidFilter(CriteriaBuilder cb, List<FilterParam<?>> filters, Root<?> root) {
        if (filters == null) {
            return new ArrayList<>();
        }
        List<Predicate> predicates = new ArrayList<>();
        filters.forEach((filter) -> {
            String[] path = filter.getField().split("\\.");
            Path<?> fieldPath = root;
            for (String field : path) {
                fieldPath = fieldPath.get(field);
            }
            predicates.add(Util.createPredicate(cb, fieldPath, filter));
        });

        return predicates;
    }

    public static List<Order> buidSort(CriteriaBuilder cb, List<SortParam> sorts, Root<?> root) {
        if (sorts == null) {
            return new ArrayList<>();
        }
        List<Order> orders = new ArrayList<>();
        sorts.forEach((sort) -> {
            orders.add(sort.getDirection().equals(Direction.ASC) ? cb.asc(root.get(sort.getField()))
                    : cb.desc(root.get(sort.getField())));
        });
        return orders;
    }

    public static <T> long countByCriteria(EntityManager entityManager, CriteriaBuilder cb,
            List<FilterParam<?>> search,
            List<FilterParam<?>> criteria,
            Class<T> entity) {
        CriteriaQuery<Long> cqTotal = cb.createQuery(Long.class);
        Root<T> countRoot = cqTotal.from(entity);
        cqTotal.select(cb.count(countRoot));
        cqTotal.where(
                cb.and(
                        cb.and(buidFilter(cb, criteria, countRoot).toArray(new Predicate[0])),
                        cb.or(buidFilter(cb, search, countRoot).toArray(new Predicate[0]))));

        return entityManager.createQuery(cqTotal).getSingleResult();
    }
}
