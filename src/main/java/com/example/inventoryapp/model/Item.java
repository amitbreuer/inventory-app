package com.example.inventoryapp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Item {

    @Id @GeneratedValue
    private Integer itemNo;
    @NotNull
    private String name;
    @NotNull
    private Integer amount;
    @NotNull
    private String inventoryCode;

    public Integer getItemNo() {
        return itemNo;
    }

    public void setItemNo(Integer itemNo) {
        this.itemNo = itemNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getInventoryCode() {
        return inventoryCode;
    }

    public void setInventoryCode(String inventoryCode) {
        this.inventoryCode = inventoryCode;
    }

    public void withdrawal(int amount) {
        this.amount -= amount;
    }

    public void deposit(int amount) {
        this.amount += amount;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemNo=" + itemNo +
                ", name=" + name +
                ", amount=" + amount +
                ", inventoryCode=" + inventoryCode +
                '}';
    }
}
