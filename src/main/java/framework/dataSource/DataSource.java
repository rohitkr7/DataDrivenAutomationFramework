package framework.dataSource;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import framework.testBase.TestBase;

public class DataSource {
	public static Properties config;

	long implicitWait;
	long explicitWait;
	long pageLoadTime;

	static {
		config = new Properties();
		File file = new File(TestBase.getResourcePath("/configFiles/Config.properties"));

		try {
			FileInputStream inStream = new FileInputStream(file);
			config.load(inStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String getBrowser() {
		return config.getProperty("browser");
	}

	public static String getUserName() {
		return config.getProperty("userName");
	}

	public static String getPassword() {
		return config.getProperty("password");
	}

	public static String getUrl() {
		return config.getProperty("url");
	}

	public void setUrl(String url) {
	}

	public static long getImplicitWait() {
		return Integer.parseInt(config.getProperty("implicitWait"));
	}

	public static long getExplicitWait() {
		return Integer.parseInt(config.getProperty("explicitWait"));
	}

	public static long getPageLoadTime() {
		return Integer.parseInt(config.getProperty("pageLoadTime"));
	}

}
