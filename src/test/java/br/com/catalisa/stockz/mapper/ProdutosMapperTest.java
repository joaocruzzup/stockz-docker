package br.com.catalisa.stockz.mapper;

import br.com.catalisa.stockz.model.Categoria;
import br.com.catalisa.stockz.model.Produto;
import br.com.catalisa.stockz.model.dto.CategoriaDTO;
import br.com.catalisa.stockz.model.dto.ProdutoDTO;
import br.com.catalisa.stockz.model.dto.ProdutoDTOResponse;
import br.com.catalisa.stockz.utils.mapper.CategoriaMapper;
import br.com.catalisa.stockz.utils.mapper.ProdutosMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

public class ProdutosMapperTest {
    @InjectMocks
    private ProdutosMapper produtosMapper;

    @Mock
    private CategoriaMapper categoriaMapper;

    @Test
    public void toProdutosDTO() {
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto 1");
        produto.setDescricao("Descrição do Produto 1");
        produto.setPreco(BigDecimal.valueOf(19.99));

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Categoria 1");
        produto.setCategoria(categoria);

        ProdutoDTO produtoDTO = produtosMapper.toProdutosDTO(produto);

        assertEquals(produto.getNome(), produtoDTO.getNome());
        assertEquals(produto.getDescricao(), produtoDTO.getDescricao());
        assertEquals(produto.getPreco(), produtoDTO.getPreco());
        assertEquals(produto.getCategoria(), produtoDTO.getCategoria());
    }

    @Test
    public void toProdutos() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Produto 1");
        produtoDTO.setDescricao("Descrição do Produto 1");
        produtoDTO.setPreco(BigDecimal.valueOf(19.99));

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Categoria 1");
        produtoDTO.setCategoria(categoria);

        Produto produto = produtosMapper.toProdutos(produtoDTO);

        assertEquals(produtoDTO.getNome(), produto.getNome());
        assertEquals(produtoDTO.getDescricao(), produto.getDescricao());
        assertEquals(produtoDTO.getPreco(), produto.getPreco());
        assertEquals(produtoDTO.getCategoria(), produto.getCategoria());
    }

    @Test
    public void toProdutoResponse() {
        ProdutoDTO produtoDTO = new ProdutoDTO();
        produtoDTO.setNome("Produto 1");
        produtoDTO.setDescricao("Descrição do Produto 1");
        produtoDTO.setPreco(BigDecimal.valueOf(19.99));

        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("");
        produtoDTO.setCategoria(categoria);

        when(categoriaMapper.toCategoriasDto(categoria)).thenReturn(new CategoriaDTO("Categoria 1"));

        ProdutoDTOResponse produtoDTOResponse = produtosMapper.toProdutoResponse(produtoDTO);

        assertEquals(produtoDTO.getNome(), produtoDTOResponse.getNome());
        assertEquals(produtoDTO.getDescricao(), produtoDTOResponse.getDescricao());
        assertEquals(produtoDTO.getPreco(), produtoDTOResponse.getPreco());
    }
}
