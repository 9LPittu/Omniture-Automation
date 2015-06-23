package supportlibraries;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import com.cognizant.framework.FrameworkParameters;

public class GetPropertiesFile {	
	
	public static String DeviceNameAndroid = null;
	public String DeviceNameiOS = null;		
	public String Available_prt;		
	public String AndroidPort,iOSPort,Orientation;	        
	public String hostIP;
	public String userName;
	public String password;
	public String DownloadPath;	
	public String iOSApplicationPath;
	public String AndroidAPKFilePath;
	private final FrameworkParameters frameworkParameters =
			FrameworkParameters.getInstance();
	//private SeleniumTestParameters testParameters;
	
	
	
	
	public String getPropValues() throws IOException{
		
				
		Properties prop = new Properties();
		String propFileName = "config.properties";
		
		InputStream inputstream = new FileInputStream(frameworkParameters.getRelativePath() +"/"+propFileName);
		if(inputstream != null)
		{
			prop.load(inputstream);
		}
		else
		{
			throw new FileNotFoundException("Property file : " + propFileName + " not found in the classpath.");
		}
		
	
		DeviceNameiOS = prop.getProperty("DeviceNameiOS");
		Available_prt = prop.getProperty("Available_prt");//Integer.toString(s1.getLocalPort());
		AndroidPort = prop.getProperty("AndroidPort");
		iOSPort = prop.getProperty("iOSPort");
		Orientation = prop.getProperty("Orientation");
		hostIP = prop.getProperty("hostIP");
		userName = prop.getProperty("userName");
		password = prop.getProperty("password");
		DownloadPath = prop.getProperty("DownloadPath");
		iOSApplicationPath = prop.getProperty("iOSApplicationPath");
		AndroidAPKFilePath = prop.getProperty("AndroidAPKFilePath");
		
		return propFileName;
	}
}
