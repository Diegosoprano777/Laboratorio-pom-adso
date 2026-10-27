# Laboratorio POM - ADSO

Automatización de pruebas con **Selenium WebDriver + JUnit 5** aplicando el patrón
**Page Object Model (POM)**.

## Tecnologías
- Java 17
- Maven
- Selenium Java 4.18.1
- WebDriverManager 5.7.0
- JUnit 5 (Jupiter) 5.10.2

## Estructura

```
src/
├── main/java/com/adso/pom/pages/      <-- Clases de Página (localizadores + acciones)
│   ├── FormPage.java                  (Formy)
│   └── PracticeFormPage.java          (DemoQA)
└── test/java/com/adso/pom/tests/      <-- Clases de Prueba (flujos + aserciones)
    ├── FormTest.java
    └── PracticeFormTest.java
```

## Casos de prueba

| ID   | Clase            | Flujo                                   | Aserción              |
|------|------------------|-----------------------------------------|-----------------------|
| CP01 | FormTest         | Formy: diligenciamiento exitoso         | assertTrue            |
| CP01 | PracticeFormTest | DemoQA: registro completo + archivo     | assertTrue / assertEquals |
| CP02 | PracticeFormTest | DemoQA: campos obligatorios vacíos      | assertEquals / assertFalse |
| CP03 | PracticeFormTest | DemoQA: email con formato inválido      | assertEquals / assertFalse |

## Ejecución

```bash
mvn test                          # todas las pruebas
mvn test -Dtest=PracticeFormTest  # solo DemoQA
```

Requiere Google Chrome instalado (WebDriverManager descarga el driver automáticamente).
