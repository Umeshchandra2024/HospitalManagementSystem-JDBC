package hospital_management_system;

import java.util.Optional;

/**
 * JDBC settings from environment variables (safe for GitHub).
 * Set {@code HOSPITAL_DB_PASSWORD} before running; optional overrides for URL and user.
 */
public final class DatabaseConfig {

    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/hospital";

    private DatabaseConfig() {
    }

    public static String getUrl() {
        return Optional.ofNullable(System.getenv("HOSPITAL_DB_URL")).filter(s -> !s.isBlank()).orElse(DEFAULT_URL);
    }

    public static String getUsername() {
        return Optional.ofNullable(System.getenv("HOSPITAL_DB_USER")).filter(s -> !s.isBlank()).orElse("root");
    }

    public static Optional<String> getPassword() {
        String p = System.getenv("HOSPITAL_DB_PASSWORD");
        if (p == null || p.isBlank()) {
            return Optional.empty();
        }
        return Optional.of(p);
    }
}
