package br.com.catalisa.stockz.mapper;

import br.com.catalisa.stockz.model.Comprador;
import br.com.catalisa.stockz.model.dto.CompradorDTO;
import br.com.catalisa.stockz.utils.mapper.CompradorMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CompradorMapperTest {
    @InjectMocks
    private CompradorMapper compradorMapper;

    @Test
    public void toCompradoresDto() {
        Comprador comprador = new Comprador();
        comprador.setId(1L);
        comprador.setNome("Comprador Teste");

        CompradorDTO compradorDTO = compradorMapper.toCompradoresDto(comprador);

        assertEquals(comprador.getNome(), compradorDTO.getNome());
    }

    @Test
    public void toCompradores() {
        CompradorDTO compradorDTO = new CompradorDTO();
        compradorDTO.setNome("Comprador Teste");

        Comprador comprador = compradorMapper.toCompradores(compradorDTO);

        assertEquals(compradorDTO.getNome(), comprador.getNome());
    }
}
