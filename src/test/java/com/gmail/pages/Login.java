package com.gmail.pages;

import org.openqa.selenium.By;

import static framework.testBase.TestBase.*;

public class Login {

	private static final By email = By.xpath("//input[@type='email']");
	private static final By next = By.xpath("//span[text()='Next']");
	private static final By password = By.xpath("//input[@type='password']");

	/*
	 * public static void setEmail(String emailId) { waitForElementVisible(email,
	 * 20).sendKeys(emailId); }
	 * 
	 * public static void clickNext() { waitForElementVisible(next, 20).click(); }
	 * 
	 * public static void setPassword(String password) {
	 * waitForElementVisible(email, 20).sendKeys(password); }
	 */

	public static void login(String emailId, String pass) {
		waitForElementVisible(email, "Entering the email", 20).sendKeys(emailId);
		waitForElementVisible(next, "Clicking next button", 20).click();
		waitForElementVisible(password, "Entering the password", 20).sendKeys(pass);
		// waitForElementVisible(next,"Clicking next button", 20).click();
		hoverAndClickOnNext();
	}

	public static void hoverAndClickOnNext() {
		mouseHoverAndClick(waitForElementVisible(next, "Clicking next button", 20));
	}

}
