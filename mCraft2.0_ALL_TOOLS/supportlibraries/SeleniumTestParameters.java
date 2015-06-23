package supportlibraries;

import com.cognizant.framework.TestParameters;
import org.openqa.selenium.Platform;


/**
 * Class to encapsulate various input parameters required for each test script
 * @author Cognizant
 * @param <DeviceName>
 */
public class SeleniumTestParameters<DeviceName> extends TestParameters
{
	public SeleniumTestParameters(String currentScenario, String currentTestcase)
	{
		super(currentScenario, currentTestcase);
	}
	
	
	private MobilePlatform browser;
	/**
	 * Function to get the browser for a specific test
	 * @return The browser
	 */
	public MobilePlatform getBrowser()
	{
		return browser;
	}
	/**
	 * Function to set the browser for a specific test
	 */
	public void setBrowser(MobilePlatform browser)
	{
		this.browser = browser;
	}
	
	
	private Platform platform;
	/**
	 * Function to get the platform for a specific test
	 * @return The platform
	 */
	public Platform getPlatform()
	{
		return platform;
	}
	/**
	 * Function to set the platform for a specific test
	 */
	public void setPlatform(Platform platform)
	{
		this.platform = platform;
	}
	
	private String devicename;

	/**
	 * Function to get the browser for a specific test
	 * @return The browser
	 */
	public String getDeviceName()
	{
		return devicename;
	}
	/**
	 * Function to set the browser for a specific test
	 */
	public void setDeviceName(String devicename)
	{
		this.devicename = devicename;
	}
	
	private String ip_port;
	
	public void setIP_Port(String ip_port) {
		// TODO Auto-generated method stub
		this.ip_port=ip_port;
	}
	
	public String getIP_Port() {
		// TODO Auto-generated method stub
		return ip_port;
	}
	
}