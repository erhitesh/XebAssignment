package pomImp;

import com.aventstack.extentreports.Status;
import io.appium.java_client.MobileBy;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import utils.ExtentManager;

public class RegistrationPage extends BasePage {

    public RegistrationPage(AndroidDriver driver) {
        super(driver);
    }

    public By registrationTxt = MobileBy.xpath("//*[@text='Welcome to register a new User']");
    public By userNameTxt = MobileBy.id("inputUsername");
    public By emailTxt = MobileBy.id("inputEmail");
    public By passwordTxt = MobileBy.id("inputPassword");
    public By nameTxt = MobileBy.id("inputName");
    public By programmingLanguageDropdown = MobileBy.id("input_preferedProgrammingLanguage");
    public By getProgrammingLanguageAlertTitle = MobileBy.id("android:id/alertTitle");
    String plOption = "//*[@resource-id='android:id/select_dialog_listview']/*[@text='plOption']";
    public By plSelectedText = MobileBy.xpath("//*[@resource-id='android:id/text1']");
    public By tandCCheckBox = MobileBy.id("input_adds");
    public By registerBtn = MobileBy.id("btnRegisterUser");

    public RegistrationConfirmationPage doRegistration(String name, String userName, String email, String password, String pL, String tAndC) {
        logger.info("Do the User Registration.....");
        ExtentManager.logs(Status.INFO, "Do the User Registration.....");
        if (isElementPresent(registrationTxt)) {
            logger.info("Welcome to register a new User Page title");
            ExtentManager.logs(Status.INFO, "Welcome to register a new User Page title");
            logger.info("Enter Required info as needed for registration...");
            ExtentManager.logs(Status.INFO, "Enter Required info as needed for registration...");
            sendText(userNameTxt, userName);
            sendText(emailTxt, email);
            sendText(passwordTxt, password);
            sendText(nameTxt, name);
            click(programmingLanguageDropdown);
            By newPl = MobileBy.xpath(plOption.replace("plOption", pL));
            if (!waitAndFindElement(newPl).isSelected())
                click(newPl);
            String selectedPL = getText(plSelectedText);
            assertEquals(selectedPL, pL);
            if (!waitAndFindElement(tandCCheckBox).isSelected())
                click(tandCCheckBox);
            click(registerBtn);
            logger.info("Register User Button click successfully....");
        } else {
            logger.error("Welcome to register a new User Page title not found");
            ExtentManager.logs(Status.FAIL, "Welcome to register a new User Page title not found");
        }
        return new RegistrationConfirmationPage(driver);
    }
}
