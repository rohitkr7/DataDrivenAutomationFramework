package framework.listeners;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

public class TestListeners extends TestListenerAdapter {
	/*
	 * Overridden methods of TestListenerAdapter
	 */
	public void onTestStart(ITestResult result) {
		System.out.println("Test Started.");
	}

	public void onTestSuccess(ITestResult result) {
		System.out.println("Test Success.");
	}

	public void onTestFailure(ITestResult result) {
		System.out.println("Test Failed here.");
	}

	public void onTestSkipped(ITestResult result) {
		System.out.println("Test Skipped.");
	}
}
