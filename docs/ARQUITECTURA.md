# Arquitectura de la solución SICORO

Diseñé SICORO como una aplicación Java de consola organizada por capas. Esta estructura separa la interacción con el usuario, las reglas de negocio, el acceso a datos y la generación de archivos.

```mermaid
flowchart TB
    U[Usuario] --> UI[Aplicación de consola - Main]
    UI --> AUTH[Servicio de autenticación]
    UI --> REG[Servicio de registros]
    UI --> REP[Servicio de reportes]
    UI --> AUD[Servicio de auditoría]
    REG --> REPO[Repositorio CSV]
    REP --> REPO
    AUTH --> USERS[(usuarios.csv)]
    REPO --> DATA[(registros.csv)]
    REP --> OUT[(reportes CSV)]
    AUD --> LOG[(bitácora local)]
    GH[GitHub] --> CI[Travis CI]
    CI --> TEST[Prueba JUnit]
```

## Componentes

| Componente | Responsabilidad |
|---|---|
| Aplicación de consola | Presentar el menú y coordinar las acciones del usuario. |
| Servicios | Aplicar autenticación, validaciones, consulta, actualización, reportes y auditoría. |
| Repositorio CSV | Leer y guardar registros ficticios de forma local. |
| Archivos de salida | Conservar reportes y bitácoras generados por el prototipo. |
| GitHub y Travis CI | Administrar versiones y ejecutar automáticamente la prueba JUnit. |

En esta versión no utilizo servidores web ni bases de datos corporativas, porque el alcance corresponde a una prueba de concepto académica local.
