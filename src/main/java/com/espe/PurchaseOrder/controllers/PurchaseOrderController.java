package com.espe.PurchaseOrder.controllers;

import com.espe.PurchaseOrder.models.entities.PurchaseOrder;
import com.espe.PurchaseOrder.enums.Currency;
import com.espe.PurchaseOrder.enums.Status;
import com.espe.PurchaseOrder.services.PurchaseOrderService;
import com.espe.PurchaseOrder.specifications.PurchaseOrderSpecifications;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/purchase-orders")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService service;

    @PostMapping
    public ResponseEntity<PurchaseOrder> save(
            @Valid @RequestBody PurchaseOrder order) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(service.save(order));
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOrder>> getAll(
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Status status,
            @RequestParam(required = false) Currency currency,
            @RequestParam(required = false) BigDecimal minTotal,
            @RequestParam(required = false) BigDecimal maxTotal,
            @RequestParam(required = false) LocalDateTime from,
            @RequestParam(required = false) LocalDateTime to
    ) {

        if (minTotal != null && minTotal.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("minTotal inválido");

        if (maxTotal != null && maxTotal.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("maxTotal inválido");

        if (from != null && to != null && from.isAfter(to))
            throw new IllegalArgumentException("from no puede ser mayor que to");

        Specification<PurchaseOrder> spec = Specification
                .where(PurchaseOrderSpecifications.textSearch(q))
                .and(PurchaseOrderSpecifications.hasStatus(status))
                .and(PurchaseOrderSpecifications.hasCurrency(currency))
                .and(PurchaseOrderSpecifications.minTotal(minTotal))
                .and(PurchaseOrderSpecifications.maxTotal(maxTotal))
                .and(PurchaseOrderSpecifications.dateBetween(from, to));

        return ResponseEntity.ok(service.findAll(spec));
    }
}
