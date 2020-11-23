package testCases;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.GlobalParam;
import utils.XLUtilis;

public class XebiaTest extends BaseTest {

    @Test(priority = 0, description = "Verify Selendroid test app launched & land successfully....")
    public void verifyAppHomePageTitleLoadingSuccessfully() {
        homePage.validateHomePageLoadedSuccessfully();
    }

    @Test(priority = 1, description = "Validate Registration", dataProvider = "registration")
    public void validateUserRegistration(String name, String userName, String email, String password,  String language, String tAndC) {
        homePage.clickOnRegistrationBtn().doRegistration(name, userName, email, password, language, tAndC)
                .validateRegisterUserInfo(name, userName, password, email, language, tAndC)
                .clickOnRegisterUser().validateHomePageLoadedSuccessfully();
    }

    @Test(priority = 2, description = "Validate Display new Window")
    public void validateNewWindowPopup() {
        homePage.validateNewWindowPopup();
    }

    @Test(priority = 3, description = "Validate Toast Message...")
    public void validateToastMessage() {
        homePage.validateToastMessage();
    }

    // fetching data from excel sheet
    @DataProvider(name="registration")
    public Object[][] dataForRegistration() {
        String filLocation = GlobalParam.CURRENT_PROJECT_PATH + "/ConfigFiles/RegistrationData.xlsx";
        return XLUtilis.getExcelData(filLocation, "Registration");
    }
}
