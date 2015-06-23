package supportlibraries;

import java.util.Properties;

//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.firefox.FirefoxDriver;

import com.cognizant.framework.CraftliteDataTable;
import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.Settings;


/**
 * Abstract base class for reusable libraries created by the user
 * @author Cognizant
 */
public abstract class ReusableLibrary
{
	/**
	 * The {@link CraftliteDataTable} object (passed from the test script)
	 */
	protected CraftliteDataTable dataTable;
	/**
	 * The {@link SeleniumReport} object (passed from the test script)
	 */
	protected static SeleniumReport report;
	/**
	 * The {@link WebDriver} object (passed from the test script)
	 */
	//protected static WebDriver driver;
	//protected static WebDriver firefoxdriver;
	/**
	 * The {@link ScriptHelper} object (required for calling one reusable library from another)
	 */
	protected ScriptHelper scriptHelper;
	
	/**
	 * The {@link Properties} object with settings loaded from the framework properties file
	 */
	protected static Properties properties;
	/**
	 * The {@link FrameworkParameters} object
	 */
	protected static FrameworkParameters frameworkParameters;
	
	
	/**
	 * Constructor to initialize the {@link ScriptHelper} object and in turn the objects wrapped by it
	 * @param scriptHelper The {@link ScriptHelper} object
	 */
	public ReusableLibrary(ScriptHelper scriptHelper)
	{
		this.scriptHelper = scriptHelper;		
		this.dataTable = scriptHelper.getDataTable();
		report = scriptHelper.getReport();
		//this.driver = scriptHelper.getDriver();
		
		properties = Settings.getInstance();
		frameworkParameters = FrameworkParameters.getInstance();
	}
}