package com.example.inventoryapp.controller;

import com.example.inventoryapp.model.Item;
import com.example.inventoryapp.model.Stock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/")
public class StockController {

    @Autowired
    Stock stock;

    @PostMapping("item")
    public ResponseEntity<Item> addItem(@RequestBody Item newItem) {
        // check if there is already an item with this name and inventory code in stock
        // if there is, just add newItem's quantity to existing item
        Item checkItem = stock.findByName(newItem.getName());
        if(checkItem != null && checkItem.getInventoryCode().equals(newItem.getInventoryCode())) {
                checkItem.deposit(newItem.getAmount());
                stock.save(checkItem);
                newItem = checkItem;
        } else {
            stock.save(newItem);
        }

        return ResponseEntity.ok(newItem);
    }

    @GetMapping("items")
    public List<Item> getItems() {
        return stock.findAll();
    }

    @GetMapping("item/{itemNo}")
    public ResponseEntity<Item> getItemDetails(@PathVariable int itemNo) {
        Optional<Item> item = stock.findById(itemNo);

        return item.isPresent() ? ResponseEntity.ok(item.get()) : ResponseEntity.notFound().build();
    }

    @PutMapping("item/{itemNo}/withdrawal/{quantity}")
    public ResponseEntity<Item> withdrawal(@PathVariable int itemNo,@PathVariable int quantity) {
        Optional<Item> optionalItem = stock.findById(itemNo);
        if(optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.withdrawal(quantity);
            stock.save(item);
        }

        return optionalItem.isPresent() ? ResponseEntity.ok(optionalItem.get()) : ResponseEntity.notFound().build();
    }

    @PutMapping("item/{itemNo}/deposit/{quantity}")
    public ResponseEntity<Item> deposit(@PathVariable int itemNo,@PathVariable int quantity) {
        Optional<Item> optionalItem = stock.findById(itemNo);
        if(optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.deposit(quantity);
            stock.save(item);
        }

        return optionalItem.isPresent() ? ResponseEntity.ok(optionalItem.get()) : ResponseEntity.notFound().build();
    }

    @PutMapping("item/{itemNo}/update-quantity/{quantity}")
    public ResponseEntity<Item> updateQuantity(@PathVariable int itemNo,@PathVariable int quantity) {
        Optional<Item> optionalItem = stock.findById(itemNo);
        if(optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setAmount(quantity);
            stock.save(item);
        }

        return optionalItem.isPresent() ? ResponseEntity.ok(optionalItem.get()) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("item/{itemNo}")
    public ResponseEntity<Item> deleteItem(@PathVariable int itemNo) {
        ResponseEntity<Item> responseEntity = stock.findById(itemNo).isPresent() ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();

        if(stock.findById(itemNo).isPresent()) {
            stock.deleteById(itemNo);
        }

        return responseEntity;
    }
}

