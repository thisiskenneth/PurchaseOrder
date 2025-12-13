package com.espe.PurchaseOrder.clientes;



import com.espe.PurchaseOrder.models.dto.AutorDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(name = "autor", url = "localhost:8003/autores")
public interface AutorClienteRest {

    @GetMapping("/{id}")
    Optional<AutorDTO> buscarPorId(@PathVariable Long id);
}


