package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.model.Fornecedores;
import br.com.catalisa.stockz.model.dto.FornecedoresDTO;
import br.com.catalisa.stockz.repository.FornecedoresRepository;
import br.com.catalisa.stockz.utils.mapper.FornecedoresMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FornecedoresService {
    @Autowired
    private FornecedoresRepository fornecedoresRepository;

    @Autowired
    private FornecedoresMapper fornecedoresMapper;

    public List<FornecedoresDTO> listarTodos(){

        List<Fornecedores> fornecedoresList = fornecedoresRepository.findAll();
        List<FornecedoresDTO> fornecedoresDTOList = new ArrayList<>();
        for (Fornecedores fornecedores: fornecedoresList) {
            FornecedoresDTO fornecedoresDTO = fornecedoresMapper.toFornecedoresDTO(fornecedores);
            fornecedoresDTOList.add(fornecedoresDTO);
        }

        return fornecedoresDTOList;
    }

    public FornecedoresDTO listarPorId(Long id) throws Exception {

        Optional<Fornecedores> fornecedoresOptional = fornecedoresRepository.findById(id);

        if (fornecedoresOptional.isEmpty()){
            throw new Exception("Fornecedor não encontrada");
        }
        Fornecedores fornecedores = fornecedoresOptional.get();
        FornecedoresDTO fornecedoresDTO = fornecedoresMapper.toFornecedoresDTO(fornecedores);

        return fornecedoresDTO;
    }

    public FornecedoresDTO criar(FornecedoresDTO fornecedoresDTO){
        Fornecedores fornecedores = fornecedoresMapper.toFornecedores(fornecedoresDTO);
        fornecedoresRepository.save(fornecedores);
        return fornecedoresDTO;
    }

    public FornecedoresDTO atualizar(Long id, FornecedoresDTO fornecedoresDTO) throws Exception {

        Optional<Fornecedores> fornecedoresOptional = fornecedoresRepository.findById(id);
        if (fornecedoresOptional.isEmpty()){
            throw new Exception("Fornecedor não encontrada");
        }
        Fornecedores fornecedores = fornecedoresOptional.get();
        FornecedoresDTO fornecedoresDTORetorno = fornecedoresMapper.toFornecedoresDTO(fornecedores);

        if (fornecedoresDTO.getNome() != null){
            fornecedoresDTORetorno.setNome(fornecedoresDTO.getNome());
        }
        if (fornecedoresDTO.getEmail() != null){
            fornecedoresDTORetorno.setEmail(fornecedoresDTO.getEmail());
        }

        return fornecedoresDTORetorno;
    }

    public void deletar(Long id) throws Exception {
        Optional<Fornecedores> fornecedoresOptional = fornecedoresRepository.findById(id);
        if (fornecedoresOptional.isEmpty()){
            throw new Exception("Fornecedor não encontrada");
        }
        Fornecedores fornecedores = fornecedoresOptional.get();
        fornecedoresRepository.delete(fornecedores);
    }

}
