package fa.training.common;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {
    private static final Properties properties = new Properties();

    static {
        try(InputStream input = PropertyManager
                .class
                .getResourceAsStream("/database.properties")) {
            properties.load(input);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
