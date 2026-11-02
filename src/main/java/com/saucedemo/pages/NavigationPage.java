// ABOUTME: Page object for the SauceDemo sidebar navigation menu.
// ABOUTME: Provides methods to open/close the menu and click sidebar links.
package com.saucedemo.pages;

import com.saucedemo.utils.WaitHelper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class NavigationPage {

    private WebDriver driver;
    private WaitHelper waitHelper;

    // Locators
    private By burgerMenuButton = By.id("react-burger-menu-btn");
    private By closeBurgerMenu = By.id("react-burger-cross-btn");
    private By allItemsLink = By.id("inventory_sidebar_link");
    private By aboutLink = By.id("about_sidebar_link");
    private By logoutLink = By.id("logout_sidebar_link");
    private By resetAppStateLink = By.id("reset_sidebar_link");
    private By menuWrap = By.className("bm-menu-wrap");

    public NavigationPage(WebDriver driver) {
        this.driver = driver;
        this.waitHelper = new WaitHelper(driver);
    }

    public void openMenu() {
        waitHelper.waitForElementClickable(burgerMenuButton).click();
    }

    public void closeMenu() {
        waitHelper.waitForElementClickable(closeBurgerMenu).click();
    }

    public void clickAllItems() {
        waitHelper.waitForElementClickable(allItemsLink).click();
    }

    public void clickAbout() {
        waitHelper.waitForElementClickable(aboutLink).click();
    }

    public void clickLogout() {
        waitHelper.waitForElementClickable(logoutLink).click();
    }

    public void clickResetAppState() {
        waitHelper.waitForElementClickable(resetAppStateLink).click();
    }

    public boolean isMenuOpen() {
        try {
            String ariaHidden = driver.findElement(menuWrap).getAttribute("aria-hidden");
            return "false".equals(ariaHidden);
        } catch (Exception e) {
            return false;
        }
    }
}
