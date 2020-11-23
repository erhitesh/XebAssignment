package pomImp;

import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;

public class RegistrationConfirmationPage extends BasePage {

    public RegistrationConfirmationPage(AndroidDriver driver) {
        super(driver);
    }

    public By userConfirmationPageTxt = MobileBy.xpath("//*[@text='Verify user']");
    public By nameTxt = MobileBy.id("label_name_data");
    public By userNameTxt = MobileBy.id("label_username_data");
    public By password = MobileBy.id("label_password_data");
    public By emailTxt = MobileBy.id("label_email_data");
    public By programmingLanguageTxt = MobileBy.id("label_preferedProgrammingLanguage_data");
    public By tAndCTxt = MobileBy.id("label_acceptAdds_data");
    By registerUserBtn = MobileBy.id("buttonRegisterUser");

    public String getNameText()
    {
        return waitAndFindElement(nameTxt).getText();
    }

    public String getUserNameText()
    {
        return waitAndFindElement(userNameTxt).getText();
    }

    public String getPasswordText()
    {
        return waitAndFindElement(password).getText();
    }

    public String getEmailText()
    {
        return waitAndFindElement(emailTxt).getText();
    }

    public String getPL()
    {
        return waitAndFindElement(programmingLanguageTxt).getText();
    }

    public String getTAndCStatus()
    {
        return waitAndFindElement(tAndCTxt).getText();
    }


    public HomePage clickOnRegisterUser()
    {
        click(registerUserBtn);
        return new HomePage(driver);
    }

    public RegistrationConfirmationPage validateRegisterUserInfo(String name, String userName, String password, String email, String pl, String tAndC) {
        assertEquals(getPageTitle(userConfirmationPageTxt), "Verify user");
        assertEquals(getNameText(), name);
        assertEquals(getUserNameText(), userName);
        assertEquals(getPasswordText(), password);
        assertEquals(getEmailText(), email);
        assertEquals(getPL(), pl);
        assertEquals(getTAndCStatus(), tAndC);
        return new RegistrationConfirmationPage(driver);
    }
}
