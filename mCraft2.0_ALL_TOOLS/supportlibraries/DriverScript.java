package supportlibraries;

import io.appium.java_client.AppiumDriver;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;


import allocator.Allocator;

import com.cognizant.framework.*;
import com.cognizant.framework.ReportThemeFactory.Theme;
import com.perfectomobile.selenium.api.IMobileWebDriver;

import org.apache.commons.io.FileUtils;



/**
 * Driver script class which encapsulates the core logic of the framework
 * @author Cognizant
 */
public class DriverScript
{
	public static int currentIteration;
	public static int IterationNo;
	public static int StartIterationNo;
	public static String currentTestcase;
	private static boolean linkScreenshotsToTestLog = false;
	private TestCase testCase;
	
	private Date startTime, endTime;
	private String timeStamp;
	public static String reportPath;
	private String platform;
	static AppiumDriver appiumdriver;
	static IMobileWebDriver perfectodriver;
	private CraftliteDataTable dataTable;
	private ReportSettings reportSettings;
	private SeleniumReport report;

	public static org.openqa.selenium.WebDriver webdriver;
	private ScriptHelper scriptHelper;
	
	private Properties properties;
	private ExecutionMode executionMode;
	private final FrameworkParameters frameworkParameters =
										FrameworkParameters.getInstance();
	//public static Platform selectedPlatform=null;
	private String testStatus;
	
	@SuppressWarnings("rawtypes")
	public	static SeleniumTestParameters testParameters;
	private String deviceName;	
	
	public static ToolName toolName;
	
	/**
	 * Function to set a Boolean variable indicating whether any screenshots taken should be linked to the test log
	 * @param linkScreenshotsToTestLog
	 */
	public void setLinkScreenshotsToTestLog(Boolean linkScreenshotsToTestLog)
	{
		DriverScript.linkScreenshotsToTestLog = linkScreenshotsToTestLog;
	}

	/**
	 * Function to get the status of the test case executed
	 * @return The test status
	 */
	public String getTestStatus()
	{
		return testStatus;
	}
	
	
	/**
	 * Constructor to initialize the DriverScript
	 */
	@SuppressWarnings("rawtypes")
	public DriverScript(SeleniumTestParameters testParameters)
	{
		this.testParameters = testParameters;
	}
	
	/**
	 * Function to execute the given test case
	 */
	public void driveTestExecution()
	{
		
		startUp();
		initializeTestIterations();
		if(properties.getProperty("ToolName").toString().equals("Appium") && Allocator.ContinuousIntegration.equalsIgnoreCase("No")  )
		{
			/*if(Allocator.isEmulatorFlag)*/		initializeEmulator();
			//initializeWebDriver();
		}
		initializeWebDriver();
		initializeTestReport();
		initializeDatatable();
		initializeTestScript();
		
		try {
			testCase.setUp();
			executeTestIterations();
			testCase.tearDown();
		} catch (FrameworkException fx) {
			exceptionHandler(fx, fx.errorName);
		}  catch (Exception ex) {
			exceptionHandler(ex, "Error");
		}
		
		quitWebDriver();
		//quitEmulator();
		wrapUp();
		System.out.println("Test Case Done");
		//Globals.stopServer();
	}
	
	public static void getListOfTools(){
		if(Allocator.ToolName.equalsIgnoreCase("Appium")){
			toolName = ToolName.valueOf("Appium");			
		}else if(Allocator.ToolName.equalsIgnoreCase("Selenium_Desktop")){
			toolName = ToolName.valueOf("Selenium_Desktop");			
		}else if(Allocator.ToolName.equalsIgnoreCase("Seetest")){
			toolName = ToolName.valueOf("Seetest");			
		}else if(Allocator.ToolName.equalsIgnoreCase("RemoteWebDriver")){
			toolName = ToolName.valueOf("RemoteWebDriver");			
		}else if(Allocator.ToolName.equalsIgnoreCase("Others")){
			toolName = ToolName.valueOf("Others");			
		}else if(Allocator.ToolName.equalsIgnoreCase("Perfecto")){
			toolName = ToolName.valueOf("Perfecto");			
		}else if(Allocator.ToolName.equalsIgnoreCase("MobileLabs")){
			toolName = ToolName.valueOf("MobileLabs");			
		}
	}
	
	private void startUp()
	{
		startTime = Util.getCurrentTime();
		
		properties = Settings.getInstance();
		
		setDefaultTestParameters();
		
		getListOfTools();
	}
	
	private void setDefaultTestParameters()
	{
		if (testParameters.getIterationMode() == null) {
			testParameters.setIterationMode(IterationOptions.RunAllIterations);
		}
		
		/*if (testParameters.getBrowser() == null) {
			testParameters.setBrowser(MobilePlatform.valueOf(properties.getProperty("DefaultBrowser")));
		}
		
		if (testParameters.getPlatform() == null) {
			testParameters.setPlatform(Platform.valueOf(properties.getProperty("DefaultPlatform")));
		}*/
	}
	
	private void initializeTestIterations()
	{
		System.out.println("TestPlatform: "+testParameters.getPlatform());
		switch(testParameters.getIterationMode()) {
		case RunAllIterations:
			String datatablePath = frameworkParameters.getRelativePath() +
									Util.getFileSeparator() + "Datatables";
			ExcelDataAccess testDataAccess =
					new ExcelDataAccess(datatablePath, testParameters.getCurrentScenario());
			testDataAccess.setDatasheetName(properties.getProperty("DefaultDataSheet"));
			int nIterations = testDataAccess.getRowCount(testParameters.getCurrentTestcase(), 0);
			
			testParameters.setEndIteration(nIterations);
			
			currentIteration = 1;
			StartIterationNo =1;
			break;
			
		case RunOneIterationOnly:
			currentIteration = 1;
			StartIterationNo =1;
			
			break;
			
		case RunRangeOfIterations:
			if(testParameters.getStartIteration() > testParameters.getEndIteration()) {
				throw new FrameworkException("Error","StartIteration cannot be greater than EndIteration!");
			}
			StartIterationNo =testParameters.getStartIteration();
			//System.out.println("Start I NO in RROI"+StartIterationNo);
			currentIteration = testParameters.getStartIteration();
			
			break;
			
		default:
			throw new FrameworkException("Unhandled Iteration Mode!");
		}
	}
	
	private void initializeWebDriver()
	{
		MobilePlatform MobilePlatform1 = null;
		executionMode = ExecutionMode.valueOf(properties.getProperty("ExecutionMode"));
		String tempPlatform=testParameters.getBrowser().getValue();
		//System.out.println("BrowserSet: "+tempPlatform);
		switch(executionMode) {
		case Local:

			//System.out.println("@@@@@@"+ Allocator.platformToExecute.equals("iOS") );

			if(tempPlatform.equalsIgnoreCase("iOS") /*&& Allocator.isDevice.equals("true")*/){
				MobilePlatform1 =  MobilePlatform.valueOf("iOS");
				//driver = WebDriverFactory.getDriver(MobilePlatform1);
			}

			else if(tempPlatform.equalsIgnoreCase("Web_Android") /*&& Allocator.isDevice.equals("false")*/){
				MobilePlatform1 = MobilePlatform.valueOf("Web_Android");
				//rdriver = WebDriverFactory.getDriver(MobilePlatform1);
			}
			else if(tempPlatform.equalsIgnoreCase("Web_iOS") /*&& Allocator.isDevice.equals("false")*/){
				MobilePlatform1 = MobilePlatform.valueOf("Web_iOS");
				//rdriver = WebDriverFactory.getDriver(MobilePlatform1);
			}else if(tempPlatform.equalsIgnoreCase("Android")){
				MobilePlatform1 = MobilePlatform.valueOf("Android");
				//driver = WebDriverFactory.getDriver(MobilePlatform1);
			}else if(tempPlatform.equalsIgnoreCase("Firefox")){
				MobilePlatform1 = MobilePlatform.valueOf("Firefox");
			}else if(tempPlatform.equalsIgnoreCase("Chrome")){
				MobilePlatform1 = MobilePlatform.valueOf("Chrome");
			}else if(tempPlatform.equalsIgnoreCase("IE")){
				MobilePlatform1 = MobilePlatform.valueOf("IE");
			}else if(tempPlatform.equalsIgnoreCase("Safari")){
				MobilePlatform1 = MobilePlatform.valueOf("Safari");
			}

			//testParameters.getBrowser()

			/*if( properties.getProperty("OpeningEmulator").toString().equals("Automatic"))
			{*/
			//driver = WebDriverFactory.getDriver(MobilePlatform1);

			/*}
			else 
			{
				driver = WebDriverFactory.getDriver(testParameters.getBrowser());
			}*/
			switch(toolName){
			case Appium:
				appiumdriver = AppiumDriverFactory.getDriver(MobilePlatform1);
				break;
			case Seetest:
				break;
			case Selenium_Desktop:
				webdriver=WebDriverFactory.getDriver(MobilePlatform1);
				break;
			case RemoteWebDriver:
			case MobileLabs:
				webdriver=WebDriverFactory.getDriver(MobilePlatform1);
				break;
			case Perfecto:
				perfectodriver=PerfectoWebDriverFactory.getDriver(MobilePlatform1);
				break;
			case Selenium:
				if( properties.getProperty("OpeningEmulator").toString().equals("Automatic"))
				{
					appiumdriver = AppiumDriverFactory.getDriver(MobilePlatform1);
				}
				else 
				{
					appiumdriver = AppiumDriverFactory.getDriver(testParameters.getBrowser());
				}
				break;
			case Others:
				/////////////////

				//Tool Specific Configuration

				////////////////
				break;
			default: break;
			}


			break;

			/*case Remote:
			driver = WebDriverFactory.getDriver(testParameters.getBrowser(),
													properties.getProperty("RemoteUrl"));
			break;

		case Grid:
			driver = WebDriverFactory.getDriver(testParameters.getBrowser(),
													testParameters.getBrowserVersion(),
													testParameters.getPlatform(),
													properties.getProperty("RemoteUrl"));
			break; */

		default:
			throw new FrameworkException("Unhandled Execution Mode!");
		}
	}
	
	public static String returnDriver(){
		String tempPlatform=testParameters.getBrowser().getValue();
		if(tempPlatform.equalsIgnoreCase("Web_Android") || tempPlatform.equalsIgnoreCase("Web_iOS")){
			return "rdriver";
		}
		return "null";
	}
	
	private void initializeEmulator()
	{
	
	   
	   if( properties.getProperty("OpeningEmulator").toString().equals("Automatic"))
	    {
		   platform = Allocator.platformToExecute.toString();
	   }
	   else
	   {
		   platform = testParameters.getBrowser().toString();
	   }
	  
       //System.out.println("Allocator.platformToExecute" + platform.toString());

		if( properties.getProperty("OpeningEmulator").toString().equals("Automatic") && !platform.equals("iOS")) 
		{
			Emulators.main(Allocator.isDevice,Allocator.DeviceName,properties.getProperty("ToolName"), frameworkParameters.getRelativePath() +"/Utilities/android-server-2.21.0.apk");
		}
		
		else if( properties.getProperty("OpeningEmulator").equals("Manual")&&!platform.equals("iOS")) 
		{
     		Emulators.main("",testParameters.getDeviceName(),properties.getProperty("ToolName"), frameworkParameters.getRelativePath() +"/Utilities/android-server-2.21.0.apk");
     		System.out.println("Android Manual Execution : initializeEmulator");
		}
     	
     	
		else if( properties.getProperty("OpeningEmulator").toString().equals("Automatic") && platform.equals("iOS")) 
		{		
    		//SimulatorsOrDevicesOpener.main(Allocator.isDevice,Allocator.DeviceName);
		}
		
		else if ( properties.getProperty("OpeningEmulator").equals("Manual")&& platform.equals("iOS")) 
		{
     		SimulatorsOrDevicesOpener.main(Allocator.isDevice,testParameters.getDeviceName());  			
		}
		
		else
		{
			System.out.println("invalid option @ Initialize Emulator ");
		}
     	

	}	
	
	private void initializeTestReport()
	{
		timeStamp = TimeStamp.getInstance();
		
		initializeReportSettings();
		ReportTheme reportTheme = ReportThemeFactory.getReportsTheme(Theme.valueOf("CLASSIC"));
		
		report = new SeleniumReport(reportSettings, reportTheme);
		report.initialize();
		/*if(returnDriver().endsWith("rdriver"))
			report.setDriver(rdriver);
		else*/
		switch(toolName){
		case Others:
		case RemoteWebDriver:
		case Selenium_Desktop:
		case Selenium:
		case MobileLabs:
			report.setDriver(webdriver);
			break;
		case Appium:
			report.setDriver(appiumdriver);
			break;
		case Perfecto:
			report.setDriver(perfectodriver);
			break;
		}
	
	}
	
	private void initializeReportSettings()
	{
		reportPath = frameworkParameters.getRelativePath() +
								Util.getFileSeparator() +	"Results" +
								Util.getFileSeparator() + timeStamp;	
		deviceName = "";
		
		switch(DriverScript.toolName){
		case Appium:
		case Selenium:
			if(Allocator.ContinuousIntegration.equalsIgnoreCase("no")){
				deviceName = Emulators.selectedDeviceName;		
        	}else if(Allocator.ContinuousIntegration.equalsIgnoreCase("yes")){
        		deviceName=testParameters.getDeviceName();
        	}
			break;
		case Selenium_Desktop:
			deviceName = testParameters.getBrowser().name(); break;
			
		case RemoteWebDriver:
		case MobileLabs:
		case Perfecto:
		case Others:
		default:
			deviceName=testParameters.getDeviceName(); break;
		
			
			
		}
        
    
        System.out.println("Report Path:"+reportPath);
        reportSettings = new ReportSettings(reportPath,
			     // testParameters.getDeviceName() +
				deviceName +
			"_" + testParameters.getCurrentScenario() +
			"_" + testParameters.getCurrentTestcase());
        
		reportSettings.setDateFormatString(properties.getProperty("DateFormatString"));

		reportSettings.setLogLevel(4);
		reportSettings.setProjectName(properties.getProperty("ProjectName"));
		reportSettings.generateHtmlReports = Boolean.parseBoolean("True");
		
		//reportSettings.setLogLevel(Integer.parseInt(properties.getProperty("LogLevel")));
		//reportSettings.generateExcelReports = Boolean.parseBoolean(properties.getProperty("ExcelReport"));
		//reportSettings.generateHtmlReports = Boolean.parseBoolean(properties.getProperty("HtmlReport"));
		//reportSettings.takeScreenshotFailedStep = Boolean.parseBoolean(properties.getProperty("TakeScreenshotFailedStep"));
		//reportSettings.takeScreenshotPassedStep = Boolean.parseBoolean(properties.getProperty("TakeScreenshotPassedStep"));
		//reportSettings.linkScreenshotsToTestLog = linkScreenshotsToTestLog;
		

	}
	
	
	private void initializeDatatable()
	{
		String datatablePath = frameworkParameters.getRelativePath() +
									Util.getFileSeparator() + "Datatables";
		
		String runTimeDatatablePath;
		Boolean includeTestDataInReport =
				Boolean.parseBoolean(properties.getProperty("IncludeTestDataInReport"));
		if (includeTestDataInReport) {
			runTimeDatatablePath = reportPath + Util.getFileSeparator() + "Datatables";
			
			File runTimeDatatable = new File(runTimeDatatablePath + Util.getFileSeparator() +
									testParameters.getCurrentScenario() + ".xls");
			if (!runTimeDatatable.exists()) {
				File datatable = new File(datatablePath + Util.getFileSeparator() +
										testParameters.getCurrentScenario() + ".xls");
				
				try {
					FileUtils.copyFile(datatable, runTimeDatatable);
				} catch (IOException e) {
					e.printStackTrace();
					throw new FrameworkException("Error in creating run-time datatable: Copying the datatable failed...");
				}
			}
			
			File runTimeCommonDatatable = new File(runTimeDatatablePath +
													Util.getFileSeparator() +
													"Common Testdata.xls");
			if (!runTimeCommonDatatable.exists()) {
				File commonDatatable = new File(datatablePath +
										Util.getFileSeparator() + "Common Testdata.xls");
				
				try {
					FileUtils.copyFile(commonDatatable, runTimeCommonDatatable);
				} catch (IOException e) {
					e.printStackTrace();
					throw new FrameworkException("Error in creating run-time datatable: Copying the common datatable failed...");
				}
			}
		} else {
			runTimeDatatablePath = datatablePath;
		}
		
		dataTable = new CraftliteDataTable(runTimeDatatablePath, testParameters.getCurrentScenario());
		dataTable.setDataReferenceIdentifier(properties.getProperty("DataReferenceIdentifier"));
		
		// Initialize the datatable row in case test data is required during the setUp()
		dataTable.setCurrentRow(testParameters.getCurrentTestcase(), currentIteration);
	}
	
	private void initializeTestScript()
	{
		switch(toolName){		
		case Appium:
			scriptHelper = new ScriptHelper(dataTable, report, appiumdriver);
		break;
		case Selenium:
		case MobileLabs:
		case Selenium_Desktop:
		case RemoteWebDriver:
		case Others:
			scriptHelper = new ScriptHelper(dataTable, report, webdriver);
			break;
		case Perfecto: 
			scriptHelper = new ScriptHelper(dataTable, report, perfectodriver);
			break;
			default: break;
		}
		
		
		//currentTest = new TC1();
		testCase = getTestCaseInstance();
		testCase.initialize(scriptHelper);
	}
	
	private TestCase getTestCaseInstance()
	{
		Class<?> testScriptClass;
		try {
			testScriptClass = Class.forName("testscripts." +
							testParameters.getCurrentScenario().toLowerCase() +
							"." + testParameters.getCurrentTestcase());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new FrameworkException("The specified test script is not found!");
		}
		
		try {
			return (TestCase) testScriptClass.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
			throw new FrameworkException("Error while instantiating the specified test script");
		}
	}
	
	private void executeTestIterations()
	{
		IterationNo=1;
		while(currentIteration <= testParameters.getEndIteration()) {
			System.out.println("Current Iteration No: "+currentIteration);
			//System.out.println(testParameters.getEndIteration());
		
			System.out.println("Iteration No:"+IterationNo);
			if(IterationNo==1)
			{

				
			report.initializeTestCaseLog(
					properties.getProperty("ToolName")+ "#"+ properties.getProperty("ProjectName") + "#"+
			        testParameters.getCurrentTestcase() + "#"+ testParameters.getBrowser().toString()+ "#"+
			        testParameters.getPlatform().toString()+"#"+ Util.getCurrentFormattedTime(properties.getProperty("DateFormatString"))+"#"+
			        testParameters.getIterationMode()+"#STRSTATUS#"+
			        testParameters.getEndIteration()+"#"+	testParameters.getStartIteration()+"#"+
			        "STRENDTIME"+"#"+"PASSED"+"#"+"FAILED"+"#"+"TOTALITERATION");	
			//HtmlReport.IterationNo();
			}
			
			/*else
			{
			
			HtmlReport.IterationNo();
			}*/
			System.out.println("------------------------> Executing "+testParameters.getCurrentTestcase()+" <-----------------------");
			testCase.executeTest();
			currentTestcase=testParameters.getCurrentTestcase();
			currentIteration++;
			IterationNo++;
			dataTable.setCurrentRow(testParameters.getCurrentTestcase(), currentIteration);
		}
	}
	
	private void exceptionHandler(Exception ex, String exceptionName)
	{
		// Error reporting
		String exceptionDescription = ex.getMessage();
		if(exceptionDescription == null) {
			exceptionDescription = ex.toString();
		}
		
		if(ex.getCause() != null) {
            System.out.println(exceptionDescription + ex.getCause());
			report.updateTestCaseLog(exceptionName, exceptionDescription + ex.getCause(), Status.FAIL);
			
		} else {
            
			report.updateTestCaseLog(exceptionName, exceptionDescription, Status.FAIL);
		}
		ex.printStackTrace();
		
		// Error response
		if (frameworkParameters.getStopExecution()) {
			report.updateTestCaseLog("mCraft2.O Info",
					"Test execution terminated by user! All subsequent tests aborted...",
					Status.DONE);
			currentIteration = testParameters.getEndIteration();
		} else {
			OnError onError = OnError.valueOf(properties.getProperty("OnError"));
			switch(onError) {
			case NextIteration:
				/*report.updateTestCaseLog("mCraft2.O Info",
						"Test case iteration terminated by user! Proceeding to next iteration (if applicable)...",
						Status.DONE); */
				break;
				
			case NextTestCase:
				/*report.updateTestCaseLog("mCraft2.O Info",
						"Test case terminated by user! Proceeding to next test case (if applicable)...",
						Status.DONE); */
				currentIteration = testParameters.getEndIteration();
				break;
				
			case Stop:
				frameworkParameters.setStopExecution(true);
				/*report.updateTestCaseLog("mCraft2.O Info",
						"Test execution terminated by user! All subsequent tests aborted...",
						Status.DONE); */
				currentIteration = testParameters.getEndIteration();
				break;
				
			default:
				throw new FrameworkException("Unhandled OnError option!");
			}
		}
	}
	
	private void quitWebDriver()
	{
		String RWD=Allocator.RemoteWebDriver_Tool;
		switch(toolName){
		case Appium:
			appiumdriver.quit();
			break;
		case Selenium_Desktop:	
		case RemoteWebDriver:
		case MobileLabs:
			webdriver.quit();
			break;
		case Others:
			break;
		case Perfecto:
			ElementsAction.mobiledriver.quit();
			break;
		default:  break;
		}		
	}

	private void quitEmulator()
	{
		if(properties.getProperty("OpeningEmulator").equals("Automatic") ) 
		{		
		  //Emulators.killEmulator();
		}  
	}
	
	private void wrapUp()
	{
		endTime = Util.getCurrentTime();
		closeTestReport();
			
		testStatus = report.getTestStatus();	
		String strEndTime = Util.getCurrentFormattedTime(properties.getProperty("DateFormatString"));
		HtmlReport a=new HtmlReport(reportSettings, null);
		a.iterationResult();
		String passed=String.valueOf(HtmlReport.passed);
		String failed=String.valueOf(HtmlReport.failed);
		String TotalIterations=String.valueOf(HtmlReport.TotalNoOfIterations);
		HtmlCoversion.ConvertToHtml(reportSettings.getReportPath(), reportSettings.getReportName(),testStatus,strEndTime,passed,failed,TotalIterations);
		
		if(Allocator.ToolName.equals("Perfecto")){
			//downloadReport(PerfectoWebDriverFactory.mobiledriver);
			ElementsAction.downloadReport(ElementsAction.mobiledriver);
		}
	}
	
	/*private static void downloadReport(IMobileDriver driver) {
		InputStream reportStream = driver.downloadReport(MediaType.PDF);
		if (reportStream != null) {
		           File reportFile = new File(reportPath+"\\Perfecto_Report.pdf");
		           try {
                       FileUtils.write(reportStream, reportFile);
                 } catch (IOException e) {
                       // TODO Auto-generated catch block
                       e.printStackTrace();
                 }}
		}*/
	
	private void closeTestReport()
	{
		Util.getTimeDifference(startTime, endTime);
	}

	protected void setRelativePath()
	{
		String relativePath = new File(System.getProperty("user.dir")).getAbsolutePath();
		if(relativePath.contains("supportlibraries")) {
			relativePath = new File(System.getProperty("user.dir")).getParent();
		}
		frameworkParameters.setRelativePath(relativePath);
	}
	
}