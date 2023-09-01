package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.model.dto.TransacaoSaidaDTO;
import br.com.catalisa.stockz.model.dto.TransacaoSaidaResponseDTO;
import br.com.catalisa.stockz.service.TransacaoSaidaService;
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
@RequestMapping(path = "/api/transacao/saida")
@Tag(name = "6. Transações de saída", description = "Endpoint para gerenciamento de transações de saída")
public class TransacaoSaidaController {
    @Autowired
    private TransacaoSaidaService transacaoSaidaService;

    @Operation(summary = "Listar todas as transações de saída", method = "GET")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Listagem de transações de saída realizada com sucesso"))
    @GetMapping
    ResponseEntity<List<TransacaoSaidaResponseDTO>> listarTodos(){
        return ResponseEntity.ok(transacaoSaidaService.listarTodos());
    }

    @Operation(summary = "Buscar transação de saída por ID", method = "GET", description = "O ID da Transação de Saída é o ID da Transação (que pode ser entrada ou saída), \" + \"sendo assim atente-se nisso quando realizar a busca. Ex.: Caso seja criado uma entrada e uma saída, este último terá id 2.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transação de saída encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transação de saída não encontrada")
    })
    @GetMapping(path = "{id}")
    ResponseEntity<TransacaoSaidaResponseDTO> listarPorId(
            @Parameter(description = "ID da transação de saída a ser buscada", required = true, in = ParameterIn.PATH)
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(transacaoSaidaService.listarPorId(id));
    }

    @Operation(summary = "Criar transação de saída", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transação de saída criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, confira os atributos preenchidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    ResponseEntity<TransacaoSaidaResponseDTO> criar(
            @Parameter(description = "Dados da nova transação de saída a ser criada", required = true)
            @RequestBody @Valid TransacaoSaidaDTO transacaoSaidaDTO) throws Exception {
        TransacaoSaidaResponseDTO novaTransacao = transacaoSaidaService.criar(transacaoSaidaDTO);
        return new ResponseEntity<>(novaTransacao, HttpStatus.CREATED);
    }
}
