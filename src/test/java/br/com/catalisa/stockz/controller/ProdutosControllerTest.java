package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.exception.AtributoNaoPreenchidoException;
import br.com.catalisa.stockz.exception.EntidadeNaoEncontradaException;
import br.com.catalisa.stockz.model.Categorias;
import br.com.catalisa.stockz.model.Produtos;
import br.com.catalisa.stockz.model.dto.ProdutosDTO;
import br.com.catalisa.stockz.repository.ProdutosRepository;
import br.com.catalisa.stockz.service.ProdutosService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProdutosController.class)
public class ProdutosControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProdutosController produtosController;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProdutosService produtosService;

    @MockBean
    private ProdutosRepository produtosRepository;

    private ProdutosDTO produtoDTO1;
    private ProdutosDTO produtoDTO2;
    private Categorias categoria1;

    @BeforeEach
    public void config(){
        categoria1 = new Categorias();
        categoria1.setNome("ferramentas");

        produtoDTO1 = new ProdutosDTO("computador", "30gb de ram", categoria1, BigDecimal.valueOf(3000), 0);
        produtoDTO2 = new ProdutosDTO("notebook", "15gb de ram", categoria1, BigDecimal.valueOf(5000), 0);

    }
    @Test
    @DisplayName("Buscar todos os produtos ")
    public void buscarTodasAsContasTeste() throws Exception {

        List<ProdutosDTO> produtosEsperados = new ArrayList<>();
        produtosEsperados.add(produtoDTO1);
        produtosEsperados.add(produtoDTO2);

        when(produtosService.listarTodos()).thenReturn(produtosEsperados);

        String expectedJson = objectMapper.writeValueAsString(produtosEsperados);

        mockMvc.perform(get("/api/produtos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson))
                .andDo(print());
    }

    @Test
    @DisplayName("Buscar produto por ID existente")
    public void buscarContaPorIDExistenteTeste() throws Exception {

        Long idProduto = 1L;

        when(produtosService.listarPorId(idProduto)).thenReturn(produtoDTO1);

        String expectedJson = objectMapper.writeValueAsString(produtoDTO1);

        mockMvc.perform(get("/api/produtos/{id}", idProduto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson))
                .andDo(print());
    }

    @Test
    @DisplayName("Buscar produto por ID inexistente")
    public void buscarContaPorIDInexistenteTeste() throws Exception {

        Long idProduto = 1L;

        when(produtosService.listarPorId(idProduto)).thenThrow(new EntidadeNaoEncontradaException("Produto não encontrado"));

        mockMvc.perform(get("/api/produtos/{id}", idProduto)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Produto não encontrado"))
                .andDo(print());
    }

}
