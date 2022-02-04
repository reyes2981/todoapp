package com.demo.demotodo.service;

import com.demo.demotodo.exception.InformationExistException;
import com.demo.demotodo.exception.InformationNotFoundException;
import com.demo.demotodo.model.Category;
import com.demo.demotodo.model.Item;
import com.demo.demotodo.repository.CategoryRepository;
import com.demo.demotodo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    private ItemRepository itemRepository;

    @Autowired
    public void setItemRepository(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    public Category createCategory(Category categoryObject){

        Category category = categoryRepository.findByName(categoryObject.getName());
        if(category != null){
            throw new InformationExistException("category with name " + category.getName() + " already exists");
        } else{
            return categoryRepository.save(categoryObject);
        }
    }

    public Optional<Category> getCategory(Long categoryId){

        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()){
            return category;
        } else{
            throw new InformationNotFoundException("category with Id " + categoryId + " not found");
        }

    }
    public Category updateCategory(Long categoryId, Category categoryObject){

        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()){
            //if(categoryObject.getName().equals(c))
            Category updateCategory = categoryRepository.findById(categoryId).get();
            updateCategory.setName(categoryObject.getName());
            updateCategory.setDescription(categoryObject.getDescription());
            return categoryRepository.save(updateCategory);
        } else{
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        }

    }
    public Optional<Category> deleteCategory(Long categoryId){

        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()){
            categoryRepository.deleteById(categoryId);
            return category;
        } else{
            throw new InformationNotFoundException("category with id " + categoryId +" not found");
        }
    }

    public Item createCategoryItem(Long categoryId, Item itemObject){
        Optional<Category> category = categoryRepository.findById(categoryId);
        itemObject.setCategory(category.get());
        return itemRepository.save(itemObject);
    }

    public List<Item> getCategoryItems(Long categoryId){

        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()){
            return category.get().getItemsList();
        } else{
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        }

    }
    public Item getCategoryItem(Long categoryId, Long itemId){

        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()){
            for(Item item : category.get().getItemsList()){
                if(item.getId() == itemId) {
                    return item;
                }
            }
            throw new InformationNotFoundException("item with id " + itemId + " not found");
        } else{
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        }
    }
    public Item updateCategoryItem(Long categoryId, Long itemId, Item itemObject) {
        System.out.println("service calling updateCategoryItem ==>");
        try {
            Item item = (itemRepository.findByCategoryId(
                    categoryId).stream().filter(p -> p.getId().equals(itemId)).findFirst()).get();
            item.setName(itemObject.getName());
            item.setDescription(itemObject.getDescription());
            item.setDueDate(itemObject.getDueDate());
            return itemRepository.save(item);
        } catch (NoSuchElementException e) {
            throw new InformationNotFoundException("item or category not found");
        }
    }
    public void deleteCategoryItem(Long categoryId, Long itemId) {
        try {
            Item item = (itemRepository.findByCategoryId(
                    categoryId).stream().filter(p -> p.getId().equals(itemId)).findFirst()).get();
            itemRepository.deleteById(item.getId());
        } catch (NoSuchElementException e) {
            throw new InformationNotFoundException("item or category not found");
        }
    }

}
