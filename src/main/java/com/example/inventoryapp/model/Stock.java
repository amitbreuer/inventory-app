package com.example.inventoryapp.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Stock extends JpaRepository<Item,Integer> {

    Item findByName(String name);

}
