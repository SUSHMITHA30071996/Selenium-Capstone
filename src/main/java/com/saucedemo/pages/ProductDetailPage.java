// ABOUTME: Page object for a single product's detail page on SauceDemo.
// ABOUTME: Provides methods to read product info and add to cart from the detail view.
package com.saucedemo.pages;

import com.saucedemo.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ProductDetailPage {

    private WebDriver driver;
    private WaitHelper waitHelper;

    // Locators
    private By productName = By.className("inventory_details_name");
    private By productPrice = By.className("inventory_details_price");
    private By productDescription = By.className("inventory_details_desc");
    private By addToCartButton = By.cssSelector(".btn_inventory");
    private By removeButton = By.cssSelector(".btn_secondary");
    private By backToProductsButton = By.id("back-to-products");

    public ProductDetailPage(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
    }

    public String getProductName() {
        return waitHelper.waitForElementVisible(productName).getText();
    }

    public String getProductPrice() {
        return waitHelper.waitForElementVisible(productPrice).getText();
    }

    public String getProductDescription() {
        return waitHelper.waitForElementVisible(productDescription).getText();
    }

    public void clickAddToCart() {
        waitHelper.waitForElementClickable(addToCartButton).click();
    }

    public void clickBackToProducts() {
        waitHelper.waitForElementClickable(backToProductsButton).click();
    }

    public boolean isProductDetailPageDisplayed() {
        try {
            return driver.findElement(productName).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
