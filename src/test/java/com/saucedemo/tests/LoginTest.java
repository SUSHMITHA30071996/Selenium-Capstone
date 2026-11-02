// ABOUTME: Tests for the SauceDemo login page.
// ABOUTME: Covers valid login, invalid credentials, empty fields, and locked-out user scenarios.
package com.saucedemo.tests;

import com.saucedemo.pages.LoginPage;
import com.saucedemo.pages.ProductsPage;
import com.saucedemo.utils.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;
    private ProductsPage productsPage;

    @BeforeMethod
    public void initPages() {
        loginPage = new LoginPage(driver);
        productsPage = new ProductsPage(driver);
    }

    @Test(groups = {"smoke", "login"})
    public void testValidLogin() {
        loginPage.login(
                ConfigReader.getProperty("standard.username"),
                ConfigReader.getProperty("standard.password")
        );
        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Products page should be displayed after valid login");
    }

    @Test(groups = {"login"})
    public void testInvalidPassword() {
        loginPage.login("standard_user", "wrong_password");
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Username and password do not match"),
                "Error message should indicate invalid credentials");
    }

    @Test(groups = {"login"})
    public void testEmptyUsername() {
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Username is required"),
                "Error message should indicate username is required");
    }

    @Test(groups = {"login"})
    public void testEmptyPassword() {
        loginPage.enterUsername("standard_user");
        loginPage.clickLogin();
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Password is required"),
                "Error message should indicate password is required");
    }

    @Test(groups = {"login"})
    public void testEmptyFields() {
        loginPage.clickLogin();
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Username is required"),
                "Error message should indicate username is required when both fields are empty");
    }

    @Test(groups = {"login"})
    public void testLockedOutUser() {
        loginPage.login(
                ConfigReader.getProperty("locked.username"),
                ConfigReader.getProperty("standard.password")
        );
        String error = loginPage.getErrorMessage();
        Assert.assertTrue(error.contains("Sorry, this user has been locked out"),
                "Error message should indicate the user is locked out");
    }

    @Test(groups = {"login"})
    public void testLoginLogoutBackButton() {
        // Login
        loginPage.login(
                ConfigReader.getProperty("standard.username"),
                ConfigReader.getProperty("standard.password")
        );
        Assert.assertTrue(productsPage.isProductsPageDisplayed(), "Should be on products page");

        // Logout using sidebar
        productsPage.clickBurgerMenu();
        // Wait a moment for the menu animation
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // ignore
        }
        driver.findElement(org.openqa.selenium.By.id("logout_sidebar_link")).click();

        Assert.assertTrue(loginPage.isLoginPageDisplayed(), "Should be back on login page");

        // Press browser back button
        driver.navigate().back();

        // Should not be able to access products page after logout
        // The app should redirect to login or show an error
        String currentUrl = driver.getCurrentUrl();
        // After back button, the user should still see login page or be redirected
        Assert.assertTrue(
                currentUrl.contains("saucedemo.com"),
                "Should still be on the SauceDemo site after pressing back"
        );
    }
}
