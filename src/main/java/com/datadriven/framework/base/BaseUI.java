package com.datadriven.framework.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.datadriven.framework.utils.DateAndTime;
import com.datadriven.framework.utils.ExtentReportManager;


import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseUI {

	public ExtentReports report = ExtentReportManager.getReportInstance();
	public ExtentTest logger;

	public WebDriver driver = null;
	public Properties prop;


	
	/****************Invoke Browser Function****************/

	public void invokebrowser(String browser) {

		try {
			if (browser.equalsIgnoreCase("chrome")) {
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver();
			} else if (browser.equalsIgnoreCase("mozila")) {
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver();
			} else {
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver();
			}
		}catch(Exception e) {
			reportFail(e.getMessage());
		}

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

		
		if (prop == null) {
			prop = new Properties();

			try {
				// Read the properties file
				FileInputStream file = new FileInputStream(System.getProperty("user.dir") + "\\src\\test\\resources\\openRepositeries\\projectConfig.properties");
				
				// Load the properties file
				prop.load(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**************** Open url Function ****************/

	public void openURL(String websiteurlKey) {
		try {
			driver.get(prop.getProperty(websiteurlKey));
			reportPass(websiteurlKey + "Identified Successfully");
		}catch(Exception e) {
			reportFail(e.getMessage());
		}
	}

	/**************** Enter Text Function ****************/

	public void entertext(String xpathKey, String value) {
		try {
			getElement(xpathKey).sendKeys(value);
			reportPass("Entering the Text: " + value);
		}catch(Exception e) {
			reportFail(e.getMessage());
		}
	}

	/**************** Click Function ****************/

	public void click(String path) {
		try {
			getElement(path).click();
			reportPass("The Button clicked: " + path);
		}catch(Exception e) {
			reportFail(e.getMessage());
		}
	}
	/**************** Browser Quit Function ****************/

	public void close() {
		driver.close();
	}
	public void quit() {
		driver.quit();
	}

	/**************** Finding Web element Function ****************/

	public WebElement getElement(String locator) {

		WebElement element = null;
		try {
			if (locator.endsWith("_Xpath")) {
				element = driver.findElement(By.xpath(prop.getProperty(locator)));
				logger.log(Status.INFO, "Locator Identified: " + locator);
			} else if (locator.endsWith("_Id")) {
				element = driver.findElement(By.id(prop.getProperty(locator)));
				logger.log(Status.INFO, "Locator Identified: " + locator);
			} else if (locator.endsWith("_Css")) {
				element = driver.findElement(By.cssSelector(prop.getProperty(locator)));
				logger.log(Status.INFO, "Locator Identified: " + locator);
			} else if (locator.endsWith("_Name")) {
				element = driver.findElement(By.name(prop.getProperty(locator)));
				logger.log(Status.INFO, "Locator Identified: " + locator);
			} else if (locator.endsWith("_ClassName")) {
				element = driver.findElement(By.className(prop.getProperty(locator)));
				logger.log(Status.INFO, "Locator Identified: " + locator);
			} else if (locator.endsWith("_Tag")) {
				element = driver.findElement(By.tagName(prop.getProperty(locator)));
				logger.log(Status.INFO, "Locator Identified: " + locator);
			} else if (locator.endsWith("_LinkTest")) {
				element = driver.findElement(By.linkText(prop.getProperty(locator)));
				logger.log(Status.INFO, "Locator Identified: " + locator);
			}
		}catch(Exception e) {
			reportFail(e.getMessage());
		}

		return element;
	}

	/**************** Report Function ****************/

	public void reportFail(String report) {

		logger.log(Status.FAIL, report);
		ScreenShotOnFailure();
		Assert.fail(report);
	}

	public void reportPass(String report) {

		logger.log(Status.PASS, report);
	}

	/**************** ScreenShot Function ****************/

	public void ScreenShotOnFailure() {
		TakesScreenshot screenshot = (TakesScreenshot) driver;

		File src = screenshot.getScreenshotAs(OutputType.FILE);
		File dest = new File(System.getProperty("user.dir") + "\\screenshot\\" + DateAndTime.timeStamp() + ".png");

		try {
			FileUtils.copyFile(src, dest);
			logger.addScreenCaptureFromPath(
					System.getProperty("user.dir") + "\\screenshot\\" + DateAndTime.timeStamp() + ".png");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
