package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.model.dto.CompradoresDTO;
import br.com.catalisa.stockz.model.dto.EstoqueDTO;
import br.com.catalisa.stockz.model.dto.FornecedoresDTO;
import br.com.catalisa.stockz.service.EstoqueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/estoque")
public class EstoqueController {
    @Autowired
    private EstoqueService estoqueService;

    @GetMapping
    ResponseEntity<List<EstoqueDTO>> listarTodos(){
        return ResponseEntity.ok(estoqueService.listarTodos());
    }
}
