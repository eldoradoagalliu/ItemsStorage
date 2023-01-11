import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection connection = DatabaseConnection.initializeDatabase();
        boolean exit = false;
        Scanner input = new Scanner(System.in);

        while(!exit) {
            menu();
            String option = input.next();
            switch (option){
                case "1":
                    checkStorage();
                    break;
                case "2":
                    addItem(connection);
                    break;
                case "3":
                    checkStorage();
                    System.out.print("Insert the name of the item you want to change the price: ");
                    String itemName = input.next();
                    changeItemPrice(itemName, connection);
                    break;
                case "4":
                    checkStorage();
                    System.out.print("Insert the name of the item you want to delete: ");
                    String itemToBeDeleted = input.next();
                    deleteItem(itemToBeDeleted, connection);
                    break;
                case "5":
                    exit = true;
                    connection.close();
                    input.close();
                    System.out.print("\nYou are exiting from the Items Storage!\n");
                    break;
                default:
                    System.out.println("\nWrong option number!\n");
            }
        }
    }

    public static void menu() {
        System.out.println(
                "Welcome in the Items Storage"
                        + "\n1. Check storage"
                        + "\n2. Add a new item in the storage"
                        + "\n3. Change the price of an item inside the storage"
                        + "\n4. Delete an item from the storage"
                        + "\n5. Exit\n"
        );
        System.out.print("Input the number of your chosen option: ");
    }

    private static void checkStorage() throws SQLException {
        ArrayList<Item> items = getItemsDataFromStorage(DatabaseConnection.initializeDatabase());
        int index = 1;
        System.out.println("\n####################################################################");
        System.out.println("Item    Name    Type    Price   Quantity    Date Created");
        for(Item item : items) {
            System.out.println(
                    index++ + ".      " + item.getName() + "   " + item.getType() + "   " + item.getPrice()
                            + "     " + item.getQuantity() + "      " +  item.getFormattedDate()
            );
        }
        System.out.println("####################################################################\n");
    }

    private static void addItem(Connection connection) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.println("\nAdding a new item in the storage!\n");

        System.out.print("Insert the name of the item: " );
        String itemName = input.next();

        System.out.print("Insert the type of the item: " );
        String itemType = input.next();

        System.out.print("Insert the price of the item: " );
        String itemPrice = input.next();

        System.out.print("Insert the quantity of the item: " );
        String quantity = input.next();

        LocalDateTime date = LocalDateTime.now();

        String sql = "INSERT INTO `items` (`name`, `type`, `price`, `quantity`, `date_created`) " +
                "VALUES (\"" + itemName + "\", \"" + itemType + "\", \"" + itemPrice + "\","
                + "\"" + quantity + "\", \"" + date + "\");";
        connection.createStatement().executeUpdate(sql);
        System.out.println("\nItem added successfully!\n");
    }

    private static void changeItemPrice(String itemName, Connection connection) throws SQLException {
        Scanner input = new Scanner(System.in);
        System.out.print("Insert the new item price: ");
        float newItemPrice = input.nextFloat();
        String sql = "UPDATE `items` SET `price` = \"" + newItemPrice + "\" WHERE (`name` = \"" + itemName + "\");";
        connection.createStatement().executeUpdate(sql);
        System.out.println("\n" + itemName + "'s price changed successfully!\n");
    }

    private static void deleteItem(String itemName, Connection connection) throws SQLException {
        String sql = "DELETE FROM `items` WHERE (`name` = \"" + itemName + "\");";
        connection.createStatement().executeUpdate(sql);
        System.out.println("\n" + itemName + " deleted successfully!\n");
    }

    //This method retrieves all the stored data in the DB and stores them in an ArrayList
    private static ArrayList<Item> getItemsDataFromStorage(Connection connection) throws SQLException {
        ArrayList<Item> items = new ArrayList<Item>();
        ResultSet resultSet = connection.createStatement().executeQuery(("SELECT * FROM `items`"));

        while (resultSet.next()) {
            Item item = new Item(
                    Long.parseLong(resultSet.getString(1)),
                    resultSet.getString(2), resultSet.getString(3),
                    Float.parseFloat(resultSet.getString(4)),
                    Integer.parseInt(resultSet.getString(5)),
                    LocalDateTime.parse(resultSet.getString(6), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            );
            items.add(item);
        }

        return items;
    }
}
