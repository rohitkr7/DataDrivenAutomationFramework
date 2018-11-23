package fb_gmail_TC;

import java.util.HashMap;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import framework.dataSource.ExcelRead;

public class Fb_Gmail_TC {

	HashMap<String, String> dict = null;

	@BeforeMethod
	public void beforeMethod(String excelPath, int sheetNumber, String testCaseId) {
		dict = new ExcelRead().getData(excelPath, sheetNumber, testCaseId);
	}

	@Test
	@Parameters({ "TestCaseId", "sheetNumber", "excelPath" })
	public void fb_Test(String TestCaseId, String sheetNumber, String excelPath) {
		if (dict != null) {
			for (String element : dict.keySet()) {

				String key = element.toString();
				String value = dict.get(element).toString();
				System.out.println(key + " " + value);

			}
		}
	}

	@AfterMethod
	public void afterMethod() {

	}

}
