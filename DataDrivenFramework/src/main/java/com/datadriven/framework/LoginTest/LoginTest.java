package com.datadriven.framework.LoginTest;

import org.junit.After;
import org.junit.Test;

import com.datadriven.framework.base.BaseUI;

public class LoginTest extends BaseUI {

	
	@After
	public void endReport() {
		report.flush();
	}
	
	@After
	public void close() {
		driver.quit();
	}
	
	@Test
	public void testOne() {
		
		logger = report.createTest("FaceBook Login");
		
		invokebrowser("chrome");
		openURL("websiteurl");
		entertext("emailId_Xpath", "de.soumya1990@gmail.com");
		entertext("password_Xpath", "abc123");
		
	}
	@Test
	public void testTwo() {
		
		logger = report.createTest("FaceBook Login");
		
		invokebrowser("chrome");
		openURL("websiteurl");
		entertext("emailId_Xpath", "de.soumya1990@gmail.com");
		entertext("password_Xpath", "abc123");
		
	}
	
	
}
