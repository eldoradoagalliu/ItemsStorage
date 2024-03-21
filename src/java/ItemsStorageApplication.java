import connection.DatabaseConnection;
import model.Item;
import util.SqlFileReader;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import static util.SqlFileReader.PATH;

public class ItemsStorageApplication {

    public static final String INSERT = "Insert the ";
    public static final String ITEM = " of the item: ";

    public static void main(String[] args) throws SQLException {
        Connection connection = DatabaseConnection.initializeDatabase();
        boolean exit = false;
        Scanner input = new Scanner(System.in);

        while (!exit) {
            menu();
            String option = input.nextLine();
            switch (option) {
                case "1":
                    checkStorage(connection);
                    break;
                case "2":
                    addItem(connection);
                    break;
                case "3":
                    checkStorage(connection);
                    System.out.print(INSERT + "name of the item you want to change the price: ");
                    String itemName = input.nextLine();
                    changeItemPrice(itemName, connection);
                    break;
                case "4":
                    checkStorage(connection);
                    System.out.print(INSERT + "name of the item you want to delete: ");
                    String itemToBeDeleted = input.nextLine();
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
        System.out.println("""
                Welcome in the Items Storage
                1. Check storage
                2. Add a new item in the storage
                3. Change the price of an item inside the storage
                4. Delete an item from the storage
                5. Exit
                """
        );
        System.out.print("Input the number of your chosen option: ");
    }

    private static void checkStorage(Connection connection) {
        List<Item> items = getItemsDataFromStorage(connection);
        AtomicInteger index = new AtomicInteger(1);
        System.out.println("\n" + "#".repeat(100));
        System.out.printf("%s %15s %15s %12s %12s %15s", "Item", "Name", "Type", "Price", "Quantity", "Date created");
        items.forEach(i -> System.out.printf("\n%2d %16s %18s %10.2f %8d %28s", index.getAndIncrement(), i.getName(), i.getType(),
                i.getPrice(), i.getQuantity(), i.getFormattedDate()));
        System.out.println("\n" + "#".repeat(100));
    }

    private static void addItem(Connection connection) {
        Scanner input = new Scanner(System.in);
        System.out.println("\nAdding a new item in the storage...\n");

        System.out.print(INSERT + "name" + ITEM);
        String itemName = input.nextLine();

        System.out.print(INSERT + "type" + ITEM);
        String itemType = input.nextLine();

        System.out.print(INSERT + "price" + ITEM);
        String itemPrice = input.next();

        System.out.print(INSERT + "quantity" + ITEM);
        String quantity = input.next();

        LocalDateTime now = LocalDateTime.now();

        try (PreparedStatement ps = connection.prepareStatement(SqlFileReader.readSQLStatement(PATH + "insert-item.sql"))) {
            ps.setString(1, itemName);
            ps.setString(2, itemType);
            ps.setFloat(3, Float.parseFloat(itemPrice));
            ps.setInt(4, Integer.parseInt(quantity));
            ps.setTimestamp(5, Timestamp.valueOf(now));
            ps.executeUpdate();
            System.out.println("\nItem added successfully!\n");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void changeItemPrice(String itemName, Connection connection) {
        Scanner input = new Scanner(System.in);
        System.out.print(INSERT + "new item price: ");
        float newItemPrice = input.nextFloat();

        try (PreparedStatement ps = connection.prepareStatement(SqlFileReader.readSQLStatement(PATH + "update-item-price.sql"))) {
            ps.setFloat(1, newItemPrice);
            ps.setString(2, itemName);
            ps.executeUpdate();
            System.out.println("\n" + itemName + "'s price changed successfully!\n");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void deleteItem(String itemName, Connection connection) {
        try (PreparedStatement ps = connection.prepareStatement(SqlFileReader.readSQLStatement(PATH + "delete-item.sql"))) {
            ps.setString(1, itemName);
            ps.executeUpdate();
            System.out.println("\n" + itemName + " deleted successfully!\n");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Item> getItemsDataFromStorage(Connection connection) {
        List<Item> items = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery(SqlFileReader.readSQLStatement(PATH + "select-all.sql"));
            while (resultSet.next()) {
                Item item = new Item(
                        resultSet.getString(2), resultSet.getString(3),
                        Float.parseFloat(resultSet.getString(4)),
                        Integer.parseInt(resultSet.getString(5)),
                        LocalDateTime.parse(resultSet.getString(6), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                );
                items.add(item);
            }
            return items;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
