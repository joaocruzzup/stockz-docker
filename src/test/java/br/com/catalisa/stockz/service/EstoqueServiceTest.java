package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.enums.StatusProduto;
import br.com.catalisa.stockz.enums.TipoTransacao;
import br.com.catalisa.stockz.model.*;
import br.com.catalisa.stockz.model.dto.EstoqueDTO;
import br.com.catalisa.stockz.repository.EstoqueRepository;
import br.com.catalisa.stockz.repository.TransacaoEntradaRepository;
import br.com.catalisa.stockz.repository.TransacaoSaidaRepository;
import br.com.catalisa.stockz.utils.mapper.EstoqueMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EstoqueServiceTest {
    @Mock
    private EstoqueRepository estoqueRepository;

    @Mock
    private EstoqueMapper estoqueMapper;

    @InjectMocks
    private EstoqueService estoqueService;

    private EstoqueDTO estoqueDTO;
    private Estoque estoque;
    private Transacao transacao;
    private Produto produto;

    @BeforeEach
    public void config() {
        produto = new Produto(1L, "computador","30gb ram", BigDecimal.valueOf(300), StatusProduto.ATIVO, new Categoria());
        estoque = new Estoque(1L,10, LocalDateTime.now(), produto, new ArrayList<>());
        estoqueDTO = new EstoqueDTO(produto, 10, LocalDateTime.now());
        transacao = new Transacao(1L, LocalDateTime.now(), 10, TipoTransacao.ENTRADA, produto, new Usuario(), estoque);

    }

    @Test
    @DisplayName("Listar estoque")
    public void listarEstoqueTeste() {
        List<Estoque> estoqueList = new ArrayList<>();
        estoqueList.add(estoque);

        when(estoqueRepository.findAll()).thenReturn(estoqueList);
        when(estoqueMapper.toEstoqueDTO(estoque)).thenReturn(estoqueDTO);

        List<EstoqueDTO> listaEstoqueDTO = estoqueService.listarTodos();

        assertEquals(1, listaEstoqueDTO.size());
        assertEquals(estoqueDTO, listaEstoqueDTO.get(0));

        verify(estoqueRepository, times(1)).findAll();
        verify(estoqueMapper, times(1)).toEstoqueDTO(estoque);
    }

    @Test
    @DisplayName("Adicionar estoque de entrada")
    public void adicionarEstoqueEntradaTeste() {
        when(estoqueRepository.findByProduto(produto)).thenReturn(Optional.of(estoque));

        estoqueService.adicionarEstoque(transacao);

        verify(estoqueRepository, times(1)).save(estoque);
    }

}
