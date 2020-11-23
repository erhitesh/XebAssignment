package testCases;

import com.aventstack.extentreports.Status;
import driverSessionFactory.DriverSessionManager;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pomImp.*;
import utils.ExtentManager;
import utils.RestUtils;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;


public class BaseTest {

    public AndroidDriver driver;
    public WebDriverWait wait;
    protected HomePage homePage = null;
    protected RegistrationPage registrationPage = null;

    @BeforeTest
    @Parameters({"deviceInfo"})
    public void getDriverInstance(String deviceInfo) {
        try {
            driver = new AndroidDriver<AndroidElement>(new URL("http://localhost:4723/wd/hub"),
                    AndroidCapability.getDesiredCapability(deviceInfo));
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
            DriverSessionManager.setLastExecutionDriver(driver);
            wait = new WebDriverWait(DriverSessionManager.getLastExecutionDriver(), 10);
            homePage = new HomePage(DriverSessionManager.getLastExecutionDriver());
            registrationPage = new RegistrationPage(DriverSessionManager.getLastExecutionDriver());
        } catch (MalformedURLException e) {
            e.getMessage();
        }
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        try {
            RestUtils.attachLogToExtentReport(result.getMethod().getConstructorOrMethod().getName());
            if (result.getStatus() == ITestResult.SUCCESS)
                ExtentManager.logs(Status.PASS, result.getMethod().getMethodName());

            else if (result.getStatus() == ITestResult.FAILURE) {
                ExtentManager.logs(Status.FAIL, result.getMethod().getMethodName());
                RestUtils.captureScreenshot(DriverSessionManager.getLastExecutionDriver(), result.getMethod().getMethodName());
            }

        } catch (Exception e) {
            e.printStackTrace();
            if (DriverSessionManager.getLastExecutionDriver() != null)
                DriverSessionManager.setLastExecutionDriver(null);
        }
    }

/*    @AfterSuite
    public void attachLogsToAllureReport(ITestResult result) {
        RestUtils.attachLogToExtentReport(result.getMethod().getMethodName());
    }*/
}
