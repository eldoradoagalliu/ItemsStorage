import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection initializeDatabase() throws SQLException {
        //Add the dependencies of Java DB connector on the project structure!!!

        //Run the java application once with the below code
        //DriverManager.registerDriver(new com.mysql.jdbc.Driver());

        //DB properties
        String url = "jdbc:mysql://localhost:3306/items_storage_schema";
        String username = "root";
        String password = "root";
        Connection connection = DriverManager.getConnection(url, username, password);

        //The table with be created in the DB if it doesn't exist with the below sql code
        connection.createStatement().executeUpdate(
                "CREATE TABLE IF NOT EXISTS `items_storage_schema`.`items` ( "
                + "`id` INT NOT NULL AUTO_INCREMENT,"
                + "`name` VARCHAR(45) NOT NULL,"
                + "`type` VARCHAR(45) NOT NULL,"
                + "`price` FLOAT NOT NULL,"
                + "`quantity` INT NOT NULL,"
                + "`date_created` DATETIME NOT NULL,"
                + " PRIMARY KEY (`id`),"
                + " UNIQUE INDEX `name_UNIQUE` (`name` ASC) VISIBLE)"
        );

        return connection;
    }
}
