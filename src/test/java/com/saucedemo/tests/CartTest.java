// ABOUTME: Tests for the SauceDemo shopping cart page.
// ABOUTME: Covers adding/removing items, verifying cart details, and continue shopping.
package com.saucedemo.tests;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

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

    @Test(groups = {"smoke", "cart"})
    public void testAddSingleItemToCart() {
        productsPage.addProductToCartByIndex(0);
        productsPage.clickCartIcon();

        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Cart page should be displayed");
        Assert.assertEquals(cartPage.getCartItemCount(), 1,
                "Cart should have 1 item");
    }

    @Test(groups = {"cart"})
    public void testAddMultipleItemsToCart() {
        productsPage.addProductToCartByIndex(0);
        productsPage.addProductToCartByIndex(1);
        productsPage.addProductToCartByIndex(2);
        productsPage.clickCartIcon();

        Assert.assertEquals(cartPage.getCartItemCount(), 3,
                "Cart should have 3 items");
    }

    @Test(groups = {"cart"})
    public void testRemoveItemFromCart() {
        // Add two items
        productsPage.addProductToCartByIndex(0);
        productsPage.addProductToCartByIndex(1);
        productsPage.clickCartIcon();

        Assert.assertEquals(cartPage.getCartItemCount(), 2, "Cart should have 2 items");

        // Remove first item
        cartPage.removeItemByIndex(0);

        Assert.assertEquals(cartPage.getCartItemCount(), 1,
                "Cart should have 1 item after removal");
    }

    @Test(groups = {"cart"})
    public void testVerifyItemDetailsInCart() {
        // Get the first product's name and price from listing
        List<String> productNames = productsPage.getProductNames();
        String expectedName = productNames.get(0);

        productsPage.addProductToCartByIndex(0);
        productsPage.clickCartIcon();

        List<String> cartNames = cartPage.getCartItemNames();
        Assert.assertTrue(cartNames.contains(expectedName),
                "Cart should contain the added product: " + expectedName);

        // Verify quantity is 1
        String quantity = cartPage.getItemQuantity(0);
        Assert.assertEquals(quantity, "1", "Item quantity should be 1");
    }

    @Test(groups = {"cart"})
    public void testVerifyItemPriceInCart() {
        productsPage.addProductToCartByIndex(0);
        productsPage.clickCartIcon();

        List<String> cartPrices = cartPage.getCartItemPrices();
        Assert.assertFalse(cartPrices.isEmpty(), "Cart should show item prices");
        Assert.assertTrue(cartPrices.get(0).contains("$"),
                "Price should contain dollar sign");
    }

    @Test(groups = {"cart"})
    public void testContinueShoppingButton() {
        productsPage.addProductToCartByIndex(0);
        productsPage.clickCartIcon();

        Assert.assertTrue(cartPage.isCartPageDisplayed(), "Should be on cart page");

        cartPage.clickContinueShopping();

        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Should be back on products page after clicking continue shopping");
    }

    @Test(groups = {"cart"})
    public void testCartBadgeUpdatesCorrectly() {
        productsPage.addProductToCartByIndex(0);
        Assert.assertEquals(productsPage.getCartBadgeCount(), "1",
                "Badge should show 1");

        productsPage.addProductToCartByIndex(1);
        Assert.assertEquals(productsPage.getCartBadgeCount(), "2",
                "Badge should show 2");
    }
}
