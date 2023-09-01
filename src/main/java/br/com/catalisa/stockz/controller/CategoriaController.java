package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.model.dto.CategoriaDTO;
import br.com.catalisa.stockz.model.dto.DelecaoResponse;
import br.com.catalisa.stockz.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping
    ResponseEntity<List<CategoriaDTO>> listarTodos(){
        return ResponseEntity.ok(categoriaService.listarTodos());
    }

    @GetMapping(path = "{id}")
    ResponseEntity<CategoriaDTO> listarPorId(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(categoriaService.listarPorId(id));
    }

    @PostMapping
    ResponseEntity<CategoriaDTO> criar(@RequestBody @Valid CategoriaDTO categoriaDTO){
        CategoriaDTO novaCategoria = categoriaService.criar(categoriaDTO);
        return new ResponseEntity<>(novaCategoria, HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}")
    ResponseEntity<CategoriaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid CategoriaDTO categoriaDTO) throws Exception {
        CategoriaDTO categoriaDTOAtualizado = categoriaService.atualizar(id, categoriaDTO);
        return ResponseEntity.ok(categoriaDTOAtualizado);
    }

    @DeleteMapping(path = "{id}")
    ResponseEntity<DelecaoResponse> deletar(@PathVariable Long id) throws Exception {
        DelecaoResponse delecaoResponse = categoriaService.deletar(id);
        return ResponseEntity.ok(delecaoResponse);
    }
}
