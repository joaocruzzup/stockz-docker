package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.exception.EntidadeNaoEncontradaException;
import br.com.catalisa.stockz.model.dto.CompradorDTO;
import br.com.catalisa.stockz.service.CompradorService;
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

@WebMvcTest(CompradorController.class)
public class CompradorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CompradorController compradorController;

    @MockBean
    private CompradorService compradorService;

    @Autowired
    private ObjectMapper objectMapper;

    private CompradorDTO compradorDTO1;
    private CompradorDTO compradorDTO1Repetido;
    private CompradorDTO compradorDTO2;

    @BeforeEach
    public void config(){

        compradorDTO1 = new CompradorDTO("joao", "joao@example.com");
        compradorDTO1Repetido = new CompradorDTO("joao", "joao@example.com");
        compradorDTO2 = new CompradorDTO("joao", "joao@example.com");

    }

    @Test
    @DisplayName("Buscar todos os compradores ")
    public void buscarTodosOsCompradoresTeste() throws Exception {

        List<CompradorDTO> compradoresEsperados = new ArrayList<>();
        compradoresEsperados.add(compradorDTO1);
        compradoresEsperados.add(compradorDTO2);

        when(compradorService.listarTodos()).thenReturn(compradoresEsperados);

        String responseJson = objectMapper.writeValueAsString(compradoresEsperados);

        mockMvc.perform(get("/api/compradores")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andDo(print());
    }

    @Test
    @DisplayName("Buscar comprador por ID existente")
    public void buscarCompradorPorIDExistenteTeste() throws Exception {

        Long idComprador = 1L;

        when(compradorService.listarPorId(idComprador)).thenReturn(compradorDTO1);

        String responseJson = objectMapper.writeValueAsString(compradorDTO1);

        mockMvc.perform(get("/api/compradores/{id}", idComprador)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andDo(print());
    }

    @Test
    @DisplayName("Cadastrar novo comprador")
    public void cadastrarCompradorTeste() throws Exception {

        when(compradorService.criar(Mockito.any(compradorDTO1.getClass()))).thenReturn(compradorDTO1);

        String requestJson = objectMapper.writeValueAsString(compradorDTO1);
        String responseJson = objectMapper.writeValueAsString(compradorDTO1);

        mockMvc.perform(post("/api/compradores")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseJson))
                .andDo(print());

        verify(compradorService, times(1)).criar(Mockito.any());

    }

    @Test
    @DisplayName("Atualizar um produto")
    public void alterarCompradorTest() throws Exception {

        Long idComprador = 1L;
        when(compradorService.atualizar(Mockito.any(), Mockito.any(compradorDTO2.getClass()))).thenReturn(compradorDTO2);

        String requestJson = objectMapper.writeValueAsString(compradorDTO2);
        String responseJson = objectMapper.writeValueAsString(compradorDTO2);

        mockMvc.perform(put("/api/compradores/{id}", idComprador)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andDo(print());

        verify(compradorService, times(1)).atualizar(Mockito.any(), Mockito.any(compradorDTO2.getClass()));
    }

    @Test
    @DisplayName("Deletar um comprador existente")
    public void deletarUmaCompradorTest() throws Exception{

        mockMvc.perform(delete("/api/compradores/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk());

        Mockito.verify(compradorService, times(1)).deletar(Mockito.any());
    }

    @Test
    @DisplayName("Deletar um comprador inexistente")
    public void deletarUmCompradorInexistenteTest() throws Exception{

        doThrow(new EntidadeNaoEncontradaException("Comprador não encontrada"))
                .when(compradorService).deletar(1L);

        mockMvc.perform(delete("/api/compradores/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNotFound());

    }


}
