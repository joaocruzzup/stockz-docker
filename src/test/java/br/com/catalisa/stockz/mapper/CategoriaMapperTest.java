package br.com.catalisa.stockz.mapper;

import br.com.catalisa.stockz.model.Categoria;
import br.com.catalisa.stockz.model.dto.CategoriaDTO;
import br.com.catalisa.stockz.utils.mapper.CategoriaMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class CategoriaMapperTest {
    @InjectMocks
    private CategoriaMapper categoriaMapper;

    @Test
    public void ToCategoriasDtoTeste() {
        Categoria categoria = new Categoria();
        categoria.setId(1L);
        categoria.setNome("Categoria Teste");

        CategoriaDTO categoriaDTO = categoriaMapper.toCategoriasDto(categoria);

        assertEquals(categoria.getNome(), categoriaDTO.getNome());
    }

    @Test
    public void ToCategoriasTeste() {
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        categoriaDTO.setNome("Categoria Teste");

        Categoria categoria = categoriaMapper.toCategorias(categoriaDTO);

        assertEquals(categoriaDTO.getNome(), categoria.getNome());
    }
}
