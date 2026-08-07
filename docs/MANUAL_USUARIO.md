# Manual de usuario y administrador - SICORO

## Inicio de la aplicación

Desde la raíz del proyecto ejecuto:

```bash
java -jar producto/SICORO-1.0.jar
```

También puedo ejecutar el JAR generado por Maven:

```bash
java -jar target/SICORO-1.0.jar
```

## Inicio de sesión

### Usuario de consulta

Utilizo estas credenciales ficticias para probar el rol de consulta:

- Usuario: `consulta`
- Contraseña: `Consulta123!`
- Rol: `CONSULTA`

Con este rol puedo consultar y filtrar información.

### Usuario administrador

Utilizo estas credenciales ficticias para probar el rol administrador:

- Usuario: `admin`
- Contraseña: `Admin123!`
- Rol: `ADMIN`

Con este rol puedo consultar, registrar, actualizar estatus, filtrar y generar reportes.

## Opciones del usuario CONSULTA

### 1. Consultar registros

Selecciono la opción `1` para visualizar los registros disponibles.

### 4. Filtrar registros

Selecciono la opción `4`. Puedo escribir un área, un estatus o dejar el campo vacío para no aplicar ese filtro.

### 0. Salir

Selecciono `0` para cerrar la sesión correctamente.

## Opciones del usuario ADMIN

### 1. Consultar registros

Selecciono la opción `1` para visualizar todos los registros disponibles.

### 2. Registrar información

Selecciono la opción `2` y capturo:

- Fecha en formato `AAAA-MM-DD`.
- Área.
- Responsable.
- Tipo.
- Descripción.

El sistema asigna un identificador al nuevo registro.

### 3. Actualizar estatus

Selecciono la opción `3`, indico el ID del registro y elijo uno de los siguientes estatus:

- `ABIERTO`
- `EN_PROCESO`
- `CERRADO`

### 4. Filtrar registros

Selecciono la opción `4` para consultar información por área y/o estatus.

### 5. Generar reporte automático

Selecciono la opción `5` para generar un archivo CSV dentro de la carpeta `reportes/`. El reporte incluye un resumen por área y estatus y el porcentaje de cierre.

### 0. Salir

Selecciono `0` para finalizar la sesión. El sistema registra el cierre en la bitácora.

## Archivos generados

Los principales archivos que genera el prototipo se almacenan en:

- Reportes: `reportes/`
- Bitácora: `data/auditoria.log`

Cuando corresponde, excluyo estos archivos del control de versiones.
