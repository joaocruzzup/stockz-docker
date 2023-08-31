package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.model.dto.ProdutosDTO;
import br.com.catalisa.stockz.service.ProdutosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutosController {
    @Autowired
    private ProdutosService produtosService;

    @GetMapping
    ResponseEntity<List<ProdutosDTO>> listarTodos(){
        return ResponseEntity.ok(produtosService.listarTodos());
    }

    @GetMapping(path = "{id}")
    ResponseEntity<ProdutosDTO> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtosService.listarPorId(id));
    }

    @GetMapping(path = "/nome/{nome}")
    ResponseEntity<ProdutosDTO> listarPorId(@PathVariable String nome)  {
        return ResponseEntity.ok(produtosService.listarPorNome(nome));
    }

    @PostMapping
    ResponseEntity<ProdutosDTO> criar(@RequestBody ProdutosDTO produtosDTO)  {
        ProdutosDTO novoProduto = produtosService.criar(produtosDTO);
        return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}")
    ResponseEntity<ProdutosDTO> atualizar(@PathVariable Long id, @RequestBody ProdutosDTO produtosDTO) {
        ProdutosDTO produtosDTOAtualizados = produtosService.atualizar(id, produtosDTO);
        return ResponseEntity.ok(produtosDTOAtualizados);
    }

    @DeleteMapping(path = "{id}")
    ResponseEntity<Void> deletar(@PathVariable Long id) {
        produtosService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
