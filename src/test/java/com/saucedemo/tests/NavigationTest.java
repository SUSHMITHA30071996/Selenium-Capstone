// ABOUTME: Tests for the SauceDemo sidebar navigation menu.
// ABOUTME: Covers menu open/close, all sidebar links, and reset app state functionality.
package com.saucedemo.tests;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.NavigationPage;
import com.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class NavigationTest extends BaseTest {

    private ProductsPage productsPage;
    private NavigationPage navigationPage;
    private LoginPage loginPage;

    @BeforeMethod
    public void initPages() {
        loginWithDefaultUser();
        productsPage = new ProductsPage(driver);
        navigationPage = new NavigationPage(driver);
        loginPage = new LoginPage(driver);
    }

    @Test(groups = {"navigation"})
    public void testSidebarMenuOpen() {
        navigationPage.openMenu();

        // Small wait for menu animation
        try { Thread.sleep(500); } catch (InterruptedException e) { }

        Assert.assertTrue(navigationPage.isMenuOpen(),
                "Sidebar menu should be open");
    }

    @Test(groups = {"navigation"})
    public void testSidebarMenuClose() {
        navigationPage.openMenu();
        try { Thread.sleep(500); } catch (InterruptedException e) { }

        navigationPage.closeMenu();
        try { Thread.sleep(500); } catch (InterruptedException e) { }

        Assert.assertFalse(navigationPage.isMenuOpen(),
                "Sidebar menu should be closed");
    }

    @Test(groups = {"navigation"})
    public void testAllItemsLink() {
        // First navigate away by going to cart
        productsPage.clickCartIcon();

        // Then use sidebar to go back to all items
        navigationPage.openMenu();
        try { Thread.sleep(500); } catch (InterruptedException e) { }

        navigationPage.clickAllItems();

        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Should be on products page after clicking All Items");
    }

    @Test(groups = {"navigation"})
    public void testAboutLink() {
        navigationPage.openMenu();
        try { Thread.sleep(500); } catch (InterruptedException e) { }

        navigationPage.clickAbout();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("saucelabs.com"),
                "Should navigate to Sauce Labs website");
    }

    @Test(groups = {"smoke", "navigation"})
    public void testLogoutLink() {
        navigationPage.openMenu();
        try { Thread.sleep(500); } catch (InterruptedException e) { }

        navigationPage.clickLogout();

        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Should be on login page after logout");
    }

    @Test(groups = {"navigation"})
    public void testResetAppState() {
        // Add items to cart first
        productsPage.addProductToCartByIndex(0);
        productsPage.addProductToCartByIndex(1);

        Assert.assertEquals(productsPage.getCartBadgeCount(), "2",
                "Cart should have 2 items before reset");

        // Reset app state
        navigationPage.openMenu();
        try { Thread.sleep(500); } catch (InterruptedException e) { }

        navigationPage.clickResetAppState();

        // Close menu and verify cart is empty
        navigationPage.closeMenu();
        try { Thread.sleep(500); } catch (InterruptedException e) { }

        Assert.assertEquals(productsPage.getCartBadgeCount(), "0",
                "Cart should be empty after reset");
    }
}
