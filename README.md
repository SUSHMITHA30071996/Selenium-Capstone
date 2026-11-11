# SauceDemo Selenium Test Automation Framework

A test automation framework built with Selenium WebDriver, Java, Maven, and TestNG to test the [SauceDemo](https://www.saucedemo.com) e-commerce application.

## Prerequisites

- **JDK 11 or later** installed with `JAVA_HOME` set
- **Maven 3.6+** installed and available on PATH
- **Google Chrome** browser installed
- **Git** for version control

## Project Structure

```
saucedemo-selenium/
├── pom.xml                          # Maven project config and dependencies
├── testng.xml                       # TestNG suite configuration
├── src/
│   ├── main/java/com/saucedemo/
│   │   ├── pages/                   # Page Object Model classes
│   │   │   ├── LoginPage.java
│   │   │   ├── ProductsPage.java
│   │   │   ├── ProductDetailPage.java
│   │   │   ├── CartPage.java
│   │   │   ├── CheckoutStepOnePage.java
│   │   │   ├── CheckoutStepTwoPage.java
│   │   │   ├── CheckoutCompletePage.java
│   │   │   └── NavigationPage.java
│   │   └── utils/                   # Utility/helper classes
│   │       ├── ConfigurationReader.java
│   │       ├── WaitShape.java
│   │       └── Screenshot.java
│   └── test/
│       ├── java/com/saucedemo/tests/  # Test classes
│       │   ├── BaseTest.java
│       │   ├── TestListener.java
│       │   ├── LoginTest.java
│       │   ├── ProductListingTest.java
│       │   ├── ProductDetailTest.java
│       │   ├── CartTest.java
│       │   ├── CheckoutTest.java
│       │   ├── NavigationTest.java
│       │   └── EndToEndTest.java
│       └── resources/
│           └── config.properties    # Configuration file
├── screenshots/                     # Failure screenshots (auto-generated)
└── reports/                         # Extent HTML reports (auto-generated)
```

## How to Run

### Run all tests (full suite)
```bash
mvn clean test
```

### Run only smoke tests
```bash
mvn clean test -Dgroups=smoke
```

### Run a specific test class
```bash
mvn clean test -Dtest=LoginTest
```

### Run using testng.xml
```bash
mvn clean test -DsuiteXmlFile=testng.xml
```

## Test Modules

| Module           | Test Class             | Scenarios Covered                                             |
|------------------|------------------------|---------------------------------------------------------------|
| Login            | LoginTest              | Valid login, invalid password, empty fields, locked-out user  |
| Product Listing  | ProductListingTest     | Product count, sort A-Z, Z-A, price low-high, price high-low |
| Product Details  | ProductDetailTest      | Detail navigation, info matching, add to cart, back button    |
| Shopping Cart    | CartTest               | Add single/multiple items, remove, verify details, continue   |
| Checkout         | CheckoutTest           | Full checkout, empty form, missing fields, price totals       |
| Navigation       | NavigationTest         | Menu open/close, All Items, About, Logout, Reset App State    |
| End-to-End       | EndToEndTest           | Login -> add items -> cart -> checkout -> confirmation         |

## Test Reports

After running the suite, the Extent HTML report is generated at:
```
reports/TestReport.html
```
Open this file in a browser to view pass/fail/skip counts and failure screenshots.

## Configuration

Test settings are in `src/test/resources/config.properties`:

| Property           | Description                  | Default                        |
|--------------------|------------------------------|--------------------------------|
| browser            | Browser to use               | chrome                         |
| base.url           | Application URL              | https://www.saucedemo.com      |
| standard.username  | Default test username        | standard_user                  |
| standard.password  | Default test password        | secret_sauce                   |
| implicit.wait      | Implicit wait in seconds     | 10                             |
| explicit.wait      | Explicit wait in seconds     | 10                             |
| page.load.timeout  | Page load timeout in seconds | 30                             |

## Key Design Decisions

- **Page Object Model (POM)**: Each page has its own class with locators and methods. Test classes never use locators directly.
- **Explicit Waits**: All element interactions use explicit waits via `WaitShape`. No `Thread.sleep()` in production code (only used for menu animations in navigation tests).
- **Config-Driven**: URLs, credentials, and timeouts come from `config.properties`, not hardcoded in tests.
- **Screenshot on Failure**: The `TestListener` automatically captures and attaches screenshots when a test fails.
- **Extent Reports**: HTML reports with pass/fail/skip counts are generated after every run.
