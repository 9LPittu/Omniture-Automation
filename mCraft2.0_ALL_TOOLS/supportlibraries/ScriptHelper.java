package supportlibraries;

import io.appium.java_client.AppiumDriver;

import org.openqa.selenium.WebDriver;

import com.cognizant.framework.CraftliteDataTable;
import com.perfectomobile.selenium.api.IMobileWebDriver;


/**
 * Wrapper class for common framework objects, to be used across the entire test case and dependent libraries
 * @author Cognizant
 */
public class ScriptHelper
{
	private final CraftliteDataTable dataTable;
	private final SeleniumReport report;
	private  AppiumDriver appiumdriver;
	private  WebDriver webdriver;
	private  IMobileWebDriver perfectodriver;
	
	/**
	 * Constructor to initialize all the objects wrapped by the {@link ScriptHelper} class
	 * @param dataTable The {@link CraftliteDataTable} object
	 * @param report The {@link SeleniumReport} object
	 * @param appiumdriver The {@link WebDriver} object
	 */
	public ScriptHelper(CraftliteDataTable dataTable, SeleniumReport report, AppiumDriver appiumdriver)
	{
		this.dataTable = dataTable;
		this.report = report;
		this.appiumdriver = appiumdriver;
	}
	
	public ScriptHelper(CraftliteDataTable dataTable, SeleniumReport report, WebDriver webdriver)
	{
		this.dataTable = dataTable;
		this.report = report;
		this.webdriver = webdriver;
	}
	
	public ScriptHelper(CraftliteDataTable dataTable, SeleniumReport report, IMobileWebDriver perfectodriver)
	{
		this.dataTable = dataTable;
		this.report = report;
		this.perfectodriver = perfectodriver;
	}
	
	/**
	 * Function to get the {@link CraftliteDataTable} object
	 * @return The {@link CraftliteDataTable} object
	 */
	public CraftliteDataTable getDataTable()
	{
		return dataTable;
	}
	
	/**
	 * Function to get the {@link SeleniumReport} object
	 * @return The {@link SeleniumReport} object
	 */
	public SeleniumReport getReport()
	{
		return report;
	}
	
	/**
	 * Function to get the {@link WebDriver} object
	 * @return The {@link WebDriver} object
	 */
	public AppiumDriver getAppiumDriver()
	{
		return appiumdriver;
	}
	
	public WebDriver getWebDriver()
	{
		return webdriver;
	}
	
	public IMobileWebDriver getPerfectoDriver()
	{
		return perfectodriver;
	}
}