package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.model.dto.CompradorDTO;
import br.com.catalisa.stockz.service.CompradorService;
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
@RequestMapping("/api/compradores")
@Tag(name = "5. Compradores", description = "Endpoint para gerenciamento de compradores")
public class CompradorController {
    @Autowired
    private CompradorService compradorService;

    @Operation(summary = "Listar todos os compradores", method = "GET")
    @ApiResponses(value = @ApiResponse(responseCode = "200", description = "Listagem de compradores realizada com sucesso"))
    @GetMapping
    ResponseEntity<List<CompradorDTO>> listarTodos(){
        return ResponseEntity.ok(compradorService.listarTodos());
    }

    @Operation(summary = "Buscar comprador por ID", method = "GET", description = "O ID do comprador é o ID do usuário (que pode ser comprador ou fornecedor), " + "sendo assim atente-se nisso quando realizar a busca. Ex.: Caso seja criado um fornecedor e o comprador, este último terá id 2 ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comprador encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Comprador não encontrado")
    })
    @GetMapping(path = "{id}")
    ResponseEntity<CompradorDTO> listarPorId(
            @Parameter(description = "ID do comprador a ser buscado", required = true, in = ParameterIn.PATH)
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(compradorService.listarPorId(id));
    }

    @Operation(summary = "Criar comprador", method = "POST")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comprador criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, confira os atributos preenchidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    ResponseEntity<CompradorDTO> criar(
            @Parameter(description = "Dados do novo comprador a ser criado", required = true)
            @RequestBody @Valid CompradorDTO compradorDTO){
        CompradorDTO novoComprador = compradorService.criar(compradorDTO);
        return new ResponseEntity<>(novoComprador, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar comprador por ID", method = "PUT", description = "O ID do comprador é o ID do usuário (que pode ser comprador ou fornecedor), " + "sendo assim atente-se nisso quando realizar a busca. Ex.: Caso seja criado um fornecedor e o comprador, este último terá id 2 ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comprador atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, confira os atributos preenchidos"),
            @ApiResponse(responseCode = "404", description = "Comprador não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PutMapping(path = "{id}")
    ResponseEntity<CompradorDTO> atualizar(
            @Parameter(description = "ID do usuário referente ao comprador a ser atualizado", required = true, in = ParameterIn.PATH)
            @PathVariable Long id,
            @Parameter(description = "Dados do comprador atualizado", required = true)
            @RequestBody @Valid CompradorDTO compradorDTO) throws Exception {
        CompradorDTO compradorDTOAtualizado = compradorService.atualizar(id, compradorDTO);
        return ResponseEntity.ok(compradorDTOAtualizado);
    }

    @Operation(summary = "Deletar comprador por ID", method = "DELETE", description = "O ID do comprador é o ID do usuário (que pode ser comprador ou fornecedor), " + "sendo assim atente-se nisso quando realizar a busca. Ex.: Caso seja criado um fornecedor e o comprador, este último terá id 2 ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comprador deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Comprador não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @DeleteMapping(path = "{id}")
    ResponseEntity<Void> deletar(
            @Parameter(description = "ID do usuário referente ao comprador a ser atualizado", required = true, in = ParameterIn.PATH)
            @PathVariable Long id) throws Exception {
        compradorService.deletar(id);
        return ResponseEntity.ok().build();
    }
}
