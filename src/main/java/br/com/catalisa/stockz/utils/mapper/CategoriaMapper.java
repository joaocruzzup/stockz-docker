package br.com.catalisa.stockz.utils.mapper;

import br.com.catalisa.stockz.model.Categoria;
import br.com.catalisa.stockz.model.dto.CategoriaDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CategoriaMapper {
    public CategoriaDTO toCategoriasDto(Categoria categoria){
        CategoriaDTO categoriaDTO = new CategoriaDTO();
        BeanUtils.copyProperties(categoria, categoriaDTO);
        return categoriaDTO;
    }
    public Categoria toCategorias(CategoriaDTO categoriaDTO){
        Categoria categoria = new Categoria();
        BeanUtils.copyProperties(categoriaDTO, categoria);
        return categoria;
    }
}
