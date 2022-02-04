package com.demo.demotodo.controller;

import com.demo.demotodo.model.Category;
import com.demo.demotodo.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api")
public class CategoryController {

//    private CategoryRepository categoryRepository;
//
//    @Autowired
//    public void setCategoryRepository(CategoryRepository categoryRepository) {
//        this.categoryRepository = categoryRepository;
//    }

    private CategoryService categoryService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping(path = "/categories/")
    public List<Category> getAllCategories() {
        System.out.println("calling getAllCategories");
        return categoryService.getAllCategories();
    }
    @PostMapping("/categories/")
    public Category createCategory(@RequestBody Category categoryObject){
        System.out.println("calling createCategory");
        return categoryService.createCategory(categoryObject);
    }
    @GetMapping("/categories/{categoryId}/")
    public Optional<Category> getCategory(@PathVariable(value = "categoryId") Long categoryId) {
        System.out.println("getting the category with Id of " + categoryId);
        return categoryService.getCategory(categoryId);
    }
    @PutMapping("/categories/{categoryId}/")
    public Category updateCategory(@PathVariable(value = "categoryId") Long categoryId, @RequestBody Category categoryObject)  {
        System.out.println("calling createCategory ==>");
        return categoryService.updateCategory(categoryId, categoryObject);
    }
    @DeleteMapping("/categories/{categoryId}/")
    public Optional<Category> deleteCategory(@PathVariable(value = "categoryId") Long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }
}
