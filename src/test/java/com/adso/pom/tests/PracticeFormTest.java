package com.adso.pom.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import com.adso.pom.pages.PracticeFormPage;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * TEST CLASS: PracticeFormTest
 * Responsabilidad: Suite de pruebas sobre el Practice Form de DemoQA.
 * Flujo 1 (Éxito) y Flujo 2 (Fallo controlado) con aserciones JUnit 5.
 */
public class PracticeFormTest {

    private WebDriver driver;
    private PracticeFormPage practiceFormPage;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        // DemoQA carga mucha publicidad: no esperamos a que termine todo
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        driver = new ChromeDriver(options);
        practiceFormPage = new PracticeFormPage(driver);
        practiceFormPage.abrir();
    }

    @Test
    @DisplayName("CP01 - Flujo exitoso: registro completo de estudiante")
    public void testRegistroExitoso() {
        System.out.println("Ejecutando CP01 - Flujo exitoso...");
        String rutaArchivo = Paths.get("src", "test", "resources", "archivos", "foto-prueba.png")
                .toAbsolutePath().toString();

        // 1. Flujo mediante métodos del Page Object
        practiceFormPage.ingresarDatos("Laura", "Rodriguez", "laura.rodriguez@adso.com", "3001234567");
        practiceFormPage.seleccionarGeneroMasculino();
        practiceFormPage.seleccionarSubject("Maths");
        practiceFormPage.seleccionarHobbyDeportes();
        practiceFormPage.subirArchivo(rutaArchivo);
        practiceFormPage.ingresarDireccion("Calle 10 # 20-30, Bogota");
        practiceFormPage.seleccionarEstadoYCiudad("NCR", "Delhi");

        // 2. Enviar
        practiceFormPage.enviarFormulario();

        // 3. Aserciones
        String titulo = practiceFormPage.obtenerTituloModal();
        System.out.println("Título del modal: \"" + titulo + "\"");
        assertTrue(titulo.contains("Thanks for submitting the form"),
                "LA PRUEBA FALLÓ: No apareció el modal de éxito.");
        assertEquals("Laura Rodriguez", practiceFormPage.obtenerValorModal("Student Name"));
        assertEquals("laura.rodriguez@adso.com", practiceFormPage.obtenerValorModal("Student Email"));
        assertEquals("foto-prueba.png", practiceFormPage.obtenerValorModal("Picture"));

        System.out.println("CP01 PASÓ.");
    }

    @Test
    @DisplayName("CP02 - Fallo controlado: envío con campos obligatorios vacíos")
    public void testCamposVacios() {
        System.out.println("Ejecutando CP02 - Campos vacíos...");

        practiceFormPage.enviarFormulario();

        assertEquals(true, practiceFormPage.formularioEnValidacion(),
                "El formulario debió activar la validación.");
        assertEquals(false, practiceFormPage.esNombreValido(),
                "El campo nombre vacío debió ser inválido.");
        assertFalse(practiceFormPage.modalEstaVisible(),
                "No debe aparecer el modal de éxito.");

        System.out.println("CP02 PASÓ.");
    }

    @Test
    @DisplayName("CP03 - Fallo controlado: email con formato inválido")
    public void testEmailInvalido() {
        System.out.println("Ejecutando CP03 - Email inválido...");

        practiceFormPage.ingresarDatos("Laura", "Rodriguez", "correo-invalido", "3001234567");
        practiceFormPage.seleccionarGeneroMasculino();
        practiceFormPage.enviarFormulario();

        assertEquals(false, practiceFormPage.esEmailValido(),
                "El email con formato inválido debió ser rechazado.");
        assertFalse(practiceFormPage.modalEstaVisible(),
                "No debe aparecer el modal de éxito.");

        System.out.println("CP03 PASÓ.");
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            System.out.println("Cerrando sesión de Chrome...");
            driver.quit();
        }
    }
}
