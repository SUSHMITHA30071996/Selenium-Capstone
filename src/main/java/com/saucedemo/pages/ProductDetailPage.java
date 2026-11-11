// ABOUTME: Page object for a single product's detail page on SauceDemo.
// ABOUTME: Provides methods to read product info and add to cart from the detail view.
package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class ProductDetailPage extends BasePage {

    @FindBy(className = "inventory_details_name")
    private WebElement productName;

    @FindBy(className = "inventory_details_price")
    private WebElement productPrice;

    @FindBy(className = "inventory_details_desc")
    private WebElement productDescription;

    @FindBy(css = ".btn_inventory")
    private WebElement addToCartButton;

    @FindBy(css = ".btn_secondary")
    private WebElement removeButton;

    @FindBy(id = "back-to-products")
    private WebElement backToProductsButton;

    public ProductDetailPage(WebDriver driver) {
        super(driver);
    }

    public String getProductName() {
        return waitShape.waitForElementVisible(productName).getText();
    }

    public String getProductPrice() {
        return waitShape.waitForElementVisible(productPrice).getText();
    }

    public String getProductDescription() {
        return waitShape.waitForElementVisible(productDescription).getText();
    }

    public void clickAddToCart() {
        waitShape.waitForElementClickable(addToCartButton).click();
    }

    public void clickBackToProducts() {
        waitShape.waitForElementClickable(backToProductsButton).click();
    }

    public boolean isProductDetailPageDisplayed() {
        try {
            return productName.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}
