package connection;

import util.ConfigFileReader;
import util.SqlFileReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static util.SqlFileReader.PATH;

public class DatabaseConnection {
    public static Connection initializeDatabase() throws SQLException {
        ConfigFileReader configFileReader = new ConfigFileReader();

        //Database properties
        String url = configFileReader.getUrl();
        String username = configFileReader.getUsername();
        String password = configFileReader.getPassword();
        Connection connection = DriverManager.getConnection(url, username, password);

        //The table with be created in the Database if it doesn't exist
        connection.createStatement().executeUpdate(SqlFileReader.readSQLStatement(PATH + "create-table.sql"));

        return connection;
    }
}
