// ABOUTME: Page object for the SauceDemo products/inventory page.
// ABOUTME: Provides methods to interact with product listings, sorting, and the cart icon.
package com.saucedemo.pages;

import com.saucedemo.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;

public class ProductsPage {

    private WebDriver driver;
    private WaitHelper waitHelper;

    // Locators
    private By pageTitle = By.className("title");
    private By inventoryItems = By.className("inventory_item");
    private By inventoryItemNames = By.className("inventory_item_name");
    private By inventoryItemPrices = By.className("inventory_item_price");
    private By sortDropdown = By.className("product_sort_container");
    private By cartBadge = By.className("shopping_cart_badge");
    private By cartLink = By.className("shopping_cart_link");
    private By burgerMenuButton = By.id("react-burger-menu-btn");
    private By addToCartButtons = By.cssSelector(".btn_inventory");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
    }

    public String getPageTitle() {
        return waitHelper.waitForElementVisible(pageTitle).getText();
    }

    public boolean isProductsPageDisplayed() {
        try {
            return waitHelper.waitForElementVisible(pageTitle).getText().equals("Products");
        } catch (Exception e) {
            return false;
        }
    }

    public int getProductCount() {
        return driver.findElements(inventoryItems).size();
    }

    public List<String> getProductNames() {
        List<String> names = new ArrayList<>();
        List<WebElement> elements = driver.findElements(inventoryItemNames);
        for (WebElement element : elements) {
            names.add(element.getText());
        }
        return names;
    }

    public List<Double> getProductPrices() {
        List<Double> prices = new ArrayList<>();
        List<WebElement> elements = driver.findElements(inventoryItemPrices);
        for (WebElement element : elements) {
            String priceText = element.getText().replace("$", "");
            prices.add(Double.parseDouble(priceText));
        }
        return prices;
    }

    public void sortBy(String optionValue) {
        WebElement dropdown = waitHelper.waitForElementClickable(sortDropdown);
        Select select = new Select(dropdown);
        select.selectByValue(optionValue);
    }

    public void addProductToCartByIndex(int index) {
        List<WebElement> buttons = driver.findElements(addToCartButtons);
        buttons.get(index).click();
    }

    public void clickProductByName(String productName) {
        By productLink = By.xpath("//div[@class='inventory_item_name ' and text()='" + productName + "']");
        waitHelper.waitForElementClickable(productLink).click();
    }

    public void clickCartIcon() {
        waitHelper.waitForElementClickable(cartLink).click();
    }

    public String getCartBadgeCount() {
        try {
            return driver.findElement(cartBadge).getText();
        } catch (Exception e) {
            return "0";
        }
    }

    public void clickBurgerMenu() {
        waitHelper.waitForElementClickable(burgerMenuButton).click();
    }
}
