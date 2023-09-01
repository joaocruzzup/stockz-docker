package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.exception.EntidadeNaoEncontradaException;
import br.com.catalisa.stockz.exception.error.ErrorMessage;
import br.com.catalisa.stockz.model.Categoria;
import br.com.catalisa.stockz.model.dto.ProdutoDTO;
import br.com.catalisa.stockz.service.ProdutoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProdutoController.class)
public class ProdutoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutoController produtosController;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProdutoService produtoService;


    private ProdutoDTO produtoDTO1;
    private ProdutoDTO produtoDTO2;
    private Categoria categoria1;

    @BeforeEach
    public void config(){
        categoria1 = new Categoria();
        categoria1.setNome("ferramentas");

        produtoDTO1 = new ProdutoDTO("computador", "30gb de ram", categoria1, BigDecimal.valueOf(3000));
        produtoDTO2 = new ProdutoDTO("notebook", "15gb de ram", categoria1, BigDecimal.valueOf(5000));

    }
    @Test
    @DisplayName("Buscar todos os produtos ")
    public void buscarTodasOsProdutosTeste() throws Exception {

        List<ProdutoDTO> produtosEsperados = new ArrayList<>();
        produtosEsperados.add(produtoDTO1);
        produtosEsperados.add(produtoDTO2);

        when(produtoService.listarTodos()).thenReturn(produtosEsperados);

        String responseJson = objectMapper.writeValueAsString(produtosEsperados);

        mockMvc.perform(get("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andDo(print());
    }

    @Test
    @DisplayName("Buscar produto por ID existente")
    public void buscarProdutoPorIDExistenteTeste() throws Exception {

        Long idProduto = 1L;

        when(produtoService.listarPorId(idProduto)).thenReturn(produtoDTO1);

        String responseJson = objectMapper.writeValueAsString(produtoDTO1);

        mockMvc.perform(get("/api/produtos/{id}", idProduto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andDo(print());
    }

    @Test
    @DisplayName("Buscar produto por ID inexistente")
    public void buscarProdutoPorIDInexistenteTeste() throws Exception {

        Long idProduto = 1L;

        when(produtoService.listarPorId(idProduto)).thenThrow(new EntidadeNaoEncontradaException("Produto nao encontrado"));

        ErrorMessage errorResponse = new ErrorMessage(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), "Produto nao encontrado");


        mockMvc.perform(get("/api/produtos/{id}", idProduto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(errorResponse)))
                .andDo(print());
    }

//    @Test
//    @DisplayName("Buscar produto por Nome existente")
//    public void buscarProdutoPorNomeExistenteTeste() throws Exception {
//
//        String nomeProduto = "computador";
//
//        when(produtoService.listarPorNome(nomeProduto)).thenReturn(produtoDTO1);
//
//        String responseJson = objectMapper.writeValueAsString(produtoDTO1);
//
//        mockMvc.perform(get("/api/produtos/nome/{nome}", nomeProduto)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().json(responseJson))
//                .andDo(print());
//    }

    @Test
    @DisplayName("Buscar produto por Nome inexistente")
    public void buscarProdutoPorNomeInexistenteTeste() throws Exception {

        String nomeProduto = "celular";

        when(produtoService.listarPorNome(nomeProduto)).thenThrow(new EntidadeNaoEncontradaException("Produto nao encontrado"));

        ErrorMessage errorResponse = new ErrorMessage(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), "Produto nao encontrado");

        mockMvc.perform(get("/api/produtos/nome/{nome}", nomeProduto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().json(objectMapper.writeValueAsString(errorResponse)))
                .andDo(print());
    }

    @Test
    @DisplayName("Cadastrar novo Produto")
    public void cadastrarProdutoTeste() throws Exception {

        when(produtoService.criar(Mockito.any(produtoDTO1.getClass()))).thenReturn(produtoDTO1);

        String requestJson = objectMapper.writeValueAsString(produtoDTO1);
        String responseJson = objectMapper.writeValueAsString(produtoDTO1);

        mockMvc.perform(post("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseJson))
                .andDo(print());

        verify(produtoService, times(1)).criar(Mockito.any());

    }

    @Test
    @DisplayName("Atualizar um produto")
    public void alterarProdutoTest() throws Exception {

        Long idProduto = 1L;
        when(produtoService.atualizar(Mockito.any(), Mockito.any(produtoDTO1.getClass()))).thenReturn(produtoDTO2);

        String requestJson = objectMapper.writeValueAsString(produtoDTO2);
        String responseJson = objectMapper.writeValueAsString(produtoDTO2);

        mockMvc.perform(put("/api/produtos/{id}", idProduto)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andDo(print());

        verify(produtoService, times(1)).atualizar(Mockito.any(), Mockito.any(produtoDTO2.getClass()));
    }

    @Test
    @DisplayName("Deletar um produto existente")
    public void deletarUmProdutoTest() throws Exception{

        mockMvc.perform(delete("/api/produtos/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk());

        Mockito.verify(produtoService, times(1)).deletar(Mockito.any());
    }

    @Test
    @DisplayName("Deletar um produto inexistente")
    public void deletarUmProdutoInexistenteTest() throws Exception{

        doThrow(new EntidadeNaoEncontradaException("Produto nao encontrado"))
                .when(produtoService).deletar(1L);

        mockMvc.perform(delete("/api/produtos/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNotFound());

        Mockito.verify(produtoService, times(1)).deletar(Mockito.any());
    }

}
