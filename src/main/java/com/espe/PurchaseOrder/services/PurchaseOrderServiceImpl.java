package com.espe.PurchaseOrder.services;

import com.espe.PurchaseOrder.models.entities.PurchaseOrder;
import com.espe.PurchaseOrder.repositories.PurchaseOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseOrderServiceImpl implements PurchaseOrderService {

    @Autowired
    private PurchaseOrderRepository repository;

    @Override
    public PurchaseOrder save(PurchaseOrder order) {
        order.setCreatedAt(LocalDateTime.now());
        return repository.save(order);
    }

    @Override
    public List<PurchaseOrder> findAll(Specification<PurchaseOrder> spec) {
        return repository.findAll(spec);
    }
}
