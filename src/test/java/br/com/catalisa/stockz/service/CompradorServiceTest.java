package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.exception.EmailDuplicadoException;
import br.com.catalisa.stockz.exception.EntidadeNaoEncontradaException;
import br.com.catalisa.stockz.model.Comprador;
import br.com.catalisa.stockz.model.Usuario;
import br.com.catalisa.stockz.model.dto.CompradorDTO;
import br.com.catalisa.stockz.repository.CompradorRepository;
import br.com.catalisa.stockz.utils.mapper.CompradorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.validation.constraints.Email;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CompradorServiceTest {
    @Mock
    private CompradorRepository compradorRepository;

    @Mock
    private CompradorMapper compradorMapper;

    @InjectMocks
    private CompradorService compradorService;

    private CompradorDTO compradorDTO1;
    private Comprador comprador1;

    @BeforeEach
    public void config() {
        comprador1 = new Comprador();
        comprador1.setEmail("joao@example.com");
        comprador1.setEmail("João");
        compradorDTO1 = new CompradorDTO("João", "joao@example.com");
    }

    @Test
    @DisplayName("listar compradores")
    public void listarCompradoresTeste() {
        List<CompradorDTO> compradorDTOList = Arrays.asList(compradorDTO1);

        when(compradorRepository.findAll()).thenReturn(Arrays.asList(comprador1));
        when(compradorMapper.toCompradoresDto(Mockito.any())).thenReturn(compradorDTO1);

        List<CompradorDTO> listaCompradoresDTO = compradorService.listarTodos();

        assertEquals(1, listaCompradoresDTO.size());
        assertEquals(compradorDTOList, listaCompradoresDTO);
        assertEquals(compradorDTO1, listaCompradoresDTO.get(0));

        verify(compradorRepository, times(1)).findAll();
        verify(compradorMapper, times(1)).toCompradoresDto(comprador1);
    }

    @Test
    @DisplayName("listar comprador por id")
    public void listarCompradorPorIdTeste() throws Exception {
        Long id = 1L;

        when(compradorRepository.findById(id)).thenReturn(Optional.of(comprador1));
        when(compradorMapper.toCompradoresDto(comprador1)).thenReturn(compradorDTO1);

        CompradorDTO resultado = compradorService.listarPorId(id);

        assertEquals(compradorDTO1, resultado);
    }

    @Test
    @DisplayName("listar comprador com id inexistente")
    public void listarCompradorPorIdInexistente() throws Exception {
        Long id = 1L;
        when(compradorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> compradorService.listarPorId(id));
    }

    @Test
    @DisplayName("Teste criar CompradorDTO")
    public void criarCompradorDTOTest() {
        Comprador comprador = new Comprador();
        when(compradorMapper.toCompradores(compradorDTO1)).thenReturn(comprador);

        compradorService.criar(compradorDTO1);

        verify(compradorRepository, times(1)).save(comprador);
        verify(compradorMapper, times(1)).toCompradores(compradorDTO1);
    }

    @Test
    @DisplayName("Teste atualizar CompradorDTO")
    public void atualizarCompradorDTOTest() throws Exception {
        Long id = 1L;

        when(compradorRepository.findById(id)).thenReturn(Optional.of(comprador1));
        CompradorDTO compradorDTOAtualizado = new CompradorDTO("Maria", "maria@example.com");

        Comprador compradorAtualizado = new Comprador();
        compradorAtualizado.setId(id);
        compradorAtualizado.setNome("Maria");
        compradorAtualizado.setEmail("maria@example.com");


        when(compradorMapper.toCompradoresDto(Mockito.any())).thenReturn(compradorDTOAtualizado);

        CompradorDTO resultado = compradorService.atualizar(id, compradorDTOAtualizado);

        assertEquals(compradorDTOAtualizado, resultado);

        verify(compradorRepository, times(1)).findById(id);
        verify(compradorRepository, times(1)).save(Mockito.any());
        verify(compradorMapper, times(1)).toCompradoresDto(Mockito.any());

        assertEquals(compradorDTOAtualizado.getNome(), compradorAtualizado.getNome());
        assertEquals(compradorDTOAtualizado.getEmail(), compradorAtualizado.getEmail());
    }

    @Test
    public void testDeletarCompradorExistente() throws Exception {
        Long id = 1L;

        when(compradorRepository.findById(id)).thenReturn(Optional.of(comprador1));

        compradorService.deletar(id);

        verify(compradorRepository, times(1)).delete(comprador1);
    }

    @Test
    public void testDeletarCompradorNaoExistente() throws Exception {
        Long id = 1L;

        when(compradorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> compradorService.deletar(id));

        verify(compradorRepository, never()).delete(any());
    }

}
