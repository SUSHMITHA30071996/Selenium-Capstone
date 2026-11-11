// ABOUTME: End-to-end happy path test for the SauceDemo application.
// ABOUTME: Covers the full flow from login through checkout to order confirmation.
package com.saucedemo.tests;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utils.ConfigurationReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EndToEndTest extends BaseTest {

    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    @BeforeMethod(alwaysRun = true)
    public void initPages() {
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @Test(groups = {"happy path"})
    public void testCompleteHappyPathFlow() {
        // Step 1: Login
        loginWithDefaultUser();
        Assert.assertTrue(productsPage.isProductsPageDisplayed());

        // Step 2: Add items to cart
        productsPage.addProductToCartByName("Sauce Labs Backpack");
        Assert.assertEquals(productsPage.getCartCount(), "1");

        // Step 3: Go to cart
        productsPage.clickCartIcon();
        Assert.assertTrue(cartPage.isCartPageDisplayed());

        // Step 4: Proceed to checkout
        cartPage.clickCheckout();
        checkoutPage.fillShippingInfo(
                ConfigurationReader.getProperty("checkout.firstName"),
                ConfigurationReader.getProperty("checkout.lastName"),
                ConfigurationReader.getProperty("checkout.zipCode"));
        checkoutPage.clickContinue();

        // Step 5: Verify order summary
        Assert.assertTrue(checkoutPage.isCheckoutOverviewDisplayed());
        Assert.assertEquals(checkoutPage.getItemCount(), 1);

        // Verify price calculation
        double subtotal = checkoutPage.getSubtotalAmount();
        Assert.assertTrue(subtotal > 0, "Step 5: Subtotal should be greater than 0");

        // Step 6: Finish order
        checkoutPage.clickFinish();

        // Step 7: Verify confirmation
        Assert.assertTrue(checkoutPage.isOrderComplete());
        Assert.assertEquals(checkoutPage.getCompleteHeader(),
                "Thank you for your order!");

        // Step 8: Go back to products
        checkoutPage.clickBackHome();
        Assert.assertTrue(productsPage.isProductsPageDisplayed());
    }
}
