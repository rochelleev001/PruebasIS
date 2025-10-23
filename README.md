# Playwright + JUnit 5 + POM para OrangeHRM

Este proyecto implementa automatización de pruebas para [OrangeHRM Demo](https://opensource-demo.orangehrmlive.com/) usando Playwright para Java, JUnit 5 y el patrón Page Object Model (POM).

## Estructura del proyecto

```
src/test/java/
  base/
    TestBase.java         # Configuración Playwright y hooks JUnit
  pages/
    LoginPage.java        # Acciones y validaciones de login
    DashboardPage.java    # Acciones y validaciones de dashboard
    PIMPage.java          # Acciones para módulo PIM
  tests/
    PIMTests.java         # Escenarios automatizados
```

## Requisitos
- Java 17+
- Maven 3.8+

## Instalación y ejecución
1. Instala dependencias y navegadores Playwright:

   ```powershell
   mvn -Dplaywright.install=true test
   ```

2. Los reportes se generan en `target/surefire-reports`.

### Alternativa rápida (script PowerShell)

Puedes usar el script `run-tests.ps1` en la raíz del proyecto:

```powershell
# Ejecutar todo e instalar navegadores si hace falta (headless por defecto)
./run-tests.ps1

# Modo visible (no headless)
./run-tests.ps1 -Visible

# Ejecutar solo la suite PIM
./run-tests.ps1 -Class tests.PIMTests

# Ejecutar un método específico
./run-tests.ps1 -Class tests.PIMTests -Method testCreateEmployee

# Saltar la instalación de navegadores (cuando ya están instalados)
./run-tests.ps1 -SkipInstall

# Especificar ruta de Maven si no está en PATH
./run-tests.ps1 -MavenPath "$env:USERPROFILE\apache-maven-3.9.11\bin\mvn.cmd"
```

## Configuración de modo headless/visible
Por defecto, las pruebas corren en modo headless. Para ver el navegador, ejecuta:

```powershell
mvn -Dplaywright.install=true -DPLAYWRIGHT_HEADLESS=false test
```

## Escenarios automatizados
1. **Login válido y verificación de Dashboard**
2. **Creación de empleado con nombre aleatorio**
3. **Búsqueda de empleado existente por nombre**

## Credenciales demo
- **URL:** https://opensource-demo.orangehrmlive.com/
- **Usuario:** Admin
- **Contraseña:** admin123

---

**Autor:** EduardoHG017
