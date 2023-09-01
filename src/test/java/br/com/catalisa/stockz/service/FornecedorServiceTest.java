package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.exception.EmailDuplicadoException;
import br.com.catalisa.stockz.model.Fornecedor;
import br.com.catalisa.stockz.model.dto.FornecedorDTO;
import br.com.catalisa.stockz.repository.FornecedorRepository;
import br.com.catalisa.stockz.utils.mapper.FornecedorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

public class FornecedorServiceTest {
    @Mock
    private FornecedorRepository fornecedorRepository;

    @Mock
    private FornecedorMapper fornecedorMapper;

    @InjectMocks
    private FornecedorService fornecedorService;

    private FornecedorDTO fornecedorDTO1;
    private Fornecedor fornecedor1;

    @BeforeEach
    public void config() {
        fornecedor1 = new Fornecedor();
        fornecedor1.setNome("Fornecedor1");
        fornecedor1.setNome("fornecedor1@example.com");
        fornecedorDTO1 = new FornecedorDTO("Fornecedor1", "fornecedor1@example.com");
    }

    @Test
    @DisplayName("Listar todos os fornecedores")
    public void listarTodosTest() {
        List<Fornecedor> fornecedorList = new ArrayList<>();
        fornecedorList.add(fornecedor1);

        List<FornecedorDTO> fornecedorDTOList = new ArrayList<>();
        fornecedorDTOList.add(fornecedorDTO1);

        when(fornecedorRepository.findAll()).thenReturn(fornecedorList);
        when(fornecedorMapper.toFornecedoresDTO(Mockito.any())).thenReturn(fornecedorDTO1);

        List<FornecedorDTO> listaFornecedoresDTO = fornecedorService.listarTodos();

        assertEquals(1, listaFornecedoresDTO.size());
        assertEquals(fornecedorDTOList, listaFornecedoresDTO);
        assertEquals(fornecedorDTO1, listaFornecedoresDTO.get(0));

        verify(fornecedorRepository, times(1)).findAll();
        verify(fornecedorMapper, times(1)).toFornecedoresDTO(fornecedor1);
    }

    @Test
    @DisplayName("Listar fornecedor por ID")
    public void listarPorIdTest() throws Exception {
        Long id = 1L;

        when(fornecedorRepository.findById(id)).thenReturn(Optional.of(fornecedor1));
        when(fornecedorMapper.toFornecedoresDTO(fornecedor1)).thenReturn(fornecedorDTO1);

        FornecedorDTO resultado = fornecedorService.listarPorId(id);

        assertEquals(fornecedorDTO1, resultado);
    }

    @Test
    @DisplayName("Listar fornecedor por ID inexistente")
    public void listarPorIdInexistente() {
        Long id = 1L;
        when(fornecedorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> fornecedorService.listarPorId(id));
    }

    @Test
    @DisplayName("Criar fornecedor com email único")
    public void criarFornecedorDTOComEmailUnicoTest() {
        Fornecedor fornecedor = new Fornecedor();
        when(fornecedorMapper.toFornecedores(fornecedorDTO1)).thenReturn(fornecedor);
        when(fornecedorRepository.findByEmail(fornecedor.getEmail())).thenReturn(Optional.empty());

        FornecedorDTO resultado = fornecedorService.criar(fornecedorDTO1);

        assertEquals(fornecedorDTO1, resultado);

        verify(fornecedorRepository, times(1)).findByEmail(fornecedor.getEmail());
        verify(fornecedorRepository, times(1)).save(fornecedor);
        verify(fornecedorMapper, times(1)).toFornecedores(fornecedorDTO1);
    }

//    @Test
//    @DisplayName("Criar fornecedor com email duplicado")
//    public void criarFornecedorDTOComEmailDuplicadoTest() {
//        Fornecedor fornecedor = new Fornecedor();
//        fornecedor.setEmail(fornecedorDTO1.getEmail());
//
//        when(fornecedorMapper.toFornecedores(fornecedorDTO1)).thenReturn(fornecedor);
//        when(fornecedorRepository.findByEmail(fornecedor.getEmail())).thenReturn(Optional.of(fornecedor));
//
//        assertThrows(EmailDuplicadoException.class, () -> fornecedorService.criar(fornecedorDTO1));
//
//        verify(fornecedorRepository, times(1)).findByEmail(fornecedor.getEmail());
//        verify(fornecedorRepository, never()).save(fornecedor);
//        verify(fornecedorMapper, times(1)).toFornecedores(fornecedorDTO1);
//    }

//    @Test
//    @DisplayName("Atualizar fornecedor por ID")
//    public void atualizarFornecedorDTOTest() throws Exception {
//        Long id = 1L;
//
//        when(fornecedorRepository.findById(id)).thenReturn(Optional.of(fornecedor1));
//        when(fornecedorMapper.toFornecedoresDTO(fornecedor1)).thenReturn(fornecedorDTO1);
//
//        FornecedorDTO resultado = fornecedorService.atualizar(id, fornecedorDTO1);
//
//        assertEquals(fornecedorDTO1, resultado);
//    }

    @Test
    @DisplayName("Deletar fornecedor existente")
    public void deletarFornecedorExistente() throws Exception {
        Long id = 1L;

        when(fornecedorRepository.findById(id)).thenReturn(Optional.of(fornecedor1));

        assertDoesNotThrow(() -> fornecedorService.deletar(id));

        verify(fornecedorRepository, times(1)).delete(fornecedor1);
    }

    @Test
    @DisplayName("Deletar fornecedor inexistente")
    public void deletarFornecedorInexistente() {
        Long id = 1L;

        when(fornecedorRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> fornecedorService.deletar(id));

        verify(fornecedorRepository, never()).delete(Mockito.any());
    }


}
