package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.enums.StatusProduto;
import br.com.catalisa.stockz.model.Categoria;
import br.com.catalisa.stockz.model.Produto;
import br.com.catalisa.stockz.model.dto.EstoqueDTO;
import br.com.catalisa.stockz.model.dto.TransacaoSaidaResponseDTO;
import br.com.catalisa.stockz.service.EstoqueService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EstoqueController.class)
public class EstoqueControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EstoqueController estoqueController;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EstoqueService estoqueService;

    @Test
    @DisplayName("Buscar todos as transações de entrada ")
    public void buscarTodasAsTransacoesTeste() throws Exception {
        Categoria categoria = new Categoria();
        categoria.setNome("ferramentas");
        Produto produto = new Produto(1L, "computador","30gb ram", BigDecimal.valueOf(300), StatusProduto.ATIVO, categoria);
        EstoqueDTO estoqueDTO = new EstoqueDTO(produto, 100, LocalDateTime.now());

        List<EstoqueDTO> estoque = new ArrayList<>();
        estoque.add(estoqueDTO);

        when(estoqueService.listarTodos()).thenReturn(estoque);

        String responseJson = objectMapper.writeValueAsString(estoque);

        mockMvc.perform(get("/api/estoque")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andDo(print());
    }
}
