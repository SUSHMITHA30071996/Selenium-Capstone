// ABOUTME: Page object for the SauceDemo products/inventory page.
// ABOUTME: Provides methods to interact with product listings, sorting, and the cart icon.
package com.saucedemo.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class ProductsPage extends BasePage {

    @FindBy(className = "title")
    private WebElement productPageTitle;

    @FindBy(className = "inventory_item")
    private List<WebElement> items;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> itemNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> itemPrices;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartIcon;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartLink;

    @FindBy(id = "react-burger-menu-btn")
    private WebElement sideMenuButton;

    @FindBy(css = ".btn_inventory")
    private List<WebElement> addToCartButton;

    public ProductsPage(WebDriver driver) {
        super(driver);
    }

    public String getPageTitle() {
        return waitShape.waitForElementVisible(productPageTitle).getText();
    }

    public boolean isProductsPageDisplayed() {
        try {
            return waitShape.waitForElementVisible(productPageTitle).getText().equals("Products");
        } catch (Exception e) {
            return false;
        }
    }

    public int getProductCount() {
        return items.size();
    }

    public List<String> getProductNames() {
        List<String> names = new ArrayList<>();
        for (WebElement element : itemNames) {
            names.add(element.getText());
        }
        return names;
    }

    public List<Double> getProductPrices() {
        List<Double> prices = new ArrayList<>();
        for (WebElement element : itemPrices) {
            String priceText = element.getText().replace("$", "");
            prices.add(Double.parseDouble(priceText));
        }
        return prices;
    }

    public void sortBy(String optionValue) {
        waitShape.waitForElementClickable(sortDropdown);
        Select select = new Select(sortDropdown);
        select.selectByValue(optionValue);
    }

    public void addProductToCartByIndex(int index) {
        addToCartButton.get(index).click();
    }

    public void addProductToCartByName(String productName) {
        for (WebElement item : items) {
            String name = item.findElement(By.className("inventory_item_name")).getText();
            if (name.equals(productName)) {
                item.findElement(By.cssSelector(".btn_inventory")).click();
                return;
            }
        }
        throw new IllegalArgumentException("Product not found: " + productName);
    }

    public void clickProductByName(String productName) {
        for (WebElement element : itemNames) {
            if (element.getText().equals(productName)) {
                element.click();
                return;
            }
        }
        throw new IllegalArgumentException("Product not found: " + productName);
    }

    public void clickCartIcon() {
        waitShape.waitForElementClickable(cartLink).click();
        waitShape.waitForUrlContains("cart.html");
    }

    public String getCartCount() {
        try {
            return cartIcon.getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public void clickSideMenu() {
        waitShape.waitForElementClickable(sideMenuButton).click();
    }
}
