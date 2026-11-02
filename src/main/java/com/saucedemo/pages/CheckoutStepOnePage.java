// ABOUTME: Page object for the SauceDemo checkout step one (shipping info) page.
// ABOUTME: Provides methods to fill in first name, last name, and zip code fields.
package com.saucedemo.pages;

import com.saucedemo.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CheckoutStepOnePage {

    private WebDriver driver;
    private WaitHelper waitHelper;

    // Locators
    private By firstNameField = By.id("first-name");
    private By lastNameField = By.id("last-name");
    private By zipCodeField = By.id("postal-code");
    private By continueButton = By.id("continue");
    private By cancelButton = By.id("cancel");
    private By errorMessage = By.cssSelector("[data-test='error']");

    public CheckoutStepOnePage(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
    }

    public void enterFirstName(String firstName) {
        WebElement element = waitHelper.waitForElementVisible(firstNameField);
        element.clear();
        element.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        WebElement element = waitHelper.waitForElementVisible(lastNameField);
        element.clear();
        element.sendKeys(lastName);
    }

    public void enterZipCode(String zipCode) {
        WebElement element = waitHelper.waitForElementVisible(zipCodeField);
        element.clear();
        element.sendKeys(zipCode);
    }

    public void fillShippingInfo(String firstName, String lastName, String zipCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterZipCode(zipCode);
    }

    public void clickContinue() {
        waitHelper.waitForElementClickable(continueButton).click();
    }

    public void clickCancel() {
        waitHelper.waitForElementClickable(cancelButton).click();
    }

    public String getErrorMessage() {
        return waitHelper.waitForElementVisible(errorMessage).getText();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return driver.findElement(errorMessage).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
