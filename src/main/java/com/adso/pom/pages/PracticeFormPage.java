package com.adso.pom.pages;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * PAGE CLASS: PracticeFormPage
 * Responsabilidad: Encapsular los localizadores DOM y las interacciones
 * del formulario "Practice Form" de DemoQA.
 * URL: https://demoqa.com/automation-practice-form
 */
public class PracticeFormPage {

    public static final String URL = "https://demoqa.com/automation-practice-form";

    private WebDriver driver;
    private WebDriverWait wait;
    private JavascriptExecutor js;

    // =====================================================================
    // 1. ENCAPSULAMIENTO DE LOCALIZADORES (Atributos Privados By)
    // =====================================================================
    private By formulario = By.id("userForm");
    private By txtNombre = By.id("firstName");
    private By txtApellido = By.id("lastName");
    private By txtEmail = By.id("userEmail");
    private By radioGeneroMasculino = By.xpath("//label[@for='gender-radio-1']");
    private By txtCelular = By.id("userNumber");
    private By txtSubjects = By.id("subjectsInput");
    private By menuSubjects = By.cssSelector(".subjects-auto-complete__menu");
    private By chkHobbyDeportes = By.xpath("//label[@for='hobbies-checkbox-1']");
    private By inputArchivo = By.id("uploadPicture");
    private By txtDireccion = By.id("currentAddress");
    private By inputEstado = By.xpath("//div[@id='state']//input");
    private By inputCiudad = By.xpath("//div[@id='city']//input");
    private By btnSubmit = By.id("submit");
    private By tituloModal = By.id("example-modal-sizes-title-lg");

    // =====================================================================
    // 2. CONSTRUCTOR
    // =====================================================================
    public PracticeFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    // =====================================================================
    // 3. MÉTODOS DE ACCIÓN E INTERACCIÓN
    // =====================================================================

    /** Abre la página y elimina la publicidad que puede tapar los botones. */
    public void abrir() {
        driver.get(URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(txtNombre));
        js.executeScript(
                "document.querySelectorAll('#fixedban, footer, iframe, #adplus-anchor').forEach(e => e.remove());");
    }

    public void ingresarDatos(String nombre, String apellido, String email, String celular) {
        escribir(txtNombre, nombre);
        escribir(txtApellido, apellido);
        escribir(txtEmail, email);
        escribir(txtCelular, celular);
    }

    public void seleccionarGeneroMasculino() {
        clickSeguro(radioGeneroMasculino);
    }

    public void seleccionarSubject(String subject) {
        WebElement input = driver.findElement(txtSubjects);
        input.sendKeys(subject);
        wait.until(ExpectedConditions.visibilityOfElementLocated(menuSubjects));
        input.sendKeys(Keys.ENTER);
    }

    public void seleccionarHobbyDeportes() {
        clickSeguro(chkHobbyDeportes);
    }

    /** Recibe la ruta ABSOLUTA del archivo a cargar. */
    public void subirArchivo(String rutaAbsoluta) {
        driver.findElement(inputArchivo).sendKeys(rutaAbsoluta);
    }

    public void ingresarDireccion(String direccion) {
        escribir(txtDireccion, direccion);
    }

    public void seleccionarEstadoYCiudad(String estado, String ciudad) {
        WebElement inputE = driver.findElement(inputEstado);
        inputE.sendKeys(estado);
        inputE.sendKeys(Keys.ENTER);

        WebElement inputC = driver.findElement(inputCiudad);
        inputC.sendKeys(ciudad);
        inputC.sendKeys(Keys.ENTER);
    }

    public void enviarFormulario() {
        clickSeguro(btnSubmit);
    }

    // =====================================================================
    // 4. MÉTODOS DE CONSULTA (para las aserciones en la Test Class)
    // =====================================================================

    public String obtenerTituloModal() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(tituloModal)).getText();
    }

    /** Devuelve el valor de una fila de la tabla del modal, ej: "Student Name". */
    public String obtenerValorModal(String etiqueta) {
        By celda = By.xpath("//td[text()='" + etiqueta + "']/following-sibling::td");
        return wait.until(ExpectedConditions.visibilityOfElementLocated(celda)).getText();
    }

    public boolean modalEstaVisible() {
        List<WebElement> modales = driver.findElements(tituloModal);
        return !modales.isEmpty() && modales.get(0).isDisplayed();
    }

    /** true si el formulario activó la validación (clase "was-validated"). */
    public boolean formularioEnValidacion() {
        String clases = driver.findElement(formulario).getAttribute("class");
        return clases != null && clases.contains("was-validated");
    }

    public boolean esNombreValido() {
        return esCampoValido(txtNombre);
    }

    public boolean esEmailValido() {
        return esCampoValido(txtEmail);
    }

    // =====================================================================
    // 5. MÉTODOS PRIVADOS DE APOYO
    // =====================================================================

    private void escribir(By localizador, String texto) {
        WebElement campo = wait.until(ExpectedConditions.visibilityOfElementLocated(localizador));
        campo.clear();
        campo.sendKeys(texto);
    }

    private void clickSeguro(By localizador) {
        WebElement elemento = wait.until(ExpectedConditions.presenceOfElementLocated(localizador));
        js.executeScript("arguments[0].scrollIntoView({block: 'center'});", elemento);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(elemento)).click();
        } catch (ElementClickInterceptedException e) {
            js.executeScript("arguments[0].click();", elemento);
        }
    }

    /** Usa la validación HTML5 del navegador (checkValidity). */
    private boolean esCampoValido(By localizador) {
        WebElement campo = driver.findElement(localizador);
        return (Boolean) js.executeScript("return arguments[0].checkValidity();", campo);
    }
}
