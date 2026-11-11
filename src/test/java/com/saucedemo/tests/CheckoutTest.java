// ABOUTME: Tests for the SauceDemo checkout flow.
// ABOUTME: Covers complete checkout, form validation, order summary, and price calculations.
package com.saucedemo.tests;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utils.ConfigurationReader;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutPage checkoutPage;

    @BeforeMethod(alwaysRun = true)
    public void initPages() {
        loginWithDefaultUser();
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @Test
    public void testCompleteCheckoutFlow() {
        // Add an item and go to cart
        String productName = ConfigurationReader.getProperty("cart.single.item");
        productsPage.addProductToCartByName(productName);
        productsPage.clickCartIcon();
        cartPage.clickCheckout();

        // Fill shipping info
        checkoutPage.fillShippingInfo(
                ConfigurationReader.getProperty("checkout.firstName"),
                ConfigurationReader.getProperty("checkout.lastName"),
                ConfigurationReader.getProperty("checkout.zipCode"));
        checkoutPage.clickContinue();

        // Verify overview page and finish
        Assert.assertTrue(checkoutPage.isCheckoutOverviewDisplayed(),
                "Checkout overview should be displayed");
        checkoutPage.clickFinish();

        // Verify order complete
        Assert.assertTrue(checkoutPage.isOrderComplete(),
                "Order completion page should be displayed");
        Assert.assertEquals(checkoutPage.getCompleteHeader(), "Thank you for your order!",
                "Should show thank you message");
    }

    @Test()
    public void testOrderSummaryAndPriceCalculation() {
        // Add products by name
        List<String> products = Arrays.asList(
                ConfigurationReader.getProperty("checkout.products").split(","));
        for (String product : products) {
            productsPage.addProductToCartByName(product);
        }
        productsPage.clickCartIcon();
        cartPage.clickCheckout();

        checkoutPage.fillShippingInfo(
                ConfigurationReader.getProperty("checkout.firstName"),
                ConfigurationReader.getProperty("checkout.lastName"),
                ConfigurationReader.getProperty("checkout.zipCode"));
        checkoutPage.clickContinue();

        // Verify if the product count is same in checkout page
        Assert.assertEquals(checkoutPage.getItemCount(), products.size(),
                "Order summary should show " + products.size() + " items");

        // Verify if the same product name appears in checkout page
        List<String> checkoutItemNames = checkoutPage.getItemNames();
        for (String product : products) {
            Assert.assertTrue(checkoutItemNames.contains(product),
                    "Checkout should contain product: " + product);
        }

        // Verify individual item prices sum to subtotal
        List<String> itemPriceTexts = checkoutPage.getItemPrices();
        double itemPriceSum = 0;
        for (String priceText : itemPriceTexts) {
            itemPriceSum += Double.parseDouble(priceText.replaceAll("[^0-9.]", ""));
        }
        double subtotal = checkoutPage.getSubtotalAmount();
        Assert.assertEquals(subtotal, itemPriceSum,
                "Subtotal should equal sum of individual item prices");

    }

    @Test()
    public void testCheckoutCancelReturnsToCart() {
        productsPage.addProductToCartByIndex(0);
        productsPage.clickCartIcon();
        cartPage.clickCheckout();

        checkoutPage.clickCancel();

        Assert.assertTrue(cartPage.isCartPageDisplayed(),
                "Cancel should return to cart page");
    }

    @Test()
    public void testEmptyFormSubmission() {
        productsPage.addProductToCartByIndex(0);
        productsPage.clickCartIcon();
        cartPage.clickCheckout();

        // Click continue without filling any fields
        checkoutPage.clickContinue();

        Assert.assertTrue(checkoutPage.isErrorMessageDisplayed(),
                "Error message should be displayed for empty form");

        String error = checkoutPage.getErrorMessage();
        Assert.assertTrue(error.contains("First Name is required"),
                "Error should mention first name is required");
    }


    @Test()
    public void testMissingZipCode() {
        productsPage.addProductToCartByIndex(0);
        productsPage.clickCartIcon();
        cartPage.clickCheckout();

        checkoutPage.enterFirstName(ConfigurationReader.getProperty("checkout.firstName"));
        checkoutPage.enterLastName(ConfigurationReader.getProperty("checkout.lastName"));
        checkoutPage.clickContinue();

        String error = checkoutPage.getErrorMessage();
        Assert.assertTrue(error.contains("Postal Code is required"),
                "Error should mention postal code is required");
    }
}
