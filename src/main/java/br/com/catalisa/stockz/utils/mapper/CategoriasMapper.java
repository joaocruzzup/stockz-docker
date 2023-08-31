package br.com.catalisa.stockz.utils.mapper;

import br.com.catalisa.stockz.model.Categoria;
import br.com.catalisa.stockz.model.dto.CategoriasDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CategoriasMapper {
    public CategoriasDTO toCategoriasDto(Categoria categoria){
        CategoriasDTO categoriasDTO = new CategoriasDTO();
        BeanUtils.copyProperties(categoria, categoriasDTO);
        return categoriasDTO;
    }
    public Categoria toCategorias(CategoriasDTO categoriasDTO){
        Categoria categoria = new Categoria();
        BeanUtils.copyProperties(categoriasDTO, categoria);
        return categoria;
    }
}
