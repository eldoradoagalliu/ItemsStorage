package connection;

import util.SqlFileReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static util.SqlFileReader.PATH;

public class DatabaseConnection {
    public static Connection initializeDatabase() throws SQLException {
        //Add the dependencies of Java DB connector on the project structure!!!

        //Run the java application once with the below code
        //DriverManager.registerDriver(new com.mysql.jdbc.Driver());

        //Database properties
        String url = "jdbc:mysql://localhost:3306/items_storage_schema";
        String username = "root";
        String password = "root";
        Connection connection = DriverManager.getConnection(url, username, password);

        //The table with be created in the Database if it doesn't exist
        connection.createStatement().executeUpdate(SqlFileReader.readSQLStatement(PATH + "create-table.sql"));

        return connection;
    }
}
