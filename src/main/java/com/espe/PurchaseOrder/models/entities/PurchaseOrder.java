package com.espe.PurchaseOrder.models.entities;

import com.espe.PurchaseOrder.enums.Currency;
import com.espe.PurchaseOrder.enums.Status;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "purchase_orders",
        uniqueConstraints = @UniqueConstraint(columnNames = "orderNumber")
)
public class PurchaseOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Pattern(
            regexp = "^PO-\\d{4}-\\d{6}$",
            message = "Formato requerido: PO-YYYY-XXXXXX"
    )
    private String orderNumber;

    @NotBlank
    private String supplierName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    private BigDecimal totalAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Currency currency;

    // ❗ backend-only
    private LocalDateTime createdAt;

    @NotNull
    private LocalDate expectedDeliveryDate;

    // getters y setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(LocalDate expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }
}
