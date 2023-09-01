package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.exception.CategoriaJaCadastradaException;
import br.com.catalisa.stockz.exception.EntidadeNaoEncontradaException;
import br.com.catalisa.stockz.model.Categoria;
import br.com.catalisa.stockz.model.Produto;
import br.com.catalisa.stockz.model.dto.CategoriaDTO;
import br.com.catalisa.stockz.model.dto.DelecaoResponse;
import br.com.catalisa.stockz.repository.CategoriaRepository;
import br.com.catalisa.stockz.repository.ProdutoRepository;
import br.com.catalisa.stockz.utils.mapper.CategoriaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CategoriaServiceTest {
    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaMapper categoriaMapper;

    @InjectMocks
    private CategoriaService categoriaService;

    private CategoriaDTO categoriaDTO1;
    private Categoria categoria1;

    @BeforeEach
    public void config() {
        categoria1 = new Categoria(1L, "eletrônicos", new ArrayList<>());
        categoriaDTO1 = new CategoriaDTO("eletrônicos");
    }

    @Test
    @DisplayName("listar categorias")
    public void listarCategoriasTeste() {
        List<CategoriaDTO> categoriaDTOList = Arrays.asList(categoriaDTO1);

        when(categoriaRepository.findAll()).thenReturn(Arrays.asList(categoria1));
        when(categoriaMapper.toCategoriasDto(Mockito.any())).thenReturn(categoriaDTO1);

        List<CategoriaDTO> listaCategoriasDTO = categoriaService.listarTodos();

        assertEquals(1, listaCategoriasDTO.size());
        assertEquals(categoriaDTOList, listaCategoriasDTO);
        assertEquals(categoriaDTO1, listaCategoriasDTO.get(0));

        verify(categoriaRepository, times(1)).findAll();
        verify(categoriaMapper, times(1)).toCategoriasDto(categoria1);
    }

    @Test
    @DisplayName("listar categoria por id")
    public void listarCategoriaPorIdTeste() throws Exception {
        Long id = 1L;

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria1));
        when(categoriaMapper.toCategoriasDto(categoria1)).thenReturn(categoriaDTO1);

        CategoriaDTO resultado = categoriaService.listarPorId(id);

        assertEquals(categoriaDTO1, resultado);
    }

    @Test
    @DisplayName("listar categoria com id inexistente")
    public void listarCategoriaPorIdInexistente() throws Exception {
        Long id = 1L;
        when(categoriaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> categoriaService.listarPorId(id));
    }

    @Test
    @DisplayName("Teste criar CategoriaDTO")
    public void criarCategoriaDTOTest() {
        Categoria categoria = new Categoria();
        when(categoriaMapper.toCategorias(categoriaDTO1)).thenReturn(categoria);

        categoriaService.criar(categoriaDTO1);

        verify(categoriaRepository, times(1)).save(categoria);
        verify(categoriaMapper, times(1)).toCategorias(categoriaDTO1);
    }

    @Test
    @DisplayName("Teste criar CategoriaDTO com Categoria já cadastrada")
    public void criarCategoriaDTOComCategoriaJaCadastradaTest() {
        when(categoriaRepository.findByNome(categoriaDTO1.getNome())).thenReturn(Optional.of(categoria1));

        assertThrows(CategoriaJaCadastradaException.class, () -> categoriaService.criar(categoriaDTO1));

        verify(categoriaRepository, times(1)).findByNome(categoriaDTO1.getNome());
    }

    @Test
    public void DeletarCategoriaExistente() throws Exception {
        Long id = 1L;

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria1));
        when(produtoRepository.findByCategoria(categoria1)).thenReturn(Optional.empty());

        DelecaoResponse response = categoriaService.deletar(id);

        assertNotNull(response);
        assertEquals("Categoria deletada com sucesso", response.getMensagem());

        verify(categoriaRepository, times(1)).delete(categoria1);
    }

    @Test
    public void DeletarCategoriaAtreladaAProduto() throws Exception {
        Long id = 1L;

        when(categoriaRepository.findById(id)).thenReturn(Optional.of(categoria1));
        when(produtoRepository.findByCategoria(categoria1)).thenReturn(Optional.of(new Produto()));

        assertThrows(Exception.class, () -> categoriaService.deletar(id));

        verify(categoriaRepository, never()).delete(any());
    }
}
