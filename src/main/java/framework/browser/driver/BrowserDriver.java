package framework.browser.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class BrowserDriver {

	public static WebDriver driver = null;

	private static String driverPath = null;

	public static String getDriverPath() {
		return driverPath;
	}

	public static void setDriverPath(String driverPath) {
		BrowserDriver.driverPath = driverPath;
	}

	/**
	 * This method is used to get the desired browser instance
	 * 
	 * @param browser
	 * @return the specified browser driver
	 * @throws DriverPathNull 
	 */

	protected WebDriver getBrowser(String browser) throws DriverPathNull {
		if(driverPath == null) {
			throw new DriverPathNull("Driver Path is not set.");
		}
		if (browser.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", driverPath + "chromedriver.exe");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--disable-notifications");
			driver = new ChromeDriver(options);
		} else if (browser.equalsIgnoreCase("firefox")) {
			System.setProperty("webdriver.firefox.driver", driverPath + "geckodriver.exe");
			driver = new FirefoxDriver();
		} else if (browser.equalsIgnoreCase("ie")) {
			System.setProperty("webdriver.ie.driver", driverPath + "IEDriverServer.exe");
			driver = new InternetExplorerDriver();
		} else if (browser.equalsIgnoreCase("edge")) {
			System.setProperty("webdriver.edge.driver", driverPath + "MicrosoftWebDriver.exe");
			driver = new EdgeDriver();
		}

		driver.manage().window().maximize();
		return driver;
	}

}
