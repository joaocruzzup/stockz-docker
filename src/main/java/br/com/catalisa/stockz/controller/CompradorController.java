package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.model.dto.CompradorDTO;
import br.com.catalisa.stockz.service.CompradorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/compradores")
public class CompradorController {
    @Autowired
    private CompradorService compradorService;

    @GetMapping
    ResponseEntity<List<CompradorDTO>> listarTodos(){
        return ResponseEntity.ok(compradorService.listarTodos());
    }

    @GetMapping(path = "{id}")
    ResponseEntity<CompradorDTO> listarPorId(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(compradorService.listarPorId(id));
    }

    @PostMapping
    ResponseEntity<CompradorDTO> criar(@RequestBody @Valid CompradorDTO compradorDTO){
        CompradorDTO novoComprador = compradorService.criar(compradorDTO);
        return new ResponseEntity<>(novoComprador, HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}")
    ResponseEntity<CompradorDTO> atualizar(@PathVariable Long id, @RequestBody @Valid CompradorDTO compradorDTO) throws Exception {
        CompradorDTO compradorDTOAtualizado = compradorService.atualizar(id, compradorDTO);
        return ResponseEntity.ok(compradorDTOAtualizado);
    }

    @DeleteMapping(path = "{id}")
    ResponseEntity<Void> deletar(@PathVariable Long id) throws Exception {
        compradorService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
