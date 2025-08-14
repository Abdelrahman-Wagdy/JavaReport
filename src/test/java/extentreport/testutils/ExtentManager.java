//package testutils;
//
//import com.aventstack.extentreports.ExtentReports;
//import com.aventstack.extentreports.reporter.ExtentSparkReporter;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//public class ExtentManager {
//
//    public static ExtentReports getInstance() {
//            // Generate a timestamp
//            String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//            String reportPath = "test-output/"+timestamp+"/ExtentReport_" + timestamp + ".html";
//
//            // Initialize ExtentReports
//        ExtentReports extentReports = new ExtentReports();
//
//            // Set up the ExtentSparkReporter
//            ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
//            sparkReporter.config().setReportName("Mobile Automation Results");
//            sparkReporter.config().setDocumentTitle("Test Results");
//
//            // Attach the reporter to ExtentReports
//            extentReports.attachReporter(sparkReporter);
//
//        // Add system information
//        extentReports.setSystemInfo("Executed By", "VOIS QA");
//        return extentReports;
//    }
//}

package testutils;

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
        String reportPath = "test-output/" + timestamp + "_" + testName.replaceAll(" ", "_") + "/ExtentReport.html";

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