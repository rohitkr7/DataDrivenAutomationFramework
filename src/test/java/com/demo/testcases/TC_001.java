package com.demo.testcases;

import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import framework.browser.driver.DriverPathNull;
import framework.testBase.TestBase;


public class TC_001 extends TestBase{
	
	String searchMsg;
	Scanner sc;
	@BeforeMethod
	public void setup() throws DriverPathNull {
		 sc = new Scanner(System.in);
		System.out.println("Hi what do you want to search on google: ");
		searchMsg = sc.nextLine();
		setDriverPath("F:/Selenium_files/drivers/");
		getBrowser("chrome");
		navigateToURL("https://www.google.com");
	}
	@Test
	public void TC_method_001() throws InterruptedException {
		driver.findElement(By.name("q")).sendKeys(searchMsg+Keys.ENTER);
		Thread.sleep(10000);
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) {
		commonTearDown(result);
		sc.close();
	}
}
