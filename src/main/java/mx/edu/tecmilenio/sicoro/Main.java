package mx.edu.tecmilenio.sicoro;

import mx.edu.tecmilenio.sicoro.model.Registro;
import mx.edu.tecmilenio.sicoro.repository.CsvRegistroRepository;
import mx.edu.tecmilenio.sicoro.service.AuditService;
import mx.edu.tecmilenio.sicoro.service.AuthService;
import mx.edu.tecmilenio.sicoro.service.RegistroService;
import mx.edu.tecmilenio.sicoro.service.ReporteService;

import java.io.Console;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public final class Main {
    private final Scanner scanner = new Scanner(System.in);
    private final AuthService authService = new AuthService(Path.of("data", "usuarios.csv"));
    private final RegistroService registroService = new RegistroService(
            new CsvRegistroRepository(Path.of("data", "registros.csv")));
    private final ReporteService reporteService = new ReporteService(Path.of("reportes"));
    private final AuditService auditService = new AuditService(Path.of("data", "auditoria.log"));

    public static void main(String[] args) {
        try {
            new Main().run();
        } catch (IOException ex) {
            System.err.println("Error de lectura o escritura: " + ex.getMessage());
        } catch (RuntimeException ex) {
            System.err.println("Error: " + ex.getMessage());
        }
    }

    private void run() throws IOException {
        System.out.println("=== SICORO | Control y Reportes Operativos ===");
        AuthService.Session session = login();
        if (session == null) {
            System.out.println("Acceso bloqueado después de tres intentos.");
            return;
        }
        auditService.log(session.getUsername(), "INICIO_SESION");
        boolean running = true;
        while (running) {
            printMenu(session);
            String option = scanner.nextLine().trim();
            try {
                switch (option) {
                    case "1": list(registroService.listAll()); break;
                    case "2": requireAdmin(session); add(session); break;
                    case "3": requireAdmin(session); update(session); break;
                    case "4": filter(); break;
                    case "5": requireAdmin(session); report(session); break;
                    case "0": running = false; break;
                    default: System.out.println("Opción no válida.");
                }
            } catch (IllegalArgumentException ex) {
                System.out.println("Validación: " + ex.getMessage());
            }
        }
        auditService.log(session.getUsername(), "CIERRE_SESION");
        System.out.println("Sesión finalizada.");
    }

    private AuthService.Session login() throws IOException {
        for (int attempt = 1; attempt <= 3; attempt++) {
            System.out.print("Usuario: ");
            String user = scanner.nextLine().trim();
            String password = readPassword();
            Optional<AuthService.Session> session = authService.authenticate(user, password);
            if (session.isPresent()) return session.get();
            auditService.log(user, "ACCESO_FALLIDO");
            System.out.println("Credenciales inválidas. Intento " + attempt + " de 3.");
        }
        return null;
    }

    private String readPassword() {
        Console console = System.console();
        if (console != null) {
            char[] value = console.readPassword("Contraseña: ");
            return value == null ? "" : new String(value);
        }
        System.out.print("Contraseña: ");
        return scanner.nextLine();
    }

    private void printMenu(AuthService.Session session) {
        System.out.println("\nUsuario: " + session.getUsername() + " | Rol: " + session.getRole());
        System.out.println("1. Consultar registros");
        if (session.isAdmin()) {
            System.out.println("2. Registrar información");
            System.out.println("3. Actualizar estatus");
        }
        System.out.println("4. Filtrar registros");
        if (session.isAdmin()) System.out.println("5. Generar reporte automático");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private void add(AuthService.Session session) throws IOException {
        System.out.print("Fecha (AAAA-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine().trim());
        System.out.print("Área: "); String area = scanner.nextLine();
        System.out.print("Responsable: "); String responsible = scanner.nextLine();
        System.out.print("Tipo: "); String type = scanner.nextLine();
        System.out.print("Descripción: "); String description = scanner.nextLine();
        Registro r = registroService.add(date, area, responsible, type, description);
        auditService.log(session.getUsername(), "ALTA " + r.getId());
        System.out.println("Registro creado: " + r.getId());
    }

    private void update(AuthService.Session session) throws IOException {
        System.out.print("ID del registro: "); String id = scanner.nextLine().trim();
        System.out.print("Nuevo estatus (ABIERTO, EN_PROCESO, CERRADO): ");
        String status = scanner.nextLine().trim();
        if (registroService.updateStatus(id, status)) {
            auditService.log(session.getUsername(), "CAMBIO_ESTATUS " + id + " -> " + status);
            System.out.println("Estatus actualizado.");
        } else {
            System.out.println("No se encontró el registro.");
        }
    }

    private void filter() throws IOException {
        System.out.print("Área (vacío para todas): "); String area = scanner.nextLine();
        System.out.print("Estatus (vacío para todos): "); String status = scanner.nextLine();
        list(registroService.filter(area, status));
    }

    private void report(AuthService.Session session) throws IOException {
        Path output = reporteService.generateSummary(registroService.listAll());
        auditService.log(session.getUsername(), "GENERA_REPORTE " + output.getFileName());
        System.out.println("Reporte generado en: " + output.toAbsolutePath());
    }

    private void list(List<Registro> records) {
        if (records.isEmpty()) {
            System.out.println("No existen registros para mostrar.");
            return;
        }
        System.out.printf("%-10s %-10s %-20s %-18s %-12s %s%n",
                "ID", "FECHA", "ÁREA", "RESPONSABLE", "ESTATUS", "DESCRIPCIÓN");
        for (Registro r : records) {
            System.out.printf("%-10s %-10s %-20.20s %-18.18s %-12s %s%n",
                    r.getId(), r.getFecha(), r.getArea(), r.getResponsable(),
                    r.getEstatus(), r.getDescripcion());
        }
    }

    private void requireAdmin(AuthService.Session session) {
        if (!session.isAdmin()) throw new IllegalArgumentException("La operación requiere rol ADMIN.");
    }
}
