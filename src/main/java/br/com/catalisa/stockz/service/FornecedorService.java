package br.com.catalisa.stockz.service;

import br.com.catalisa.stockz.exception.EmailDuplicadoException;
import br.com.catalisa.stockz.model.Fornecedor;
import br.com.catalisa.stockz.model.dto.FornecedorDTO;
import br.com.catalisa.stockz.repository.FornecedorRepository;
import br.com.catalisa.stockz.utils.mapper.FornecedorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FornecedorService {
    @Autowired
    private FornecedorRepository fornecedorRepository;

    @Autowired
    private FornecedorMapper fornecedorMapper;

    public List<FornecedorDTO> listarTodos(){

        List<Fornecedor> fornecedorList = fornecedorRepository.findAll();
        List<FornecedorDTO> fornecedorDTOList = new ArrayList<>();
        for (Fornecedor fornecedor : fornecedorList) {
            FornecedorDTO fornecedorDTO = fornecedorMapper.toFornecedoresDTO(fornecedor);
            fornecedorDTOList.add(fornecedorDTO);
        }

        return fornecedorDTOList;
    }

    public FornecedorDTO listarPorId(Long id) throws Exception {
        Fornecedor fornecedorEncontrado = buscarFornecedorPorId(id);
        FornecedorDTO fornecedorDTO = fornecedorMapper.toFornecedoresDTO(fornecedorEncontrado);

        return fornecedorDTO;
    }

    public FornecedorDTO criar(FornecedorDTO fornecedorDTO){
        Fornecedor fornecedor = fornecedorMapper.toFornecedores(fornecedorDTO);
        validarEmailUnicoFornecedor(fornecedor);
        fornecedorRepository.save(fornecedor);
        return fornecedorDTO;
    }

    public FornecedorDTO atualizar(Long id, FornecedorDTO fornecedorDTO) throws Exception {

        Fornecedor fornecedorEncontrado = buscarFornecedorPorId(id);
        FornecedorDTO fornecedorDTORetorno = fornecedorMapper.toFornecedoresDTO(fornecedorEncontrado);

        if (fornecedorDTO.getNome() != null){
            fornecedorDTORetorno.setNome(fornecedorDTO.getNome());
        }
        if (fornecedorDTO.getEmail() != null){
            fornecedorDTORetorno.setEmail(fornecedorDTO.getEmail());
        }

        return fornecedorDTORetorno;
    }

    public void deletar(Long id) throws Exception {
        Fornecedor fornecedorEncontrado = buscarFornecedorPorId(id);
        fornecedorRepository.delete(fornecedorEncontrado);
    }

    private Fornecedor buscarFornecedorPorId(Long id) throws Exception {
        Optional<Fornecedor> fornecedoresOptional = fornecedorRepository.findById(id);
        if (fornecedoresOptional.isEmpty()) {
            throw new Exception("Fornecedor não encontrado");
        }
        return fornecedoresOptional.get();
    }

    public void validarEmailUnicoFornecedor(Fornecedor fornecedor)  {
        Optional<Fornecedor> fornecedorExistente = fornecedorRepository.findByEmail(fornecedor.getEmail());
        if (fornecedorExistente.isPresent()) {
            throw new EmailDuplicadoException("Fornecedor já existente");
        }
    }

}
