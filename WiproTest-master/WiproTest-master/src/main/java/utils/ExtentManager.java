package utils;

import com.aventstack.extentreports.AnalysisStrategy;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import driverSessionFactory.DriverSessionManager;
import org.testng.Reporter;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ExtentManager {

	private static ExtentHtmlReporter htmlReporter;
	private static ExtentReports extent;
	private static InetAddress ip;
	
	public static ExtentReports getInstance() {
		if (extent == null)
			extent = createInstance();

		return extent;
	}
	
	public static ExtentReports createInstance() {
		String timeStamp = "";
		String repoName = "";
		try {
			timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss a").format(new Date());
			repoName = "Report" + GlobalParam.slash + "Test-Report-" + timeStamp + ".html";
			
			htmlReporter = new ExtentHtmlReporter(
					GlobalParam.CURRENT_PROJECT_PATH + GlobalParam.slash + "test-output" + GlobalParam.slash + repoName);
			htmlReporter.config().setDocumentTitle(PropertyOperations.getPropertyData("Report", "ReportName"));
			htmlReporter.config().setReportName(PropertyOperations.getPropertyData("Report", "ReportDocumentTitle"));
			htmlReporter.config().setEncoding("UTF-8");
			htmlReporter.config().setTheme(Theme.STANDARD);
			htmlReporter.config().setTheme(Theme.DARK);
			htmlReporter.config().setTimeStampFormat("MM/dd/yyyy hh:mm:ss a");
			htmlReporter.config().setAutoCreateRelativePathMedia(true);
			
			ip = InetAddress.getLocalHost();
			extent = new ExtentReports();
			extent.attachReporter(htmlReporter);
			extent.setSystemInfo("OS", System.getProperty("os.name").toUpperCase());
			extent.setSystemInfo("Host Name", ip.getHostName());
			extent.setSystemInfo("IP", ip.toString());
			extent.setSystemInfo("User Name", System.getProperty("user.name"));
			extent.setSystemInfo("Environment", PropertyOperations.getPropertyData("Report", "Environment"));
			extent.setAnalysisStrategy(AnalysisStrategy.TEST);
			extent.setReportUsesManualConfiguration(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return extent;
	}
	
	public static void logs(Status status, String message) {
		switch (status) {
		case INFO:
			DriverSessionManager.getExtent().log(Status.INFO, MarkupHelper.createLabel(message, ExtentColor.GREEN));
			break;
		case PASS:
			DriverSessionManager.getExtent().log(Status.PASS, MarkupHelper.createLabel(message, ExtentColor.GREEN));
			break;
		case FAIL:
			DriverSessionManager.getExtent().log(Status.FAIL, MarkupHelper.createLabel(message, ExtentColor.RED));
			break;
		case SKIP:
			DriverSessionManager.getExtent().log(Status.SKIP, MarkupHelper.createLabel(message, ExtentColor.YELLOW));
			break;
		default:
			Reporter.log("Not Found Appropriate operation types...");
		}
	}
	
}
