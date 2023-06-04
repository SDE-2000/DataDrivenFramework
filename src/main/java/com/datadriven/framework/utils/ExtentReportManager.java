package com.datadriven.framework.utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;

public class ExtentReportManager {

	//public static ExtentHtmlReporter htmlreport;
	public static ExtentReports report;
	
	public static ExtentReports getReportInstance() {
		
		if(report ==null) {
			String reportname =DateAndTime.timeStamp();
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") +"\\test-output\\"+reportname+".html");
			report = new ExtentReports();
			report.attachReporter(htmlReporter);
			
			report.setSystemInfo("OS", "Windows-10");
			report.setSystemInfo("Environment", "UAT");
			report.setSystemInfo("Build Number", "10.08.1");
			report.setSystemInfo("Browser", "chrome");
			
			htmlReporter.config().setDocumentTitle("Automation Results");
			htmlReporter.config().setReportName("All headlines");
			htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			htmlReporter.config().setTimeStampFormat("MMM dd,yyyy HH:mm:ss");
		}
		
		return report;
	}
	
}
