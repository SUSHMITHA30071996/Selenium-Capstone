// ABOUTME: Page object for the SauceDemo order confirmation page.
// ABOUTME: Provides methods to verify the order success message and navigate back.
package com.saucedemo.pages;

import com.saucedemo.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage {

    private WebDriver driver;
    private WaitHelper waitHelper;

    // Locators
    private By completeHeader = By.className("complete-header");
    private By completeText = By.className("complete-text");
    private By backHomeButton = By.id("back-to-products");

    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
    }

    public String getCompleteHeader() {
        return waitHelper.waitForElementVisible(completeHeader).getText();
    }

    public String getCompleteText() {
        return waitHelper.waitForElementVisible(completeText).getText();
    }

    public void clickBackHome() {
        waitHelper.waitForElementClickable(backHomeButton).click();
    }

    public boolean isOrderComplete() {
        try {
            return driver.findElement(completeHeader).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
