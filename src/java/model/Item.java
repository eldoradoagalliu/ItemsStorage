package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Item {
    private String name;
    private String type;
    private float price;
    private int quantity;
    private LocalDateTime dateCreated;

    public Item(String name, String type, float price, int quantity, LocalDateTime dateCreated) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.dateCreated = dateCreated;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss d MMMM yyyy");
        return formatter.format(getDateCreated());
    }
}
