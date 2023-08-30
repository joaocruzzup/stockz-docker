package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.model.dto.TransacaoSaidaDTO;
import br.com.catalisa.stockz.service.TransacaoSaidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/transacao/saida")
public class TransacaoSaidaController {
    @Autowired
    private TransacaoSaidaService transacaoSaidaService;

    @GetMapping
    ResponseEntity<List<TransacaoSaidaDTO>> listarTodos(){
        return ResponseEntity.ok(transacaoSaidaService.listarTodos());
    }

    @GetMapping(path = "{id}")
    ResponseEntity<TransacaoSaidaDTO> listarPorId(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(transacaoSaidaService.listarPorId(id));
    }

    @PostMapping
    ResponseEntity<TransacaoSaidaDTO> criar(@RequestBody TransacaoSaidaDTO transacaoSaidaDTO) throws Exception {
        TransacaoSaidaDTO novaTransacao = transacaoSaidaService.criar(transacaoSaidaDTO);
        return new ResponseEntity<>(novaTransacao, HttpStatus.CREATED);
    }

    @PutMapping(path = "{id}")
    ResponseEntity<TransacaoSaidaDTO> atualizar(@PathVariable Long id, @RequestBody TransacaoSaidaDTO transacaoSaidaDTO) throws Exception {
        TransacaoSaidaDTO transacaoSaidaDTOAtualizados = transacaoSaidaService.atualizar(id, transacaoSaidaDTO);
        return ResponseEntity.ok(transacaoSaidaDTOAtualizados);
    }

    @DeleteMapping(path = "{id}")
    ResponseEntity<Void> deletar(@PathVariable Long id) throws Exception {
        transacaoSaidaService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
