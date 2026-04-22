package com.restaurant.common;

public class Dish {
    private int id;
    private String name;
    private double price;
    private boolean isAvailable;

    public Dish(int id, String name, double price, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public boolean isAvailable() { return isAvailable; }
}