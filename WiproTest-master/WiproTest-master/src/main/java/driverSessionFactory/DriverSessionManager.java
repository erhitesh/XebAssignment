package driverSessionFactory;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

public class DriverSessionManager {
    private static ThreadLocal<AndroidDriver<AndroidElement>> driver = new ThreadLocal<AndroidDriver<AndroidElement>>();
    private static ThreadLocal<ExtentReports> extentReport = new ThreadLocal<ExtentReports>();
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

    public static void setLastExecutionDriver(AndroidDriver wd) {
        driver.set(wd);
    }

    public static AndroidDriver getLastExecutionDriver() {
        return driver.get();
    }

    public static void setExtentReport(ExtentReports report) {
        extentReport.set(report);
    }

    public static ExtentReports getExtentReport() {
        return extentReport.get();
    }

    public static void setExtent(ExtentTest test) {
        extentTest.set(test);
    }

    public static ExtentTest getExtent() {
        return extentTest.get();
    }
}
