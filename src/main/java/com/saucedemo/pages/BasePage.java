// ABOUTME: Base class for all page objects, initializing Page Factory and shared utilities.
// ABOUTME: Provides WebDriver and WaitShape access to all page subclasses.
package com.saucedemo.pages;

import com.saucedemo.utils.WaitShape;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {

    protected WebDriver driver;
    protected WaitShape waitShape;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.waitShape = new WaitShape(driver);
        PageFactory.initElements(driver, this);
    }
}
