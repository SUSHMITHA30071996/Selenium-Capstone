// ABOUTME: Tests for the SauceDemo product listing page.
// ABOUTME: Covers product count verification and all four sorting options.
package com.saucedemo.tests;

import com.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProductListingTest extends BaseTest {

    private ProductsPage productsPage;

    @BeforeMethod(alwaysRun = true)
    public void initPages() {
        loginWithDefaultUser();
        productsPage = new ProductsPage(driver);
    }

    @Test(groups = {"smoke", "products"})
    public void testProductCount() {
        int count = productsPage.getProductCount();
        Assert.assertEquals(count, 6, "There should be 6 products on the page");
    }

    @Test(groups = {"products"})
    public void testSortByNameAtoZ() {
        productsPage.sortBy("az");
        List<String> productNames = productsPage.getProductNames();

        List<String> sortedNames = new ArrayList<>(productNames);
        Collections.sort(sortedNames);

        Assert.assertEquals(productNames, sortedNames,
                "Products should be sorted alphabetically A to Z");
    }

    @Test(groups = {"products"})
    public void testSortByNameZtoA() {
        productsPage.sortBy("za");
        List<String> productNames = productsPage.getProductNames();

        List<String> sortedNames = new ArrayList<>(productNames);
        sortedNames.sort(Collections.reverseOrder());

        Assert.assertEquals(productNames, sortedNames,
                "Products should be sorted alphabetically Z to A");
    }

    @Test(groups = {"products"})
    public void testSortByPriceLowToHigh() {
        productsPage.sortBy("lohi");
        List<Double> prices = productsPage.getProductPrices();

        List<Double> sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices);

        Assert.assertEquals(prices, sortedPrices,
                "Products should be sorted by price low to high");
    }

    @Test(groups = {"products"})
    public void testSortByPriceHighToLow() {
        productsPage.sortBy("hilo");
        List<Double> prices = productsPage.getProductPrices();

        List<Double> sortedPrices = new ArrayList<>(prices);
        sortedPrices.sort(Collections.reverseOrder());

        Assert.assertEquals(prices, sortedPrices,
                "Products should be sorted by price high to low");
    }

    @Test(groups = {"products"})
    public void testProductsPageTitle() {
        String title = productsPage.getPageTitle();
        Assert.assertEquals(title, "Products", "Page title should be 'Products'");
    }
}
