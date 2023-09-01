package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.model.dto.DelecaoResponse;
import br.com.catalisa.stockz.model.dto.ProdutoDTO;
import br.com.catalisa.stockz.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/produtos")
@Tag(name = "2. Produtos", description = "Endpoint para gerenciamento de produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @Operation(summary = "Listar todos os produtos", method = "GET")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Listagem realizada com sucesso"))
    @GetMapping
    ResponseEntity<List<ProdutoDTO>> listarTodos(){
        return ResponseEntity.ok(produtoService.listarTodos());
    }

    @Operation(summary = "Buscar produto por id", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping(path = "{id}")
    ResponseEntity<ProdutoDTO> listarPorId(
            @Parameter(description = "ID do produto a ser buscado", example = "1")
            @PathVariable Long id) {
        return ResponseEntity.ok(produtoService.listarPorId(id));
    }

    @Operation(summary = "Buscar produto por nome", method = "GET")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @GetMapping(path = "/nome/{nome}")
    ResponseEntity<List<ProdutoDTO>> listarPorNome(
            @Parameter(description = "NOME do produto a ser buscado", example = "joao")
            @PathVariable String nome)  {
        return ResponseEntity.ok(produtoService.listarPorNome(nome));
    }

    @Operation(summary = "Criar produto", method = "POST", description = "Para criar um novo produto, certifique-se de que uma categoria já foi criada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, confira os atributos preenchidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    ResponseEntity<ProdutoDTO> criar(
            @Parameter(description = "Dados do novo produto a ser criado", required = true)
            @RequestBody @Valid ProdutoDTO produtoDTO
    )  {
        ProdutoDTO novoProduto = produtoService.criar(produtoDTO);
        return new ResponseEntity<>(novoProduto, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar produto", method = "PUT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping(path = "{id}")
    ResponseEntity<ProdutoDTO> atualizar(
            @Parameter(description = "ID do produto a ser atualizado", example = "1")
            @PathVariable Long id,
            @Parameter(description = "Dados atualizados do produto", required = true)
            @RequestBody ProdutoDTO produtoDTO
    ) {
        ProdutoDTO produtoDTOAtualizados = produtoService.atualizar(id, produtoDTO);
        return ResponseEntity.ok(produtoDTOAtualizados);
    }

    @Operation(summary = "Deletar um produto", method = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping(path = "{id}")
    ResponseEntity<DelecaoResponse> deletar(
            @Parameter(description = "ID do produto a ser deletado", example = "1")
            @PathVariable Long id) {
        DelecaoResponse delecaoResponse = produtoService.deletar(id);
        return ResponseEntity.ok(delecaoResponse);
    }
}
