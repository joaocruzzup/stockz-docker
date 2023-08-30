package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.model.dto.CompradoresDTO;
import br.com.catalisa.stockz.model.dto.FornecedoresDTO;
import br.com.catalisa.stockz.service.FornecedoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedoresController {
    @Autowired
    private FornecedoresService fornecedoresService;

    @GetMapping
    ResponseEntity<List<FornecedoresDTO>> listarTodos(){
        return ResponseEntity.ok(fornecedoresService.listarTodos());
    }
    @GetMapping(path = "{id}")
    ResponseEntity<FornecedoresDTO> listarPorId(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(fornecedoresService.listarPorId(id));
    }

    @PostMapping
    ResponseEntity<FornecedoresDTO> criar(@RequestBody FornecedoresDTO fornecedoresDTO){
        FornecedoresDTO novoFornecedor = fornecedoresService.criar(fornecedoresDTO);
        return new ResponseEntity<>(novoFornecedor, HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}")
    ResponseEntity<FornecedoresDTO> atualizar(@PathVariable Long id, @RequestBody FornecedoresDTO fornecedoresDTO) throws Exception {
        FornecedoresDTO fornecedoresDTOAtualizados = fornecedoresService.atualizar(id, fornecedoresDTO);
        return ResponseEntity.ok(fornecedoresDTOAtualizados);
    }

    @DeleteMapping(path = "{id}")
    ResponseEntity<Void> deletar(@PathVariable Long id) throws Exception {
        fornecedoresService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
