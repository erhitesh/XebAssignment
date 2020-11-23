package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyOperations {

	public static String getPropertyData(String configFileName, String propertyName) {
		String propertyValue = null;
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(new File(GlobalParam.CURRENT_PROJECT_PATH + GlobalParam.slash + "ConfigFiles"
					+ GlobalParam.slash + configFileName + ".properties")));
			propertyValue = prop.getProperty(propertyName);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return propertyValue;
	}
}
