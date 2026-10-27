package com.adso.pom.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

/**
 * PAGE CLASS: FormPage
 * Responsabilidad: Encapsular los localizadores DOM y las interacciones
 * de la vista del formulario web.
 */
public class FormPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // =====================================================================
    // 1. ENCAPSULAMIENTO DE LOCALIZADORES (Atributos Privados By)
    // =====================================================================
    private By txtNombre = By.id("first-name");
    private By txtApellido = By.id("last-name");
    private By txtCargo = By.id("job-title");
    private By radioUniversidad = By.id("radio-button-2");
    private By chkMasculino = By.id("checkbox-1");
    private By selectExperiencia = By.id("select-menu");
    private By txtFecha = By.id("datepicker");
    private By btnSubmit = By.xpath("//a[contains(@class,'btn-primary')]");
    private By msgRespuesta = By.xpath("//div[contains(@class,'alert')]");

    // =====================================================================
    // 2. CONSTRUCTOR
    // =====================================================================
    public FormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // =====================================================================
    // 3. MÉTODOS DE ACCIÓN E INTERACCIÓN
    // =====================================================================

    public void ingresarNombreCompleto(String nombre, String apellido) {
        WebElement inputNombre = wait.until(ExpectedConditions.visibilityOfElementLocated(txtNombre));
        inputNombre.clear();
        inputNombre.sendKeys(nombre);

        WebElement inputApellido = driver.findElement(txtApellido);
        inputApellido.clear();
        inputApellido.sendKeys(apellido);
    }

    public void ingresarCargo(String cargo) {
        WebElement inputCargo = driver.findElement(txtCargo);
        inputCargo.clear();
        inputCargo.sendKeys(cargo);
    }

    public void seleccionarNivelEducativo() {
        WebElement radio = driver.findElement(radioUniversidad);
        if (!radio.isSelected()) {
            radio.click();
        }
    }

    public void seleccionarGenero() {
        WebElement chk = driver.findElement(chkMasculino);
        if (!chk.isSelected()) {
            chk.click();
        }
    }

    public void seleccionarExperiencia(String textoVisible) {
        WebElement elementoSelect = driver.findElement(selectExperiencia);
        Select combo = new Select(elementoSelect);
        combo.selectByVisibleText(textoVisible);
    }

    public void ingresarFecha(String fecha) {
        WebElement campoFecha = driver.findElement(txtFecha);
        campoFecha.sendKeys(fecha);
    }

    public void enviarFormulario() {
        WebElement boton = wait.until(ExpectedConditions.elementToBeClickable(btnSubmit));
        boton.click();
    }

    public String obtenerMensajeConfirmacion() {
        WebElement alerta = wait.until(ExpectedConditions.visibilityOfElementLocated(msgRespuesta));
        return alerta.getText();
    }
}
