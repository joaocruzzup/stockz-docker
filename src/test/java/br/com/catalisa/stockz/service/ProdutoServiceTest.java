package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.enums.StatusProduto;
import br.com.catalisa.stockz.exception.EntidadeNaoEncontradaException;
import br.com.catalisa.stockz.model.Categoria;
import br.com.catalisa.stockz.model.Produto;
import br.com.catalisa.stockz.model.dto.ProdutoDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProdutoServiceTest {
    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private ProdutosMapper produtosMapper;

    @InjectMocks
    private ProdutoService produtoService;

    private ProdutoDTO produtoDTO1;
    private Produto produto1;
    private Categoria categoria;

    @BeforeEach
    public void config(){

        categoria = new Categoria(1L,"ferramentas", new ArrayList<>());
        produto1 = new Produto(1L, "computador","30gb ram", BigDecimal.valueOf(3000), StatusProduto.ATIVO, categoria);
        produtoDTO1 = new ProdutoDTO("computador", "30gb ram", categoria, BigDecimal.valueOf(3000));

    }
    @Test
    @DisplayName("listar contas")
    public void listarContasTeste(){
        List<ProdutoDTO> produtoDTOList = Arrays.asList(produtoDTO1);

        when(produtoRepository.findAll()).thenReturn(Arrays.asList(produto1));
        when(produtosMapper.toProdutosDTO(Mockito.any())).thenReturn(produtoDTO1);

        List<ProdutoDTO> listaContasDTO = produtoService.listarTodos();

        assertEquals(1  , listaContasDTO.size());
        assertEquals(produtoDTOList, listaContasDTO);
        assertEquals(produtoDTO1, listaContasDTO.get(0));

        verify(produtoRepository).findAll();
        verify(produtosMapper).toProdutosDTO(produto1);
    }

    @Test
    @DisplayName("listar produto por id")
    public void listarProdutoPorIdTeste(){
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
}
