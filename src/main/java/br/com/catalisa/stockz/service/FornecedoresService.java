package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.exception.EmailDuplicadoException;
import br.com.catalisa.stockz.model.Fornecedor;
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

        List<Fornecedor> fornecedorList = fornecedoresRepository.findAll();
        List<FornecedoresDTO> fornecedoresDTOList = new ArrayList<>();
        for (Fornecedor fornecedor : fornecedorList) {
            FornecedoresDTO fornecedoresDTO = fornecedoresMapper.toFornecedoresDTO(fornecedor);
            fornecedoresDTOList.add(fornecedoresDTO);
        }

        return fornecedoresDTOList;
    }

    public FornecedoresDTO listarPorId(Long id) throws Exception {
        Fornecedor fornecedorEncontrado = buscarFornecedorPorId(id);
        FornecedoresDTO fornecedoresDTO = fornecedoresMapper.toFornecedoresDTO(fornecedorEncontrado);

        return fornecedoresDTO;
    }

    public FornecedoresDTO criar(FornecedoresDTO fornecedoresDTO){
        Fornecedor fornecedor = fornecedoresMapper.toFornecedores(fornecedoresDTO);
        validarEmailUnicoFornecedor(fornecedor);
        fornecedoresRepository.save(fornecedor);
        return fornecedoresDTO;
    }

    public FornecedoresDTO atualizar(Long id, FornecedoresDTO fornecedoresDTO) throws Exception {

        Fornecedor fornecedorEncontrado = buscarFornecedorPorId(id);
        FornecedoresDTO fornecedoresDTORetorno = fornecedoresMapper.toFornecedoresDTO(fornecedorEncontrado);

        if (fornecedoresDTO.getNome() != null){
            fornecedoresDTORetorno.setNome(fornecedoresDTO.getNome());
        }
        if (fornecedoresDTO.getEmail() != null){
            fornecedoresDTORetorno.setEmail(fornecedoresDTO.getEmail());
        }

        return fornecedoresDTORetorno;
    }

    public void deletar(Long id) throws Exception {
        Fornecedor fornecedorEncontrado = buscarFornecedorPorId(id);
        fornecedoresRepository.delete(fornecedorEncontrado);
    }

    private Fornecedor buscarFornecedorPorId(Long id) throws Exception {
        Optional<Fornecedor> fornecedoresOptional = fornecedoresRepository.findById(id);
        if (fornecedoresOptional.isEmpty()) {
            throw new Exception("Fornecedor não encontrado");
        }
        return fornecedoresOptional.get();
    }

    public void validarEmailUnicoFornecedor(Fornecedor fornecedor)  {
        Optional<Fornecedor> fornecedorExistente = fornecedoresRepository.findByEmail(fornecedor.getEmail());
        if (fornecedorExistente.isPresent()) {
            throw new EmailDuplicadoException("Fornecedor já existente");
        }
    }

}
