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

    @BeforeMethod(alwaysRun = true)
    public void initPages() {
        loginWithDefaultUser();
        productsPage = new ProductsPage(driver);
        navigationPage = new NavigationPage(driver);
        loginPage = new LoginPage(driver);
    }

    @Test(groups = {"smoke"})
    public void testSidebarMenuNavigation() {
        navigationPage.openMenu();
        navigationPage.waitForMenuOpen();

        Assert.assertTrue(navigationPage.isMenuOpen(),
                "Sidebar menu should be open");

        navigationPage.closeMenu();

        Assert.assertFalse(navigationPage.isMenuOpen(),
                "Sidebar menu should be closed");            
    }


    @Test(groups = {"smoke"})
    public void testAllItemsLinkNavigation() {
        // First navigating to cart
        productsPage.clickCartIcon();

        // Then use sidebar to go back to all items
        navigationPage.openMenu();
        navigationPage.waitForMenuOpen();

        navigationPage.clickAllItems();

        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Should be on products page after clicking All Items");
    }

    @Test(groups = {"smoke"})
    public void testAboutLinkNavigation() {
        navigationPage.openMenu();
        navigationPage.waitForMenuOpen();

        String aboutHref = navigationPage.getAboutLinkHref();
        Assert.assertTrue(aboutHref.contains("saucelabs.com"),
                "About link should point to Sauce Labs website");
    }

    @Test(groups = {"smoke"})
    public void testLogoutLink() {
        navigationPage.openMenu();
        navigationPage.waitForMenuOpen();

        navigationPage.clickLogout();

        Assert.assertTrue(loginPage.isLoginPageDisplayed(),
                "Should be on login page after logout");
    }

    @Test(groups = {"smoke"})
    public void testResetAppState() {
        // Add items to cart first
        productsPage.addProductToCartByIndex(0);
        productsPage.addProductToCartByIndex(1);

        Assert.assertEquals(productsPage.getCartCount(), "2",
                "Cart should have 2 items before reset");

        // Reset app state
        navigationPage.openMenu();
        navigationPage.waitForMenuOpen();

        navigationPage.clickResetAppState();

        navigationPage.closeMenu();
        navigationPage.waitForMenuClosed();

        Assert.assertEquals(productsPage.getCartCount(), "0",
                "Cart should be empty after reset");
    }
}
