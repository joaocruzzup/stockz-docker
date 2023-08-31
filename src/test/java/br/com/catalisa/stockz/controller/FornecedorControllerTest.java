package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.exception.EntidadeNaoEncontradaException;
import br.com.catalisa.stockz.model.dto.FornecedorDTO;
import br.com.catalisa.stockz.service.FornecedorService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FornecedorController.class)
public class FornecedorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private FornecedorController fornecedorController;

    @MockBean
    private FornecedorService fornecedorService;

    @Autowired
    private ObjectMapper objectMapper;

    private FornecedorDTO fornecedorDTO1;
    private FornecedorDTO fornecedorDTO1Repetido;
    private FornecedorDTO fornecedorDTO2;

    @BeforeEach
    public void config(){

        fornecedorDTO1 = new FornecedorDTO("joao", "joao@example.com");
        fornecedorDTO1Repetido = new FornecedorDTO("joao", "joao@example.com");
        fornecedorDTO2 = new FornecedorDTO("joao", "joao@example.com");

    }

    @Test
    @DisplayName("Buscar todos os fornecedores ")
    public void buscarTodosOsFornecedoresTeste() throws Exception {

        List<FornecedorDTO> fornecedoresEsperados = new ArrayList<>();
        fornecedoresEsperados.add(fornecedorDTO1);
        fornecedoresEsperados.add(fornecedorDTO2);

        when(fornecedorService.listarTodos()).thenReturn(fornecedoresEsperados);

        String responseJson = objectMapper.writeValueAsString(fornecedoresEsperados);

        mockMvc.perform(get("/api/fornecedores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andDo(print());
    }

    @Test
    @DisplayName("Buscar comprador por ID existente")
    public void buscarFornecedorPorIDExistenteTeste() throws Exception {

        Long idFornecedor = 1L;

        when(fornecedorService.listarPorId(idFornecedor)).thenReturn(fornecedorDTO1);

        String responseJson = objectMapper.writeValueAsString(fornecedorDTO1);

        mockMvc.perform(get("/api/fornecedores/{id}", idFornecedor)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andDo(print());
    }

    @Test
    @DisplayName("Cadastrar novo fornecedor")
    public void cadastrarFornecedorTeste() throws Exception {

        when(fornecedorService.criar(Mockito.any(fornecedorDTO1.getClass()))).thenReturn(fornecedorDTO1);

        String requestJson = objectMapper.writeValueAsString(fornecedorDTO1);
        String responseJson = objectMapper.writeValueAsString(fornecedorDTO1);

        mockMvc.perform(post("/api/fornecedores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseJson))
                .andDo(print());

        verify(fornecedorService, times(1)).criar(Mockito.any());

    }

    @Test
    @DisplayName("Atualizar um fornecedor")
    public void alterarFornecedorTest() throws Exception {

        Long idFornecedor = 1L;
        when(fornecedorService.atualizar(Mockito.any(), Mockito.any(fornecedorDTO2.getClass()))).thenReturn(fornecedorDTO2);

        String requestJson = objectMapper.writeValueAsString(fornecedorDTO2);
        String responseJson = objectMapper.writeValueAsString(fornecedorDTO2);

        mockMvc.perform(put("/api/fornecedores/{id}", idFornecedor)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andDo(print());

        verify(fornecedorService, times(1)).atualizar(Mockito.any(), Mockito.any(fornecedorDTO2.getClass()));
    }

    @Test
    @DisplayName("Deletar um fornecedor existente")
    public void deletarUmFornecedorTest() throws Exception{

        mockMvc.perform(delete("/api/fornecedores/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk());

        Mockito.verify(fornecedorService, times(1)).deletar(Mockito.any());
    }

    @Test
    @DisplayName("Deletar um fornecedor inexistente")
    public void deletarUmFornecedorInexistenteTest() throws Exception{

        doThrow(new EntidadeNaoEncontradaException("Fornecedor não encontrado"))
                .when(fornecedorService).deletar(1L);

        mockMvc.perform(delete("/api/fornecedores/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNotFound());

    }
}
