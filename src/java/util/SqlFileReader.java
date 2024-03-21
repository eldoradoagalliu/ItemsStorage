package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class SqlFileReader {
    public static final String PATH = "src/resources/sql/";

    public static String readSQLStatement(String fileName) {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }
}
