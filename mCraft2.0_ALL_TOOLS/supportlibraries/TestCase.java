package supportlibraries;

import io.appium.java_client.AppiumDriver;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import com.cognizant.framework.CraftliteDataTable;
import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.HtmlReport;
import com.cognizant.framework.Settings;
import com.cognizant.framework.Util;
import com.perfectomobile.selenium.api.IMobileWebDriver;


/**
 * Abstract base class for all the test cases to be automated
 * @author Cognizant
 */
public abstract class TestCase extends ResultSummaryManager
{
	//String tempPlatform=testParameters.getBrowser().getValue();
	/**
	 * The {@link SeleniumTestParameters} object to be used to specify the test parameters
	 */
	@SuppressWarnings("rawtypes")
	protected SeleniumTestParameters testParameters;
	/**
	 * The {@link DriverScript} object to be used to execute the required test case
	 */
	protected DriverScript driverScript;
	
	private Date startTime, endTime;
	
	/**
	 * The {@link CraftliteDataTable} object (passed from the Driver script)
	 */
	protected CraftliteDataTable dataTable;
	/**
	 * The {@link SeleniumReport} object (passed from the Driver script)
	 */
	protected SeleniumReport report;
	/**
	 * The {@link WebDriver} object (passed from the Driver script)
	 */
	protected AppiumDriver appiumdriver;
	protected WebDriver webdriver;
	protected IMobileWebDriver perfectodriver;
	/**
	 * The {@link ScriptHelper} object (required for calling one reusable library from another)
	 */
	protected ScriptHelper scriptHelper;
	
	
	/**
	 * Function to initialize the {@link ScriptHelper} object and in turn the objects wrapped by it,
	 * as well as load the {@link Properties} object using the {@link Settings} object
	 * @param scriptHelper The {@link ScriptHelper} object
	 */
	public void initialize(ScriptHelper scriptHelper)
	{
		this.scriptHelper = scriptHelper;
		
		this.dataTable = scriptHelper.getDataTable();
		this.report = scriptHelper.getReport();
		switch(DriverScript.toolName){
		case Appium:
			this.appiumdriver = scriptHelper.getAppiumDriver();
		break;
		case Selenium:
		case Selenium_Desktop:
		case RemoteWebDriver:
		case Others:
			this.webdriver = scriptHelper.getWebDriver();
			break;
		case Perfecto: 
			this.perfectodriver=scriptHelper.getPerfectoDriver();
			break;
			default: break;
		}
		
		
		properties = Settings.getInstance();
	}
	
	@BeforeSuite
	public void suiteSetup(ITestContext testContext)
	{
		setRelativePath();
		initializeTestBatch();
		
		int nThreads;
		if (testContext.getSuite().getParallel().equalsIgnoreCase("false")) {
			nThreads = 1;
		} else {
			nThreads = testContext.getCurrentXmlTest().getThreadCount();
		}
		initializeSummaryReport(testContext.getSuite().getName(), nThreads);
		
		try {
			setupErrorLog();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while setting up the Error log!");
		}
	}
	
	@SuppressWarnings("rawtypes")
	@BeforeMethod
	public void testMethodSetup()
	{
		if(frameworkParameters.getStopExecution()) {
			suiteTearDown();
			
			throw new SkipException("Aborting all subsequent tests!");
		} else {
			startTime = Util.getCurrentTime();
			
			String currentScenario =
					capitalizeFirstLetter(this.getClass().getPackage().getName().substring(12));
			String currentTestcase = this.getClass().getSimpleName();
			
			testParameters = new SeleniumTestParameters(currentScenario, currentTestcase);
		}
	}
	
	private String capitalizeFirstLetter(String myString)
	{
		StringBuilder stringBuilder = new StringBuilder(myString);
		stringBuilder.setCharAt(0, Character.toUpperCase(stringBuilder.charAt(0)));
		return stringBuilder.toString();
	}
	
	/**
	 * Function to do required setup activities before starting the test execution
	 */
	public abstract void setUp();
	
	/**
	 * Function which implements the test case to be automated
	 */
	public abstract void executeTest();
	
	/**
	 * Function to do required teardown activities at the end of the test execution
	 */
	public abstract void tearDown();
	
	@AfterMethod
	public void testMethodTearDown()
	{
		String testStatus = driverScript.getTestStatus();
		endTime = Util.getCurrentTime();
		String executionTime = Util.getTimeDifference(startTime, endTime);
		String passed=String.valueOf(HtmlReport.passed);
		String failed=String.valueOf(HtmlReport.failed);
		summaryReport.updateResultSummary(testParameters.getDeviceName(),testParameters.getCurrentScenario(),
									testParameters.getCurrentTestcase(),
									testParameters.getCurrentTestDescription(),
									executionTime, testStatus,passed,failed);
	}
	
	@AfterSuite
	public void suiteTearDown()
	{
		try {
			wrapUp();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//launchResultSummary();
	}
}