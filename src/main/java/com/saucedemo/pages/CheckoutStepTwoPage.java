// ABOUTME: Page object for the SauceDemo checkout step two (order summary) page.
// ABOUTME: Provides methods to read item totals, tax, grand total, and finish the order.
package com.saucedemo.pages;

import com.saucedemo.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CheckoutStepTwoPage {

    private WebDriver driver;
    private WaitHelper waitHelper;

    // Locators
    private By pageTitle = By.className("title");
    private By cartItems = By.className("cart_item");
    private By cartItemNames = By.className("inventory_item_name");
    private By cartItemPrices = By.className("inventory_item_price");
    private By subtotalLabel = By.className("summary_subtotal_label");
    private By taxLabel = By.className("summary_tax_label");
    private By totalLabel = By.className("summary_total_label");
    private By finishButton = By.id("finish");
    private By cancelButton = By.id("cancel");

    public CheckoutStepTwoPage(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
    }

    public boolean isCheckoutOverviewDisplayed() {
        try {
            return waitHelper.waitForElementVisible(pageTitle).getText().equals("Checkout: Overview");
        } catch (Exception e) {
            return false;
        }
    }

    public int getItemCount() {
        return driver.findElements(cartItems).size();
    }

    public List<String> getItemNames() {
        List<String> names = new ArrayList<>();
        List<WebElement> elements = driver.findElements(cartItemNames);
        for (WebElement element : elements) {
            names.add(element.getText());
        }
        return names;
    }

    public List<String> getItemPrices() {
        List<String> prices = new ArrayList<>();
        List<WebElement> elements = driver.findElements(cartItemPrices);
        for (WebElement element : elements) {
            prices.add(element.getText());
        }
        return prices;
    }

    public String getSubtotal() {
        return waitHelper.waitForElementVisible(subtotalLabel).getText();
    }

    public String getTax() {
        return waitHelper.waitForElementVisible(taxLabel).getText();
    }

    public String getTotal() {
        return waitHelper.waitForElementVisible(totalLabel).getText();
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
        waitHelper.waitForElementClickable(finishButton).click();
    }

    public void clickCancel() {
        waitHelper.waitForElementClickable(cancelButton).click();
    }
}
