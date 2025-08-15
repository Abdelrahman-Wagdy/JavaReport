package basetest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ExtentManager {
    private static final Map<String, ExtentReports> reportMap = new ConcurrentHashMap<>();

    public static ExtentReports getInstance(String testName) {
        return reportMap.computeIfAbsent(testName, ExtentManager::createReport);
    }

    private static ExtentReports createReport(String testName) {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String reportPath = "extent-report/" + timestamp + "_" + testName.replaceAll(" ", "_") + "/ExtentReport.html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        sparkReporter.config().setReportName("Mobile Automation Results - " + testName);
        sparkReporter.config().setDocumentTitle("Test Results");

        ExtentReports extentReports = new ExtentReports();
        extentReports.attachReporter(sparkReporter);
        extentReports.setSystemInfo("Executed By", "VOIS QA");
        extentReports.setSystemInfo("Device", testName);

        return extentReports;
    }
}