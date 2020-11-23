package pomImp;

import com.aventstack.extentreports.Status;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.ExtentManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.logging.LogManager;

public class BasePage {
    protected AndroidDriver driver;
    protected WebDriverWait wait;
    public static Logger logger;
    private static final int TIMEOUT = 2;
    private static final int POLLING = 200;

    protected BasePage(AndroidDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, TIMEOUT, POLLING);
        logger = Logger.getLogger("QA-Automation");
        PropertyConfigurator.configure("Log4j.properties");
    }

    public String getPageTitle(By locator) {
        return waitAndFindElement(locator).getText();
    }

    /**
     * This method is used to get WebElement
     * @param by : locator type
     * @return : Webelement
     */
    protected WebElement getAndroidElement(By by) {
        WebElement element = null;
        element = driver.findElement(by);

        return element;
    }

    /**
     * his method is used to get list of Webelement
     * @param by : locator type
     * @return : list of Webelement
     */
    protected List<AndroidElement> getAndroidListElement(By by) {
        List<AndroidElement> elementList = null;
        elementList = (List<AndroidElement>) driver.findElements(by);

        return elementList;
    }

    /**
     * This method is used for wait for element to be visible
     * @param by locator value
     */
    protected void waitVisibility(By by) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    /** This method is used to halt execution for specified number of seconds
     * @param timeoutsInSeconds : integer value
     */
    protected void sleep(int timeoutsInSeconds) {
        try {
            Thread.sleep(1000 * timeoutsInSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is used to wait and click
     * @param by locator type
     */
    protected void waitAndClick(By by) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by)).click();
    }

    /**
     * This method is used to click
     * @param by locator type
     */
    protected void click(By by) {
        waitVisibility(by);
        driver.findElement(by).click();
    }

    /**
     * This method is used to get the status of radio button
     * @param by locator type
     * @return boolean value
     */
    protected boolean getRadioBtnStatus(By by) {
        boolean status = false;
        sleep(2);
        try {
            if (driver.findElement(by).isSelected())
                status = true;
        } catch (Exception e) {
        }

        return status;
    }

    /**
     * This method is used to check element is present or not
     * @param by locator type
     * @return boolean value
     */
    protected boolean isElementPresent(By by) {
        boolean elementStatus = false;
        sleep(2);
        try {
            elementStatus = driver.findElement(by).isDisplayed();
        } catch (Exception e) {
            elementStatus = false;
        }

        return elementStatus;
    }

    /**
     * This method is used to wait and find the element
     * @param by locator type
     * @return WebElement
     */
    protected WebElement waitAndFindElement(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    /** This method is used to wait and find out the list of element
     * @param by locator type
     * @return List of Webelement
     */
    protected List<WebElement> waitAndFindElements(By by) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }

    /** This method is used to get the text
     * @param by locator type
     * @return String value
     */
    protected String getText(By by) {
        return waitAndFindElement(by).getText();
    }

    /** This method is used to get the text from element
     * @param element Webelement
     * @return String value
     */
    protected String getText(AndroidElement element) {
        return element.getText();
    }

    /** This method is used to send the value to textbox
     * @param by      locator type
     * @param message String msg
     */
    protected void sendText(By by, String message) {
        waitAndFindElement(by).clear();
        waitAndFindElement(by).sendKeys(message);
    }

    public void submitEnter() {
        driver.getKeyboard().sendKeys(Keys.ENTER);
    }

    public void clearTextField(By by) {
        waitAndFindElement(by).clear();
    }

    /** This method is used to check the assert condition
     * @param status boolean value
     * @param msg    String message
     */
    protected void assertTrue(boolean status, String msg) {
        Assert.assertTrue(status, "Condition is " + msg + " true");
    }

    /** This method is used to check the assert equal value
     * @param actual   String actual value
     * @param expected String expected value
     */
    protected void assertEquals(String actual, String expected) {
        Assert.assertEquals(actual, expected, actual + " and " + expected + " text are not matched");
    }

    /** This method is used to check the assert equal value
     * @param actual   String actual value
     * @param expected String expected value
     * @param msg      String message
     */
    protected void assertEquals(String actual, String expected, String msg) {
        Assert.assertEquals(actual, expected, actual + " and " + expected + " " + msg + " are not matched");
    }

    protected void contains(String actual, String expected, String msg) {
        Assert.assertTrue(actual.contains(expected), actual + " and " + expected + " " + msg + " are not matched");
    }

    protected void containsCon(String actual, String expected, String msg) {
        if (actual.contains(expected)) {
            logger.info(actual + " & " + expected + " " + msg + " are matched");
            ExtentManager.logs(Status.PASS, actual + " & " + expected + " " + msg + " are matched");
        }
        else {
            logger.error(actual + " & " + expected + " " + msg + " are not matched");
            ExtentManager.logs(Status.FAIL, actual + " & " + expected + " " + msg + " are not matched");
        }
    }

    /**
     * This method is used to check the assert equal value
     * @param actual   int actual value
     * @param expected int expected value
     * @param msg      String message
     */
    protected void assertEquals(int actual, int expected, String msg) {
        Assert.assertEquals(actual, expected, actual + " and " + expected + " " + msg + " are not matched");
    }

    /**
     *
     * This method is used to check how many times you want to swipe..
     * @param numberOfTimesSwipe : integer value
     * @param swipeDirection: String value either up & down
     */
    protected void swipe(int numberOfTimesSwipe, String swipeDirection) {
        int counter = 0;
        int startX = 0, endX = 0;
        int startY = 0, endY = 0;
        Dimension dimension = driver.manage().window().getSize();
        startX = dimension.getWidth() / 2;
        endX = startX;

        if (swipeDirection.equalsIgnoreCase("up")) {
            startY = (int) (dimension.getHeight() * .60);
            endY = (int) (dimension.getHeight() * .20);
        } else if (swipeDirection.equalsIgnoreCase("down")) {
            startY = (int) (dimension.getHeight() * .20);
            endY = (int) (dimension.getHeight() * .60);
        }

        TouchAction action = new TouchAction(driver);
        while (numberOfTimesSwipe > counter) {
            action.press(PointOption.point(startX, startY)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(endX, endY)).release().perform();
            sleep(2);
            counter++;
        }
    }

    public static String getToastMessageText(String fileLocation) {
        String message = "";
        ITesseract instance;
        try {
            instance = new Tesseract();
            instance.setDatapath("/usr/local/share/tessdata");
            message = instance.doOCR(new File(fileLocation));
        } catch (TesseractException e) {
            e.printStackTrace();
        }

        return message;
    }
}
