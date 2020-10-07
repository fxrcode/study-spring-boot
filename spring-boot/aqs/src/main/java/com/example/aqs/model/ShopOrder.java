package com.example.aqs.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "shop_order")
public class ShopOrder {
    @Id
    @GeneratedValue
    private Long id;

    private int stock;

    public ShopOrder() {
        super();
    }

    public ShopOrder(Long id, int stock) {
        this.id = id;
        this.stock = stock;
    }
}
