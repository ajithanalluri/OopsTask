package org.example.models;


import java.util.List;

public class Food {
    int id;
    String name;
    int calories;
    String description;
    int price;
    Category category;

    List<Food> foodsList;

    public Food() {
    }

    public List<Food> getFoodsList() {
        return List.of(
            new Food(1, "Chicken rice", 558, "Chicken rice", 20, Category.MEAL),
            new Food(2, "Pizza", 260, "Veg loaded", 15, Category.MEAL),
            new Food(3, "Burger", 558, "Burger", 15, Category.MEAL),
            new Food(4, "Chocolate Shake", 50, "Chocolate Shake", 10, Category.DRINK),
            new Food(5, "Coffee", 50, "Coffee", 10, Category.DRINK),
            new Food(6, "Donut", 200, "Donut", 5, Category.DESERT),
            new Food(7, "Kebab", 400, "Kebab", 15, Category.DESERT)
        );
    }

    public Food(int id, String name, int calories, String description, int price, Category category) {
        this.id = id;
        this.name = name;
        this.calories = calories;
        this.description = description;
        this.price = price;
        this.category = category;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
