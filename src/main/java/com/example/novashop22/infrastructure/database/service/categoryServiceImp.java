package com.example.novashop22.infrastructure.database.service;

import com.example.novashop22.commun.exception.APIException;
import com.example.novashop22.domain.model.CategorieDto;
import com.example.novashop22.domain.model.CategoryResponse;
import com.example.novashop22.domain.service.ICategorieService;
import com.example.novashop22.infrastructure.database.entities.Category;
import com.example.novashop22.infrastructure.database.repositories.CategoryRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class categoryServiceImp implements ICategorieService {

    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;

    @Override
    public CategoryResponse getCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Sort sortByAndOrder = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageDetails = PageRequest.of(pageNumber, pageSize, sortByAndOrder);

        Page<Category> pageCategories = categoryRepo.findAll(pageDetails);

        List<Category> categories = pageCategories.getContent();

        if (categories.size() == 0) {
            throw new APIException("No category is created till now");
        }

        List<CategorieDto> categoryDTOs = categories.stream()
                .map(category -> modelMapper.map(category, CategorieDto.class)).collect(Collectors.toList());

        CategoryResponse categoryResponse = new CategoryResponse();

        categoryResponse.setContent(categoryDTOs);
        categoryResponse.setPageNumber(pageCategories.getNumber());
        categoryResponse.setPageSize(pageCategories.getSize());
        categoryResponse.setTotalElements(pageCategories.getTotalElements());
        categoryResponse.setTotalPages(pageCategories.getTotalPages());
        categoryResponse.setLastPage(pageCategories.isLast());

        return categoryResponse;
    }
    @Override
    public CategorieDto getCategoryById(Long id){

      Category category =categoryRepo.findByCategoryId(id);

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
        Category category =categoryRepo.findByCategoryId(id);
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
        Category category =categoryRepo.findByCategoryId(id);
        if(category==null){
            return null;
        }
        categoryRepo.delete(category);
        log.info("categorie deleted");
        return category.getCategoryName();
    }
}
