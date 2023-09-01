package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.enums.StatusProduto;
import br.com.catalisa.stockz.enums.TipoTransacao;
import br.com.catalisa.stockz.exception.EntidadeNaoEncontradaException;
import br.com.catalisa.stockz.model.*;
import br.com.catalisa.stockz.model.dto.TransacaoSaidaDTO;
import br.com.catalisa.stockz.model.dto.TransacaoSaidaResponseDTO;
import br.com.catalisa.stockz.repository.CompradorRepository;
import br.com.catalisa.stockz.repository.EstoqueRepository;
import br.com.catalisa.stockz.repository.ProdutoRepository;
import br.com.catalisa.stockz.repository.TransacaoSaidaRepository;
import br.com.catalisa.stockz.utils.mapper.TransacaoSaidaMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class TransacaoSaidaServiceTest {
    @Mock
    private TransacaoSaidaRepository transacaoSaidaRepository;

    @Mock
    private CompradorRepository compradorRepository;

    @Mock
    private EstoqueRepository estoqueRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private TransacaoSaidaMapper transacaoSaidaMapper;

    @InjectMocks
    private TransacaoSaidaService transacaoSaidaService;

    private TransacaoSaidaDTO transacaoSaidaDTO;
    private Comprador comprador;
    private Produto produto;
    private Estoque estoque;
    private TransacaoSaida transacaoSaida = new TransacaoSaida();

    @BeforeEach
    public void config() {
        comprador = new Comprador();
        comprador.setEmail("comprador@example.com");
        comprador.setNome("Comprador Teste");

        produto = new Produto(1L, "Produto Teste", "Descrição do Produto", BigDecimal.valueOf(100.00), StatusProduto.ATIVO, new Categoria(1L, "Categoria Teste", new ArrayList<>()));

        transacaoSaidaDTO = new TransacaoSaidaDTO(50, produto, comprador.getEmail(), LocalDateTime.now());

        estoque = new Estoque();
        estoque.setQuantidade(100);

        transacaoSaida.setProduto(produto);
        transacaoSaida.setQuantidade(50);
        transacaoSaida.setComprador(comprador);
        transacaoSaida.setTipoTransacao(TipoTransacao.SAIDA);
        transacaoSaida.setEstoque(estoque);
        transacaoSaida.setDataHora(LocalDateTime.now());
    }

    @Test
    @DisplayName("Listar todas as transações de saída")
    public void listarTodasTransacoesSaida() {
        List<TransacaoSaida> transacaoSaidaList = new ArrayList<>();
        transacaoSaidaList.add(transacaoSaida);

        when(transacaoSaidaRepository.findAll()).thenReturn(transacaoSaidaList);
        when(transacaoSaidaMapper.toTransacaoSaidaResponseDTO(Mockito.any())).thenReturn(new TransacaoSaidaResponseDTO());

        List<TransacaoSaidaResponseDTO> listaTransacoes = transacaoSaidaService.listarTodos();

        assertEquals(1, listaTransacoes.size());

        verify(transacaoSaidaRepository, times(1)).findAll();
        verify(transacaoSaidaMapper, times(1)).toTransacaoSaidaResponseDTO(transacaoSaida);
    }

    @Test
    @DisplayName("Listar transação de saída por ID")
    public void listarTransacaoSaidaPorId() throws Exception {
        Long id = 1L;

        when(transacaoSaidaRepository.findById(id)).thenReturn(Optional.of(transacaoSaida));
        when(transacaoSaidaMapper.toTransacaoSaidaResponseDTO(transacaoSaida)).thenReturn(new TransacaoSaidaResponseDTO());

        TransacaoSaidaResponseDTO resultado = transacaoSaidaService.listarPorId(id);

        assertNotNull(resultado);
        verify(transacaoSaidaRepository, times(1)).findById(id);
        verify(transacaoSaidaMapper, times(1)).toTransacaoSaidaResponseDTO(transacaoSaida);
    }

    @Test
    @DisplayName("Listar transação de saída por ID inexistente")
    public void listarTransacaoSaidaPorIdInexistente() {
        Long id = 1L;

        when(transacaoSaidaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> transacaoSaidaService.listarPorId(id));
        verify(transacaoSaidaRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Criar transação de saída com produto inativo")
    public void criarTransacaoSaidaComProdutoInativo() throws Exception {
        produto.setStatusProduto(StatusProduto.INATIVO);

        assertThrows(EntidadeNaoEncontradaException.class, () -> transacaoSaidaService.criar(transacaoSaidaDTO));
    }
}
