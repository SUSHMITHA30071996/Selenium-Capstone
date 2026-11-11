// ABOUTME: TestNG listener that generates Extent Reports and captures screenshots on failure.
// ABOUTME: Hooks into test lifecycle events to log results and attach failure screenshots.
package com.saucedemo.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.saucedemo.utils.Screenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.lang.reflect.Field;

public class TestListener implements ITestListener, ISuiteListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

    @Override
    public void onStart(ISuite suite) {
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("reports/TestReport.html");
        sparkReporter.config().setDocumentTitle("SauceDemo Test Report");
        sparkReporter.config().setReportName("Automation Test Results");

        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        extent.setSystemInfo("Application", "SauceDemo");
        extent.setSystemInfo("Browser", "Chrome");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getMethodName());
        test.set(extentTest);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        test.get().log(Status.PASS, "Test passed: " + result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().log(Status.FAIL, "Test failed: " + result.getMethod().getMethodName());
        test.get().log(Status.FAIL, "Error: " + result.getThrowable().getMessage());

        // Capture screenshot on failure
        WebDriver driver = getDriverFromTestInstance(result);
        if (driver != null) {
            String screenshotPath = Screenshot.captureScreenshot(driver, result.getMethod().getMethodName());
            test.get().addScreenCaptureFromPath("../" + screenshotPath.replace("\\", "/"));
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().log(Status.SKIP, "Test skipped: " + result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ISuite suite) {
        extent.flush();
        try {
            String reportPath = new File("reports/TestReport.html").getAbsolutePath();
            Runtime.getRuntime().exec(new String[]{"cmd", "/c", "start", "", reportPath});
        } catch (Exception e) {
            System.out.println("Could not open report in browser: " + e.getMessage());
        }
    }

    // Gets the WebDriver instance from the test class using reflection
    private WebDriver getDriverFromTestInstance(ITestResult result) {
        try {
            Object testInstance = result.getInstance();
            Field driverField = testInstance.getClass().getSuperclass().getDeclaredField("driver");
            driverField.setAccessible(true);
            return (WebDriver) driverField.get(testInstance);
        } catch (Exception e) {
            System.out.println("Could not get driver for screenshot");
            return null;
        }
    }
}
