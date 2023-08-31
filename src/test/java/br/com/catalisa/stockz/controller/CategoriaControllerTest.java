package br.com.catalisa.stockz.controller;

import br.com.catalisa.stockz.exception.CategoriaJaCadastradaException;
import br.com.catalisa.stockz.exception.EntidadeNaoEncontradaException;
import br.com.catalisa.stockz.model.dto.CategoriaDTO;
import br.com.catalisa.stockz.service.CategoriaService;
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
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoriaController.class)
public class CategoriaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoriaService categoriaService;

    @Autowired
    private ObjectMapper objectMapper;


    private CategoriaDTO categoriaDTO1;
    private CategoriaDTO categoriaDTO1Repetida;
    private CategoriaDTO categoriaDTO2;

    @BeforeEach
    public void config(){

        categoriaDTO1 = new CategoriaDTO("ferramentas");
        categoriaDTO1Repetida = new CategoriaDTO("ferramentas");
        categoriaDTO2 = new CategoriaDTO("brinquedos");

    }

    @Test
    @DisplayName("Buscar todos as categorias ")
    public void buscarTodasAsCategoriasTeste() throws Exception {

        List<CategoriaDTO> categoriasEsperadas = new ArrayList<>();
        categoriasEsperadas.add(categoriaDTO1);
        categoriasEsperadas.add(categoriaDTO2);

        when(categoriaService.listarTodos()).thenReturn(categoriasEsperadas);

        String responseJson = objectMapper.writeValueAsString(categoriasEsperadas);

        mockMvc.perform(get("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andDo(print());
    }

    @Test
    @DisplayName("Buscar categoria por ID existente")
    public void buscarCategoriaPorIDExistenteTeste() throws Exception {

        Long idCategoria = 1L;

        when(categoriaService.listarPorId(idCategoria)).thenReturn(categoriaDTO1);

        String responseJson = objectMapper.writeValueAsString(categoriaDTO1);

        mockMvc.perform(get("/api/categorias/{id}", idCategoria)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andDo(print());
    }

    @Test
    @DisplayName("Buscar categoria por ID inexistente")
    public void buscarCategoriaPorIDInexistenteTeste() throws Exception {

        Long idCategoria = 1L;

        when(categoriaService.listarPorId(idCategoria)).thenThrow(new EntidadeNaoEncontradaException("Categoria não encontrada"));

        mockMvc.perform(get("/api/categorias/{id}", idCategoria)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Categoria não encontrada"))
                .andDo(print());
    }

    @Test
    @DisplayName("Cadastrar nova Categoria")
    public void cadastrarCategoriaTeste() throws Exception {

        when(categoriaService.criar(Mockito.any(categoriaDTO1.getClass()))).thenReturn(categoriaDTO1);

        String requestJson = objectMapper.writeValueAsString(categoriaDTO1);
        String responseJson = objectMapper.writeValueAsString(categoriaDTO1);

        mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(content().json(responseJson))
                .andDo(print());

        verify(categoriaService, times(1)).criar(Mockito.any());

    }

    @Test
    @DisplayName("Cadastrar nova Categoria Duplicada")
    public void cadastrarCategoriaDuplicadaTeste() throws Exception {

        when(categoriaService.criar(Mockito.any(categoriaDTO1.getClass()))).thenThrow(new CategoriaJaCadastradaException("Categoria com esse nome já está cadastrada"));

        String requestJson = objectMapper.writeValueAsString(categoriaDTO1);

        mockMvc.perform(post("/api/categorias")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isBadRequest())
                .andDo(print());

        verify(categoriaService, times(1)).criar(Mockito.any());

    }

    @Test
    @DisplayName("Atualizar um produto")
    public void alterarCategoriaTest() throws Exception {

        Long idCategoria = 1L;
        when(categoriaService.atualizar(Mockito.any(), Mockito.any(categoriaDTO2.getClass()))).thenReturn(categoriaDTO2);

        String requestJson = objectMapper.writeValueAsString(categoriaDTO2);
        String responseJson = objectMapper.writeValueAsString(categoriaDTO2);

        mockMvc.perform(put("/api/categorias/{id}", idCategoria)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(content().json(responseJson))
                .andDo(print());

        verify(categoriaService, times(1)).atualizar(Mockito.any(), Mockito.any(categoriaDTO2.getClass()));
    }

    @Test
    @DisplayName("Deletar uma categoria existente")
    public void deletarUmaCategoriaTest() throws Exception{

        mockMvc.perform(delete("/api/categorias/{id}", 1L))
                .andDo(print())
                .andExpect(status().isOk());

        Mockito.verify(categoriaService, times(1)).deletar(Mockito.any());
    }

    @Test
    @DisplayName("Deletar uma Categoria inexistente")
    public void deletarUmaCategoriaInexistenteTest() throws Exception{

        doThrow(new EntidadeNaoEncontradaException("Categoria não encontrada"))
                .when(categoriaService).deletar(1L);

        mockMvc.perform(delete("/api/categorias/{id}", 1L))
                .andDo(print())
                .andExpect(status().isNotFound());

    }
}
