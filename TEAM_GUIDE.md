# Guía para el equipo: Playwright + JUnit 5 + POM

Este documento resume qué se cambió en el repo, cómo está estructurado el framework de pruebas y cómo ejecutar los tests.

## Cambios realizados
- Se añadió un framework de pruebas UI con Playwright para Java y JUnit 5 siguiendo el patrón Page Object Model (POM).
- Páginas y pruebas creadas:
  - `src/test/java/base/TestBase.java`: inicializa Playwright, Browser y Page. Hooks JUnit (`@BeforeAll`, `@AfterAll`, `@BeforeEach`, `@AfterEach`). Modo headless por defecto y `page.setDefaultTimeout(15000)`.
  - `src/test/java/pages/LoginPage.java`: login y espera a URL de Dashboard.
  - `src/test/java/pages/DashboardPage.java`: verificación de Dashboard y navegación a PIM con esperas de selectores/URL.
  - `src/test/java/pages/PIMPage.java`: alta de empleado y búsqueda con autocompletado.
  - `src/test/java/tests/PIMTests.java`: 3 escenarios (login válido, creación de empleado aleatorio, búsqueda por nombre).
- `pom.xml` ajustado:
  - Dependencias: `com.microsoft.playwright:playwright:1.45.0`, `org.junit.jupiter:*:5.11.0`.
  - Plugin `maven-surefire-plugin:3.3.0` configurado con reportes en `target/surefire-reports` y propiedad `PLAYWRIGHT_HEADLESS` (true por defecto).
  - Repositorio central explícito.
- Script de ayuda `run-tests.ps1` para ejecutar pruebas de forma simple en PowerShell.
- README actualizado con instrucciones.

## Requisitos
- Java 17 o superior (el proyecto compila con `maven.compiler.release=17`).
- Maven 3.8+ (probado con 3.9.11). Si no está en PATH, usar el parámetro `-MavenPath` del script.
- Internet habilitado para la primera instalación de navegadores Playwright.

## Estructura del proyecto (tests)
```
src/test/java/
  base/
    TestBase.java
  pages/
    LoginPage.java
    DashboardPage.java
    PIMPage.java
  tests/
    PIMTests.java
```

## Cómo ejecutar las 3 pruebas del laboratorio
1) Con Maven (PowerShell):
```powershell
# Instalar navegadores y ejecutar todo
mvn -Dplaywright.install=true test

# Solo las 3 pruebas de PIM
mvn "-Dtest=tests.PIMTests" test

# Modo visible (no headless)
mvn "-Dplaywright.install=true" "-DPLAYWRIGHT_HEADLESS=false" test
```

2) Con el script `run-tests.ps1` (recomendado en Windows):
```powershell
# Ejecutar todo (headless por defecto)
./run-tests.ps1

# Modo visible
./run-tests.ps1 -Visible

# Solo PIM
./run-tests.ps1 -Class tests.PIMTests

# Un método específico
./run-tests.ps1 -Class tests.PIMTests -Method testCreateEmployee

# Saltar la instalación de navegadores (ya instalados)
./run-tests.ps1 -SkipInstall

# Especificar ruta de Maven si no está en PATH
./run-tests.ps1 -MavenPath "$env:USERPROFILE\apache-maven-3.9.11\bin\mvn.cmd"
```

## Reports
- Se generan en `target/surefire-reports` (XML y .txt), por ejemplo `TEST-tests.PIMTests.xml`.

## Credenciales y URL (demo pública)
- URL: `https://opensource-demo.orangehrmlive.com/`
- Usuario: `Admin`
- Contraseña: `admin123`

## Tips y solución de problemas
- Si VS Code muestra errores de dependencias (p. ej. "Missing artifact"):
  1) `Maven: Reload project`
  2) `Java: Clean Java Language Server Workspace`
  3) (Opcional) `Developer: Reload Window`
- En PowerShell, pon las propiedades `-D...` entre comillas (sobre todo `-Dplaywright.install=true`).
- Para ver el navegador sin cambiar código: añade `-DPLAYWRIGHT_HEADLESS=false` o usa `-Visible`.

## Breve sobre el POM (Page Object Model)
- Cada clase en `pages/` encapsula selectores y acciones de una pantalla.
- Las pruebas en `tests/` orquestan esas acciones con aserciones.
- `TestBase` aísla cada prueba creando/cerrando contexto/navegador, evitando efectos colaterales.
