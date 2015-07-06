package supportlibraries;

import io.appium.java_client.AppiumDriver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Date;

import org.omg.CORBA.portable.InputStream;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import allocator.Allocator;

import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.Status;
import com.perfectomobile.httpclient.MediaType;
import com.perfectomobile.httpclient.utils.FileUtils;
import com.perfectomobile.selenium.MobileDriver;
import com.perfectomobile.selenium.api.IMobileWebDriver;
import com.perfectomobile.selenium.options.MobileBrowserType;


public abstract class ElementsAction  extends ReusableLibrary {

	

	public ElementsAction(ScriptHelper scriptHelper) {
		super(scriptHelper);
		//getListOfTools();
		// TODO Auto-generated constructor stub
	}
	
	public static String ElementValue;
	public static AppiumDriver appiumdriver;
	public static WebDriver webdriver;
	public static IMobileWebDriver perfectodriver;
	public static WebDriverWait myWait;

	public static Date date=new Date();
	public static String fileOrFolderPath;
	public static String Folder;
	public static String CreateFolder;
	public static String devicetype;
	private static String present;
	private static String RemoteWebdriver = Allocator.RemoteWebDriver_Tool;
	
	public static MobileDriver mobiledriver;


	public static void setDriver(AppiumDriver driver,SeleniumReport report)
	{
		ElementsAction.appiumdriver=DriverScript.appiumdriver;
		ElementsAction.report=report;
	}

	public static void setDriver(WebDriver remotedriver,SeleniumReport report)
	{
		ElementsAction.webdriver=DriverScript.webdriver;
		ElementsAction.report=report;
	}
	
	public static void setDriver(IMobileWebDriver perfectodriver,SeleniumReport report)
	{
		ElementsAction.perfectodriver=DriverScript.perfectodriver;
		ElementsAction.report=report;
	}


	public static void setDriver(SeleniumReport report){
		
		switch(DriverScript.toolName){
		case Appium:
			ElementsAction.setDriver(DriverScript.appiumdriver,report);
		break;
		case Selenium_Desktop :			
		case  RemoteWebDriver:
		case MobileLabs:
			ElementsAction.setDriver(DriverScript.webdriver,report);
		break;
		case Perfecto:
			ElementsAction.setDriver(DriverScript.perfectodriver,report);
		break;
		default: report.updateTestCaseLog(" Please enter a valid ToolName in GlobalSettings ","",Status.FAIL); break;
		}	
		if(DriverScript.testParameters.getBrowser().name().equals("Android") || DriverScript.testParameters.getBrowser().name().equals("iOS")){
			report.updateTestCaseLog("Invoke Application", "Invoke the application under test @ " +	Allocator.ApplicationPackageName, Status.DONE);
		}else {
			report.updateTestCaseLog("Invoke Application", "Invoke the application under test @ " +	Allocator.ApplicationUrl, Status.DONE);
		}
	}

	//BY SENDING ID	id
	/**
	 * Function to identify the id of the object and set value to the object and run corresponding elements
	 * @param value The unique value of the object
	 * @param action The action to be performed on the object selected
	 * @param sendkeys The parameter to be passed during execution
	 */

	public static void act(WebElement Element,String action,String sendKeys)
	{	
		try
		{
			if(Element.isDisplayed())
			{
				ElementValue=Element.getText();				
				if (action.equalsIgnoreCase("click"))
				{					
					Element.click();									
					report.updateTestCaseLog("Clicked ", ElementValue, Status.PASS);
				}
				else if (action.equalsIgnoreCase("clear"))
				{
					Element.clear();
					report.updateTestCaseLog("Cleared ", ElementValue, Status.PASS);
				}
				else if(action.equalsIgnoreCase("entertext"))
				{					
					Element.sendKeys(sendKeys);
					report.updateTestCaseLog("Typed ", ElementValue, Status.PASS);
				}
				else if(action.equalsIgnoreCase("select"))
				{
					Select select = new Select(Element);
					select.selectByValue(sendKeys);
					report.updateTestCaseLog("Selected ", ElementValue, Status.PASS);
				}
				else if(action.equalsIgnoreCase("submit"))
				{
					Element.submit();
					report.updateTestCaseLog("Typed ", ElementValue, Status.PASS);
				}
				else if(action.equalsIgnoreCase("verifytext"))
				{					
					if(Element.getText().equals(sendKeys))
					{					
						report.updateTestCaseLog("Text Verified ", ElementValue, Status.PASS);					
					}
					else
					{
						report.updateTestCaseLog("Text NOT Verified ", ElementValue, Status.PASS);
					}
				}
				
			}
		}
		catch(NoSuchElementException e)
		{			
			report.updateTestCaseLog("Please verify  whether the entered element is Valid", "Element Not Present", Status.FAIL);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	public static void linkText(final String value,String action,String sendkeys) 
	{		

		int a=0;
		if (action=="click" || action=="Click")
		{
			a=1;
		}
		else if(action=="clear" || action=="Clear")
		{
			a=2;
		}
		else if(action=="entertext" || action=="Entertext")
		{
			a=3;
		}
		else if( action=="dropdown" ||  action=="Dropdown")
		{
			a=4;
		}
		else if(action=="submit" || action=="Submit")
		{
			a=5;
		}
		else if(action=="verify" || action=="Verify")
		{
			a=6;
		}

		String exceptionmessage;
		try
		{
			switch(DriverScript.toolName){
			case Appium:
				present = "true";	
				for(int i=0;isElementPresent(By.linkText(value))==false;i++)
				{
					if(i>=60)
					{
						present="false";
						//report.updateTestCaseLog("Please verify  whether the entered element is Valid",value.toString(),Status.FAIL);
						break;
						//frameworkParameters.setStopExecution(true);
						//throw new FrameworkException(value.toString(), "Please verify  whether the entered element is Valid");
					}
				}	

				if(present.equals("true"))
				{

					switch(a)
					{
					case 1:
						//Click

						try {
							appiumdriver.findElement(By.linkText(value)).click();
							report.updateTestCaseLog("Clicked",value.toString(),Status.PASS);
						} 

						catch (Exception e)
						{
							//report.updateTestCaseLog(value.toString() +"Not Clicked",value.toString(),Status.FAIL);
							throw new FrameworkException(value.toString() +"Not Clicked", "failed to click");
						} 
						break;

					case 2:
						//clear
						appiumdriver.findElement(By.linkText(value)).clear();
						report.updateTestCaseLog("cleared",value.toString(),Status.PASS);

						break;
					case 3:
						//EnterValue
						appiumdriver.findElement(By.linkText(value)).sendKeys(sendkeys);
						report.updateTestCaseLog("  Typed :  :",value.toString(),Status.PASS);
						break;
					case 4:
						//Dropdown Selection
						Select select = new Select(appiumdriver.findElement(By.linkText(value)));
						select.selectByVisibleText(sendkeys);
						report.updateTestCaseLog("  selected the dropdown   ",value.toString(),Status.PASS);
						break;
					case 5:
						//submit
						appiumdriver.findElement(By.linkText(value)).submit();
						report.updateTestCaseLog("  Submitted   ",value.toString(),Status.PASS);
						break;
					case 6:
						appiumdriver.findElement(By.linkText(value)).isDisplayed();
						report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
						try{callMeToWait(3000);}catch(Exception e){}
						break;
					default :
						report.updateTestCaseLog("  Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);

					}
				}
			break;
			case Selenium_Desktop:
				present = "true";	
				for(int i=0;isElementPresent(By.linkText(value))==false;i++)
				{
					if(i>=60)
					{
						present="false";
						//report.updateTestCaseLog("Please verify  whether the entered element is Valid",value.toString(),Status.FAIL);
						break;
						//frameworkParameters.setStopExecution(true);
						//throw new FrameworkException(value.toString(), "Please verify  whether the entered element is Valid");
					}
				}	

				if(present.equals("true"))
				{

					switch(a)
					{
					case 1:
						//Click

						try {
							webdriver.findElement(By.linkText(value)).click();
							report.updateTestCaseLog("Clicked",value.toString(),Status.PASS);
						} 

						catch (Exception e)
						{
							//report.updateTestCaseLog(value.toString() +"Not Clicked",value.toString(),Status.FAIL);
							throw new FrameworkException(value.toString() +"Not Clicked", "failed to click");
						} 
						break;

					case 2:
						//clear
						webdriver.findElement(By.linkText(value)).clear();
						report.updateTestCaseLog("cleared",value.toString(),Status.PASS);

						break;
					case 3:
						//EnterValue
						webdriver.findElement(By.linkText(value)).sendKeys(sendkeys);
						report.updateTestCaseLog("  Typed :  :",value.toString(),Status.PASS);
						break;
					case 4:
						//Dropdown Selection
						Select select = new Select(webdriver.findElement(By.linkText(value)));
						select.selectByVisibleText(sendkeys);
						report.updateTestCaseLog("  selected the dropdown   ",value.toString(),Status.PASS);
						break;
					case 5:
						//submit
						webdriver.findElement(By.linkText(value)).submit();
						report.updateTestCaseLog("  Submitted   ",value.toString(),Status.PASS);
						break;
					case 6:
						webdriver.findElement(By.linkText(value)).isDisplayed();
						report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
						try{callMeToWait(3000);}catch(Exception e){}
						break;
					default :
						report.updateTestCaseLog("  Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);

					}
				}
			break;
				
			case  RemoteWebDriver:
			case MobileLabs:
				present = "true";	
				for(int i=0;isElementPresent(By.linkText(value))==false;i++)
				{
					if(i>=60)
					{
						present = "false";	
						report.updateTestCaseLog("Element Not Found",value.toString(),Status.FAIL);
						break;
						//frameworkParameters.setStopExecution(true);
						//throw new FrameworkException(value.toString(), "Please verify  whether the entered element is Valid");
					}
				}	
				
				if(present.equals("true"))
				{

				  switch(a)
					{
					case 1:
						//Click
					
						
						webdriver.findElement(By.linkText(value)).click();
						report.updateTestCaseLog("Clicked",value.toString(),Status.PASS);
						
						
						
						break;
						
					case 2:
						//clear
						webdriver.findElement(By.linkText(value)).clear();
						report.updateTestCaseLog("cleared",value.toString(),Status.PASS);
						
						break;
					case 3:
						//EnterValue
						webdriver.findElement(By.linkText(value)).sendKeys(sendkeys);
						report.updateTestCaseLog("  Typed :  :",value.toString(),Status.PASS);
						break;
					case 4:
						//Dropdown Selection
						Select select = new Select(webdriver.findElement(By.linkText(value)));
						select.selectByVisibleText(sendkeys);
						report.updateTestCaseLog("  selected the dropdown   ",value.toString(),Status.PASS);
						break;
					case 5:
						//submit
						webdriver.findElement(By.linkText(value)).submit();
						report.updateTestCaseLog("  Submitted   ",value.toString(),Status.PASS);
						break;
					default :
						report.updateTestCaseLog("  Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
							
					}
					
				}
			break;
			case Perfecto:
				present = "true";	
				for(int i=0;isElementPresent(By.linkText(value))==false;i++)
				{
					if(i>=60)
					{
						present="false";
						report.updateTestCaseLog("Please verify  whether the entered element is Valid",value.toString(),Status.FAIL);
						break;
						//frameworkParameters.setStopExecution(true);
						//throw new FrameworkException(value.toString(), "Please verify  whether the entered element is Valid");
					}
				}	

				if(present.equals("true"))
				{

					switch(a)
					{
					case 1:
						//Click

						try {
							perfectodriver.findElement(By.linkText(value)).click();
							report.updateTestCaseLog("Clicked",value.toString(),Status.PASS);
						} 

						catch (Exception e)
						{
							//report.updateTestCaseLog(value.toString() +"Not Clicked",value.toString(),Status.FAIL);
							throw new FrameworkException(value.toString() +"Not Clicked", "failed to click");
						} 
						break;

					case 2:
						//clear
						perfectodriver.findElement(By.linkText(value)).clear();
						report.updateTestCaseLog("cleared",value.toString(),Status.PASS);

						break;
					case 3:
						//EnterValue
						perfectodriver.findElement(By.linkText(value)).sendKeys(sendkeys);
						report.updateTestCaseLog("  Typed :  :",value.toString(),Status.PASS);
						break;
					case 4:
						//Dropdown Selection
						Select select = new Select(perfectodriver.findElement(By.linkText(value)));
						select.selectByVisibleText(sendkeys);
						report.updateTestCaseLog("  selected the dropdown   ",value.toString(),Status.PASS);
						break;
					case 5:
						//submit
						perfectodriver.findElement(By.linkText(value)).submit();
						report.updateTestCaseLog("  Submitted   ",value.toString(),Status.PASS);
						break;
					case 6:
						perfectodriver.findElement(By.linkText(value)).isDisplayed();
						report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
						try{callMeToWait(3000);}catch(Exception e){}
						break;
					default :
						report.updateTestCaseLog("  Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);

					}
				}
			break;
			default: report.updateTestCaseLog(" Please enter a valid ToolName in GlobalSettings ","",Status.FAIL); break;
			}

			
			

		}
		catch(ElementNotVisibleException ex)
		{

			exceptionmessage = "Element not found in the page";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);			

		}
		catch(UnhandledAlertException ex)
		{   
			exceptionmessage = "Alert is present,handle it";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);			
		}
		catch(UnreachableBrowserException ex)
		{
			exceptionmessage = "Problem Encountered in Webdriver";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);			
		}
		catch(NoSuchElementException ex)
		{
			exceptionmessage = "Failed to Identify";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);			
		}

	}
	public static boolean isElementPresent(By by)
	{
		switch(DriverScript.toolName){
		case Appium:
			try {
				return appiumdriver.findElement(by).isDisplayed();
				//return true;
			} catch (NoSuchElementException e) {
				return false;
			}
		case Selenium_Desktop:
		case RemoteWebDriver:
		case MobileLabs:
			try {
				return webdriver.findElement(by).isDisplayed();
				//return true;
			} catch (NoSuchElementException e) {
				return false;
			}
		case Perfecto:
			try {
				 perfectodriver.findElement(by);
				 return true;
				//return true;
			} catch (NoSuchElementException e) {
				return false;
			}
		default: return false;
		}
	}
	public static void callMeToWait(int milliSec)
	{
		try {
			Thread.sleep(milliSec);
		} catch (InterruptedException ie) 
		{
			// TODO Auto-generated catch block
			ie.printStackTrace();
		}
	}
	static void downloadReport(MobileDriver mobiledriver) {
		java.io.InputStream reportStream = mobiledriver.downloadReport(MediaType.PDF);
		if (reportStream != null) {
			String DeviceID = DriverScript.testParameters.getDeviceName();
			File reportFile = new File(DriverScript.reportPath+"\\Perfecto_Report_"+DeviceID+".pdf");			try {
				FileUtils.write(reportStream, reportFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	}
