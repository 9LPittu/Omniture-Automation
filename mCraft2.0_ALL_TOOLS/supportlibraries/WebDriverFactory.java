package supportlibraries;


import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import allocator.Allocator;

import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.Utility;



/**
 * Factory class for creating the {@link WebDriver} object as required
 * @author Cognizant
 */
public class WebDriverFactory
{
	
	//MobileLabs
	static DesiredCapabilities caps;		
	public static String forwardport,uploadcommand,connectcommand,FilePath,sendport,AppName,ReleaseCommand,DeviceName=null; 

	/////
	
	
	public static String Device_Name = DriverScript.testParameters.getDeviceName();
	
	protected static Properties properties;
	private final static FrameworkParameters frameworkParameters =
			FrameworkParameters.getInstance();
	
	private WebDriverFactory()
	{
		// To prevent external instantiation of this class
	}
	
	
	/**
	 * Function to return the appropriate {@link WebDriver} object based on the parameters passed
	 * @param browser The {@link MobilePlatform} to be used for the test execution
	 * @return The corresponding {@link WebDriver} object
	 */
	public static WebDriver getDriver(MobilePlatform MobilePlatform)
	{

		/*Dimension DimensionDesk = new Dimension(800, 600); //width , height
		driver.manage().window().setSize(DimensionDesk);*/
		WebDriver driver = null;	
		
		switch(MobilePlatform) {
		
		case iOS:		   
		case Android:
			System.out.println("Not Compatible for the selected Platform "+MobilePlatform);
			break;
		
		case Web_Android:
			if(Allocator.ToolName.equalsIgnoreCase("MobileLabs")){
				GetPropertiesFile properties = new GetPropertiesFile();
		    	try {
					properties.getPropValues();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				/*if(!properties.DeviceNameAndroid.equals(""))
		    	{ */   		
					DeviceName = "\"" + DriverScript.testParameters.getDeviceName() + "\"";   
		    		sendport = properties.AndroidPort;
		    		FilePath = properties.AndroidAPKFilePath;
		    		AppName =  "\"" + "WebDriver" + "\""; // Name of the application displayed on DC Web UI
		    		caps = DesiredCapabilities.android();
		    	/*}
		    	if(!properties.DeviceNameiOS.equals(""))
		    	{    		
		    		DeviceName = "\"" + properties.DeviceNameiOS + "\"";   
		    		sendport = properties.iOSPort;
		    		FilePath = properties.iOSApplicationPath;
		    		AppName =  "\"" + "iWebDriver" + "\""; // Name of the application displayed on DC Web UI    		
		    		caps = DesiredCapabilities.ipad();
		    	} */   
		    	
		         ///**********************************forward local port to the device specified at remote port***********************************************/
		         forwardport = properties.DownloadPath + "MobileLabs.DeviceConnect.Cli.exe " + properties.hostIP + " " + properties.userName + " " + properties.password + " -device " + DeviceName + " -forward " + properties.Available_prt + " " + sendport;
		         
		         ///**********************************upload command to upload the OS specific application***********************************************///
		         uploadcommand = properties.DownloadPath + "MobileLabs.DeviceConnect.Cli.exe " + properties.hostIP + " " + properties.userName + " " + properties.password  + " -upload " + FilePath;
		         Runtime runtime = Runtime.getRuntime(); 		 
				 try {
					runtime.exec("cmd /c " + uploadcommand).getInputStream();
					
					
		 		 runtime.exec("cmd /c " + forwardport).getInputStream();

		         ///**********************************Connect to device with uploaded application***********************************************/
		         connectcommand = properties.DownloadPath + "MobileLabs.DeviceConnect.Cli.exe " + "" + properties.hostIP + " " + properties.userName + " " + properties.password  + " -device " + DeviceName + " -orientation " + properties.Orientation + " -scale 25 -retain -install " + AppName + " -autoconnect " + AppName;
		         runtime.exec("cmd /c " + connectcommand).getInputStream();
				 } catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
		         try {
					Thread.sleep(50000);
				 } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				 }
		         
		 		 URL url;
				try {
					url = new URL("http://localhost:" + properties.Available_prt + "/wd/hub");
				
		 		driver = new RemoteWebDriver(url, caps);  
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
			try {
				driver = new RemoteWebDriver(new URL("http://"+DriverScript.testParameters.getIP_Port()+"/wd/hub"), DesiredCapabilities.android());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		    break;
		case Web_iOS:
			if(Allocator.ToolName.equalsIgnoreCase("MobileLabs")){
				GetPropertiesFile properties = new GetPropertiesFile();
		    	try {
					properties.getPropValues();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				/*if(!properties.DeviceNameAndroid.equals(""))
		    	{   		
					DeviceName = "\"" + DriverScript.testParameters.getDeviceName() + "\"";   
		    		sendport = properties.AndroidPort;
		    		FilePath = properties.AndroidAPKFilePath;
		    		AppName =  "\"" + "WebDriver" + "\""; // Name of the application displayed on DC Web UI
		    		caps = DesiredCapabilities.ipad();
		    	/*}
		    	if(!properties.DeviceNameiOS.equals(""))
		    	{    */		
		    		DeviceName = "\"" + properties.DeviceNameiOS + "\"";   
		    		sendport = properties.iOSPort;
		    		FilePath = properties.iOSApplicationPath;
		    		AppName =  "\"" + "iWebDriver" + "\""; // Name of the application displayed on DC Web UI    		
		    		caps = DesiredCapabilities.ipad();
		    	//}   
		    	
		         ///**********************************forward local port to the device specified at remote port***********************************************/
		         forwardport = properties.DownloadPath + "MobileLabs.DeviceConnect.Cli.exe " + properties.hostIP + " " + properties.userName + " " + properties.password + " -device " + DeviceName + " -forward " + properties.Available_prt + " " + sendport;
		         
		         ///**********************************upload command to upload the OS specific application***********************************************///
		         uploadcommand = properties.DownloadPath + "MobileLabs.DeviceConnect.Cli.exe " + properties.hostIP + " " + properties.userName + " " + properties.password  + " -upload " + FilePath;
		         Runtime runtime = Runtime.getRuntime(); 		 
				 try {
					runtime.exec("cmd /c " + uploadcommand).getInputStream();
					
					
		 		 runtime.exec("cmd /c " + forwardport).getInputStream();

		         ///**********************************Connect to device with uploaded application***********************************************/
		         connectcommand = properties.DownloadPath + "MobileLabs.DeviceConnect.Cli.exe " + "" + properties.hostIP + " " + properties.userName + " " + properties.password  + " -device " + DeviceName + " -orientation " + properties.Orientation + " -scale 25 -retain -install " + AppName + " -autoconnect " + AppName;
		         runtime.exec("cmd /c " + connectcommand).getInputStream();
				 } catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}	
		         try {
					Thread.sleep(50000);
				 } catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				 }
		         
		 		 URL url;
				try {
					url = new URL("http://localhost:" + properties.Available_prt + "/wd/hub");
				
		 		driver = new RemoteWebDriver(url, caps);  
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
			try {
				driver = new RemoteWebDriver(new URL("http://"+DriverScript.testParameters.getIP_Port()+"/wd/hub"), DesiredCapabilities.iphone());
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		    break;
		case Firefox:
			/*FirefoxProfile profile = new FirefoxProfile(); 
			//profile.setPreference("general.useragent.override", "iPhone"); 
			profile.setPreference("general.useragent.override", "Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3");
			WebDriver driver = new FirefoxDriver(profile); */
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
			break;
		case Chrome:
			if(Utility.getSystemType().contains("Windows")){
				System.setProperty("webdriver.chrome.driver", frameworkParameters.getRelativePath() +"/Utilities/"+"chromedriver.exe");
			}else if(Utility.getSystemType().contains("Mac")){
				System.setProperty("webdriver.chrome.driver", frameworkParameters.getRelativePath() +"/Utilities/"+"chromedriver_mac32.exec");
			}
			driver = new ChromeDriver();
			driver.manage().window().maximize();
			break;
		case IE:
			System.setProperty("webdriver.ie.driver", frameworkParameters.getRelativePath() +"/Utilities/"+"IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			driver.manage().window().maximize();
			break;
		case Safari:
			driver	= new SafariDriver();
			break;
		default:
			throw new FrameworkException("Unhandled browser!");
		}
		//driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get(Allocator.ApplicationUrl);
		return driver; 
		
	}	
	
	
	
}

