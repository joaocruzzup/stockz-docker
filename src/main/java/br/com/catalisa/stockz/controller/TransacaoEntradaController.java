package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.model.dto.TransacaoEntradaDTO;
import br.com.catalisa.stockz.service.TransacaoEntradaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/transacao/entrada")
public class TransacaoEntradaController {
    @Autowired
    private TransacaoEntradaService transacaoEntradaService;

    @GetMapping
    ResponseEntity<List<TransacaoEntradaDTO>> listarTodos(){
        return ResponseEntity.ok(transacaoEntradaService.listarTodos());
    }

    @GetMapping(path = "{id}")
    ResponseEntity<TransacaoEntradaDTO> listarPorId(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(transacaoEntradaService.listarPorId(id));
    }

    @PostMapping
    ResponseEntity<TransacaoEntradaDTO> criar(@RequestBody TransacaoEntradaDTO transacaoEntradaDTO) throws Exception {
        TransacaoEntradaDTO novaTransacao = transacaoEntradaService.criar(transacaoEntradaDTO);
        return new ResponseEntity<>(novaTransacao, HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}")
    ResponseEntity<TransacaoEntradaDTO> atualizar(@PathVariable Long id, @RequestBody TransacaoEntradaDTO transacaoEntradaDTO) throws Exception {
        TransacaoEntradaDTO transacaoEntradaDTOAtualizados = transacaoEntradaService.atualizar(id, transacaoEntradaDTO);
        return ResponseEntity.ok(transacaoEntradaDTOAtualizados);
    }

    @DeleteMapping(path = "{id}")
    ResponseEntity<Void> deletar(@PathVariable Long id) throws Exception {
        transacaoEntradaService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
