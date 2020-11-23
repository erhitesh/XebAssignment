package utils;

import driverSessionFactory.DriverSessionManager;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.testng.Reporter;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RestUtils {
	private static String reportFolderLocation = GlobalParam.CURRENT_PROJECT_PATH + GlobalParam.slash + "test-output"
			+ GlobalParam.slash + "Report";

	private static String screenshotFolderLocation = GlobalParam.CURRENT_PROJECT_PATH + GlobalParam.slash
			+ "Screenshots";

	private static String allureResultFolderLocation = GlobalParam.CURRENT_PROJECT_PATH + GlobalParam.slash
			+ "allure-results";

	private static String logsFolderLocation = GlobalParam.CURRENT_PROJECT_PATH + GlobalParam.slash
			+ "LogsInfo" + GlobalParam.slash + "Log.txt";

	public static void clearReportsFromReportsFolder() {
		File reportFile = new File(reportFolderLocation);
		if (reportFile.exists()) {
			File[] files = reportFile.listFiles();
			for (File file : files) {
				file.delete();
			}
		}
	}

	public static void clearAllureResultFolder() {
		File reportFile = new File(allureResultFolderLocation);
		if (reportFile.exists()) {
			File[] files = reportFile.listFiles();
			for (File file : files) {
				file.delete();
			}
		}
	}

	public static void clearScreenshotFromScreenshotFolder() {
		File reportFile = new File(screenshotFolderLocation);
		if (reportFile.exists()) {
			File[] files = reportFile.listFiles();
			for (File file : files) {
				file.delete();
			}
		}
	}

	public static String captureScreenshot(WebDriver driver, String testCaseName) {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String screenshotLocation = screenshotFolderLocation + GlobalParam.slash + testCaseName + ".png";		
		File destFile = new File(screenshotLocation);
		try {
			FileUtils.copyFile(srcFile, destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return screenshotLocation;
	}

	public static void clearLogsInfoFolder() {
		File reportFile = new File(logsFolderLocation);
		if (reportFile.exists()) {
			File[] files = reportFile.listFiles();
			for (File file : files) {
				file.delete();
			}
		}
	}

	// Add Logs to Allure Report
	public static void attachLogToExtentReport(String tcName) {
		String fileLocation =
				GlobalParam.CURRENT_PROJECT_PATH + GlobalParam.slash
						+ "LogsInfo" + GlobalParam.slash + "Log.txt";
		FileReader fr = null;
		BufferedReader br = null;
		StringBuilder logs = null;
		try {
			fr = new FileReader(new File(fileLocation));
			br = new BufferedReader(fr);
			String line;
			logs = new StringBuilder();
			while ((line = br.readLine()) != null) {
				logs.append(line+"\n");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Allure.addAttachment(tcName + " Log: ", logs.toString());
	}

	public static void analyzeLogs() {

		LogEntries logEntries = DriverSessionManager.getLastExecutionDriver().manage().logs().get(LogType.CLIENT);
		StringBuilder logs = null;
		if (logEntries != null) {
			logs = new StringBuilder();
			for (LogEntry entry : logEntries) {

/*                logs.append(new SimpleDateFormat("dd-MM-yyyy")
                        .format(new Date(entry.getTimestamp())) + ":"
                        + entry.getLevel() + ":" + entry.getMessage());
                logs.append(System.lineSeparator());*/

				// or below code is also same
				logs.append(new SimpleDateFormat("dd-MM-yyyy").format(new Date(entry.getTimestamp()))).append(":")
						.append(entry.getLevel())
						.append(entry.getMessage()).append("\n");
				System.out.println(logs.toString());
			}
		}
		Allure.addAttachment("Console Log: ", logs.toString());
	}

	@Attachment
	public static String logOutput(List<String> outputList) {
		String output = "";
		for (String ss : outputList)
			output += ss + "";

		return output;
	}
}
