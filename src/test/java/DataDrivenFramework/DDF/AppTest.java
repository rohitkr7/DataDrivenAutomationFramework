package DataDrivenFramework.DDF;

import java.io.IOException;

import org.openqa.selenium.By;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.gmail.pages.Login;

import framework.browser.driver.DriverPathNull;
import framework.testBase.TestBase;
@Listeners(framework.listeners.TestListeners.class)
public class AppTest extends TestBase {

	
	//@Test
	public void gmailTestDemo() throws InterruptedException, IOException {
		
		Login.login("wowfalanadingla@gmail.com","drainroy");
		/*
		waitForElementVisible(By.xpath("//input[@type='email']"), 15).sendKeys("wowfalanadingla@gmail.com");;
		takeScreenShot(waitForElementVisible(By.xpath("//input[@type='email']"), 15), "EmailBox");
		takeScreenShot(driver.findElement(By.xpath("//span[text()='Next']")),"NextButton");
		*/
		Thread.sleep(2000);
		takeScreenShot("AfterLogin");
		Thread.sleep(5000);
		
	}
	
	@Test
	public void gooleHoverTest() {
		//mouseHover(driver.findElement(By.xpath("//div[@id='gs_st0']")));
		mouseHoverAndClick(waitForElementVisible(By.xpath("//div[@id='gs_st0']"), 10));
	}

	@BeforeMethod
	public void setup() throws DriverPathNull {
		setDriverPath("F:/Selenium_files/drivers/");
		setScreenShotPath("./ScreenShots/");
		getBrowser("chrome");
		//navigateToURL("https://www.gmail.com");
		navigateToURL("https://www.google.com");
		takeScreenShot("gmailHome");
	}

	@AfterMethod
	public void tearDown(ITestResult result) {
		commonTearDown(result);
	}

}
