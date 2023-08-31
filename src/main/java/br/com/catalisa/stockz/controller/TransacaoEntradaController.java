package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.model.dto.TransacaoEntradaDTO;
import br.com.catalisa.stockz.model.dto.TransacaoEntradaResponseDTO;
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
    ResponseEntity<List<TransacaoEntradaResponseDTO>> listarTodos(){
        return ResponseEntity.ok(transacaoEntradaService.listarTodos());
    }

    @GetMapping(path = "{id}")
    ResponseEntity<TransacaoEntradaResponseDTO> listarPorId(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(transacaoEntradaService.listarPorId(id));
    }

    @PostMapping
    ResponseEntity<TransacaoEntradaResponseDTO> criar(@RequestBody TransacaoEntradaDTO transacaoEntradaDTO) throws Exception {
        TransacaoEntradaResponseDTO novaTransacao = transacaoEntradaService.criar(transacaoEntradaDTO);
        return new ResponseEntity<>(novaTransacao, HttpStatus.CREATED);
    }
}
