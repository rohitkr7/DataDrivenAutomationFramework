package framework.waitHelper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import framework.browser.driver.BrowserDriver;
import framework.dataSource.DataSource;
import framework.testBase.TestBase;

public class WaitHelper extends BrowserDriver {

	/**
	 * This method returns a WebElement, It basically waits for the element until
	 * that element is loaded in the given time.
	 * 
	 * @param by
	 * @param description
	 * @param seconds
	 */
	public static WebElement waitForElementVisible(By by, String description, int seconds) {
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		System.out.println(description);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		TestBase.highLight(element);
		return element;	
	}

	/**
	 * This method returns a WebElement, It basically waits for the element until
	 * that element is loaded in 10 seconds time.
	 * 
	 * @param by
	 * @param description
	 */
	public static WebElement waitForElementVisible(By by, String description) {
		WebDriverWait wait = new WebDriverWait(driver, DataSource.getExplicitWait());
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		TestBase.highLight(element);
		return element;
	}

	/**
	 * This method returns a WebElement, It basically waits for the element until
	 * that element is loaded in the given time.
	 * 
	 * @param by
	 * @param seconds
	 */
	public static WebElement waitForElementVisible(By by, int seconds) {
		System.out.println("waitForElementVisible : " + driver);
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		TestBase.highLight(element);
		return element;
	}
	
	/**
	 * This method returns a WebElement, It basically waits for the element until
	 * that element is loaded in 10 seconds time.
	 * 
	 * @param by
	 * @param description
	 */
	public static WebElement waitForElementVisible(By by) {
		WebDriverWait wait = new WebDriverWait(driver, DataSource.getExplicitWait());
		WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(by));
		TestBase.highLight(element);
		return element;
	}

}
