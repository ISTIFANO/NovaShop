package com.example.novashop22.infrastructure.database.service;

import com.example.novashop22.domain.model.CategorieDto;
import com.example.novashop22.domain.service.ICategorieService;
import com.example.novashop22.infrastructure.database.entities.Category;
import com.example.novashop22.infrastructure.database.repositories.CategoryRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@AllArgsConstructor
public class categoryServiceImp implements ICategorieService {

    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;

    @Override
    public CategorieDto getCategoryById(Long id){

      Category category =categoryRepo.findbyCategoryId(id);

      if(category==null){
          return null;
      }
        CategorieDto categorieDto = modelMapper.map(category,CategorieDto.class);
        return categorieDto;
    }

    @Override
    public CategorieDto getCategorieByName( String name){

        Category category =categoryRepo.findByCategoryName(name);
        if(category==null){
            return null;
        }
        CategorieDto categorieDto = modelMapper.map(category,CategorieDto.class);
        return categorieDto;
    }

    @Override
    public CategorieDto createCategorie(Category categorie){

        Category category =categoryRepo.save(categorie);

        return  modelMapper.map(category,CategorieDto.class);
    }

    @Override
    public CategorieDto updateCategorie(Long id, Category categorie){
        Category category =categoryRepo.findbyCategoryId(id);
        if(category==null){
            return null;
        }
        category.setCategoryName(categorie.getCategoryName());
        category.setCategoryId(categorie.getCategoryId());
        categoryRepo.save(category);
        log.info("categorie updated ");
       return modelMapper.map(category,CategorieDto.class);
    }
    @Override
    public  String deleteCategorie(Long id){
        Category category =categoryRepo.findbyCategoryId(id);
        if(category==null){
            return null;
        }
        categoryRepo.delete(category);
        log.info("categorie deleted");
        return category.getCategoryName();
    }
}
