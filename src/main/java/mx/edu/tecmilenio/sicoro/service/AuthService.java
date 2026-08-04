package mx.edu.tecmilenio.sicoro.service;

import mx.edu.tecmilenio.sicoro.util.CsvUtils;
import mx.edu.tecmilenio.sicoro.util.PasswordUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

public final class AuthService {
    public static final class Session {
        private final String username;
        private final String role;

        public Session(String username, String role) {
            this.username = username;
            this.role = role;
        }

        public String getUsername() { return username; }
        public String getRole() { return role; }
        public boolean isAdmin() { return "ADMIN".equalsIgnoreCase(role); }
    }

    private final Path usersFile;

    public AuthService(Path usersFile) {
        this.usersFile = usersFile;
    }

    public Optional<Session> authenticate(String username, String password) throws IOException {
        List<String> lines = Files.readAllLines(usersFile, StandardCharsets.UTF_8);
        for (int i = 1; i < lines.size(); i++) {
            List<String> fields = CsvUtils.parseLine(lines.get(i));
            if (fields.size() < 4) continue;
            if (fields.get(0).equalsIgnoreCase(username)
                    && PasswordUtil.verify(password, fields.get(1), fields.get(2))) {
                return Optional.of(new Session(fields.get(0), fields.get(3)));
            }
        }
        return Optional.empty();
    }
}
