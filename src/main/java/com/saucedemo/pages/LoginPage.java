// ABOUTME: Page object for the SauceDemo login page.
// ABOUTME: Provides methods to enter credentials, click login, and read error messages.
package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement Username;

    @FindBy(id = "password")
    private WebElement Password;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement LoginErrorMessage;

    public LoginPage(WebDriver driver) {
        super(driver);
    }


    public void login(String username, String password) {
       
        Username.sendKeys(username);
        Password.sendKeys(password);
        loginButton.click();
    }

    public String getErrorMessage() {
        return waitShape.waitForElementVisible(LoginErrorMessage).getText();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return LoginErrorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isLoginPageDisplayed() {
        try {
            return loginButton.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}

