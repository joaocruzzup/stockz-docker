package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.enums.StatusProduto;
import br.com.catalisa.stockz.model.Categoria;
import br.com.catalisa.stockz.model.Produto;
import br.com.catalisa.stockz.model.dto.*;
import br.com.catalisa.stockz.service.TransacaoSaidaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransacaoSaidaController.class)
public class TransacaoSaidaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransacaoSaidaController transacaoSaidaController;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransacaoSaidaService transacaoSaidaService;

    CategoriaDTO categoriaDTO1;
    Produto produtoRequest;
    ProdutoDTOResponse produtoDTOResponse;
    CompradorDTO compradorDTO;
    TransacaoSaidaResponseDTO transacaoResponse;
    TransacaoSaidaDTO transacaoRequest;


    @BeforeEach
    public void config() {
        categoriaDTO1 = new CategoriaDTO("ferramentas");
        Categoria categoria = new Categoria();
        categoria.setNome("ferramentas");
        produtoRequest = new Produto(1L, "computador","30gb ram", BigDecimal.valueOf(300), StatusProduto.ATIVO, categoria);
        produtoDTOResponse = new ProdutoDTOResponse("computador", "30gb ram", categoriaDTO1, BigDecimal.valueOf(300));
        compradorDTO = new CompradorDTO("joao", "joao@example.com");
        transacaoResponse = new TransacaoSaidaResponseDTO();
        transacaoResponse.setComprador(compradorDTO);
        transacaoResponse.setProduto(produtoDTOResponse);
        transacaoResponse.setQuantidade(100);
        transacaoResponse.setDataHoraSaida(LocalDateTime.now());
        transacaoRequest = new TransacaoSaidaDTO(100, produtoRequest, compradorDTO.getEmail(), LocalDateTime.now());

    }

    @Test
    @DisplayName("Buscar todos as transações de entrada ")
    public void buscarTodasAsTransacoesTeste() throws Exception {

        List<TransacaoSaidaResponseDTO> transacoes = new ArrayList<>();
        transacoes.add(transacaoResponse);

        when(transacaoSaidaService.listarTodos()).thenReturn(transacoes);

        String responseJson = objectMapper.writeValueAsString(transacoes);

        mockMvc.perform(get("/api/transacao/saida")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andDo(print());
    }

    @Test
    @DisplayName("Buscar transacao por ID existente")
    public void buscarTransacaoPorIDExistenteTeste() throws Exception {

        Long idTransacao = 1L;

        when(transacaoSaidaService.listarPorId(idTransacao)).thenReturn(transacaoResponse);

        String responseJson = objectMapper.writeValueAsString(transacaoResponse);

        mockMvc.perform(get("/api/transacao/saida/{id}", idTransacao)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andDo(print());
    }

    @Test
    @DisplayName("Cadastrar nova Transacao")
    public void cadastrarTransacaoTeste() throws Exception {

        when(transacaoSaidaService.criar(Mockito.any())).thenReturn(transacaoResponse);

        String requestJson = objectMapper.writeValueAsString(transacaoRequest);
        String responseJson = objectMapper.writeValueAsString(transacaoResponse);

        mockMvc.perform(post("/api/transacao/saida")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseJson))
                .andDo(print());

        verify(transacaoSaidaService, times(1)).criar(Mockito.any());

    }
}
