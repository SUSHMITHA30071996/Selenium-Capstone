// ABOUTME: Page object for the SauceDemo shopping cart page.
// ABOUTME: Provides methods to verify cart items, remove items, and proceed to checkout.
package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> cartItemNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> cartItemPrices;

    @FindBy(className = "cart_quantity")
    private List<WebElement> cartItemQuantities;

    @FindBy(id = "continue-shopping")
    private WebElement continueShoppingButton;

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    @FindBy(css = ".cart_button")
    private List<WebElement> removeButtons;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isCartPageDisplayed() {
        try {
            return waitShape.waitForElementVisible(pageTitle).getText().equals("Your Cart");
        } catch (Exception e) {
            return false;
        }
    }

    public int getCartItemCount() {
        return cartItems.size();
    }

    public List<String> getCartItemNames() {
        List<String> names = new ArrayList<>();
        for (WebElement element : cartItemNames) {
            names.add(element.getText());
        }
        return names;
    }

    public List<String> getCartItemPrices() {
        List<String> prices = new ArrayList<>();
        for (WebElement element : cartItemPrices) {
            prices.add(element.getText());
        }
        return prices;
    }

    public String getItemQuantity(int index) {
        return cartItemQuantities.get(index).getText();
    }

    public void removeItemByIndex(int index) {
        removeButtons.get(index).click();
    }

    public void clickContinueShopping() {
        waitShape.waitForElementClickable(continueShoppingButton).click();
    }

    public void clickCheckout() {
        waitShape.waitForElementClickable(checkoutButton).click();
        waitShape.waitForUrlContains("checkout-step-one.html");
    }
}
