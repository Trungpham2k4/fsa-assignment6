package fa.training.common;

import java.time.format.DateTimeFormatter;

public class Constants {
    public static final String DELIMITER = "==========================================";
    public static final DateTimeFormatter DATE_FORMATTER = java.time.format.DateTimeFormatter
            .ofPattern("uuuu-MM-dd")
            .withResolverStyle(java.time.format.ResolverStyle.STRICT);
}
