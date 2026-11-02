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

    @BeforeMethod
    public void initPages() {
        loginWithDefaultUser();
        productsPage = new ProductsPage(driver);
        productDetailPage = new ProductDetailPage(driver);
    }

    @Test(groups = {"products"})
    public void testNavigateToProductDetail() {
        // Get the first product name from the listing
        List<String> productNames = productsPage.getProductNames();
        String firstProductName = productNames.get(0);

        // Click on the product
        productsPage.clickProductByName(firstProductName);

        // Verify we are on the detail page
        Assert.assertTrue(productDetailPage.isProductDetailPageDisplayed(),
                "Product detail page should be displayed");
    }

    @Test(groups = {"products"})
    public void testProductDetailInfoMatchesListing() {
        // Get product info from listing page
        List<String> listingNames = productsPage.getProductNames();
        List<Double> listingPrices = productsPage.getProductPrices();

        String expectedName = listingNames.get(0);
        String expectedPrice = "$" + String.format("%.2f", listingPrices.get(0));

        // Navigate to detail page
        productsPage.clickProductByName(expectedName);

        // Verify name and price match
        String detailName = productDetailPage.getProductName();
        String detailPrice = productDetailPage.getProductPrice();

        Assert.assertEquals(detailName, expectedName,
                "Product name on detail page should match listing");
        Assert.assertEquals(detailPrice, expectedPrice,
                "Product price on detail page should match listing");
    }

    @Test(groups = {"products"})
    public void testProductDetailHasDescription() {
        List<String> productNames = productsPage.getProductNames();
        productsPage.clickProductByName(productNames.get(0));

        String description = productDetailPage.getProductDescription();
        Assert.assertFalse(description.isEmpty(),
                "Product description should not be empty");
    }

    @Test(groups = {"products"})
    public void testAddToCartFromDetailPage() {
        List<String> productNames = productsPage.getProductNames();
        productsPage.clickProductByName(productNames.get(0));

        productDetailPage.clickAddToCart();

        // Go back and check cart badge
        productDetailPage.clickBackToProducts();
        String cartCount = productsPage.getCartBadgeCount();
        Assert.assertEquals(cartCount, "1",
                "Cart badge should show 1 after adding an item");
    }

    @Test(groups = {"products"})
    public void testBackToProductsFromDetail() {
        List<String> productNames = productsPage.getProductNames();
        productsPage.clickProductByName(productNames.get(0));

        productDetailPage.clickBackToProducts();

        Assert.assertTrue(productsPage.isProductsPageDisplayed(),
                "Should be back on the products page");
    }
}
