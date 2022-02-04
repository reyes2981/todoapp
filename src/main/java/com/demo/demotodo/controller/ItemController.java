package com.demo.demotodo.controller;

import com.demo.demotodo.model.Item;
import com.demo.demotodo.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/api/categories/")
public class ItemController {

    private ItemService itemService;

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    //    private ItemRepository itemRepository;
//    private CategoryRepository categoryRepository;
//
//    @Autowired
//    public void setItemRepository(ItemRepository itemRepository, CategoryRepository categoryRepository) {
//        this.itemRepository = itemRepository;
//        this.categoryRepository = categoryRepository;
//    }
    @GetMapping("{categoryId}/items/")
    public List<Item> getAllItems(@PathVariable(value = "categoryId") Long categoryId){
        System.out.println("calling getAllItems");
        return itemService.getAllItems(categoryId);
    }
    @PostMapping("{categoryId}/items/")
    public Item createItem(@PathVariable (value = "categoryId") Long categoryId, @RequestBody Item itemObject){
        System.out.println("calling createItem");
        return itemService.createItem(categoryId, itemObject);
    }
    @GetMapping("{categoryId}/items/{itemId}/")
    public Item getItemId(@PathVariable (value = "categoryId") Long categoryId, @PathVariable(value = "itemId") Long itemId) {
        System.out.println("getting item with the Id of " + itemId);
        return itemService.getItemId(categoryId, itemId);
    }
    @PutMapping("{categoryId}/items/{itemId}/")
    public Item updateItem(@PathVariable (value = "categoryId") Long categoryId, @PathVariable(value = "itemId") Long itemId, @RequestBody Item itemObject) {
        System.out.println("updating the item with the Id of " + itemId + itemObject);
        return itemService.updateItem(categoryId, itemId, itemObject);
    }
    @DeleteMapping("{categoryId}/items/{itemId}/")
    public Item deleteItem(@PathVariable (value = "categoryId") Long categoryId, @PathVariable(value = "itemId") Long itemId) {
        System.out.println("deleting the item with the Id of " +itemId);
        return itemService.deleteItem(categoryId, itemId);
    }
}
