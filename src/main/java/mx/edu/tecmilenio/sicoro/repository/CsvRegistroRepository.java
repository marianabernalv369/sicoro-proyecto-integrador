package mx.edu.tecmilenio.sicoro.repository;

import mx.edu.tecmilenio.sicoro.model.Registro;
import mx.edu.tecmilenio.sicoro.util.CsvUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public final class CsvRegistroRepository {
    private static final String HEADER = "id,fecha,area,responsable,tipo,descripcion,estatus,fecha_actualizacion";
    private final Path file;

    public CsvRegistroRepository(Path file) {
        this.file = file;
    }

    public List<Registro> findAll() throws IOException {
        ensureFile();
        List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        List<Registro> records = new ArrayList<>();
        for (int i = 1; i < lines.size(); i++) {
            if (lines.get(i).isBlank()) continue;
            List<String> f = CsvUtils.parseLine(lines.get(i));
            if (f.size() < 8) continue;
            records.add(new Registro(
                    f.get(0), LocalDate.parse(f.get(1)), f.get(2), f.get(3),
                    f.get(4), f.get(5), f.get(6), LocalDateTime.parse(f.get(7))));
        }
        return records;
    }

    public void saveAll(List<Registro> records) throws IOException {
        ensureFile();
        List<String> lines = new ArrayList<>();
        lines.add(HEADER);
        for (Registro r : records) lines.add(toCsv(r));
        Files.write(file, lines, StandardCharsets.UTF_8,
                StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
    }

    private void ensureFile() throws IOException {
        Path parent = file.getParent();
        if (parent != null) Files.createDirectories(parent);
        if (!Files.exists(file)) Files.writeString(file, HEADER + System.lineSeparator(), StandardCharsets.UTF_8);
    }

    private String toCsv(Registro r) {
        return String.join(",",
                CsvUtils.escape(r.getId()),
                r.getFecha().toString(),
                CsvUtils.escape(r.getArea()),
                CsvUtils.escape(r.getResponsable()),
                CsvUtils.escape(r.getTipo()),
                CsvUtils.escape(r.getDescripcion()),
                CsvUtils.escape(r.getEstatus()),
                r.getFechaActualizacion().toString());
    }
}
