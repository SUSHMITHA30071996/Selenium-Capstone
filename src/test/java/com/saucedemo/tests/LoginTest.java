// ABOUTME: Tests for the SauceDemo login page.
// ABOUTME: Covers valid login, invalid credentials, locked-out user, and logout scenarios.
package com.saucedemo.tests;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.NavigationPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utils.ConfigurationReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private ProductsPage productsPage;
    private NavigationPage navigationPage;

    @BeforeMethod(alwaysRun = true)
    public void initPages() {
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
        navigationPage = new NavigationPage(driver);
    }

    @Test(groups = {"smoke"})
    public void testwithValidLogin() {
        loginPage.login(
                ConfigurationReader.getProperty("standard.username"),
                ConfigurationReader.getProperty("standard.password")
        );
        Assert.assertTrue(productsPage.isProductsPageDisplayed());
    }

    @Test(groups = {"smoke"})
    public void testwithInvalidLogin() {
        loginPage.login(
                ConfigurationReader.getProperty("standard.username"),
                ConfigurationReader.getProperty("Wrong.password"));
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Username and password do not match any user in this service"));
    }

    @Test(groups = {"smoke"})
    public void testwithLockedOutUser() {
        loginPage.login(
                ConfigurationReader.getProperty("locked.username"),
                ConfigurationReader.getProperty("standard.password")
        );
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Sorry, this user has been locked out"));
    }

    @Test(groups = {"smoke"})
    public void testLoginLogoutBackButton() {
        // Login
        loginPage.login(
                ConfigurationReader.getProperty("standard.username"),
                ConfigurationReader.getProperty("standard.password")
        );
        Assert.assertTrue(productsPage.isProductsPageDisplayed());

        // Logout using sidebar

        navigationPage.openMenu();
        navigationPage.clickLogout();

        Assert.assertTrue(loginPage.isLoginPageDisplayed());

    }
}
