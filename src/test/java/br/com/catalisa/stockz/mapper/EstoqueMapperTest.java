package br.com.catalisa.stockz.mapper;

import br.com.catalisa.stockz.model.Estoque;
import br.com.catalisa.stockz.model.dto.EstoqueDTO;
import br.com.catalisa.stockz.utils.mapper.EstoqueMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)

public class EstoqueMapperTest {

    @InjectMocks
    private EstoqueMapper estoqueMapper;

    @Test
    public void toEstoqueDTO() {
        Estoque estoque = new Estoque();
        estoque.setId(1L);
        estoque.setQuantidade(10);

        EstoqueDTO estoqueDTO = estoqueMapper.toEstoqueDTO(estoque);

        assertEquals(estoque.getQuantidade(), estoqueDTO.getQuantidade());
    }

    @Test
    public void toEstoque() {
        EstoqueDTO estoqueDTO = new EstoqueDTO();
        estoqueDTO.setQuantidade(10);

        Estoque estoque = estoqueMapper.toEstoque(estoqueDTO);

        assertEquals(estoqueDTO.getQuantidade(), estoque.getQuantidade());
    }
}
