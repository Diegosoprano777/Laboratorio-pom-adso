package com.adso.pom.tests;

import com.adso.pom.pages.FormPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * TEST CLASS: FormTest
 * Responsabilidad: Definir los casos de prueba, orquestar el flujo
 * mediante la clase de página y ejecutar ASERCIONES con JUnit 5.
 */
public class FormTest {

    private WebDriver driver;
    private FormPage formPage;

    @BeforeEach
    public void setUp() {
        // Configuración previa a cada prueba
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");

        driver = new ChromeDriver(options);
        driver.get("https://formy-project.herokuapp.com/form");

        // Instanciar el Page Object pasando el driver activo
        formPage = new FormPage(driver);
    }

    @Test
    @DisplayName("CP01 - Diligenciamiento Exitoso de Formulario Web")
    public void testDiligenciamientoExitoso() throws InterruptedException {
        System.out.println("Ejecutando CP01 con Patrón POM...");

        // 1. Ejecución del flujo mediante métodos del Page Object
        formPage.ingresarNombreCompleto("Laura", "Rodriguez");
        formPage.ingresarCargo("Analista QA ADSO");
        formPage.seleccionarNivelEducativo();
        formPage.seleccionarGenero();
        formPage.seleccionarExperiencia("2-4");
        formPage.ingresarFecha("10/25/2026");

        // 2. Enviar formulario
        formPage.enviarFormulario();

        // 3. Obtener respuesta para la aserción
        String mensajeObtenido = formPage.obtenerMensajeConfirmacion();
        System.out.println("Mensaje devuelto por la interfaz: \"" + mensajeObtenido + "\"");

        // 4. ASERCIÓN JUNIT 5: Validar que el mensaje de éxito contenga la palabra clave
        assertTrue(mensajeObtenido.contains("successfully"),
                "LA PRUEBA FALLÓ: El mensaje de confirmación no fue el esperado.");

        System.out.println("PRUEBA PASÓ: Aserción verificada correctamente.");
        Thread.sleep(3000);
    }

    @AfterEach
    public void tearDown() {
        // Cierre del navegador al finalizar cada prueba
        if (driver != null) {
            System.out.println("Cerrando sesión de Chrome...");
            driver.quit();
        }
    }
}
