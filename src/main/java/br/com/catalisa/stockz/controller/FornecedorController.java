package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.model.dto.FornecedorDTO;
import br.com.catalisa.stockz.service.FornecedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorController {
    @Autowired
    private FornecedorService fornecedorService;

    @GetMapping
    ResponseEntity<List<FornecedorDTO>> listarTodos(){
        return ResponseEntity.ok(fornecedorService.listarTodos());
    }
    @GetMapping(path = "{id}")
    ResponseEntity<FornecedorDTO> listarPorId(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(fornecedorService.listarPorId(id));
    }

    @PostMapping
    ResponseEntity<FornecedorDTO> criar(@RequestBody @Valid FornecedorDTO fornecedorDTO){
        FornecedorDTO novoFornecedor = fornecedorService.criar(fornecedorDTO);
        return new ResponseEntity<>(novoFornecedor, HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}")
    ResponseEntity<FornecedorDTO> atualizar(@PathVariable Long id, @RequestBody @Valid FornecedorDTO fornecedorDTO) throws Exception {
        FornecedorDTO fornecedorDTOAtualizados = fornecedorService.atualizar(id, fornecedorDTO);
        return ResponseEntity.ok(fornecedorDTOAtualizados);
    }

    @DeleteMapping(path = "{id}")
    ResponseEntity<Void> deletar(@PathVariable Long id) throws Exception {
        fornecedorService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
