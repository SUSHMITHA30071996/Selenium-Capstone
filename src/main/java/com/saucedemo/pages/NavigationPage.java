// ABOUTME: Page object for the SauceDemo sidebar navigation menu.
// ABOUTME: Provides methods to open/close the menu and click sidebar links.
package com.saucedemo.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class NavigationPage extends BasePage {

    @FindBy(id = "react-burger-menu-btn")
    private WebElement sideMenuButton;

    @FindBy(id = "react-burger-cross-btn")
    private WebElement closeSideMenu;

    @FindBy(id = "inventory_sidebar_link")
    private WebElement allItemsLink;

    @FindBy(id = "about_sidebar_link")
    private WebElement aboutLink;

    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    @FindBy(id = "reset_sidebar_link")
    private WebElement resetAppStateLink;

    @FindBy(className = "bm-menu-wrap")
    private WebElement menuWrap;

    public NavigationPage(WebDriver driver) {
        super(driver);
    }

    public void openMenu() {
        waitShape.waitForElementClickable(sideMenuButton).click();
    }

    public void closeMenu() {
        waitShape.waitForElementClickable(closeSideMenu).click();
    }

    public void clickAllItems() {
        waitShape.waitForElementClickable(allItemsLink).click();
    }

    public void clickAbout() {
        waitShape.waitForElementClickable(aboutLink).click();
    }

    public String getAboutLinkHref() {
        return waitShape.waitForElementVisible(aboutLink).getAttribute("href");
    }

    public void clickLogout() {
        waitShape.waitForElementClickable(logoutLink).click();
    }

    public void clickResetAppState() {
        waitShape.waitForElementClickable(resetAppStateLink).click();
    }

    public void waitForMenuOpen() {
        waitShape.waitForElementVisible(closeSideMenu);
    }

    public void waitForMenuClosed() {
        waitShape.waitForElementVisible(sideMenuButton);
    }

    public boolean isMenuOpen() {
        return "false".equals(menuWrap.getAttribute("aria-hidden"));
    }
}
