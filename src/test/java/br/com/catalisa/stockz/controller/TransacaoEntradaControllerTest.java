package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.enums.StatusProduto;
import br.com.catalisa.stockz.enums.TipoTransacao;
import br.com.catalisa.stockz.model.*;
import br.com.catalisa.stockz.model.dto.*;
import br.com.catalisa.stockz.service.TransacaoEntradaService;
import br.com.catalisa.stockz.utils.mapper.TransacaoEntradaMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransacaoEntradaController.class)
public class TransacaoEntradaControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TransacaoEntradaController transacaoEntradaController;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TransacaoEntradaService transacaoEntradaService;

    CategoriaDTO categoriaDTO1;
    Produto produtoRequest;
    ProdutoDTOResponse produtoDTOResponse;
    FornecedorDTO fornecedorDTO;
    TransacaoEntradaResponseDTO transacaoResponse;
    TransacaoEntradaDTO transacaoRequest;


    @BeforeEach
    public void config() {
        categoriaDTO1 = new CategoriaDTO("ferramentas");
        Categoria categoria = new Categoria(1L,"ferramentas", new ArrayList<>());
        produtoRequest = new Produto(1L, "computador","30gb ram", BigDecimal.valueOf(300), StatusProduto.ATIVO, categoria);
        produtoDTOResponse = new ProdutoDTOResponse("computador", "30gb ram", categoriaDTO1, BigDecimal.valueOf(300));
        fornecedorDTO = new FornecedorDTO("joao", "joao@example.com");
        transacaoResponse = new TransacaoEntradaResponseDTO();
        transacaoResponse.setFornecedor(fornecedorDTO);
        transacaoResponse.setProduto(produtoDTOResponse);
        transacaoResponse.setQuantidade(100);
        transacaoResponse.setDataHoraEntrada(LocalDateTime.now());
        transacaoRequest = new TransacaoEntradaDTO(100, produtoRequest, fornecedorDTO.getEmail(), LocalDateTime.now());

    }

    @Test
    @DisplayName("Buscar todos as transações de entrada ")
    public void buscarTodasAsTransacoesTeste() throws Exception {

        List<TransacaoEntradaResponseDTO> transacoes = new ArrayList<>();
        transacoes.add(transacaoResponse);

        when(transacaoEntradaService.listarTodos()).thenReturn(transacoes);

        String responseJson = objectMapper.writeValueAsString(transacoes);

        mockMvc.perform(get("/api/transacao/entrada")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andDo(print());
    }

    @Test
    @DisplayName("Buscar transacao por ID existente")
    public void buscarTransacaoPorIDExistenteTeste() throws Exception {

        Long idTransacao = 1L;

        when(transacaoEntradaService.listarPorId(idTransacao)).thenReturn(transacaoResponse);

        String responseJson = objectMapper.writeValueAsString(transacaoResponse);

        mockMvc.perform(get("/api/transacao/entrada/{id}", idTransacao)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andDo(print());
    }

    @Test
    @DisplayName("Cadastrar nova Transacao")
    public void cadastrarTransacaoTeste() throws Exception {

        when(transacaoEntradaService.criar(Mockito.any())).thenReturn(transacaoResponse);

        String requestJson = objectMapper.writeValueAsString(transacaoRequest);
        String responseJson = objectMapper.writeValueAsString(transacaoResponse);

        mockMvc.perform(post("/api/transacao/entrada")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseJson))
                .andDo(print());

        verify(transacaoEntradaService, times(1)).criar(Mockito.any());

    }

}
