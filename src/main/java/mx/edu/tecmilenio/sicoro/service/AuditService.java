package mx.edu.tecmilenio.sicoro.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;

public final class AuditService {
    private final Path logFile;

    public AuditService(Path logFile) {
        this.logFile = logFile;
    }

    public void log(String user, String action) {
        try {
            Files.createDirectories(logFile.getParent());
            String line = LocalDateTime.now() + " | " + sanitize(user) + " | " + sanitize(action)
                    + System.lineSeparator();
            Files.writeString(logFile, line, StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ignored) {
            System.err.println("Advertencia: no fue posible escribir la bitácora de auditoría.");
        }
    }

    private String sanitize(String value) {
        return value == null ? "" : value.replace("\r", " ").replace("\n", " ");
    }
}
