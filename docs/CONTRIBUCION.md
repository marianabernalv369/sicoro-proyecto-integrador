# Guía de contribución - SICORO

Para mantener un historial ordenado utilizo `master` como rama estable y `develop` como rama de integración.

## 1. Clono el repositorio

```bash
git clone https://github.com/marianabernalv369/sicoro-proyecto-integrador.git
cd sicoro-proyecto-integrador
```

## 2. Actualizo develop

```bash
git checkout develop
git pull origin develop
```

## 3. Creo un branch

```bash
git checkout -b feature/nombre-de-la-funcionalidad
```

## 4. Desarrollo y pruebo los cambios

```bash
mvn clean test
git status
git add .
git commit -m "feat: describir cambio realizado"
```

## 5. Envío el branch

```bash
git push -u origin feature/nombre-de-la-funcionalidad
```

## 6. Envío el Pull Request

En GitHub abro un Pull Request desde `feature/nombre-de-la-funcionalidad` hacia `develop` y describo el cambio realizado.

## 7. Espero la revisión

Antes de integrar el cambio reviso las pruebas y posibles conflictos. Si existe alguna observación, la corrijo en la misma rama y actualizo el Pull Request.

## 8. Realizo el merge

Una vez aprobada la revisión y las pruebas, realizo el merge hacia `develop`. Cuando considero estable el conjunto de cambios, integro posteriormente `develop` a `master`.
