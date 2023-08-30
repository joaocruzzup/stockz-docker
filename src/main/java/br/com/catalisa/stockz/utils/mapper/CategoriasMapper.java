package br.com.catalisa.stockz.utils.mapper;

import br.com.catalisa.stockz.model.Categorias;
import br.com.catalisa.stockz.model.dto.CategoriasDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class CategoriasMapper {
    public CategoriasDTO toCategoriasDto(Categorias categorias){
        CategoriasDTO categoriasDTO = new CategoriasDTO();
        BeanUtils.copyProperties(categorias, categoriasDTO);
        return categoriasDTO;
    }
    public Categorias toCategorias(CategoriasDTO categoriasDTO){
        Categorias categorias = new Categorias();
        BeanUtils.copyProperties(categoriasDTO, categorias);
        return categorias;
    }
}
