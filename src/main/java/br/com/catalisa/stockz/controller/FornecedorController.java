package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.model.dto.FornecedorDTO;
import br.com.catalisa.stockz.service.FornecedorService;
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
@RequestMapping("/api/fornecedores")
@Tag(name = "3. Fornecedores", description = "Endpoint para gerenciamento de fornecedores")
public class FornecedorController {
    @Autowired
    private FornecedorService fornecedorService;

    @Operation(summary = "Listar todos os fornecedores", method = "GET")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Listagem de fornecedores realizada com sucesso"))
    @GetMapping
    ResponseEntity<List<FornecedorDTO>> listarTodos(){
        return ResponseEntity.ok(fornecedorService.listarTodos());
    }

    @Operation(summary = "Buscar fornecedor por ID", method = "GET", description = "O ID do fornecedor é o ID do usuário (que pode ser comprador ou fornecedor), " + "sendo assim atente-se nisso quando realizar a busca. Ex.: Caso seja criado um comprador e um fornecedor, este último terá id 2 ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedor encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado")
    })
    @GetMapping(path = "{id}")
    ResponseEntity<FornecedorDTO> listarPorId(
            @Parameter(description = "ID do fornecedor a ser buscado", required = true, in = ParameterIn.PATH)
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(fornecedorService.listarPorId(id));
    }

    @Operation(summary = "Criar fornecedor", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Fornecedor criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, confira os atributos preenchidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    ResponseEntity<FornecedorDTO> criar(
            @Parameter(description = "Dados do novo fornecedor a ser criado", required = true)
            @RequestBody @Valid FornecedorDTO fornecedorDTO){
        FornecedorDTO novoFornecedor = fornecedorService.criar(fornecedorDTO);
        return new ResponseEntity<>(novoFornecedor, HttpStatus.CREATED);
    }


    @Operation(summary = "Atualizar fornecedor por ID", method = "PUT", description = "O ID do fornecedor é o ID do usuário (que pode ser comprador ou fornecedor), " + "sendo assim atente-se nisso quando realizar a busca. Ex.: Caso seja criado um comprador e um fornecedor, este último terá id 2")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedor atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, confira os atributos preenchidos"),
            @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping(path = "{id}")
    ResponseEntity<FornecedorDTO> atualizar(
            @Parameter(description = "ID do fornecedor a ser atualizado", required = true, in = ParameterIn.PATH)
            @PathVariable Long id,
            @Parameter(description = "Dados do fornecedor atualizado", required = true)
            @RequestBody @Valid FornecedorDTO fornecedorDTO) throws Exception {
        FornecedorDTO fornecedorDTOAtualizados = fornecedorService.atualizar(id, fornecedorDTO);
        return ResponseEntity.ok(fornecedorDTOAtualizados);
    }

    @Operation(summary = "Deletar fornecedor por ID", method = "DELETE", description = "O ID do fornecedor é o ID do usuário (que pode ser comprador ou fornecedor), " + "sendo assim atente-se nisso quando realizar a busca. Ex.: Caso seja criado um comprador e um fornecedor, este último terá id 2 ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Fornecedor deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Fornecedor não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping(path = "{id}")
    ResponseEntity<Void> deletar(
            @Parameter(description = "ID do fornecedor a ser deletado", required = true, in = ParameterIn.PATH)
            @PathVariable Long id) throws Exception {
        fornecedorService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
