package utils;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import driverSessionFactory.DriverSessionManager;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class Retry implements IRetryAnalyzer {

	private int count = 1;
	private static int maxCount = 1; // It runs the failed test case 2 times

	@Override
	public boolean retry(ITestResult result) {
		boolean status = false;
		if (!result.isSuccess()) {
			if (count < maxCount) {
				count++;
				result.setStatus(ITestResult.FAILURE);
				status = true;
			}
		} else {
			result.setStatus(ITestResult.SUCCESS);
			status = false;
		}

		return status;
	}

	public void extendReportsFailOperations(ITestResult iTestResult) {
		DriverSessionManager.getExtent().log(Status.FAIL, MarkupHelper
				.createLabel("TC " + iTestResult.getMethod().getMethodName() + " Again failed", ExtentColor.RED));
	}
}
