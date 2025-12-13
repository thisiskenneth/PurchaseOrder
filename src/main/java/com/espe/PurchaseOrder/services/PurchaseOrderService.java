package com.espe.PurchaseOrder.services;

import com.espe.PurchaseOrder.models.entities.PurchaseOrder;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface PurchaseOrderService {

    PurchaseOrder save(PurchaseOrder order);
    List<PurchaseOrder> findAll(Specification<PurchaseOrder> spec);
}
