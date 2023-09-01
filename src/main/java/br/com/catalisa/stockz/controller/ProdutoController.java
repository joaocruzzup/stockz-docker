package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.model.dto.DelecaoResponse;
import br.com.catalisa.stockz.model.dto.ProdutoDTO;
import br.com.catalisa.stockz.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    ResponseEntity<List<ProdutoDTO>> listarTodos(){
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @GetMapping(path = "{id}")
    ResponseEntity<ProdutoDTO> listarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(produtoService.listarPorId(id));
    }

    @GetMapping(path = "/nome/{nome}")
    ResponseEntity<ProdutoDTO> listarPorId(@PathVariable String nome)  {
        return ResponseEntity.ok(produtoService.listarPorNome(nome));
    }

    @PostMapping
    ResponseEntity<ProdutoDTO> criar(@RequestBody @Valid ProdutoDTO produtoDTO)  {
        ProdutoDTO novoProduto = produtoService.criar(produtoDTO);
        return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}")
    ResponseEntity<ProdutoDTO> atualizar(@PathVariable Long id, @RequestBody ProdutoDTO produtoDTO) {
        ProdutoDTO produtoDTOAtualizados = produtoService.atualizar(id, produtoDTO);
        return ResponseEntity.ok(produtoDTOAtualizados);
    }

    @DeleteMapping(path = "{id}")
    ResponseEntity<DelecaoResponse> deletar(@PathVariable Long id) {
        DelecaoResponse delecaoResponse = produtoService.deletar(id);
        return ResponseEntity.ok(delecaoResponse);
    }
}
