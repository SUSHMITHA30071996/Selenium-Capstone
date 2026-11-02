// ABOUTME: Page object for the SauceDemo shopping cart page.
// ABOUTME: Provides methods to verify cart items, remove items, and proceed to checkout.
package com.saucedemo.pages;

import com.saucedemo.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

public class CartPage {

    private WebDriver driver;
    private WaitHelper waitHelper;

    // Locators
    private By pageTitle = By.className("title");
    private By cartItems = By.className("cart_item");
    private By cartItemNames = By.className("inventory_item_name");
    private By cartItemPrices = By.className("inventory_item_price");
    private By cartItemQuantities = By.className("cart_quantity");
    private By continueShoppingButton = By.id("continue-shopping");
    private By checkoutButton = By.id("checkout");
    private By removeButtons = By.cssSelector(".cart_button");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
    }

    public boolean isCartPageDisplayed() {
        try {
            return waitHelper.waitForElementVisible(pageTitle).getText().equals("Your Cart");
        } catch (Exception e) {
            return false;
        }
    }

    public int getCartItemCount() {
        return driver.findElements(cartItems).size();
    }

    public List<String> getCartItemNames() {
        List<String> names = new ArrayList<>();
        List<WebElement> elements = driver.findElements(cartItemNames);
        for (WebElement element : elements) {
            names.add(element.getText());
        }
        return names;
    }

    public List<String> getCartItemPrices() {
        List<String> prices = new ArrayList<>();
        List<WebElement> elements = driver.findElements(cartItemPrices);
        for (WebElement element : elements) {
            prices.add(element.getText());
        }
        return prices;
    }

    public String getItemQuantity(int index) {
        List<WebElement> quantities = driver.findElements(cartItemQuantities);
        return quantities.get(index).getText();
    }

    public void removeItemByIndex(int index) {
        List<WebElement> buttons = driver.findElements(removeButtons);
        buttons.get(index).click();
    }

    public void clickContinueShopping() {
        waitHelper.waitForElementClickable(continueShoppingButton).click();
    }

    public void clickCheckout() {
        waitHelper.waitForElementClickable(checkoutButton).click();
    }
}
