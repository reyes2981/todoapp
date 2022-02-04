package com.demo.demotodo.service;

import com.demo.demotodo.exception.InformationNotFoundException;
import com.demo.demotodo.model.Category;
import com.demo.demotodo.model.Item;
import com.demo.demotodo.repository.CategoryRepository;
import com.demo.demotodo.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class ItemService {
    private CategoryRepository categoryRepository;
    private ItemRepository itemRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Autowired
    public void setItemRepository(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    //ITEM SECTION
    public List<Item> getAllItems(Long categoryId) {
        System.out.println("calling getAllItems");
        Optional<Category> category = categoryRepository.findById(categoryId);
//        Category category = categoryRepository.findById(categoryId).orElse(null);
//        if (category != null) {
//            return category.getItemList();
//        }
        if (category.isPresent()) {
            return category.get().getItemList();
        } else {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        }
    }
    public Item createItem(Long categoryId, Item itemObject){
        System.out.println("calling createItem");
        try {
            Optional<Category> category = categoryRepository.findById(categoryId);
            itemObject.setCategory(category.get());
            return itemRepository.save(itemObject);
        }catch (NoSuchElementException e){
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        }
    }
    public Item getItemId(Long categoryId, Long itemId) {
        System.out.println("getting item with the Id of " + itemId);
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()) {
            for (Item item : category.get().getItemList()) {
                if (Objects.equals(item.getId(), itemId)) {
                    return item;
                }
            }
            throw new InformationNotFoundException("item with id " + itemId + " not found");
        }else {
            throw new InformationNotFoundException("Category with the Id " + categoryId + " not found");
        }
    }
    public Item updateItem(Long categoryId, Long itemId, Item itemObject) {
        System.out.println("updating the item with the Id of " + itemId + itemObject);
        Optional<Category> category = categoryRepository.findById(categoryId);
        if (category.isPresent()) {
            for (Item item : category.get().getItemList()) {
                if (Objects.equals(item.getId(), itemId)) {
                    Item updateItem = itemRepository.findById(itemId).get();
                    updateItem.setName(itemObject.getName());
                    updateItem.setDescription(itemObject.getDescription());
                    updateItem.setDueDate(itemObject.getDueDate());
                    return itemRepository.save(updateItem);
                }
            }
            throw new InformationNotFoundException("item with id " + itemId + " not found");
        } else {
            throw new InformationNotFoundException("Category with the Id " + categoryId + " not found");
        }
    }
    public Item deleteItem(Long categoryId, Long itemId) {
        Optional<Category> category = categoryRepository.findById(categoryId);
        if(category.isPresent()) {
            for (Item item : category.get().getItemList()) {
                if (Objects.equals(item.getId(), itemId)) {
                    itemRepository.deleteById(itemId);
                    return item;
                }
            } throw new InformationNotFoundException("recipe with id " + itemId + " not found");
        } else {
            throw new InformationNotFoundException("category with id " + categoryId + " not found");
        }
    }
}
