package framework.testBase;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import framework.dataSource.DataSource;
import framework.dataSource.ExcelRead;
import framework.waitHelper.WaitHelper;

/**
 * 
 * @author rohit_roy
 *
 */
public class TestBase extends WaitHelper {

	private static String screenShotPath = "./ScreenShots/";

	public static String getScreenShotPath() {
		return screenShotPath;
	}

	public static void setScreenShotPath(String screenShotPath) {
		TestBase.screenShotPath = screenShotPath;
	}

	/**
	 * This method is for navigating to the specified URL. Also, sets an implicit
	 * wait for the driver for 10 Seconds. It Sets a pageLoad time of 60 Seconds.
	 * 
	 * @param url
	 */
	protected void navigateToURL(String url) {
		driver.manage().timeouts().implicitlyWait(DataSource.getImplicitWait(), TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(DataSource.getPageLoadTime(), TimeUnit.SECONDS);
		System.out.println("Navigating to URL: " + url);
		driver.navigate().to(url);
		System.out.println("Page Title: " + driver.getTitle());
	}

	/**
	 * This method is to switch the frame from the parent frame. First it switches
	 * to the parent frame then switches to the subFrame.
	 * 
	 * @param frameName
	 */
	public static void switchToFrameFromParent(String frameName) {
		driver.switchTo().parentFrame();
		driver.switchTo().frame(frameName);
	}

	/**
	 * This method directly switches to the required frame.
	 * 
	 * @param frameName
	 */
	public static void switchToFrameFromCurrent(String frameName) {
		driver.switchTo().frame(frameName);
	}

	/**
	 * This method is for highlighting the WebElement.
	 * 
	 * @param webElement
	 */
	public static void highLight(WebElement webElement) {
		if (driver instanceof JavascriptExecutor) {
			((JavascriptExecutor) driver)
					.executeScript("arguments[0].setAttribute('style', 'border: 2px solid black;');", webElement);
		}
	}

	/**
	 * This method closes the current instance of the browser. Even after invoking
	 * this method browser may be running in the system tasks/services.
	 */
	protected void closeCurrentBrowser() {
		driver.close();
	}

	/**
	 * It closes all the browser instances related to the driver. It also cleans the
	 * browser overhead in the memory.
	 */
	protected void closeAllBrowser() {
		driver.quit();
	}

	/**
	 * This method is to Scroll down to the page.
	 */
	public static void ScrollDownToPage() {
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}

	/**
	 * This method is to scroll down to a particular WebElement using its By
	 * locator.
	 * 
	 * @param locator
	 */
	public static void scrollIntoView(By locator) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(locator));
	}

	/**
	 * This method scrolls down the page to a specific webElement.
	 * 
	 * @param webElement
	 */
	public static void scrollIntoView(WebElement webElement) {
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", webElement);
	}

	/**
	 * This method is for reading the data from the excel file for a particular
	 * testCaseId.
	 * 
	 * @param testCaseId
	 * @param excelPath
	 * @return
	 */
	public static HashMap<String, String> getDataFromExcel(String testCaseId,int sheetNumber, String excelPath) {
		return new ExcelRead().getData(testCaseId,sheetNumber, excelPath);
	}

	/**
	 * This method takes a full page screenshot. It takes the name of the image you
	 * want to save it with as an argument.
	 * 
	 * @param ssName
	 */
	public static void takeScreenShot(String ssName) {

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		String actualImageName = screenShotPath + ssName + "_" + formater.format(calendar.getTime()) + ".png";

		// highLight(driver.findElement(By.xpath("//html")));
		TakesScreenshot screenDriver = (TakesScreenshot) driver;
		File image = screenDriver.getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(image, new File(actualImageName));
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * This method is to take screenshot of only a specific web element under
	 * consideration.
	 * 
	 * @param element
	 * @param ssName
	 * @throws IOException
	 */
	public static void takeScreenShot(WebElement element, String ssName) throws IOException {

		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");

		String actualImageName = screenShotPath + ssName + "_" + formater.format(calendar.getTime()) + ".png";

		Point point = element.getLocation();
		int eleWidth = element.getSize().getWidth();
		int eleHeight = element.getSize().getHeight();

		highLight(element);

		TakesScreenshot screenDriver = (TakesScreenshot) driver;
		File image = screenDriver.getScreenshotAs(OutputType.FILE);
		BufferedImage fullImg = ImageIO.read(image);

		BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);

		ImageIO.write(eleScreenshot, "png", image);

		FileUtils.copyFile(image, new File(actualImageName));
	}

	/**
	 * This method is for normal drag and drop operation from a source to the target
	 * element.
	 * 
	 * @param source
	 * @param target
	 */

	public static void dragAndDrop(By sourceLocator, By targetLocator) {
		Actions act = new Actions(driver);
		act.dragAndDrop(driver.findElement(sourceLocator), driver.findElement(targetLocator)).build().perform();
	}

	/**
	 * This method is for normal drag and drop operation from a source to the target
	 * element.
	 * 
	 * @param source
	 * @param target
	 */
	public static void dragAndDrop(WebElement source, WebElement target) {
		Actions act = new Actions(driver);
		act.dragAndDrop(source, target).build().perform();
	}

	/**
	 * This method does a double click operation.
	 * 
	 * @param by
	 */
	public static void doubleClick(By locator) {
		Actions act = new Actions(driver);
		act.doubleClick(driver.findElement(locator));
		act.build().perform();

	}

	/**
	 * This method does a double click operation on a given WebElement.
	 * 
	 * @param element
	 */
	public static void doubleClick(WebElement element) {
		Actions act = new Actions(driver);
		act.doubleClick(element);
		act.build().perform();
	}

	/**
	 * This method is to do a rightClick operation i.e. the contextClick and then
	 * select one of the options given.
	 * 
	 * @param element
	 * @param optionToSelect
	 */
	public static void contextClickAndSelect(By element, String optionToSelect) {
		Actions act = new Actions(driver);
		act.contextClick();
	}

	/**
	 * This method is for mouse hover operation on a specific element.
	 * 
	 * @param locator
	 */
	public static void mouseHover(By elementToHoverOn) {
		Actions act = new Actions(driver);
		act.moveToElement(driver.findElement(elementToHoverOn)).build().perform();
	}

	/**
	 * This method is for mouse hover operation on a specific element.
	 * 
	 * @param element
	 */
	public static void mouseHover(WebElement element) {
		Actions act = new Actions(driver);
		act.moveToElement(element).build().perform();
	}

	/**
	 * This method performs mouse hover on the element and after that it clicks on
	 * it.
	 * 
	 * @param element
	 */
	public static void mouseHoverAndClick(WebElement element) {
		mouseHover(element);
		element.click();
	}

	/**
	 * This method is for safely click on any element using javaScriptExecutor.
	 * 
	 * @param element
	 */
	public static void safeClickJS(WebElement element) {
		try {
			if (element.isEnabled() && element.isDisplayed()) {
				System.out.println("Clicking on element with using java script click");

				((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
			} else {
				System.out.println("Unable to click on element");
			}
		} catch (StaleElementReferenceException e) {
			System.out.println("Element is not attached to the page document " + e.getMessage());
		} catch (NoSuchElementException e) {
			System.out.println("Element was not found in DOM " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Unable to click on element " + e.getMessage());
		}
	}

	/**
	 * This method Asserts the text of given webElement with its expected text
	 * value. It Checks for the exact match of the text.
	 * 
	 * @param element
	 * @param expectedText
	 */
	public static void validateTextStrictly(WebElement element, String expectedText) {
		if (element != null) {
			highLight(element);
			Assert.assertTrue(element.getText().equals(expectedText));
		}
	}

	/**
	 * This method Asserts the text of given webElement with its expected text
	 * value. It Checks for the exact match of the text.
	 * 
	 * @param element
	 * @param expectedText
	 */
	public static void validateTextLoose(WebElement element, String expectedText) {
		if (element != null) {
			highLight(element);
			Assert.assertTrue(element.getText().contains(expectedText));
		}
	}

	/**
	 * This method asserts a specific webElement is visible on the page or not.
	 * 
	 * @param element
	 * @param failMessage
	 */
	public static void assertElementVisible(WebElement element, String failMessage) {
		Assert.assertTrue(element.isDisplayed(), failMessage);
		highLight(element);
	}

	/**
	 * This method generates and returns a random String of a given length of
	 * characters.
	 * 
	 * @param lenght
	 * @return
	 */
	public static String randomString(int lenght) {
		// For AlphaNumeric Values
		// String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		// For only Letters
		String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		while (salt.length() < lenght) {
			int index = (int) (rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		String saltStr = salt.toString();
		return saltStr;

	}

	/**
	 * This method is for generating an array of random numbers of required length.
	 * 
	 * @param length
	 * @return An Array of random Numbers
	 */
	public static int[] randomNumber(int length) {
		int[] num = new int[length];
		Random rnd = new Random();
		for (int i = 0; i < length; i++) {
			num[i] = rnd.nextInt(10);
		}
		return num;
	}

	/**
	 * This method returns a random number between the provided range.
	 * 
	 * @param lowerRange
	 * @param upperRange
	 * @return
	 */
	public static int randomNumber(int lowerRange, int upperRange) {
		return (new Random().nextInt(upperRange - lowerRange + 1) + lowerRange);
	}

	/**
	 * This method is to handle Alert as per the status passed to it as an argument.
	 * 
	 * @param status
	 */
	public static void handleAlert(String status) {
		Alert objAlert = driver.switchTo().alert();
		if (status.equalsIgnoreCase("Accept")) {
			objAlert.accept();
		} else if (status.equalsIgnoreCase("Dismiss")) {
			objAlert.accept();
		}
	}

	/**
	 * This method is to get the text content of an alert message.
	 * 
	 * @return
	 */
	public static String getAlertText() {
		Alert objAlert = driver.switchTo().alert();
		objAlert = driver.switchTo().alert();
		return objAlert.getText();
	}

	/**
	 * This is a common tearDown method and should be called in the tearDown i.e.
	 * under @afterAnnotation
	 * 
	 * @param result
	 */
	protected void commonTearDown(ITestResult result) {
		System.out.println("Running The TearDown Method");
		try {
			if (result.getStatus() == ITestResult.SUCCESS) {
				System.out.println("passed **********");
			}

			else if (result.getStatus() == ITestResult.FAILURE) {
				System.out.println("Failed ***********");
				ITestNGMethod failedTest = result.getMethod();
				String failedTestMethodName = failedTest.getMethodName();
				System.out.println(failedTestMethodName + "_Failure");
				takeScreenShot(failedTestMethodName + "_Failure");
			}

			else if (result.getStatus() == ITestResult.SKIP) {
				System.out.println("Skiped***********");
			}

			Thread.sleep(2000);

			if (driver != null) {
				System.out.println("Closing the Browser!");
				closeAllBrowser();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	/**
	 * This method returns the path of a particular resource folder under the
	 * project.
	 * 
	 * @param resource
	 * @return
	 */

	public static String getResourcePath(String resource) {
		return getBaseResourcePath() + resource;
	}

	/**
	 * This method will return the project location irrespective of the driver
	 * location.
	 * 
	 * @return
	 */
	public static String getBaseResourcePath() {
		// user.dir will give us project location.
		return System.getProperty("user.dir");
	}

	/**
	 * This method is to get the data from the config.properties files.
	 * 
	 * @param name
	 * @return
	 */
	public static String getData(String name) {
		return DataSource.config.getProperty(name);
	}

	/**
	 * This method return the current window handle name.
	 * 
	 * @return
	 */
	public static String getWindowHandle() {
		return driver.getWindowHandle();
	}

	/**
	 * This method returns a set of all the window handles currently active.
	 * 
	 * @return
	 */
	public static Set<String> getWindowHandles() {
		return driver.getWindowHandles();
	}

	/**
	 * This method is for closing all the windows other than the current parent
	 * window.
	 */
	public static void closeAllWindowsOtherThanParent() {
		String currentWindow = getWindowHandle();
		Set<String> windows = getWindowHandles();

		for (String window : windows) {
			if (currentWindow != window) {
				driver.switchTo().window(window);
				driver.close();
			}
		}

	}

	/**
	 * This method is to switch to a particular window using its handle name.
	 * 
	 * @param windowName
	 */
	public static void switchToWindow(String windowName) {
		if (!getWindowHandle().equals(windowName)) {
			System.out.println("Switching to " + windowName + " window.");
			driver.switchTo().window(windowName);
		}
	}

	/**
	 * This method returns the window handle having the specific Title. It returns
	 * in case there is no such window with that title.
	 * 
	 * @param title
	 * @return
	 */
	public static String getWindowUsingTitle(String title) {
		String currentWindow = getWindowHandle();
		Set<String> windows = getWindowHandles();

		for (String window : windows) {
			if (currentWindow == window && driver.getTitle().equals(title)) {
				return currentWindow;
			} else {
				driver.switchTo().window(window);
				if (driver.getTitle().equals(title)) {
					return window;
				}
			}
		}

		System.out.println("No Window found with Title " + title);
		return null;

	}

	/**
	 * This method is to select the visible text of any dropDown using its locator.
	 * 
	 * @param selectTagLocator
	 * @param textToSelect
	 */
	public static void selectDropdownByVisibleText(By selectTagLocator, String textToSelect) {
		Select options = new Select(driver.findElement(selectTagLocator));
		options.selectByVisibleText(textToSelect);
	}

	/**
	 * This method is to select the visible text of any dropDown using its
	 * WebElement.
	 * 
	 * @param selectTagElement
	 * @param textToSelect
	 */

	public static void selectDropdownByVisibleText(WebElement selectTagElement, String textToSelect) {
		Select options = new Select(selectTagElement);
		options.selectByVisibleText(textToSelect);
	}

	/**
	 * This method is to select any option from dropDown using its value and the
	 * locator of the dropDown.
	 * 
	 * @param selectTagLocator
	 * @param value
	 */

	public static void selectDropdownByValue(By selectTagLocator, String value) {
		Select options = new Select(driver.findElement(selectTagLocator));
		options.selectByValue(value);
	}

	/**
	 * This method is to select any option from dropDown using its value and the
	 * webElement of the dropDown.
	 * 
	 * @param selectTagElement
	 * @param value
	 */
	public static void selectDropdownByValue(WebElement selectTagElement, String value) {
		Select options = new Select(selectTagElement);
		options.selectByValue(value);
	}

	/**
	 * This method is to select an option from dropDown using its index value.
	 * 
	 * @param selectTagLocator
	 * @param index
	 */
	public static void selectDropdownByIndex(By selectTagLocator, int index) {
		Select options = new Select(driver.findElement(selectTagLocator));
		options.selectByIndex(index);
	}

	/**
	 * This method is for selecting an option from dropDown using its index and the
	 * WebElement of the dropDown.
	 * 
	 * @param selectTagElement
	 * @param index
	 */

	public static void selectDropdownByIndex(WebElement selectTagElement, int index) {
		Select options = new Select(selectTagElement);
		options.selectByIndex(index);
	}

}
