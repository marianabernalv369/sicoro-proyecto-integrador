package mx.edu.tecmilenio.sicoro.util;

import java.util.ArrayList;
import java.util.List;

public final class CsvUtils {
    private CsvUtils() { }

    public static String escape(String value) {
        if (value == null) return "";
        String normalized = value.replace("\r", " ").replace("\n", " ");
        if (normalized.contains(",") || normalized.contains("\"") ) {
            return "\"" + normalized.replace("\"", "\"\"") + "\"";
        }
        return normalized;
    }

    public static List<String> parseLine(String line) {
        List<String> values = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean quoted = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (quoted && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    quoted = !quoted;
                }
            } else if (c == ',' && !quoted) {
                values.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        values.add(current.toString());
        return values;
    }
}
