package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.enums.StatusProduto;
import br.com.catalisa.stockz.enums.TipoTransacao;
import br.com.catalisa.stockz.exception.EntidadeNaoEncontradaException;
import br.com.catalisa.stockz.model.*;
import br.com.catalisa.stockz.model.dto.FornecedorDTO;
import br.com.catalisa.stockz.model.dto.TransacaoEntradaDTO;
import br.com.catalisa.stockz.model.dto.TransacaoEntradaResponseDTO;
import br.com.catalisa.stockz.repository.FornecedorRepository;
import br.com.catalisa.stockz.repository.ProdutoRepository;
import br.com.catalisa.stockz.repository.TransacaoEntradaRepository;
import br.com.catalisa.stockz.utils.mapper.TransacaoEntradaMapper;
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
public class TransacaoEntradaServiceTest {
    @Mock
    private TransacaoEntradaRepository transacaoEntradaRepository;

    @Mock
    private ProdutoRepository produtoRepository;

    @Mock
    private TransacaoEntradaMapper transacaoEntradaMapper;

    @Mock
    private EstoqueService estoqueService;

    @Mock
    private FornecedorRepository fornecedorRepository;

    @InjectMocks
    private TransacaoEntradaService transacaoEntradaService;

    private TransacaoEntradaDTO transacaoEntradaDTO;
    private Fornecedor fornecedor;
    private Produto produto;
    private FornecedorDTO fornecedorDTO;
    private TransacaoEntrada transacaoEntrada = new TransacaoEntrada();




    @BeforeEach
    public void config() {
        fornecedorDTO = new FornecedorDTO("joao", "joao@example.com");
        fornecedor = new Fornecedor();
        fornecedor.setEmail("joao@example.com");
        fornecedor.setEmail("João");
        produto = new Produto(1L, "Produto Teste", "Descrição do Produto", BigDecimal.valueOf(100.00), StatusProduto.ATIVO, new Categoria(1L, "Categoria Teste", new ArrayList<>()));
        transacaoEntradaDTO = new TransacaoEntradaDTO(100, produto, fornecedorDTO.getEmail(), LocalDateTime.now());
        transacaoEntrada.setProduto(produto);
        transacaoEntrada.setQuantidade(100);
        transacaoEntrada.setTipoTransacao(TipoTransacao.ENTRADA);
        transacaoEntrada.setUsuario(fornecedor);
        transacaoEntrada.setEstoque(new Estoque());
        transacaoEntrada.setDataHora(LocalDateTime.now());
    }

    @Test
    @DisplayName("Listar todas as transações de entrada")
    public void listarTodasTransacoesEntrada() {
        List<TransacaoEntrada> transacaoEntradaList = new ArrayList<>();
        transacaoEntradaList.add(transacaoEntrada);

        when(transacaoEntradaRepository.findAll()).thenReturn(transacaoEntradaList);
        when(transacaoEntradaMapper.toTransacaoEntradaResponseDTO(Mockito.any())).thenReturn(new TransacaoEntradaResponseDTO());

        List<TransacaoEntradaResponseDTO> listaTransacoes = transacaoEntradaService.listarTodos();

        assertEquals(1, listaTransacoes.size());

        verify(transacaoEntradaRepository, times(1)).findAll();
        verify(transacaoEntradaMapper, times(1)).toTransacaoEntradaResponseDTO(transacaoEntrada);
    }

    @Test
    @DisplayName("Listar transação de entrada por ID")
    public void listarTransacaoEntradaPorId() throws Exception {
        Long id = 1L;

        when(transacaoEntradaRepository.findById(id)).thenReturn(Optional.of(transacaoEntrada));
        when(transacaoEntradaMapper.toTransacaoEntradaResponseDTO(transacaoEntrada)).thenReturn(new TransacaoEntradaResponseDTO());

        TransacaoEntradaResponseDTO resultado = transacaoEntradaService.listarPorId(id);

        assertNotNull(resultado);
        verify(transacaoEntradaRepository, times(1)).findById(id);
        verify(transacaoEntradaMapper, times(1)).toTransacaoEntradaResponseDTO(transacaoEntrada);
    }

    @Test
    @DisplayName("Listar transação de entrada por ID inexistente")
    public void listarTransacaoEntradaPorIdInexistente() {
        Long id = 1L;

        when(transacaoEntradaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> transacaoEntradaService.listarPorId(id));
        verify(transacaoEntradaRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Criar transação de entrada com sucesso")
    public void criarTransacaoEntradaComSucesso() throws Exception {
        when(fornecedorRepository.findByEmail(transacaoEntradaDTO.getEmailFornecedor())).thenReturn(Optional.of(fornecedor));
        when(produtoRepository.findById(transacaoEntradaDTO.getProduto().getId())).thenReturn(Optional.of(produto));
        when(transacaoEntradaMapper.toTransacao(Mockito.any())).thenReturn(new TransacaoEntrada());
        when(transacaoEntradaRepository.save(Mockito.any())).thenReturn(new TransacaoEntrada());
        when(transacaoEntradaMapper.toTransacaoEntradaResponseDTO(Mockito.any())).thenReturn(new TransacaoEntradaResponseDTO());

        TransacaoEntradaResponseDTO resultado = transacaoEntradaService.criar(transacaoEntradaDTO);

        assertNotNull(resultado);

        verify(fornecedorRepository, times(1)).findByEmail(transacaoEntradaDTO.getEmailFornecedor());
        verify(produtoRepository, times(1)).findById(transacaoEntradaDTO.getProduto().getId());
        verify(transacaoEntradaMapper, times(1)).toTransacao(transacaoEntradaDTO);
        verify(transacaoEntradaRepository, times(1)).save(Mockito.any());
        verify(transacaoEntradaMapper, times(1)).toTransacaoEntradaResponseDTO(Mockito.any());
    }

    @Test
    @DisplayName("Criar transação de entrada com produto inativo")
    public void criarTransacaoEntradaComProdutoInativo() throws Exception {
        produto.setStatusProduto(StatusProduto.INATIVO);

        assertThrows(EntidadeNaoEncontradaException.class, () -> transacaoEntradaService.criar(transacaoEntradaDTO));
    }
}
