package testCases;

import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.GlobalParam;
import utils.PropertyOperations;

public class AndroidCapability {
	
	public static String appPath = GlobalParam.CURRENT_PROJECT_PATH + GlobalParam.slash + "APPS" + GlobalParam.slash + "selendroid-test-app-0.17.0";

	public static DesiredCapabilities getDesiredCapability(String deviceInfo) {
		DesiredCapabilities cap = null;
		cap = new DesiredCapabilities();
		String systemPort = "";
		String wdaLocalPort = "";
		String str[] = deviceInfo.split(" ");
		String deviceName = str[0];
		String platformName = str[1];
		String platformVersion = str[2];
		if (platformName.equalsIgnoreCase("android"))
			systemPort = str[3];
		else
			wdaLocalPort = str[3];

		if (platformName.equalsIgnoreCase("android")){
			cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.ANDROID_UIAUTOMATOR2);
			cap.setCapability(AndroidMobileCapabilityType.SYSTEM_PORT, Integer.parseInt(systemPort));
			cap.setCapability(MobileCapabilityType.PLATFORM_NAME, Platform.ANDROID);
			cap.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
			cap.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, PropertyOperations.getPropertyData("Xebia", "appPackage"));
			cap.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, PropertyOperations.getPropertyData("Xebia", "appActivity"));
			cap.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
			cap.setCapability(MobileCapabilityType.APP, appPath + ".apk");
			cap.setCapability(MobileCapabilityType.PLATFORM_VERSION, platformVersion);
			cap.setCapability(MobileCapabilityType.NO_RESET, true);
			cap.setCapability("unicodeKeyboard", "true");
			cap.setCapability("resetKeyboard", "true");
		} else if (platformName.equalsIgnoreCase("ios")) {
			cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
		}

		return cap;
	}
}
