package util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigFileReader {

    private final Properties properties;

    public ConfigFileReader() {
        this.properties = new Properties();

        try (InputStream fileInput = new FileInputStream("src/resources/config.properties")) {
            properties.load(fileInput);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getUrl() {
        String url = properties.getProperty("DATASOURCE_URL");
        if (url != null) {
            return url;
        } else throw new RuntimeException("Database URL is not specified in the configs file");
    }

    public String getUsername() {
        String url = properties.getProperty("DATASOURCE_USERNAME");
        if (url != null) {
            return url;
        } else throw new RuntimeException("Database username is not specified in the configs file");
    }

    public String getPassword() {
        String url = properties.getProperty("DATASOURCE_PASSWORD");
        if (url != null) {
            return url;
        } else throw new RuntimeException("Database password is not specified in the configs file");
    }
}
