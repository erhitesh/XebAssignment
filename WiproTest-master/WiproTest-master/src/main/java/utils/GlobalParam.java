package utils;

import org.openqa.selenium.remote.DesiredCapabilities;


public abstract class GlobalParam {
    public static String APP_NAME = "";
    public static String CURRENT_PROJECT_PATH;
    public static DesiredCapabilities driverCapabilities = null;
    public static String serverIP = "localhost";
    public static int appiumPort;
    public static String slash;

    static {
        CURRENT_PROJECT_PATH = System.getProperty("user.dir");
        slash = System.getProperty("file.separator");
    }
}
