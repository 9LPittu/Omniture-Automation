package supportlibraries;


import java.util.Properties;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.WebDriver;

import allocator.Allocator;

import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.FrameworkParameters;
import com.perfectomobile.selenium.MobileDriver;
import com.perfectomobile.selenium.api.IMobileWebDriver;
import com.perfectomobile.selenium.options.MobileBrowserType;

public class PerfectoWebDriverFactory {

public static String Device_Name = DriverScript.testParameters.getDeviceName();
	
	protected static Properties properties;
	private final static FrameworkParameters frameworkParameters =
			FrameworkParameters.getInstance();
	
	private PerfectoWebDriverFactory()
	{
		// To prevent external instantiation of this class
	}
	
	
	/**
	 * Function to return the appropriate {@link WebDriver} object based on the parameters passed
	 * @param browser The {@link MobilePlatform} to be used for the test execution
	 * @return The corresponding {@link WebDriver} object
	 */
	public static IMobileWebDriver getDriver(MobilePlatform MobilePlatform)
	{
		
		
		ElementsAction.mobiledriver = new MobileDriver();
		IMobileWebDriver perfectodriver = null;
		String DeviceID = DriverScript.testParameters.getDeviceName();
		switch(MobilePlatform) {
		
		case iOS:		   
		case Android:
			ElementsAction.mobiledriver.getDevice(DeviceID).getNativeDriver(Allocator.PerfectoApplicationIdentifier).open();
			perfectodriver = ElementsAction.mobiledriver.getDevice(DeviceID).getNativeDriver();
			break;
			
		case Firefox:
		case Chrome:
		case IE:
		case Safari:
			System.out.println("Not Compatible for the selected Platform "+MobilePlatform);
			break;
		
		case Web_Android:
		case Web_iOS:
			ElementsAction.mobiledriver.getDevice(DeviceID).getDOMDriver(MobileBrowserType.DEFAULT).get(Allocator.ApplicationUrl);
			perfectodriver = ElementsAction.mobiledriver.getDevice(DeviceID).getDOMDriver();
			perfectodriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
			perfectodriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);					
		    break;
		
			
		
		default:
			throw new FrameworkException("Unhandled browser!");
		}
		
		return perfectodriver; 
		
	}	
	
	
}
