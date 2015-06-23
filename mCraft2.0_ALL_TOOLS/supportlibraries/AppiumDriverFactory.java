package supportlibraries;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.*;
//import io.appium.java_client.remote.MobileCapabilityType;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

//import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
//import org.openqa.selenium.remote.RemoteWebDriver;
//import org.openqa.selenium.*;

import allocator.Allocator;

import com.cognizant.framework.Emulators;
import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.FrameworkParameters;
//import com.cognizant.framework.PropertyFileHandler;
import com.cognizant.framework.TargetDetails;
import com.cognizant.framework.TargetSelectorMac;
//import com.cognizant.framework.TestParameters;



/**
 * Factory class for creating the {@link WebDriver} object as required
 * @author Cognizant
 */
public class AppiumDriverFactory
{
	
	public static String Device_Name = DriverScript.testParameters.getDeviceName();
	
	protected static Properties properties;
	private final static FrameworkParameters frameworkParameters =
			FrameworkParameters.getInstance();
	
	private AppiumDriverFactory()
	{
	
	}
	
	
	/**
	 * Function to return the appropriate {@link WebDriver} object based on the parameters passed
	 * @param browser The {@link MobilePlatform} to be used for the test execution
	 * @return The corresponding {@link WebDriver} object
	 */
	public static AppiumDriver getDriver(MobilePlatform MobilePlatform)
	{

		AppiumDriver driver = null;	
		switch(MobilePlatform) {
		
		case iOS:		    
	    	DesiredCapabilities dc3=new DesiredCapabilities();
	    	if(Allocator.ContinuousIntegration.equalsIgnoreCase("yes")){
	    		dc3.setCapability("udid",Device_Name);
	    		dc3.setCapability("deviceName", "iPhone");
        	}
			else	if(Allocator.ContinuousIntegration.equalsIgnoreCase("no")){
	    	ArrayList<TargetDetails> selecteAVDs=TargetSelectorMac.getSimulator();
			if(selecteAVDs.size()==0){
				String DeviceUDID=TargetSelectorMac.deviceID.toString();
				if(DeviceUDID.startsWith(" ")){
					TargetSelectorMac.deviceID=TargetSelectorMac.deviceID.toString().substring(1);
					DeviceUDID=DeviceUDID.substring(1);
					dc3.setCapability("deviceName", "iPhone");
					dc3.setCapability("udid", DeviceUDID);
				}
				else{
					dc3.setCapability("deviceName", "iPhone");
					dc3.setCapability("udid", TargetSelectorMac.deviceID.toString());
				}
			}else {
				dc3.setCapability("deviceName", "iPhone Simulator");
			}
			}
	    	dc3.setCapability("platformName", "ios");
	    	dc3.setCapability("app", frameworkParameters.getRelativePath() +"/Utilities/"+Allocator.IosAppName);
			
			try {
			driver=new IOSDriver(new URL(Allocator.AppiumIpPort+"/wd/hub"), dc3);		
		} 
	    catch (MalformedURLException e) {
			e.printStackTrace();
		}
	    break;
	case Web_iOS:
		
		DesiredCapabilities dc2=new DesiredCapabilities();									
		if(Allocator.ContinuousIntegration.equalsIgnoreCase("yes")){
    		dc2.setCapability("udid",Device_Name);
			dc2.setCapability("deviceName", "iPhone");
    	}
		else	if(Allocator.ContinuousIntegration.equalsIgnoreCase("no")){
	ArrayList<TargetDetails> selecteAVDs=TargetSelectorMac.getSimulator();
	if(selecteAVDs.size()==0){
		String DeviceUDID=TargetSelectorMac.deviceID.toString();
		if(DeviceUDID.startsWith(" ")){
			DeviceUDID=DeviceUDID.substring(1);			
			dc2.setCapability("deviceName", "iPhone");
			dc2.setCapability("udid", DeviceUDID);
		}
		else{
			dc2.setCapability("deviceName", "iPhone");
			dc2.setCapability("udid", TargetSelectorMac.deviceID.toString());
		}
	}else {
		dc2.setCapability("deviceName", "iPad Retina Simulator");
		dc2.setCapability("automationName", "Appium");			
		dc2.setCapability("browserName", "Safari");
		dc2.setCapability("platformName", "iOS");
		dc2.setCapability("platformVersion", "7.1");
		dc2.setCapability("newCommandTimeout", 120);			
	}
		}
	dc2.setCapability("automationName", "Appium");			
	dc2.setCapability("browserName", "Safari");
	dc2.setCapability("platformName", "iOS");
	dc2.setCapability("platformVersion", "7.1");
	dc2.setCapability("newCommandTimeout", 120);
	try {
		driver = new IOSDriver(new URL(Allocator.AppiumIpPort+"/wd/hub"), dc2);
		driver.get(Allocator.ApplicationUrl);
	} catch (MalformedURLException e) {
		e.printStackTrace();
	}
    break;
	case Android:
		DesiredCapabilities dc=new DesiredCapabilities();
		if(Allocator.ContinuousIntegration.equalsIgnoreCase("no")){
			if(Emulators.selectedDeviceName.startsWith("emulator")){
				dc.setCapability("deviceName", "Android Emulator");
			}else{
				dc.setCapability("udid", Emulators.selectedDeviceName);
				dc.setCapability("deviceName", "Android");
			}
		}else if(Allocator.ContinuousIntegration.equalsIgnoreCase("yes")){
			dc.setCapability("udid", Device_Name);
			dc.setCapability("deviceName", "Android");
		}
		dc.setCapability("platformName", "Android");
		
		dc.setCapability("appPackage", Allocator.ApplicationPackageName);
		dc.setCapability("appActivity", Allocator.ApplicationMainActivityName);		
		try {
			driver=new AndroidDriver(new URL(Allocator.AppiumIpPort+"/wd/hub"), dc);			
		} catch (MalformedURLException e1) {			
			e1.printStackTrace();
		}	 
		break;
	
	case Web_Android:
		
		DesiredCapabilities dc1=new DesiredCapabilities();
		if(Allocator.ContinuousIntegration.equalsIgnoreCase("no")){
			if(Emulators.selectedDeviceName.startsWith("emulator")){
				dc1.setCapability("deviceName", "Android Emulator");
			}else{
				dc1.setCapability("udid", Emulators.selectedDeviceName);
				dc1.setCapability("deviceName", "Android");
			}		
    	}else if(Allocator.ContinuousIntegration.equalsIgnoreCase("yes")){
    		dc1.setCapability("udid",Device_Name);
			dc1.setCapability("deviceName", "Android");
    	}
		
		
		dc1.setCapability("browserName", "Chrome");
		dc1.setCapability("platformName", "Android");		
		try {
			driver=new AndroidDriver(new URL(Allocator.AppiumIpPort+"/wd/hub"), dc1);	
			driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
			driver.get(Allocator.ApplicationUrl);
		} catch (MalformedURLException e) {
		
			e.printStackTrace();
		}
	    break;
		
	default:
		throw new FrameworkException("Unhandled browser!");
	}	
	return driver; 		
	}	
	
	
	
}

