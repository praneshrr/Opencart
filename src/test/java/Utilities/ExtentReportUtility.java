package Utilities;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import TestCases.Basetest;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtentReportUtility implements ITestListener {

    // ExtentReports variables
    private static ExtentReports extent; //content
    private static ExtentSparkReporter htmlReporter;//ui
    private static ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>(); //status

    // Setting up the report at the start of the test suite
    public void onStart(ITestContext context) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        String reportName = "Test-Report-" + timeStamp + ".html";

        // Initialize HtmlReporter
        htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/test-output/" + reportName);
        htmlReporter.config().setDocumentTitle("Automation Test Report");
        htmlReporter.config().setReportName("Functional Test Report");
        htmlReporter.config().setTheme(Theme.STANDARD);

        // Initialize ExtentReports and attach HtmlReporter
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);

        // Add system information to the report
        extent.setSystemInfo("Host Name", "Localhost");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("User", "Test User");
        
        String os = context.getCurrentXmlTest().getParameter("os");
        extent.setSystemInfo("os", os);
        String browser = context.getCurrentXmlTest().getParameter("browser");
        extent.setSystemInfo("Browser", browser);

        List<String> includedGroups = context.getCurrentXmlTest().getIncludedGroups();
        if (!includedGroups.isEmpty()) {
            extent.setSystemInfo("Groups", includedGroups.toString());
        }

    }

    // Create test logs when a test starts
    public void onTestStart(ITestResult result) {
//    	ExtentTest test1 = extent.createTest(result.getClass().getName());
    	
        ExtentTest test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        extentTest.set(test);
    }

    // Log success when a test passes
    public void onTestSuccess(ITestResult result) {
        extentTest.get().log(Status.PASS, "Test Passed: " + result.getMethod().getMethodName());
    }

    // Log failure and capture exceptions when a test fails
    public void onTestFailure(ITestResult result) {
        extentTest.get().log(Status.FAIL, "Test Failed: " + result.getMethod().getMethodName());
        extentTest.get().log(Status.FAIL, "Error" + result.getThrowable());

        try 
        {
            String imgPath = new Basetest().captureScreen(result.getName());
            extentTest.get().addScreenCaptureFromPath(imgPath);
        } 
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // Optionally, capture screenshots for failed tests
        // String screenshotPath = captureScreenshot(result.getMethod().getMethodName());
        // extentTest.get().addScreenCaptureFromPath(screenshotPath);
    }

    // Log skipped tests
    public void onTestSkipped(ITestResult result) {
        extentTest.get().log(Status.SKIP, "Test Skipped: " + result.getMethod().getMethodName());
    }

    // Flush the report at the end of the test suite
    public void onFinish(ITestContext context) {
        extent.flush();
    }

    // Not commonly used but available for tests with success percentages
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {}

    // Optional method to capture screenshots (if needed)
    /*
    public String captureScreenshot(String methodName) {
        // Implement screenshot capture logic and return path of the image
        return "path/to/screenshot";
    }
    */
}
