// ABOUTME: Tests for the SauceDemo shopping cart page.
// ABOUTME: Covers adding/removing items, verifying cart details, and continue shopping.
package com.saucedemo.tests;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.saucedemo.utils.ConfigurationReader;

import java.util.List;

public class CartTest extends BaseTest {

    private ProductsPage productsPage;
    private CartPage cartPage;

    @BeforeMethod(alwaysRun = true)
    public void initPages() {
        loginWithDefaultUser();
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
    }

    @Test()
    public void testAddSingleItemToCart() {
        String productName = ConfigurationReader.getProperty("cart.single.item");
        productsPage.addProductToCartByName(productName);
        productsPage.clickCartIcon();

        Assert.assertTrue(cartPage.isCartPageDisplayed());
        Assert.assertEquals(cartPage.getCartItemCount(), 1);
    }

    @Test()
    public void testAddMultipleItemsToCart() {
        String[] productNames = ConfigurationReader.getProperty("cart.multiple.items").split(",");
        for (String productName : productNames) {
            productsPage.addProductToCartByName(productName.trim());
        }
        productsPage.clickCartIcon();

        Assert.assertEquals(cartPage.getCartItemCount(), productNames.length,
                "Cart should have " + productNames.length + " items");
    }

    @Test()
    public void testRemoveItemFromCart() {
        String[] productNames = ConfigurationReader.getProperty("cart.multiple.items").split(",");
        for (String productName : productNames) {
            productsPage.addProductToCartByName(productName.trim());
        }
        productsPage.clickCartIcon();

        Assert.assertEquals(cartPage.getCartItemCount(), productNames.length,
                "Cart should have " + productNames.length + " items");

        cartPage.removeItemByIndex(0);

        Assert.assertEquals(cartPage.getCartItemCount(), productNames.length - 1,
                "Cart should have " + (productNames.length - 1) + " items after removal");
    }

    @Test()
    public void testVerifyItemDetailsInCart() {
        // Get the first product's name and price from listing
        List<String> productNames = productsPage.getProductNames();
        String expectedName = productNames.get(0);

        productsPage.addProductToCartByIndex(0);
        productsPage.clickCartIcon();

        List<String> cartNames = cartPage.getCartItemNames();
        Assert.assertTrue(cartNames.contains(expectedName),
                "Cart should contain the added product: " + expectedName);

    }

    @Test()
    public void testContinueShoppingButton() {
        productsPage.addProductToCartByIndex(0);
        productsPage.clickCartIcon();

        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Should be on cart page");

        cartPage.clickContinueShopping();

        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Should be back on products page after clicking continue shopping");
    }

}
