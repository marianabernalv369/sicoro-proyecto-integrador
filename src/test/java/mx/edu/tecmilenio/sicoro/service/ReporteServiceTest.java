package mx.edu.tecmilenio.sicoro.service;

import mx.edu.tecmilenio.sicoro.model.Registro;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ReporteServiceTest {

    @TempDir
    Path tempDir;

    @Test
    void generaResumenCsvConTotalesYPorcentajeDeCierre() throws Exception {
        List<Registro> registros = List.of(
                registro("REG-0001", "Taller", "ABIERTO"),
                registro("REG-0002", "Taller", "CERRADO")
        );

        Path reporte = new ReporteService(tempDir).generateSummary(registros);
        String contenido = Files.readString(reporte);

        assertTrue(contenido.contains("area,total,abiertos,en_proceso,cerrados,porcentaje_cierre"));
        assertTrue(contenido.contains("Taller,2,1,0,1,50.00"));
    }

    private Registro registro(String id, String area, String estatus) {
        return new Registro(
                id,
                LocalDate.of(2026, 8, 5),
                area,
                "Usuario Demo",
                "Mantenimiento",
                "Registro ficticio para prueba",
                estatus,
                LocalDateTime.of(2026, 8, 5, 12, 0)
        );
    }
}
