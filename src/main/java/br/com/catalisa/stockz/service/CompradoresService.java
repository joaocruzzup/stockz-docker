package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.model.Compradores;
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

        List<Compradores> compradoresList = compradoresRepository.findAll();
        List<CompradoresDTO> compradoresDTOList = new ArrayList<>();
        for (Compradores compradores: compradoresList) {
            CompradoresDTO compradoresDto = compradoresMapper.toCompradoresDto(compradores);
            compradoresDTOList.add(compradoresDto);
        }

        return compradoresDTOList;
    }

    public CompradoresDTO listarPorId(Long id) throws Exception {

        Optional<Compradores> compradoresOptional = compradoresRepository.findById(id);

        if (compradoresOptional.isEmpty()){
            throw new Exception("Comprador não encontrada");
        }
        Compradores compradores = compradoresOptional.get();
        CompradoresDTO compradoresDTO = compradoresMapper.toCompradoresDto(compradores);

        return compradoresDTO;
    }

    public CompradoresDTO criar(CompradoresDTO compradoresDTO){
        Compradores compradores = compradoresMapper.toCompradores(compradoresDTO);
        compradoresRepository.save(compradores);
        return compradoresDTO;
    }

    public CompradoresDTO atualizar(Long id, CompradoresDTO compradoresDTO) throws Exception {

        Optional<Compradores> compradoresOptional = compradoresRepository.findById(id);
        if (compradoresOptional.isEmpty()){
            throw new Exception("Comprador não encontrada");
        }
        Compradores compradores = compradoresOptional.get();
        CompradoresDTO compradoresDtoRetorno = compradoresMapper.toCompradoresDto(compradores);

        if (compradoresDTO.getNome() != null){
            compradoresDtoRetorno.setNome(compradoresDTO.getNome());
        }
        if (compradoresDTO.getEmail() != null){
            compradoresDtoRetorno.setEmail(compradoresDTO.getEmail());
        }

        return compradoresDtoRetorno;
    }

    public void deletar(Long id) throws Exception {
        Optional<Compradores> compradoresOptional = compradoresRepository.findById(id);
        if (compradoresOptional.isEmpty()){
            throw new Exception("Comprador não encontrada");
        }
        Compradores compradores = compradoresOptional.get();
        compradoresRepository.delete(compradores);
    }
}
