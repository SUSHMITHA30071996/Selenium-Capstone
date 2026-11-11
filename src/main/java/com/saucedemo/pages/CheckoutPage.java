// ABOUTME: Page object for the entire SauceDemo checkout flow.
// ABOUTME: Covers shipping info entry, order summary review, and order confirmation.
package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class CheckoutPage extends BasePage {

    // Step one: shipping info
    @FindBy(id = "first-name")
    private WebElement firstNameField;

    @FindBy(id = "last-name")
    private WebElement lastNameField;

    @FindBy(id = "postal-code")
    private WebElement zipCodeField;

    @FindBy(id = "continue")
    private WebElement continueButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    // Step two: order summary
    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> cartItemNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> cartItemPrices;

    @FindBy(className = "summary_subtotal_label")
    private WebElement subtotalLabel;

    @FindBy(className = "summary_tax_label")
    private WebElement taxLabel;

    @FindBy(className = "summary_total_label")
    private WebElement totalLabel;

    @FindBy(id = "finish")
    private WebElement finishButton;

    // Shared between step one and step two
    @FindBy(id = "cancel")
    private WebElement cancelButton;

    // Complete page
    @FindBy(className = "complete-header")
    private WebElement completeHeader;

    @FindBy(className = "complete-text")
    private WebElement completeText;

    @FindBy(id = "back-to-products")
    private WebElement backHomeButton;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    // Step one: shipping info methods

    public void enterFirstName(String firstName) {
        WebElement element = waitShape.waitForElementVisible(firstNameField);
        element.clear();
        element.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        WebElement element = waitShape.waitForElementVisible(lastNameField);
        element.clear();
        element.sendKeys(lastName);
    }

    public void enterZipCode(String zipCode) {
        WebElement element = waitShape.waitForElementVisible(zipCodeField);
        element.clear();
        element.sendKeys(zipCode);
    }

    public void fillShippingInfo(String firstName, String lastName, String zipCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterZipCode(zipCode);
    }

    public void clickContinue() {
        waitShape.waitForElementClickable(continueButton).click();
    }

    public String getErrorMessage() {
        return waitShape.waitForElementVisible(errorMessage).getText();
    }

    public boolean isErrorMessageDisplayed() {
        try {
            return errorMessage.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    // Step two: order summary methods

    public boolean isCheckoutOverviewDisplayed() {
        try {
            return waitShape.waitForElementVisible(pageTitle).getText().equals("Checkout: Overview");
        } catch (Exception e) {
            return false;
        }
    }

    public int getItemCount() {
        return cartItems.size();
    }

    public List<String> getItemNames() {
        List<String> names = new ArrayList<>();
        for (WebElement element : cartItemNames) {
            names.add(element.getText());
        }
        return names;
    }

    public List<String> getItemPrices() {
        List<String> prices = new ArrayList<>();
        for (WebElement element : cartItemPrices) {
            prices.add(element.getText());
        }
        return prices;
    }

    public String getSubtotal() {
        return waitShape.waitForElementVisible(subtotalLabel).getText();
    }

    public String getTax() {
        return waitShape.waitForElementVisible(taxLabel).getText();
    }

    public String getTotal() {
        return waitShape.waitForElementVisible(totalLabel).getText();
    }

    public double getSubtotalAmount() {
        String text = getSubtotal();
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }

    public double getTaxAmount() {
        String text = getTax();
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }

    public double getTotalAmount() {
        String text = getTotal();
        return Double.parseDouble(text.replaceAll("[^0-9.]", ""));
    }

    public void clickFinish() {
        waitShape.waitForElementClickable(finishButton).click();
        waitShape.waitForUrlContains("checkout-complete.html");
    }

    // Shared methods

    public void clickCancel() {
        waitShape.waitForElementClickable(cancelButton).click();
    }

    // Complete page methods

    public String getCompleteHeader() {
        return waitShape.waitForElementVisible(completeHeader).getText();
    }

    public String getCompleteText() {
        return waitShape.waitForElementVisible(completeText).getText();
    }

    public void clickBackHome() {
        waitShape.waitForElementClickable(backHomeButton).click();
    }

    public boolean isOrderComplete() {
        try {
            return completeHeader.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
