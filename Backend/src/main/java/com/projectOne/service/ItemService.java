package com.projectOne.service;

import java.util.List;

import com.projectOne.entity.Item;
import com.projectOne.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.projectOne.ExceptionHandling.NotFoundException;
import com.projectOne.Logger.ApplicationLogger;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ApplicationLogger loggerService;

    public void addItemsInDB(Item item) {

        loggerService.info("Adding item in DB: " + item);
        itemRepository.save(item);

    }

    public List<Item> getAllItemsFromDB() {

        loggerService.info("Fetching items... ");
        List<Item> items = itemRepository.findAll();

        if (items.isEmpty()) {
            throw new NotFoundException("Not Items Found");
        }

        return items;
    }

    public void updateItemInDB(Item item) {
        int resId = item.getItemId();
        Item res = itemRepository.findById(resId).orElseThrow(() -> new NotFoundException("Requested Item not found"));

        res.setItemName(item.getItemName());
        res.setIsAvailable(item.getIsAvailable());
        res.setItemPrice(item.getItemPrice());

        itemRepository.save(res);
        loggerService.info("Item details updated in DB: " + item);
    }

    public void delete(int id) {
        itemRepository.deleteById(id);
        loggerService.info("Item deleted in DB...");
    }
}
