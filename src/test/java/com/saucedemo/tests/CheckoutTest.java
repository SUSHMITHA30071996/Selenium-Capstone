// ABOUTME: Tests for the SauceDemo checkout flow.
// ABOUTME: Covers complete checkout, form validation, order summary, and price calculations.
package com.saucedemo.tests;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutCompletePage;
import com.saucedemo.pages.CheckoutStepOnePage;
import com.saucedemo.pages.CheckoutStepTwoPage;
import com.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutStepOnePage checkoutStepOne;
    private CheckoutStepTwoPage checkoutStepTwo;
    private CheckoutCompletePage checkoutComplete;

    @BeforeMethod(alwaysRun = true)
    public void initPages() {
        loginWithDefaultUser();
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        checkoutStepOne = new CheckoutStepOnePage(driver);
        checkoutStepTwo = new CheckoutStepTwoPage(driver);
        checkoutComplete = new CheckoutCompletePage(driver);
    }

    @Test(groups = {"smoke", "checkout"})
    public void testCompleteCheckoutFlow() {
        // Add an item and go to cart
        productsPage.addProductToCartByIndex(0);
        productsPage.clickCartIcon();
        cartPage.clickCheckout();

        // Fill shipping info
        checkoutStepOne.fillShippingInfo("John", "Doe", "12345");
        checkoutStepOne.clickContinue();

        // Verify overview page and finish
        Assert.assertTrue(checkoutStepTwo.isCheckoutOverviewDisplayed(),
                "Checkout overview should be displayed");
        checkoutStepTwo.clickFinish();

        // Verify order complete
        Assert.assertTrue(checkoutComplete.isOrderComplete(),
                "Order completion page should be displayed");
        Assert.assertEquals(checkoutComplete.getCompleteHeader(), "Thank you for your order!",
                "Should show thank you message");
    }

    @Test(groups = {"checkout"})
    public void testEmptyFormSubmission() {
        productsPage.addProductToCartByIndex(0);
        productsPage.clickCartIcon();
        cartPage.clickCheckout();

        // Click continue without filling any fields
        checkoutStepOne.clickContinue();

        Assert.assertTrue(checkoutStepOne.isErrorMessageDisplayed(),
                "Error message should be displayed for empty form");
        String error = checkoutStepOne.getErrorMessage();
        Assert.assertTrue(error.contains("First Name is required"),
                "Error should mention first name is required");
    }

    @Test(groups = {"checkout"})
    public void testMissingLastName() {
        productsPage.addProductToCartByIndex(0);
        productsPage.clickCartIcon();
        cartPage.clickCheckout();

        checkoutStepOne.enterFirstName("John");
        checkoutStepOne.clickContinue();

        String error = checkoutStepOne.getErrorMessage();
        Assert.assertTrue(error.contains("Last Name is required"),
                "Error should mention last name is required");
    }

    @Test(groups = {"checkout"})
    public void testMissingZipCode() {
        productsPage.addProductToCartByIndex(0);
        productsPage.clickCartIcon();
        cartPage.clickCheckout();

        checkoutStepOne.enterFirstName("John");
        checkoutStepOne.enterLastName("Doe");
        checkoutStepOne.clickContinue();

        String error = checkoutStepOne.getErrorMessage();
        Assert.assertTrue(error.contains("Postal Code is required"),
                "Error should mention postal code is required");
    }

    @Test(groups = {"checkout"})
    public void testOrderSummaryShowsCorrectItems() {
        // Add two items
        productsPage.addProductToCartByIndex(0);
        productsPage.addProductToCartByIndex(1);
        productsPage.clickCartIcon();
        cartPage.clickCheckout();

        checkoutStepOne.fillShippingInfo("John", "Doe", "12345");
        checkoutStepOne.clickContinue();

        Assert.assertEquals(checkoutStepTwo.getItemCount(), 2,
                "Order summary should show 2 items");
    }

    @Test(groups = {"checkout"})
    public void testPriceTotalCalculation() {
        // Add an item
        productsPage.addProductToCartByIndex(0);
        productsPage.clickCartIcon();
        cartPage.clickCheckout();

        checkoutStepOne.fillShippingInfo("John", "Doe", "12345");
        checkoutStepOne.clickContinue();

        double subtotal = checkoutStepTwo.getSubtotalAmount();
        double tax = checkoutStepTwo.getTaxAmount();
        double total = checkoutStepTwo.getTotalAmount();

        double expectedTotal = Math.round((subtotal + tax) * 100.0) / 100.0;

        Assert.assertEquals(total, expectedTotal,
                "Total should equal subtotal + tax");
    }

    @Test(groups = {"checkout"})
    public void testCheckoutCancelReturnsToCart() {
        productsPage.addProductToCartByIndex(0);
        productsPage.clickCartIcon();
        cartPage.clickCheckout();

        checkoutStepOne.clickCancel();

        Assert.assertTrue(cartPage.isCartPageDisplayed(),
                "Cancel should return to cart page");
    }
}
