// ABOUTME: End-to-end happy path test for the SauceDemo application.
// ABOUTME: Covers the full flow from login through checkout to order confirmation.
package com.saucedemo.tests;

import com.saucedemo.pages.CartPage;
import com.saucedemo.pages.CheckoutCompletePage;
import com.saucedemo.pages.CheckoutStepOnePage;
import com.saucedemo.pages.CheckoutStepTwoPage;
import com.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EndToEndTest extends BaseTest {

    private ProductsPage productsPage;
    private CartPage cartPage;
    private CheckoutStepOnePage checkoutStepOne;
    private CheckoutStepTwoPage checkoutStepTwo;
    private CheckoutCompletePage checkoutComplete;

    @BeforeMethod(alwaysRun = true)
    public void initPages() {
        productsPage = new ProductsPage(driver);
        cartPage = new CartPage(driver);
        checkoutStepOne = new CheckoutStepOnePage(driver);
        checkoutStepTwo = new CheckoutStepTwoPage(driver);
        checkoutComplete = new CheckoutCompletePage(driver);
    }

    @Test(groups = {"smoke", "e2e"})
    public void testCompleteHappyPathFlow() {
        // Step 1: Login
        loginWithDefaultUser();
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Step 1: Should be on products page after login");

        // Step 2: Add items to cart
        productsPage.addProductToCartByIndex(0);
        productsPage.addProductToCartByIndex(1);
        Assert.assertEquals(productsPage.getCartBadgeCount(), "2",
                "Step 2: Cart badge should show 2 items");

        // Step 3: Go to cart
        productsPage.clickCartIcon();
        Assert.assertTrue(cartPage.isCartPageDisplayed(),
                "Step 3: Should be on cart page");
        Assert.assertEquals(cartPage.getCartItemCount(), 2,
                "Step 3: Cart should have 2 items");

        // Step 4: Proceed to checkout
        cartPage.clickCheckout();
        checkoutStepOne.fillShippingInfo("John", "Doe", "12345");
        checkoutStepOne.clickContinue();

        // Step 5: Verify order summary
        Assert.assertTrue(checkoutStepTwo.isCheckoutOverviewDisplayed(),
                "Step 5: Should be on checkout overview page");
        Assert.assertEquals(checkoutStepTwo.getItemCount(), 2,
                "Step 5: Summary should show 2 items");

        // Verify price calculation
        double subtotal = checkoutStepTwo.getSubtotalAmount();
        double tax = checkoutStepTwo.getTaxAmount();
        double total = checkoutStepTwo.getTotalAmount();
        Assert.assertTrue(subtotal > 0, "Step 5: Subtotal should be greater than 0");
        Assert.assertEquals(total, Math.round((subtotal + tax) * 100.0) / 100.0,
                "Step 5: Total should equal subtotal + tax");

        // Step 6: Finish order
        checkoutStepTwo.clickFinish();

        // Step 7: Verify confirmation
        Assert.assertTrue(checkoutComplete.isOrderComplete(),
                "Step 7: Order complete page should be displayed");
        Assert.assertEquals(checkoutComplete.getCompleteHeader(),
                "Thank you for your order!",
                "Step 7: Should show thank you message");

        // Step 8: Go back to products
        checkoutComplete.clickBackHome();
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Step 8: Should be back on products page");
    }
}
