package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.enums.StatusProduto;
import br.com.catalisa.stockz.exception.AtributoIncorretoException;
import br.com.catalisa.stockz.exception.EntidadeNaoEncontradaException;
import br.com.catalisa.stockz.model.Categoria;
import br.com.catalisa.stockz.model.Produto;
import br.com.catalisa.stockz.model.dto.DelecaoResponse;
import br.com.catalisa.stockz.model.dto.ProdutoDTO;
import br.com.catalisa.stockz.repository.CategoriaRepository;
import br.com.catalisa.stockz.repository.ProdutoRepository;
import br.com.catalisa.stockz.utils.mapper.ProdutosMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {
    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private ProdutosMapper produtosMapper;

    @InjectMocks
    private ProdutoService produtoService;

    private ProdutoDTO produtoDTO1;
    private Produto produto1;
    private Categoria categoria;

    @BeforeEach
    public void config() {

        categoria = new Categoria(1L, "ferramentas", new ArrayList<>());
        produto1 = new Produto(1L, "computador", "30gb ram", BigDecimal.valueOf(3000), StatusProduto.ATIVO, categoria);

        produtoDTO1 = new ProdutoDTO("computador", "30gb ram", categoria, BigDecimal.valueOf(3000));

    }

    @Test
    @DisplayName("listar produtos")
    public void listarProdutosTeste() {
        List<ProdutoDTO> produtoDTOList = Arrays.asList(produtoDTO1);

        when(produtoRepository.findAll()).thenReturn(Arrays.asList(produto1));
        when(produtosMapper.toProdutosDTO(Mockito.any())).thenReturn(produtoDTO1);

        List<ProdutoDTO> listaContasDTO = produtoService.listarTodos();

        assertEquals(1, listaContasDTO.size());
        assertEquals(produtoDTOList, listaContasDTO);
        assertEquals(produtoDTO1, listaContasDTO.get(0));

        verify(produtoRepository, times(1)).findAll();
        verify(produtosMapper, times(1)).toProdutosDTO(produto1);
    }

    @Test
    @DisplayName("listar produto por id")
    public void listarProdutoPorIdTeste() {
        Long id = 1L;

        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto1));
        when(produtosMapper.toProdutosDTO(produto1)).thenReturn(produtoDTO1);

        ProdutoDTO resultado = produtoService.listarPorId(id);

        assertEquals(produtoDTO1, resultado);
    }

    @Test
    @DisplayName("listar produto com id inexistente")
    public void listarProdutoPorIdInexistente() {
        Long id = 1L;
        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> produtoService.listarPorId(id));
    }

    @Test
    @DisplayName("Teste criar ProdutoDTO")
    public void criarProdutoDTOTest() {
        Produto produto = new Produto();
        when(categoriaRepository.findById(categoria.getId())).thenReturn(Optional.of(categoria));

        when(produtosMapper.toProdutos(produtoDTO1)).thenReturn(produto);

        ProdutoDTO resultado = produtoService.criar(produtoDTO1);

        assertEquals(produtoDTO1, resultado);

        verify(categoriaRepository, times(1)).findById(categoria.getId());
        verify(produtoRepository, times(1)).save(produto);
        verify(produtosMapper, times(1)).toProdutos(produtoDTO1);
    }

    @Test
    @DisplayName("Teste criar ProdutoDTO com Categoria inexistente")
    public void criarProdutoDTOComCategoriaInexistenteTest() {
        when(categoriaRepository.findById(categoria.getId())).thenReturn(Optional.empty());

        assertThrows(AtributoIncorretoException.class, () -> produtoService.criar(produtoDTO1));

        verify(categoriaRepository, times(1)).findById(categoria.getId());
    }

    @Test
    public void DeletarProdutoExistente() {
        Long id = 1L;
        Produto produto = new Produto();
        produto.setId(id);
        produto.setStatusProduto(StatusProduto.ATIVO);

        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));

        DelecaoResponse response = produtoService.deletar(id);

        assertNotNull(response);
        assertEquals("Produto deletado com sucesso", response.getMensagem());
        assertEquals(StatusProduto.INATIVO, produto.getStatusProduto());

        verify(produtoRepository, times(1)).save(produto);
    }

    @Test
    public void DeletarProdutoNaoExistente() {
        Long id = 1L;

        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> {
            produtoService.deletar(id);
        });

        verify(produtoRepository, never()).save(any());
    }

    @Test
    public void BuscarProdutoPorIdExistente() {
        Long id = 1L;
        Produto produto = new Produto();
        produto.setId(id);

        when(produtoRepository.findById(id)).thenReturn(Optional.of(produto));

        Produto resultado = produtoService.buscarProdutoPorId(id);

        assertNotNull(resultado);
        assertEquals(id, resultado.getId());

        verify(produtoRepository, times(1)).findById(id);
    }

    @Test
    public void BuscarProdutoPorIdNaoExistente() {
        Long id = 1L;

        when(produtoRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> {
            produtoService.buscarProdutoPorId(id);
        });

        verify(produtoRepository, times(1)).findById(id);
    }

    @Test
    public void BuscarProdutosPorNomeExistente() {
        String nome = "Produto1";
        Produto produto1 = new Produto();
        produto1.setNome(nome);

        List<Produto> produtos = new ArrayList<>();
        produtos.add(produto1);

        when(produtoRepository.findAllByNome(nome)).thenReturn(produtos);

        List<Produto> resultado = produtoService.buscarProdutosPorNome(nome);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(nome, resultado.get(0).getNome());

        verify(produtoRepository, times(1)).findAllByNome(nome);
    }

    @Test
    public void BuscarProdutosPorNomeNaoExistente() {
        String nome = "Produto1";

        when(produtoRepository.findAllByNome(nome)).thenReturn(new ArrayList<>());

        assertThrows(EntidadeNaoEncontradaException.class, () -> {
            produtoService.buscarProdutosPorNome(nome);
        });

        verify(produtoRepository, times(1)).findAllByNome(nome);
    }
}








