package allocator;

import java.util.Date;
import java.util.Properties;

import supportlibraries.DriverScript;
import supportlibraries.SeleniumReport;
import supportlibraries.SeleniumTestParameters;

import com.cognizant.framework.Emulators;
import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.HtmlReport;
import com.cognizant.framework.Util;


/**
 * Class to facilitate parallel execution of test scripts
 * @author Cognizant
 */
public class ParallelRunner implements Runnable
{
	@SuppressWarnings("rawtypes")
	private final SeleniumTestParameters testParameters;
	private final SeleniumReport summaryReport;
	private final Properties properties;	
	
	/**
	 * Constructor to initialize the details of the test case to be executed
	 * @param testParameters The {@link SeleniumTestParameters} object (passed from the {@link Allocator})
	 * @param summaryReport The {@link SeleniumReport} object (passed from the {@link Allocator})
	 * @param properties 
	 */
	@SuppressWarnings("rawtypes")
	public ParallelRunner(SeleniumTestParameters testParameters, SeleniumReport summaryReport, Properties properties)
	{
		super();
		
		this.testParameters = testParameters;
		this.summaryReport = summaryReport;
		this.properties = properties;
	}
	
	@Override
	public void run()
	{
		Date startTime = Util.getCurrentTime();
		String DeviceName = "";
		String testStatus = invokeTestScript();
		Date endTime = Util.getCurrentTime();
		String executionTime = Util.getTimeDifference(startTime, endTime);
		
		switch(DriverScript.toolName){
		case Appium:
			if(Allocator.ContinuousIntegration.equalsIgnoreCase("no")){
				DeviceName = Emulators.selectedDeviceName;		
        	}else if(Allocator.ContinuousIntegration.equalsIgnoreCase("yes")){
        		DeviceName=testParameters.getDeviceName();
        	}
			break;
		case Selenium_Desktop:
			DeviceName = testParameters.getBrowser().name(); break;
		case RemoteWebDriver:
		case MobileLabs:
			DeviceName=testParameters.getDeviceName(); break;
		case Others:
			DeviceName=testParameters.getDeviceName(); break;
		default: 
			DeviceName=testParameters.getDeviceName(); break;
			
		}
		
		
//        System.out.println(DeviceName);
		
		//summaryReport.updateResultSummary(testParameters.getDeviceName(),testParameters.getCurrentScenario(),
		String passed=String.valueOf(HtmlReport.passed);
		String failed=String.valueOf(HtmlReport.failed);
	//	int TotIterations=DriverScript.currentIteration-1;
		//String TotalIteration=String.valueOf(TotIterations);
	//	System.out.println("Total No Of iterations"+TotalIteration);
		summaryReport.updateResultSummary(DeviceName,testParameters.getCurrentScenario(),
									testParameters.getCurrentTestcase(),
									testParameters.getCurrentTestDescription(),
									executionTime, testStatus,passed,failed);
	}
	
	private String invokeTestScript()
	{
		String testStatus;
		FrameworkParameters frameworkParameters = FrameworkParameters.getInstance();
		
		if(frameworkParameters.getStopExecution()) {
			testStatus = "Aborted";
		} else {
			DriverScript driverScript = new DriverScript(this.testParameters);
			driverScript.driveTestExecution();
			testStatus = driverScript.getTestStatus();
		}
		
		return testStatus;
	}
}