// ABOUTME: Tests for the SauceDemo product detail page.
// ABOUTME: Covers navigating to detail page, verifying product info, and adding to cart.
package com.saucedemo.tests;

import com.saucedemo.pages.ProductDetailPage;
import com.saucedemo.pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

public class ProductDetailTest extends BaseTest {

    private ProductsPage productsPage;
    private ProductDetailPage productDetailPage;

    @BeforeMethod(alwaysRun = true)
    public void initPages() {
        loginWithDefaultUser();
        productsPage = new ProductsPage(driver);
        productDetailPage = new ProductDetailPage(driver);
    }

    @Test()
    public void testProductDetailInfoMatchesListing() {
        // Get product info from listing page
        List<String> listofProdNames = productsPage.getProductNames();
        List<Double> listingofProdPrices = productsPage.getProductPrices();

        String expectedName = listofProdNames.get(0);
        String expectedPrice = "$" + String.format("%.2f", listingofProdPrices.get(0));

        // Navigate to detail page
        productsPage.clickProductByName(expectedName);

        // Verify name and price match
        String detailName = productDetailPage.getProductName();
        String detailPrice = productDetailPage.getProductPrice();

        Assert.assertEquals(detailName, expectedName);
        Assert.assertEquals(detailPrice, expectedPrice);
    }

    @Test()
    public void testAddToCartFromDetailPage() {
        List<String> productNames = productsPage.getProductNames();
        productsPage.clickProductByName(productNames.get(0));
        productDetailPage.clickAddToCart();

        // Go back and check cart badge
        productDetailPage.clickBackToProducts();
        Assert.assertTrue(productsPage.isProductsPageDisplayed());
        
        //verifying the cart count
        String cartCount = productsPage.getCartCount();
        Assert.assertEquals(cartCount, "1");
    }

   
}
