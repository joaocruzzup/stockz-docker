package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.model.dto.TransacaoSaidaDTO;
import br.com.catalisa.stockz.model.dto.TransacaoSaidaResponseDTO;
import br.com.catalisa.stockz.service.TransacaoSaidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/transacao/saida")
public class TransacaoSaidaController {
    @Autowired
    private TransacaoSaidaService transacaoSaidaService;

    @GetMapping
    ResponseEntity<List<TransacaoSaidaResponseDTO>> listarTodos(){
        return ResponseEntity.ok(transacaoSaidaService.listarTodos());
    }

    @GetMapping(path = "{id}")
    ResponseEntity<TransacaoSaidaResponseDTO> listarPorId(@PathVariable Long id) throws Exception {
        return ResponseEntity.ok(transacaoSaidaService.listarPorId(id));
    }

    @PostMapping
    ResponseEntity<TransacaoSaidaResponseDTO> criar(@RequestBody @Valid TransacaoSaidaDTO transacaoSaidaDTO) throws Exception {
        TransacaoSaidaResponseDTO novaTransacao = transacaoSaidaService.criar(transacaoSaidaDTO);
        return new ResponseEntity<>(novaTransacao, HttpStatus.CREATED);
    }
}
