import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Item {
    private static Long id;
    private String name;
    private String type;
    private float price;
    private int quantity;
    private LocalDateTime dateCreated;

    public Item(Long id, String name, String type, float price, int quantity, LocalDateTime dateCreated) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.dateCreated = dateCreated;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(LocalDateTime dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss d MMMM yyyy");
        String formattedDate = formatter.format(dateCreated);

        return formattedDate;
    }
}
