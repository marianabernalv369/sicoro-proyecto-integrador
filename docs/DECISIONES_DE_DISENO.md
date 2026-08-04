# Decisiones de diseño del prototipo

1. **Java 11 sin dependencias externas.** Facilita la ejecución académica y evita fallas por instalación de librerías.
2. **Persistencia CSV.** Adecuada para una prueba de concepto, pero no para producción o alta concurrencia.
3. **Capas separadas.** Modelo, repositorio, servicios y aplicación para facilitar mantenimiento.
4. **Roles mínimos.** `ADMIN` modifica y genera reportes; `CONSULTA` solo visualiza y filtra.
5. **Contraseñas derivadas con PBKDF2.** Se evita almacenar contraseñas en texto plano.
6. **Datos ficticios.** Reduce el riesgo al publicar el repositorio.
7. **Bitácora local.** Permite demostrar trazabilidad de accesos y acciones.
8. **Reporte CSV.** Formato abierto, portable y compatible con hojas de cálculo.
