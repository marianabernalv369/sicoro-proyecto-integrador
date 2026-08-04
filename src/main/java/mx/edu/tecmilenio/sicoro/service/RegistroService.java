package mx.edu.tecmilenio.sicoro.service;

import mx.edu.tecmilenio.sicoro.model.Registro;
import mx.edu.tecmilenio.sicoro.repository.CsvRegistroRepository;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

public final class RegistroService {
    private final CsvRegistroRepository repository;

    public RegistroService(CsvRegistroRepository repository) {
        this.repository = repository;
    }

    public List<Registro> listAll() throws IOException {
        return repository.findAll();
    }

    public Registro add(LocalDate fecha, String area, String responsable,
                        String tipo, String descripcion) throws IOException {
        List<Registro> records = repository.findAll();
        String id = nextId(records);
        Registro registro = new Registro(id, fecha, clean(area), clean(responsable),
                clean(tipo), clean(descripcion), "ABIERTO", LocalDateTime.now());
        records.add(registro);
        repository.saveAll(records);
        return registro;
    }

    public boolean updateStatus(String id, String status) throws IOException {
        String normalized = normalizeStatus(status);
        List<Registro> records = repository.findAll();
        Optional<Registro> match = records.stream()
                .filter(r -> r.getId().equalsIgnoreCase(id)).findFirst();
        if (match.isEmpty()) return false;
        match.get().actualizarEstatus(normalized);
        repository.saveAll(records);
        return true;
    }

    public List<Registro> filter(String area, String status) throws IOException {
        String areaFilter = area == null ? "" : area.trim().toLowerCase(Locale.ROOT);
        String statusFilter = status == null ? "" : status.trim().toUpperCase(Locale.ROOT);
        return repository.findAll().stream()
                .filter(r -> areaFilter.isEmpty() || r.getArea().toLowerCase(Locale.ROOT).contains(areaFilter))
                .filter(r -> statusFilter.isEmpty() || r.getEstatus().equalsIgnoreCase(statusFilter))
                .collect(Collectors.toList());
    }

    private String nextId(List<Registro> records) {
        int max = records.stream().map(Registro::getId)
                .filter(id -> id.matches("REG-\\d{4}"))
                .mapToInt(id -> Integer.parseInt(id.substring(4)))
                .max().orElse(0);
        return String.format("REG-%04d", max + 1);
    }

    private String normalizeStatus(String value) {
        String status = value == null ? "" : value.trim().toUpperCase(Locale.ROOT).replace(' ', '_');
        if (!status.equals("ABIERTO") && !status.equals("EN_PROCESO") && !status.equals("CERRADO")) {
            throw new IllegalArgumentException("Estatus permitido: ABIERTO, EN_PROCESO o CERRADO.");
        }
        return status;
    }

    private String clean(String value) {
        if (value == null || value.trim().isEmpty()) throw new IllegalArgumentException("Los campos son obligatorios.");
        return value.trim();
    }
}
