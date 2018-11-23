package DataDrivenFramework.DDF;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import framework.testBase.TestBase;

@Listeners(framework.listeners.TestListeners.class)
public class NewTest extends TestBase {
	WebDriver driver = null;

	@Test
	public void fun() {
		// Assert.fail();
		Assert.assertTrue(true);
	}

	@Test
	public void Demo() {
		// Assert.fail();
		System.out.println(getBaseResourcePath());
		System.out.println(getResourcePath("/configFiles/Config.properties"));

		System.out.println("The type of browser given in config file: " + getData("browser"));
	}

	/*
	 * @Test public void firstTestDemo() {
	 * setDriverPath("F:/Selenium_files/drivers/"); driver = getBrowser("chrome");
	 * navigateToURL("https://www.gmail.com"); }
	 */

	@BeforeMethod
	public void setup() {
		/*
		 * setDriverPath("F:/Selenium_files/drivers/");
		 * setScreenShotPath("./ScreenShots/"); driver = getBrowser("chrome");
		 * navigateToURL("https://www.gmail.com");
		 */
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		commonTearDown(result);
	}
}
