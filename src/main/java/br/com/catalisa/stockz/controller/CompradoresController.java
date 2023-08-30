package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.model.dto.CompradoresDTO;
import br.com.catalisa.stockz.service.CompradoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/compradores")
public class CompradoresController {
    @Autowired
    private CompradoresService compradoresService;

    @GetMapping
    ResponseEntity<List<CompradoresDTO>> listarTodos(){
        return ResponseEntity.ok(compradoresService.listarTodos());
    }

    @GetMapping(path = "{id}")
    ResponseEntity<CompradoresDTO> listarPorId(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(compradoresService.listarPorId(id));
    }

    @PostMapping
    ResponseEntity<CompradoresDTO> criar(@RequestBody CompradoresDTO compradoresDTO){
        CompradoresDTO novoComprador = compradoresService.criar(compradoresDTO);
        return new ResponseEntity<>(novoComprador, HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}")
    ResponseEntity<CompradoresDTO> atualizar(@PathVariable Long id, @RequestBody CompradoresDTO compradoresDTO) throws Exception {
        CompradoresDTO compradoresDTOAtualizado = compradoresService.atualizar(id, compradoresDTO);
        return ResponseEntity.ok(compradoresDTOAtualizado);
    }

    @DeleteMapping(path = "{id}")
    ResponseEntity<Void> deletar(@PathVariable Long id) throws Exception {
        compradoresService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
