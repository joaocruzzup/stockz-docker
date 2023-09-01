package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.exception.EmailDuplicadoException;
import br.com.catalisa.stockz.exception.EntidadeNaoEncontradaException;
import br.com.catalisa.stockz.model.Comprador;
import br.com.catalisa.stockz.model.dto.CompradorDTO;
import br.com.catalisa.stockz.repository.CompradorRepository;
import br.com.catalisa.stockz.utils.mapper.CompradorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompradorService {
    @Autowired
    private CompradorRepository compradorRepository;

    @Autowired
    private CompradorMapper compradorMapper;

    public List<CompradorDTO> listarTodos(){

        List<Comprador> compradorList = compradorRepository.findAll();
        List<CompradorDTO> compradorDTOList = new ArrayList<>();
        for (Comprador comprador : compradorList) {
            CompradorDTO compradorDto = compradorMapper.toCompradoresDto(comprador);
            compradorDTOList.add(compradorDto);
        }

        return compradorDTOList;
    }

    public CompradorDTO listarPorId(Long id) throws Exception {
        Comprador compradorEncontrado = buscarCompradorPorId(id);
        CompradorDTO compradorDTO = compradorMapper.toCompradoresDto(compradorEncontrado);
        return compradorDTO;
    }

    public CompradorDTO criar(CompradorDTO compradorDTO){
        Comprador comprador = compradorMapper.toCompradores(compradorDTO);
        validarEmailUnicoComprador(comprador.getEmail());
        compradorRepository.save(comprador);
        return compradorDTO;
    }

    public CompradorDTO atualizar(Long id, CompradorDTO compradorDTO) throws Exception {

        Comprador compradorEncontrado = buscarCompradorPorId(id);

        if (compradorDTO.getNome() != null){
            compradorEncontrado.setNome(compradorDTO.getNome());
        }
        if (compradorDTO.getEmail() != null){
            validarEmailUnicoComprador(compradorDTO.getEmail());
            compradorEncontrado.setEmail(compradorDTO.getEmail());
        }

        CompradorDTO compradorDtoRetorno = compradorMapper.toCompradoresDto(compradorEncontrado);

        compradorRepository.save(compradorEncontrado);

        return compradorDtoRetorno;
    }

    public void deletar(Long id) throws Exception {
        Comprador compradorEncontrado = buscarCompradorPorId(id);
        compradorRepository.delete(compradorEncontrado);
    }

    public void validarEmailUnicoComprador(String email)  {
        Optional<Comprador> compradorExistente = compradorRepository.findByEmail(email);
        if (compradorExistente.isPresent()) {
            throw new EmailDuplicadoException("Comprador com esse email já existe");
        }
    }

    private Comprador buscarCompradorPorId(Long id) throws Exception {
        Optional<Comprador> compradoresOptional = compradorRepository.findById(id);
        if (compradoresOptional.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Comprador não encontrado");
        }
        return compradoresOptional.get();
    }
}
