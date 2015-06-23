package allocator;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.openqa.selenium.Platform;

import supportlibraries.DriverScript;
import supportlibraries.GetPropertiesFile;
import supportlibraries.MobilePlatform;
import supportlibraries.ResultSummaryManager;
import supportlibraries.SeleniumTestParameters;

import com.cognizant.framework.Emulators;
import com.cognizant.framework.ExcelDataAccess;
import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.Globals;
import com.cognizant.framework.IterationOptions;
import com.cognizant.framework.RunConfig;
import com.cognizant.framework.TargetDetails;
import com.cognizant.framework.TargetSelectorAndroid;


/**
 * Class to manage the batch execution of test scripts within the framework
 * @author Cognizant
 */
public class Allocator extends ResultSummaryManager
{
	@SuppressWarnings("rawtypes")
	private List<SeleniumTestParameters> testInstancesToRun;
	public static String DeviceName;
	public static String isDevice;		
	public static String OpeningEmulator;
	public static String AndroidSDKPath;
	public static String platformToExecute;
	public static String ProxyUserName;
	public static String ProxyPassword;
	public static String ProxyName;
	public static String ProxyPort;
	public static String AppiumPath,NodeJsPath,AppiumIpPort;
	public static String selectedPlatform;
	public static String ApplicationUrl;
	public static String ApplicationPackageName,ApplicationMainActivityName,IosAppName,ToolName,DeviceID,ContinuousIntegration,PerfectoApplicationIdentifier;
	
	public static Boolean isEmulatorFlag=false;
	
	public static String Appium_Tool="Appium",Selenium_Desktop_Tool="Selenium_Desktop", RemoteWebDriver_Tool="RemoteWebDriver", Others_Tool="Others";
	
	public static void main(String[] args)
	{
		
		Allocator allocator = new Allocator();
		allocator.driveBatchExecution();
			
		
		}
	
	private void driveBatchExecution()
	{
		
		setRelativePath();
		initializeTestBatch();
		
		
		//int nThreads = Integer.parseInt(properties.getProperty("NumberOfThreads"));
		int nThreads = 1;
		initializeSummaryReport(properties.getProperty("RunConfiguration"), nThreads);
		
		try {
			setupErrorLog();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new FrameworkException("Error while setting up the Error log!");
		}
		
		AndroidSDKPath = properties.getProperty("AndroidSDKPath");		
		AppiumIpPort=properties.getProperty("AppiumIpPort");
	    Globals.setAllPath(AndroidSDKPath,AppiumPath,NodeJsPath);
	    ProxyUserName = properties.getProperty("Username");
	    ProxyPassword = properties.getProperty("Password");
	    ProxyName =properties.getProperty("Proxy");
	    ProxyPort = properties.getProperty("Port");
	    ApplicationUrl=properties.getProperty("ApplicationUrl");
	    ApplicationPackageName=properties.getProperty("ApplicationPackageName");
	    ApplicationMainActivityName=properties.getProperty("ApplicationMainActivityName");
	    IosAppName=properties.getProperty("IosAppName");
	    ToolName=properties.getProperty("ToolName");
	    ContinuousIntegration=properties.getProperty("ContinuousIntegration");
	    PerfectoApplicationIdentifier=properties.getProperty("PerfectoApplicationIdentifier");
	    	    
	   if(properties.getProperty("OpeningEmulator").toString().equals("Automatic")){

			TargetSelectorAndroid.main(this);
	   }
	   else
	   {

		   executeTestBatch();
			try 
			{
				wrapUp();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			Globals.stopServer();
			launchResultSummary();		
			System.exit(0);		   
	    }

		
	}
	
	public void executeTestCase(RunConfig runConfiguration)
	{
		   //Connected Device
		    System.out.println("OS TYPE :"+runConfiguration.getSystemType());
	        System.out.println("Platform TYPE :"+runConfiguration.getPlatformToRun());
	        platformToExecute=runConfiguration.getPlatformToRun();
	        
	        switch(DriverScript.toolName){
	        case Appium:
	        	if(Allocator.ContinuousIntegration.equalsIgnoreCase("no")){
	        	System.out.println("Connected Device Details:");
		        for(int i=0;i<runConfiguration.getConnectedDeviceName().size();i++)
		        {
		           TargetDetails targetDetails=(TargetDetails)runConfiguration.getConnectedDeviceName().get(i);
	               DeviceName = targetDetails.getTargetNameorSerial();
	               isDevice = String.valueOf(targetDetails.isDevice()); 
	   			   executeTestBatch();
	       			if(properties.getProperty("OpeningEmulator").toString().equals("Automatic") && !targetDetails.isDevice()&&!platformToExecute.equals("iOS")) 
	       			{
	       				isEmulatorFlag=true;
	  				 // Emulators.killEmulator();
	  			    }	       			   
	               
		        }
		        //Selected Device
		       
		        for(int i=0;i<runConfiguration.getSelecteAVDs().size();i++)
		        {
		        	 //System.out.println("Selected Simulators");
		           TargetDetails targetDetails=(TargetDetails)runConfiguration.getSelecteAVDs().get(i);
	               DeviceName = targetDetails.getTargetNameorSerial();
	               isDevice = String.valueOf(targetDetails.isDevice());
	               
	   			   executeTestBatch();
		   		   if(properties.getProperty("OpeningEmulator").toString().equals("Automatic") && !targetDetails.isDevice()&&!platformToExecute.equals("iOS"))
	       			{
	  				  Emulators.killEmulator();
	  			    }        
		        }
	        }else if(Allocator.ContinuousIntegration.equalsIgnoreCase("yes")){
	        	executeTestBatch();
	        }
	        	break;
	        case Selenium_Desktop:
	        	if(runConfiguration.getPlatformToRun().equals("Firefox")||
		        		runConfiguration.getPlatformToRun().equals("Chrome")||
		        		runConfiguration.getPlatformToRun().equals("IE")||
		        		runConfiguration.getPlatformToRun().equals("Safari")){
		        	//System.out.println("Selected Desktop");
	               
	   			   executeTestBatch();
		   		        
		        }
	        	break;
	        case MobileLabs:
	        case RemoteWebDriver:
	        case Seetest:
	        case Others:
	        case Perfecto:
	        	executeTestBatch(); break;
	        default:
	        	executeTestBatch(); break;
	        }

	    
		
		try {
			wrapUp();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		System.out.println("Execution Complete");
	//	Globals.stopServer();
		 launchResultSummary();		
		 System.exit(0);
	}
	
	@Override
	protected void initializeTestBatch()
	{
		
		super.initializeTestBatch();	
		testInstancesToRun = getRunInfo(properties.getProperty("RunConfiguration"));		
	}
	
	@SuppressWarnings("rawtypes")
	private List<SeleniumTestParameters> getRunInfo(String sheetName)
	{
		ExcelDataAccess runManagerAccess = new ExcelDataAccess(frameworkParameters.getRelativePath(), "Run Manager");			
		runManagerAccess.setDatasheetName(sheetName);
		
		int nTestInstances = runManagerAccess.getLastRowNum();
		List<SeleniumTestParameters> testInstancesToRun = new ArrayList<SeleniumTestParameters>();
		
		for (int currentTestInstance = 1; currentTestInstance <= nTestInstances; currentTestInstance++) {
			String executeFlag = runManagerAccess.getValue(currentTestInstance, "Execute");
			
			if (executeFlag.equalsIgnoreCase("Yes")) {
				String currentScenario = runManagerAccess.getValue(currentTestInstance, "TestScenario");
				String currentTestcase = runManagerAccess.getValue(currentTestInstance, "TestCase");
				SeleniumTestParameters testParameters =
						new SeleniumTestParameters(currentScenario, currentTestcase);
				
				testParameters.setCurrentTestDescription(runManagerAccess.getValue(currentTestInstance, "Description"));
				
				String iterationMode = runManagerAccess.getValue(currentTestInstance, "IterationMode");
				if (!iterationMode.equals("")) {
					testParameters.setIterationMode(IterationOptions.valueOf(iterationMode));
				} else {
					testParameters.setIterationMode(IterationOptions.RunAllIterations);
				}
				
				String startIteration = runManagerAccess.getValue(currentTestInstance, "StartIteration");
				if (!startIteration.equals("")) {
					testParameters.setStartIteration(Integer.parseInt(startIteration));
				}
				String endIteration = runManagerAccess.getValue(currentTestInstance, "EndIteration");
				if (!endIteration.equals("")) {
					testParameters.setEndIteration(Integer.parseInt(endIteration));
				}
				
				selectedPlatform = runManagerAccess.getValue(currentTestInstance, "MobilePlatform");
				System.out.println("SelectedPlatform: "+selectedPlatform);
				if (!selectedPlatform.equals("")) {
					testParameters.setBrowser(MobilePlatform.valueOf(selectedPlatform));
					
				}
				
				String devicename = runManagerAccess.getValue(currentTestInstance, "DeviceName");
				////
				GetPropertiesFile.DeviceNameAndroid=devicename;
				//System.out.println("DN: "+GetPropertiesFile.DeviceNameAndroid);
				////
				if (!devicename.equals("")) {
					testParameters.setDeviceName(devicename);
				}	
				
				String ip_port = runManagerAccess.getValue(currentTestInstance, "IP_Port");				
				if (!ip_port.equals("")) {
					testParameters.setIP_Port(ip_port);
				}
				
				String platform = runManagerAccess.getValue(currentTestInstance, "OS");
				if (!platform.equals("")) {
					testParameters.setPlatform(Platform.valueOf(platform));
				} else {
					testParameters.setPlatform(Platform.valueOf(properties.getProperty("DefaultPlatform")));
				}
				
				testInstancesToRun.add(testParameters);
			}
		}
		
		return testInstancesToRun;
	}
	
	private void executeTestBatch()
	{

		
		//int nThreads = Integer.parseInt(properties.getProperty("NumberOfThreads"));
		int nThreads = 1;
		ExecutorService parallelExecutor = Executors.newFixedThreadPool(nThreads);
		
		for (int currentTestInstance = 0; currentTestInstance < testInstancesToRun.size() ; currentTestInstance++ ) {
			ParallelRunner testRunner =	new ParallelRunner(testInstancesToRun.get(currentTestInstance), summaryReport,properties);
			parallelExecutor.execute(testRunner);
			
			if(frameworkParameters.getStopExecution()) {
				break;
			}
		}
		
		parallelExecutor.shutdown();
		while(!parallelExecutor.isTerminated()) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} 
	}
}