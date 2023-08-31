package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.exception.EmailDuplicadoException;
import br.com.catalisa.stockz.exception.EntidadeNaoEncontradaException;
import br.com.catalisa.stockz.model.Comprador;
import br.com.catalisa.stockz.model.Fornecedor;
import br.com.catalisa.stockz.model.dto.CompradoresDTO;
import br.com.catalisa.stockz.repository.CompradoresRepository;
import br.com.catalisa.stockz.utils.mapper.CompradoresMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CompradoresService {
    @Autowired
    private CompradoresRepository compradoresRepository;

    @Autowired
    private CompradoresMapper compradoresMapper;

    public List<CompradoresDTO> listarTodos(){

        List<Comprador> compradorList = compradoresRepository.findAll();
        List<CompradoresDTO> compradoresDTOList = new ArrayList<>();
        for (Comprador comprador : compradorList) {
            CompradoresDTO compradoresDto = compradoresMapper.toCompradoresDto(comprador);
            compradoresDTOList.add(compradoresDto);
        }

        return compradoresDTOList;
    }

    public CompradoresDTO listarPorId(Long id) throws Exception {
        Comprador compradorEncontrado = buscarCompradorPorId(id);
        CompradoresDTO compradoresDTO = compradoresMapper.toCompradoresDto(compradorEncontrado);
        return compradoresDTO;
    }

    public CompradoresDTO criar(CompradoresDTO compradoresDTO){
        Comprador comprador = compradoresMapper.toCompradores(compradoresDTO);
        validarEmailUnicoComprador(comprador);
        compradoresRepository.save(comprador);
        return compradoresDTO;
    }

    public CompradoresDTO atualizar(Long id, CompradoresDTO compradoresDTO) throws Exception {

        Comprador compradorEncontrado = buscarCompradorPorId(id);
        CompradoresDTO compradoresDtoRetorno = compradoresMapper.toCompradoresDto(compradorEncontrado);

        if (compradoresDTO.getNome() != null){
            compradoresDtoRetorno.setNome(compradoresDTO.getNome());
        }
        if (compradoresDTO.getEmail() != null){
            compradoresDtoRetorno.setEmail(compradoresDTO.getEmail());
        }

        return compradoresDtoRetorno;
    }

    public void deletar(Long id) throws Exception {
        Comprador compradorEncontrado = buscarCompradorPorId(id);
        compradoresRepository.delete(compradorEncontrado);
    }

    public void validarEmailUnicoComprador(Comprador comprador)  {
        Optional<Comprador> compradorExistente = compradoresRepository.findByEmail(comprador.getEmail());
        if (compradorExistente.isPresent()) {
            throw new EmailDuplicadoException("Comprador já existente");
        }
    }

    private Comprador buscarCompradorPorId(Long id) throws Exception {
        Optional<Comprador> compradoresOptional = compradoresRepository.findById(id);
        if (compradoresOptional.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Comprador não encontrado");
        }
        return compradoresOptional.get();
    }
}
