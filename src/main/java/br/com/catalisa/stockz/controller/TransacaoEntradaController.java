package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.model.dto.TransacaoEntradaDTO;
import br.com.catalisa.stockz.model.dto.TransacaoEntradaResponseDTO;
import br.com.catalisa.stockz.service.TransacaoEntradaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/transacao/entrada")
@Tag(name = "4. Transações de entrada", description = "Endpoint para gerenciamento de transações de entrada")
public class TransacaoEntradaController {
    @Autowired
    private TransacaoEntradaService transacaoEntradaService;

    @Operation(summary = "Listar todas as transações de entrada", method = "GET")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Listagem de transações de entrada realizada com sucesso"))
    @GetMapping
    ResponseEntity<List<TransacaoEntradaResponseDTO>> listarTodos(){
        return ResponseEntity.ok(transacaoEntradaService.listarTodos());
    }

    @Operation(summary = "Buscar transação de entrada por ID", method = "GET", description = "O ID da Transação de Entrada é o ID da Transação (que pode ser entrada ou saída), " + "sendo assim atente-se nisso quando realizar a busca. Ex.: Caso seja criado uma saída e uma entrada, este último terá id 2")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação de entrada encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transação de entrada não encontrada")
    })
    @GetMapping(path = "{id}")
    ResponseEntity<TransacaoEntradaResponseDTO> listarPorId(
            @Parameter(description = "ID da transação de entrada a ser buscada", required = true, in = ParameterIn.PATH)
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(transacaoEntradaService.listarPorId(id));
    }

    @Operation(summary = "Criar transação de entrada", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação de entrada criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, confira os atributos preenchidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    ResponseEntity<TransacaoEntradaResponseDTO> criar(
            @Parameter(description = "Dados da nova transação de entrada a ser criada", required = true)
            @RequestBody @Valid TransacaoEntradaDTO transacaoEntradaDTO) throws Exception {
        TransacaoEntradaResponseDTO novaTransacao = transacaoEntradaService.criar(transacaoEntradaDTO);
        return new ResponseEntity<>(novaTransacao, HttpStatus.CREATED);
    }
}
