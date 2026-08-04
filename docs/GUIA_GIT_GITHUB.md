# Guía para publicar el proyecto en GitHub

## 1. Revisión previa obligatoria

- Obtener la carta de autorización firmada.
- Confirmar la participación o revisión del área de TI.
- Verificar que solo existan datos ficticios.
- No agregar contraseñas reales, tokens, archivos `.env`, llaves, nombres de clientes o información interna.

## 2. Crear el repositorio en GitHub

1. Inicie sesión en GitHub.
2. Seleccione **New repository**.
3. Nombre sugerido: `sicoro-proyecto-integrador`.
4. Descripción: `Prototipo académico para control de información y generación de reportes operativos`.
5. Seleccione **Public** solamente cuando exista autorización. Mientras se revisa, utilice **Private**.
6. No marque la creación automática de README, licencia o `.gitignore`, porque el proyecto ya los incluye.

## 3. Publicar desde la terminal

Dentro de la carpeta del proyecto:

```bash
git status
git log --oneline
git remote add origin https://github.com/USUARIO/sicoro-proyecto-integrador.git
git push -u origin main
```

Sustituya `USUARIO` por el usuario real de GitHub.

## 4. Si GitHub solicita autenticación

Utilice el inicio de sesión del navegador o un token personal creado directamente desde su cuenta. No comparta contraseña, código de verificación ni token con terceros.

## 5. Evidencia para la entrega

Incluya:

- URL del repositorio.
- Captura de la página principal con el README visible.
- Captura de la sección **Commits**.
- Captura de la carpeta `src/main/java`.
- Captura de la ejecución del programa y del reporte generado.

## 6. Flujo de trabajo para cambios posteriores

```bash
git checkout -b feature/nombre-del-cambio
# realizar cambios
git add .
git commit -m "feat: descripción breve del cambio"
git checkout main
git merge feature/nombre-del-cambio
git push origin main
```

Convenciones sugeridas:

- `feat:` nueva funcionalidad.
- `fix:` corrección.
- `docs:` documentación.
- `test:` pruebas.
- `chore:` configuración o mantenimiento.
