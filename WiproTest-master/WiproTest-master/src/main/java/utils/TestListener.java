package utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import driverSessionFactory.DriverSessionManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.IOException;

public class TestListener implements ITestListener, ISuiteListener {

    private static ExtentReports extentReports = ExtentManager.createInstance();

    public static String getExecutingMethodName(ITestResult result) {
        return result.getMethod().getMethodName();
    }

    public static String getExecutingMethodDescription(ITestResult result) {
        return result.getMethod().getDescription();
    }

    public static String getThrowableMessage(ITestResult result) {
        return result.getThrowable().getMessage();
    }

    // Allure staff
    @Attachment
    public byte[] saveScreenshotForAllure(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "{0}", type = "text/plain")
    public static String saveTextLog(String message) {
        return message;
    }

    public void onStart(ITestContext context) {
    }

    public void onFinish(ITestContext context) {
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public void onTestSuccess(ITestResult result) {
        String screenshotPath = "";
        DriverSessionManager.getExtent().log(Status.PASS, MarkupHelper.createLabel(result.getName(), ExtentColor.GREEN));
        try {
            screenshotPath = "data:image/png;base64, " + ((TakesScreenshot) DriverSessionManager.getLastExecutionDriver()).getScreenshotAs(OutputType.BASE64);//RestUtils.captureScreenshot(DriverSessionManager.getLastExecutionDriver(), getExecutingMethodName(result));
            DriverSessionManager.getExtent().pass("Screenshot   ", MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotPath).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        saveScreenshotForAllure(DriverSessionManager.getLastExecutionDriver());
        saveTextLog(getExecutingMethodName(result) + " passed & screenshot taken");
    }

    public void onTestFailure(ITestResult result) {
        DriverSessionManager.getExtent().log(Status.FAIL, MarkupHelper.createLabel(result.getName(), ExtentColor.RED));
        DriverSessionManager.getExtent().log(Status.FAIL, "TC " + result.getName() + " is Failed");
        DriverSessionManager.getExtent().log(Status.FAIL,
                "Test Case Failed because of the reason.....> \n\n " + result.getThrowable());

        try {
            String screenshotPath = "data:image/png;base64, " + ((TakesScreenshot) DriverSessionManager.getLastExecutionDriver()).getScreenshotAs(OutputType.BASE64);//RestUtils.captureScreenshot(DriverSessionManager.getLastExecutionDriver(), getExecutingMethodName(result));
            DriverSessionManager.getExtent().fail("Screenshot for Failed TC : ", MediaEntityBuilder.createScreenCaptureFromBase64String(screenshotPath).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        saveScreenshotForAllure(DriverSessionManager.getLastExecutionDriver());
        saveTextLog(getExecutingMethodName(result) + " failed & screenshot taken");
    }

    public void onTestSkipped(ITestResult result) {
        DriverSessionManager.getExtent().log(Status.SKIP, MarkupHelper.createLabel(result.getName(), ExtentColor.YELLOW));
        DriverSessionManager.getExtent().log(Status.SKIP, "TEST CASE SKIPPED IS " + result.getName());
    }

    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extentReports.createTest(getExecutingMethodName(result),
                getExecutingMethodDescription(result));
        DriverSessionManager.setExtent(extentTest);
    }

    public void onStart(ISuite suite) {
        RestUtils.clearAllureResultFolder();
        RestUtils.clearScreenshotFromScreenshotFolder();
        RestUtils.clearReportsFromReportsFolder();
        DriverSessionManager.setExtentReport(extentReports);
    }

    public void onFinish(ISuite suite) {
        DriverSessionManager.getExtentReport().flush();
    }
}
