// ABOUTME: Captures browser screenshots and saves them with timestamped filenames.
// ABOUTME: Used by the test listener to capture screenshots on test failure.
package com.saucedemo.utils;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Screenshot {

    public static String captureScreenshot(WebDriver driver, String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = testName + "_" + timestamp + ".png";
        String filePath = "screenshots" + File.separator + fileName;

        try {
            // Create screenshots directory if it does not exist
            Path screenshotDir = Paths.get("screenshots");
            if (!Files.exists(screenshotDir)) {
                Files.createDirectories(screenshotDir);
            }

            File source = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File destination = new File(filePath);
            Files.copy(source.toPath(), destination.toPath());

            System.out.println("Screenshot saved: " + filePath);
        } catch (IOException e) {
            System.out.println("Failed to capture screenshot");
            e.printStackTrace();
        }

        return filePath;
    }
}
