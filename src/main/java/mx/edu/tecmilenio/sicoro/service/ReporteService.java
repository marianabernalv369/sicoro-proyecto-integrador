package mx.edu.tecmilenio.sicoro.service;

import mx.edu.tecmilenio.sicoro.model.Registro;
import mx.edu.tecmilenio.sicoro.util.CsvUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ReporteService {
    private final Path reportDir;

    public ReporteService(Path reportDir) {
        this.reportDir = reportDir;
    }

    public Path generateSummary(List<Registro> records) throws IOException {
        Files.createDirectories(reportDir);
        Map<String, int[]> byArea = new LinkedHashMap<>();
        for (Registro r : records) {
            int[] counters = byArea.computeIfAbsent(r.getArea(), key -> new int[4]);
            counters[0]++;
            switch (r.getEstatus().toUpperCase()) {
                case "ABIERTO": counters[1]++; break;
                case "EN_PROCESO": counters[2]++; break;
                case "CERRADO": counters[3]++; break;
                default: break;
            }
        }
        List<String> lines = new ArrayList<>();
        lines.add("area,total,abiertos,en_proceso,cerrados,porcentaje_cierre");
        for (Map.Entry<String, int[]> entry : byArea.entrySet()) {
            int[] c = entry.getValue();
            double closedPct = c[0] == 0 ? 0 : (c[3] * 100.0 / c[0]);
            lines.add(String.join(",",
                    CsvUtils.escape(entry.getKey()),
                    Integer.toString(c[0]), Integer.toString(c[1]),
                    Integer.toString(c[2]), Integer.toString(c[3]),
                    String.format(java.util.Locale.US, "%.2f", closedPct)));
        }
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        Path output = reportDir.resolve("reporte_resumen_" + timestamp + ".csv");
        Files.write(output, lines, StandardCharsets.UTF_8);
        return output;
    }
}
