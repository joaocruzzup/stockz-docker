package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.model.dto.CategoriaDTO;
import br.com.catalisa.stockz.model.dto.DelecaoResponse;
import br.com.catalisa.stockz.service.CategoriaService;
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
@RequestMapping("/api/categorias")
@Tag(name = "1. Categorias", description = "Endpoint para gerenciamento de categorias utilizadas em produtos")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;

    @Operation(summary = "Listar todas as categorias", method = "GET")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Listagem de categorias realizada com sucesso"))
    @GetMapping
    ResponseEntity<List<CategoriaDTO>> listarTodos(){
        return ResponseEntity.ok(categoriaService.listarTodos());
    }

    @Operation(summary = "Buscar categoria por ID", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping(path = "{id}")
    ResponseEntity<CategoriaDTO> listarPorId(
            @Parameter(description = "ID da categoria a ser buscada", required = true, in = ParameterIn.PATH)
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(categoriaService.listarPorId(id));
    }

    @Operation(summary = "Criar categoria", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, confira os atributos preenchidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    ResponseEntity<CategoriaDTO> criar(
            @Parameter(description = "Dados da nova categoria a ser criada", required = true)
            @RequestBody @Valid CategoriaDTO categoriaDTO){
        CategoriaDTO novaCategoria = categoriaService.criar(categoriaDTO);
        return new ResponseEntity<>(novaCategoria, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar categoria por ID", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, confira os atributos preenchidos"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping(path = "{id}")
    ResponseEntity<CategoriaDTO> atualizar(
            @Parameter(description = "ID da categoria a ser atualizada", required = true, in = ParameterIn.PATH)
            @PathVariable Long id,
            @Parameter(description = "Dados da categoria atualizada", required = true)
            @RequestBody @Valid CategoriaDTO categoriaDTO) throws Exception {
        CategoriaDTO categoriaDTOAtualizado = categoriaService.atualizar(id, categoriaDTO);
        return ResponseEntity.ok(categoriaDTOAtualizado);
    }

    @Operation(summary = "Deletar categoria por ID", method = "DELETE", description = "Certifique-se de que nenhum produto está atrelado a essa categoria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping(path = "{id}")
    ResponseEntity<DelecaoResponse> deletar(
            @Parameter(description = "ID da categoria a ser deletada", required = true, in = ParameterIn.PATH)
            @PathVariable Long id) throws Exception {
        DelecaoResponse delecaoResponse = categoriaService.deletar(id);
        return ResponseEntity.ok(delecaoResponse);
    }
}
