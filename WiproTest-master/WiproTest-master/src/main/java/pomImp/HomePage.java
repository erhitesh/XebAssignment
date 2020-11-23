package pomImp;

import com.aventstack.extentreports.Status;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import utils.ExtentManager;
import utils.RestUtils;

public class HomePage extends BasePage {
    public HomePage(AndroidDriver driver) {
        super(driver);
    }

  public By title = MobileBy.id("android:id/title");
  public By displayToastBtn = MobileBy.id("showToastButton");
  public By displayPopupWindowBtn = MobileBy.id("showPopupWindowButton");
  public By dismissBtn = MobileBy.xpath("//*[contains(@resource-id,'popup_dismiss_button')]");
  public By registrationBtn = MobileBy.id("startUserRegistration");

    /**
     * This method is used to validate whether user landed to home page or not
     */
    public void validateHomePageLoadedSuccessfully() {
        if (isElementPresent(title)) {
            logger.info("Home Page loaded successfully ");
            ExtentManager.logs(Status.PASS, "Home Page loaded successfully ");
        } else {
            logger.error("Home Page not loaded");
            ExtentManager.logs(Status.FAIL, "Home Page not loaded");
        }
    }

    public void validateNewWindowPopup() {
        String popUpWindowTxt = "";
        try {
            waitAndFindElement(displayPopupWindowBtn);
            logger.info("Click on the Display Popup Window Button....");
            click(displayPopupWindowBtn);
            logger.info("Click Successfully on the Display Popup Window Button....");
            ExtentManager.logs(Status.INFO, "Click Successfully on the Window Popup Window");
            popUpWindowTxt = getText(dismissBtn);
            assertEquals(popUpWindowTxt, "Dismiss");
            logger.info("Actual & Expected text match successfull...");
            ExtentManager.logs(Status.PASS, popUpWindowTxt + " & Dismiss text matched successfully...");
            logger.info("Click on the Dismiss Button");
            if (isElementPresent(dismissBtn)){
                logger.info("Dismiss button click successfully");
                ExtentManager.logs(Status.INFO, "Display Popup Window disappear successfully..");
            } else{
                logger.info("Dismiss button not click successfully");
                ExtentManager.logs(Status.FAIL, "Display Popup Window still visible..");
            }
        } catch (Exception e){
            logger.error("Display Popup window operation failed");
            ExtentManager.logs(Status.FAIL, "Display Popup window operation failed");
        }
    }

    public void validateToastMessage() {
        String fileLocation = "";
        waitAndFindElement(displayToastBtn);
        logger.info("Click on the Display a Toast Button....");
        click(displayToastBtn);
        logger.info("Click Successfully on the Display a Toast Button....");
        ExtentManager.logs(Status.INFO, "Click Successfully on Display a Toast Button");
        RestUtils.clearScreenshotFromScreenshotFolder();
        fileLocation = RestUtils.captureScreenshot(driver, "ToastScreenshot");
        //containsCon("Hello selendroid toast!", getToastMessageText(fileLocation), "Toast Message");
        logger.info("Toast Message verified Successfully....");
        ExtentManager.logs(Status.PASS, "Toast Message verified Successfully....");
    }

    public RegistrationPage clickOnRegistrationBtn() {
        logger.info("Click on the Registration Button....");
        ExtentManager.logs(Status.INFO, "Click on the Registration Button....");
        click(registrationBtn);
        logger.info("Registration Button Click Successfully....");
        ExtentManager.logs(Status.INFO, "Registration Button Click Successfully....");

        return new RegistrationPage(driver);
    }
}
