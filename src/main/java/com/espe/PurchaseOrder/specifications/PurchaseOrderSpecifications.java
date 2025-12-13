package com.espe.PurchaseOrder.specifications;

import com.espe.PurchaseOrder.models.entities.PurchaseOrder;
import com.espe.PurchaseOrder.enums.Currency;
import com.espe.PurchaseOrder.enums.Status;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PurchaseOrderSpecifications {

    public static Specification<PurchaseOrder> textSearch(String q) {
        return (root, query, cb) -> {
            if (q == null || q.isBlank()) return cb.conjunction();
            String like = "%" + q.toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("orderNumber")), like),
                    cb.like(cb.lower(root.get("supplierName")), like)
            );
        };
    }

    public static Specification<PurchaseOrder> hasStatus(Status status) {
        return (root, query, cb) ->
                status == null ? cb.conjunction()
                        : cb.equal(root.get("status"), status);
    }

    public static Specification<PurchaseOrder> hasCurrency(Currency currency) {
        return (root, query, cb) ->
                currency == null ? cb.conjunction()
                        : cb.equal(root.get("currency"), currency);
    }

    public static Specification<PurchaseOrder> minTotal(BigDecimal min) {
        return (root, query, cb) ->
                min == null ? cb.conjunction()
                        : cb.greaterThanOrEqualTo(root.get("totalAmount"), min);
    }

    public static Specification<PurchaseOrder> maxTotal(BigDecimal max) {
        return (root, query, cb) ->
                max == null ? cb.conjunction()
                        : cb.lessThanOrEqualTo(root.get("totalAmount"), max);
    }

    public static Specification<PurchaseOrder> dateBetween(
            LocalDateTime from, LocalDateTime to) {

        return (root, query, cb) -> {
            if (from == null && to == null) return cb.conjunction();
            if (from != null && to != null)
                return cb.between(root.get("createdAt"), from, to);
            if (from != null)
                return cb.greaterThanOrEqualTo(root.get("createdAt"), from);
            return cb.lessThanOrEqualTo(root.get("createdAt"), to);
        };
    }
}
