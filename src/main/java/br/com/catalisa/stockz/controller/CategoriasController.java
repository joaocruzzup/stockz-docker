package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.model.dto.CategoriasDTO;
import br.com.catalisa.stockz.service.CategoriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categorias")
public class CategoriasController {
    @Autowired
    private CategoriasService categoriasService;

    @GetMapping
    ResponseEntity<List<CategoriasDTO>> listarTodos(){
        return ResponseEntity.ok(categoriasService.listarTodos());
    }

    @GetMapping(path = "{id}")
    ResponseEntity<CategoriasDTO> listarPorId(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(categoriasService.listarPorId(id));
    }

    @PostMapping
    ResponseEntity<CategoriasDTO> criar(@RequestBody CategoriasDTO categoriasDTO){
        CategoriasDTO novaCategoria = categoriasService.criar(categoriasDTO);
        return new ResponseEntity<>(novaCategoria, HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}")
    ResponseEntity<CategoriasDTO> atualizar(@PathVariable Long id, @RequestBody CategoriasDTO categoriasDTO) throws Exception {
        CategoriasDTO categoriaDTOAtualizado = categoriasService.atualizar(id, categoriasDTO);
        return ResponseEntity.ok(categoriaDTOAtualizado);
    }

    @DeleteMapping(path = "{id}")
    ResponseEntity<Void> deletar(@PathVariable Long id) throws Exception {
        categoriasService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
