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

	public static void act(WebElement Element,String action,String Sendkeys)
	{	
		try
		{
			if(Element.isDisplayed())
			{
				if (action=="click")
				{
					Element.click();
					report.updateTestCaseLog("Clicked ", Element.getText(), Status.PASS);
				}
				else if (action=="clear")
				{
					Element.clear();
					report.updateTestCaseLog("Cleared ", Element.getText(), Status.PASS);
				}
				else if(action=="entertext")
				{					
					Element.sendKeys(Sendkeys);
					report.updateTestCaseLog("Typed ", Element.getText(), Status.PASS);
				}
				else if(action.equalsIgnoreCase("select"))
				{
					Select select = new Select(Element);
					select.selectByValue(Sendkeys);
					report.updateTestCaseLog("Selected ", Element.getText(), Status.PASS);
				}
				else if(action=="submit")
				{
					Element.submit();
					report.updateTestCaseLog("Typed ", Element.getText(), Status.PASS);
				}
				else if(action=="verifytext")
				{					
					if(Element.getText().equals(Sendkeys))
					{					
						report.updateTestCaseLog("Text Verified ", Element.getText(), Status.PASS);					
					}
					else
					{
						report.updateTestCaseLog("Text NOT Verified ", Element.getText(), Status.PASS);
					}
				}
				
			}
		}
		catch(NoSuchElementException e)
		{
			System.out.println("here");
			report.updateTestCaseLog("Please verify  whether the entered element is Valid", "Element Not Present", Status.FAIL);
		}
	}
	
	public static void id(final String value,String action,String sendkeys)
	{
		// Here a value is getting set based on the action required

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
				present="true";
				for(int i=0;isElementPresent(By.id(value))==false;i++)
				{
					if(i>=60)
					{
						report.updateTestCaseLog("Please verify  whether the entered element is Valid ",value.toString(),Status.FAIL);
						present = "false";
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.id(value))==true)
					{
						System.out.println("Element Found :"+value.toString());
						switch(a)
						{
						case 1:
							try {
								appiumdriver.findElement(By.id(value)).click();
								report.updateTestCaseLog("  clicked  ",value.toString(),Status.PASS);
							} 
							catch (Exception e)
							{
								throw new FrameworkException(value.toString() +"Not Clicked", "failed to click");
							}
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							appiumdriver.findElement(By.id(value)).clear();
							report.updateTestCaseLog("  cleared  ",value.toString(),Status.PASS);
							break;
						case 3:
							appiumdriver.findElement(By.id(value)).sendKeys(sendkeys);
							report.updateTestCaseLog("  Typed   ",value.toString(),Status.PASS);
							break;
						case 4:
							Select select = new Select(appiumdriver.findElement(By.id(value)));
							select.selectByValue(sendkeys);
							report.updateTestCaseLog("  selected the dropdown :",value.toString(),Status.PASS);
							break;
						case 5:
							appiumdriver.findElement(By.id(value)).submit();
							report.updateTestCaseLog("  Submitted  ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							appiumdriver.findElement(By.id(value)).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog(" Please enter a valid Action ","",Status.FAIL);
						}
					}
				}
				break;
			case Selenium_Desktop:
			case  RemoteWebDriver:
			case MobileLabs:
				present = "true";
				for(int i=0;isElementPresent(By.id(value))==false;i++)
				{
					if(i>=60)
					{
						report.updateTestCaseLog("Element Not Found",value.toString(),Status.FAIL);
						present = "false";
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.id(value))==true)
					{
						System.out.println("Element Found : "+value.toString());
						switch(a)
						{
						case 1:
							//try {
								webdriver.findElement(By.id(value)).click();
								report.updateTestCaseLog("  clicked  ",value.toString(),Status.PASS);
							/*} 
							catch (Exception e)
							{
								throw new FrameworkException(value.toString() +"Not Clicked", "failed to click");
							}*/
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							webdriver.findElement(By.id(value)).clear();
							report.updateTestCaseLog("  cleared  ",value.toString(),Status.PASS);
							break;
						case 3:
							webdriver.findElement(By.id(value)).sendKeys(sendkeys);
							report.updateTestCaseLog("  Typed   ",value.toString(),Status.PASS);
							break;
						case 4:
							Select select = new Select(webdriver.findElement(By.id(value)));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown :",value.toString(),Status.PASS);
							break;
						case 5:
							webdriver.findElement(By.id(value)).submit();
							report.updateTestCaseLog("  Submitted  ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							webdriver.findElement(By.id(value)).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog(" Please enter a valid Action ","",Status.FAIL);
						}
					}
				}
				break;
			case Perfecto:
				present="true";
				for(int i=0;isElementPresent(By.id(value))==false;i++)
				{
					if(i>=60)
					{
						report.updateTestCaseLog("Please verify  whether the entered element is Valid ",value.toString(),Status.FAIL);
						present = "false";
						break;
					}
				}	
				if(present.equals("true"))
				{
						System.out.println("Element Found :"+value.toString());
						switch(a)
						{
						case 1:
							try {
								perfectodriver.findElement(By.id(value)).click();
								report.updateTestCaseLog("  clicked  ",value.toString(),Status.PASS);
							} 
							catch (Exception e)
							{
								throw new FrameworkException(value.toString() +"Not Clicked", "failed to click");
							}
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							perfectodriver.findElement(By.id(value)).clear();
							report.updateTestCaseLog("  cleared  ",value.toString(),Status.PASS);
							break;
						case 3:
							perfectodriver.findElement(By.id(value)).sendKeys(sendkeys);
							report.updateTestCaseLog("  Typed   ",value.toString(),Status.PASS);
							break;
						case 4:
							Select select = new Select(perfectodriver.findElement(By.id(value)));
							select.selectByValue(sendkeys);
							report.updateTestCaseLog("  selected the dropdown :",value.toString(),Status.PASS);
							break;
						case 5:
							perfectodriver.findElement(By.id(value)).submit();
							report.updateTestCaseLog("  Submitted  ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							perfectodriver.findElement(By.id(value)).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog(" Please enter a valid Action ","",Status.FAIL);
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
		catch(NoSuchElementException ex)
		{
			exceptionmessage = "Element not found in the page";
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
		catch(Exception ex)
		{
			exceptionmessage = "Problem Encountered in Webdriver";
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



	//This function is not used anywhere This is for closing the popups
	public static boolean clickIfElementPresent(By by) {
		switch(DriverScript.toolName){
		case Appium:
			try {
				appiumdriver.findElement(by);	
				appiumdriver.findElement(by).click();
				report.updateTestCaseLog("  Clicked  ",by.toString(),Status.PASS);
				return true;
			} catch (Exception e) {
				report.updateTestCaseLog("  Element NOT present  ",by.toString(),Status.FAIL);
				return false;
			}
		case Selenium_Desktop:
		case  RemoteWebDriver:
		case MobileLabs:
			try {
				webdriver.findElement(by);	
				webdriver.findElement(by).click();
				report.updateTestCaseLog("  Clicked  ",by.toString(),Status.PASS);
				return true;
			} catch (Exception e) {
				report.updateTestCaseLog("  Element NOT present  ",by.toString(),Status.FAIL);
				return false;
			}
		case Perfecto:
			try {
				perfectodriver.findElement(by);	
				perfectodriver.findElement(by).click();
				report.updateTestCaseLog("  Clicked  ",by.toString(),Status.PASS);
				return true;
			} catch (Exception e) {
				report.updateTestCaseLog("  Element NOT present  ",by.toString(),Status.FAIL);
				return false;
			}
		default: return false;
		}
		
	}
	
	public static boolean enterTextIfElementPresent(By by,String value) {
		switch(DriverScript.toolName){
		case Appium:
			try {
				appiumdriver.findElement(by);	
				appiumdriver.findElement(by).sendKeys(value);
				report.updateTestCaseLog("  EnterText  ",value,Status.PASS);
				return true;
			} catch (Exception e) {
				report.updateTestCaseLog("  Element NOT present  ",value,Status.FAIL);
				return false;
			}
		case Selenium_Desktop:
		case  RemoteWebDriver:
		case MobileLabs:
			try {
				webdriver.findElement(by);	
				webdriver.findElement(by).sendKeys(value);
				report.updateTestCaseLog("  EnterText  ",value,Status.PASS);
				return true;
			} catch (Exception e) {
				report.updateTestCaseLog("  Element NOT present  ",value,Status.FAIL);
				return false;
			}
		case  Perfecto:
			try {
				perfectodriver.findElement(by);	
				perfectodriver.findElement(by).sendKeys(value);
				report.updateTestCaseLog("  EnterText  ",value,Status.PASS);
				return true;
			} catch (Exception e) {
				report.updateTestCaseLog("  Element NOT present  ",value,Status.FAIL);
				return false;
			}
		default: return false;
		}
		
	}

	//LINK TEXT linktext
	/**
	 * Function to identify the link text of the object and set value to the object and run corresponding elements
	 * @param value The unique value of the object
	 * @param action The action to be performed on the object selected
	 * @param sendkeys The parameter to be passed during execution
	 */
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
	//@SuppressWarnings("unchecked")
	/**
	 * Function to identify the object's class name and set value to the object and run corresponding elements
	 * @param value The unique value of the object
	 * @param action The action to be performed on the object selected
	 * @param sendkeys The parameter to be passed during execution
	 */
	public static void className(final String value,String action,String sendkeys) 
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

				for(int i=0;isElementPresent(By.className(value))==false;i++)
				{
					if(i>=60)
					{
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						present = "false";
						break;
					}
				}	
				if(present.equals("true"))
				{

					if(isElementPresent(By.className(value))==true)
					{

						switch(a)
						{
						case 1:
							//Click
							System.out.println("Going to click:"+value.toString());
							//screenshot.getScreenShot();
							appiumdriver.findElement(By.className(value)).click();
							System.out.println(value.toString()+" clicked");
							report.updateTestCaseLog("  clicked  ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							//clear
							appiumdriver.findElement(By.className(value)).clear();
							report.updateTestCaseLog("  cleared  ",value.toString(),Status.PASS);
							break;
						case 3:
							//EnterValue
							appiumdriver.findElement(By.className(value)).sendKeys(sendkeys);
							report.updateTestCaseLog("  Typed   ",value.toString(),Status.PASS);
							break;
						case 4:
							//Dropdown Selection
							Select select = new Select(appiumdriver.findElement(By.className(value)));
							select.selectByVisibleText(sendkeys);
							System.out.println("Selecting "+sendkeys.toString()+" from drop down list of "+value.toString());
							report.updateTestCaseLog("  selected the dropdown  ",value.toString(),Status.PASS);
							break;
						case 5:
							//screenshot.getScreenShot();
							appiumdriver.findElement(By.className(value)).submit();
							report.updateTestCaseLog("  Submitted  ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							appiumdriver.findElement(By.className(value)).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("  Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);

						}
					}
				}
			break;
			case Selenium_Desktop:
			case RemoteWebDriver:
			case MobileLabs:
				present = "true";
				
				for(int i=0;isElementPresent(By.className(value))==false;i++)
				{
					if(i>=60)
					{
						report.updateTestCaseLog("Element Not Found",value.toString(),Status.FAIL);
						present = "false";
						break;
					}
				}
				if(present.equals("true"))
				{

					if(isElementPresent(By.className(value))==true)
					{
			
							switch(a)
							{
							case 1:
								//Click
								System.out.println("Going to click:"+value.toString());
								//screenshot.getScreenShot();
								webdriver.findElement(By.className(value)).click();
								System.out.println(value.toString()+" clicked");
								report.updateTestCaseLog("  clicked  ",value.toString(),Status.PASS);
								try{callMeToWait(3000);}catch(Exception e){}
								break;
							case 2:
								//clear
								webdriver.findElement(By.className(value)).clear();
								report.updateTestCaseLog("  cleared  ",value.toString(),Status.PASS);
								break;
							case 3:
								//EnterValue
								webdriver.findElement(By.className(value)).sendKeys(sendkeys);
								report.updateTestCaseLog("  Typed   ",value.toString(),Status.PASS);
								break;
							case 4:
								//Dropdown Selection
								Select select = new Select(webdriver.findElement(By.className(value)));
								select.selectByVisibleText(sendkeys);
								System.out.println("Selecting "+sendkeys.toString()+" from drop down list of "+value.toString());
								report.updateTestCaseLog("  selected the dropdown  ",value.toString(),Status.PASS);
								break;
							case 5:
								//screenshot.getScreenShot();
								webdriver.findElement(By.className(value)).submit();
								report.updateTestCaseLog("  Submitted  ",value.toString(),Status.PASS);
								try{callMeToWait(3000);}catch(Exception e){}
								break;
							
							default :
								report.updateTestCaseLog("  Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
									
							}
					}
				}
			break;
			case Perfecto:
				present = "true";

				for(int i=0;isElementPresent(By.className(value))==false;i++)
				{
					if(i>=60)
					{
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						present = "false";
						break;
					}
				}	
				if(present.equals("true"))
				{
						switch(a)
						{
						case 1:
							//Click
							System.out.println("Going to click:"+value.toString());
							//screenshot.getScreenShot();
							perfectodriver.findElement(By.className(value)).click();
							System.out.println(value.toString()+" clicked");
							report.updateTestCaseLog("  clicked  ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							//clear
							perfectodriver.findElement(By.className(value)).clear();
							report.updateTestCaseLog("  cleared  ",value.toString(),Status.PASS);
							break;
						case 3:
							//EnterValue
							perfectodriver.findElement(By.className(value)).sendKeys(sendkeys);
							report.updateTestCaseLog("  Typed   ",value.toString(),Status.PASS);
							break;
						case 4:
							//Dropdown Selection
							Select select = new Select(perfectodriver.findElement(By.className(value)));
							select.selectByVisibleText(sendkeys);
							System.out.println("Selecting "+sendkeys.toString()+" from drop down list of "+value.toString());
							report.updateTestCaseLog("  selected the dropdown  ",value.toString(),Status.PASS);
							break;
						case 5:
							//screenshot.getScreenShot();
							perfectodriver.findElement(By.className(value)).submit();
							report.updateTestCaseLog("  Submitted  ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							perfectodriver.findElement(By.className(value)).isDisplayed();
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
			report.updateTestCaseLog(exceptionmessage,"",Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);			    
		}
		catch(UnhandledAlertException ex)
		{
			exceptionmessage = "Alert is present,handle it";
			report.updateTestCaseLog(exceptionmessage,"",Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);			    
		}
		catch(NoSuchElementException ex)
		{
			exceptionmessage = "Element not found in the page";
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


	}	

	//CSS selector		
	/**
	 * Function to identify the css selector value of the object and set value to the object and run corresponding elements
	 * @param value The unique value of the object
	 * @param action The action to be performed on the object selected
	 * @param sendkeys The parameter to be passed during execution
	 */
	public static void cssSelector(final String value,String action,String sendkeys) 
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
		String  exceptionmessage;
		try
		{
			switch(DriverScript.toolName){
			case Appium:
				present="true";
				for(int i=0;isElementPresent(By.cssSelector(value))==false;i++)
				{
					try{callMeToWait(5000);}catch(Exception e){}
					System.out.println("Waiting for the element to load");
					report.updateTestCaseLog("  Waiting for the element : ",value.toString(),Status.PASS);
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.cssSelector(value))==true)
					{
						System.out.println("Element Found :"+value.toString());
						switch(a)
						{
						case 1:			
							appiumdriver.findElement(By.cssSelector(value)).click();
							report.updateTestCaseLog("clicked",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							appiumdriver.findElement(By.cssSelector(value)).clear();
							report.updateTestCaseLog("cleared",value.toString(),Status.PASS);
							break;
						case 3:
							appiumdriver.findElement(By.cssSelector(value)).sendKeys(sendkeys);
							report.updateTestCaseLog("Typed",value.toString(),Status.PASS);
							break;
						case 4:
							Select select = new Select(appiumdriver.findElement(By.cssSelector(value)));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString(),Status.PASS);
							break;
						case 5:
							appiumdriver.findElement(By.cssSelector(value)).submit();
							report.updateTestCaseLog("  Submitted :  : ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							appiumdriver.findElement(By.cssSelector(value)).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("  Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);

						}
					}
					}
			break;
			case Selenium_Desktop:
			case  RemoteWebDriver:		
			case MobileLabs:	
				present="true";
				for(int i=0;isElementPresent(By.cssSelector(value))==false;i++)
				{
					try{callMeToWait(5000);}catch(Exception e){}
					//System.out.println("Waiting for the element to load");
					//report.updateTestCaseLog("  Waiting for the element : ",value.toString(),Status.PASS);
					if(i>=2)
					{
						present="false";
						report.updateTestCaseLog("Element Not Found",value.toString(),Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
				if(isElementPresent(By.cssSelector(value))==true)
				{
					System.out.println("Element Found :"+value.toString());
						switch(a)
						{
						case 1:			
							webdriver.findElement(By.cssSelector(value)).click();
							report.updateTestCaseLog("clicked",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							webdriver.findElement(By.cssSelector(value)).clear();
							report.updateTestCaseLog("cleared",value.toString(),Status.PASS);
							break;
						case 3:
							webdriver.findElement(By.cssSelector(value)).sendKeys(sendkeys);
							report.updateTestCaseLog("Typed",value.toString(),Status.PASS);
							break;
						case 4:
							Select select = new Select(webdriver.findElement(By.cssSelector(value)));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString(),Status.PASS);
							break;
						case 5:
							webdriver.findElement(By.cssSelector(value)).submit();
							report.updateTestCaseLog("  Submitted :  : ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("  Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
								
						}
			}}
			break;
			case Perfecto:
				present="true";
				for(int i=0;isElementPresent(By.cssSelector(value))==false;i++)
				{
					try{callMeToWait(5000);}catch(Exception e){}
					System.out.println("Waiting for the element to load");
					report.updateTestCaseLog("  Waiting for the element : ",value.toString(),Status.PASS);
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					
						System.out.println("Element Found :"+value.toString());
						switch(a)
						{
						case 1:			
							perfectodriver.findElement(By.cssSelector(value)).click();
							report.updateTestCaseLog("clicked",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							perfectodriver.findElement(By.cssSelector(value)).clear();
							report.updateTestCaseLog("cleared",value.toString(),Status.PASS);
							break;
						case 3:
							perfectodriver.findElement(By.cssSelector(value)).sendKeys(sendkeys);
							report.updateTestCaseLog("Typed",value.toString(),Status.PASS);
							break;
						case 4:
							Select select = new Select(perfectodriver.findElement(By.cssSelector(value)));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString(),Status.PASS);
							break;
						case 5:
							perfectodriver.findElement(By.cssSelector(value)).submit();
							report.updateTestCaseLog("  Submitted :  : ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							perfectodriver.findElement(By.cssSelector(value)).isDisplayed();
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
		catch(NoSuchElementException ex)
		{
			exceptionmessage = "Element not found in the page";
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

	}

	//XPath
	/**
	 * Function to identify the xpath of the object and set value to the object and run corresponding elements
	 * @param value The unique value of the object
	 * @param action The action to be performed on the object selected
	 * @param sendkeys The parameter to be passed during execution
	 */
	public static void xpath(final String value,String action,String sendkeys) 
	{

		// Here a value is getting set based on the action required

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
		else if(action=="verifytext")
		{
			a=7;
		}



		String exceptionmessage;
		try
		{
			switch(DriverScript.toolName){
			case Appium:
				present="true";
				for(int i=0;isElementPresent(By.xpath(value))==false;i++)
				{

					if(i>=60)
					{
						report.updateTestCaseLog("Please verify  whether the entered element is Valid ",value.toString(),Status.FAIL);
						present = "false";
						break;
					}
				}	

				if(present.equals("true"))
				{

					if(isElementPresent(By.xpath(value))==true)
					{
						System.out.println("Element Found :"+value.toString());
						switch(a)
						{
						case 1:

							try {
								appiumdriver.findElement(By.xpath(value)).click();
								report.updateTestCaseLog("  clicked  ",value.toString(),Status.PASS);
							} 

							catch (Exception e)
							{
								throw new FrameworkException(value.toString() +"Not Clicked", "failed to click");
							}

							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							appiumdriver.findElement(By.xpath(value)).clear();
							report.updateTestCaseLog("  cleared  ",value.toString(),Status.PASS);
							break;
						case 3:
							appiumdriver.findElement(By.xpath(value)).sendKeys(sendkeys);
							report.updateTestCaseLog("  Typed   ",value.toString(),Status.PASS);
							break;
						case 4:
							Select select = new Select(appiumdriver.findElement(By.xpath(value)));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown :",value.toString(),Status.PASS);
							break;
						case 5:
							appiumdriver.findElement(By.xpath(value)).submit();
							report.updateTestCaseLog("  Submitted  ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							appiumdriver.findElement(By.xpath(value)).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 7:
							//driver.findElement(By.xpath(value)).isDisplayed();
							if(appiumdriver.findElement(By.xpath(value)).isDisplayed()){
								if(appiumdriver.findElement(By.xpath(value)).getText().equals(sendkeys)){
									report.updateTestCaseLog(" Text Verified    ",sendkeys,Status.PASS);
								}else{
									report.updateTestCaseLog(" Text NOT Verified    ",sendkeys,Status.FAIL);
								}
							}
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog(" Please enter a valid Action ","",Status.FAIL);

						}
					}
				}
			break;
			case Selenium_Desktop:
			case  RemoteWebDriver:			
			case MobileLabs:
				present = "true";
				for(int i=0;isElementPresent(By.xpath(value))==false;i++)
				{
					if(i>=10)
					{
						report.updateTestCaseLog("Element Not Found",value.toString(),Status.FAIL);
						present = "false";
						
						break;
					}
				}	
				if(present.equals("true"))
				{
				if(isElementPresent(By.xpath(value))==true)
				{
					
					System.out.println("Element Found :"+value.toString());
						switch(a)
						{
						case 1:
							webdriver.findElement(By.xpath(value)).click();
							//webdriver.findElement(By.xpath(value.toString())).click();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							webdriver.findElement(By.xpath(value)).clear();
							report.updateTestCaseLog("  cleared   ",value.toString(),Status.PASS);
							break;
						case 3:
							webdriver.findElement(By.xpath(value)).sendKeys(sendkeys);
							report.updateTestCaseLog("  Typed :  : ",value.toString(),Status.PASS);
							break;
						case 4:	
							Select select = new Select(webdriver.findElement(By.xpath(value)));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString(),Status.PASS);						
							break;
						case 5:
							webdriver.findElement(By.xpath(value)).submit();
							report.updateTestCaseLog("  Submitted :  : ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("  Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
								
						}
				}}
			break;
			case Perfecto:
				present = "true";
				for(int i=0;isElementPresent(By.xpath(value))==false;i++)
				{
					if(i>=65)
					{
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						present = "false";
						break;
					}
				}	
				if(present.equals("true"))
				{
				
					
					System.out.println("Element Found :"+value.toString());
						switch(a)
						{
						case 1:
							perfectodriver.findElement(By.xpath(value)).click();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							//try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							perfectodriver.findElement(By.xpath(value)).clear();
							report.updateTestCaseLog("  cleared   ",value.toString(),Status.PASS);
							break;
						case 3:
							perfectodriver.findElement(By.xpath(value)).sendKeys(sendkeys);
							report.updateTestCaseLog("  Typed :  : ",value.toString(),Status.PASS);
							break;
						case 4:	
							Select select = new Select(perfectodriver.findElement(By.xpath(value)));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString(),Status.PASS);						
							break;
						case 5:
							perfectodriver.findElement(By.xpath(value)).submit();
							report.updateTestCaseLog("  Submitted :  : ",value.toString(),Status.PASS);
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
		catch(NoSuchElementException ex)
		{
			exceptionmessage = "Element not found in the page";
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
		catch(Exception ex)

		{
			exceptionmessage = "Problem Encountered in Webdriver";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);	   	

		}
	}

	//TagName
	/**
	 * Function to identify the tagname of the object and set value to the object and run corresponding elements
	 * @param value The unique value of the object
	 * @param action The action to be performed on the object selected
	 * @param sendkeys The parameter to be passed during execution
	 */
	public static void TagName(final String value,String action,String sendkeys) 
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
				present="true";
				for(int i=0;isElementPresent(By.tagName(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.tagName(value))==true)
					{
						System.out.println("Element Found :"+value.toString());

						switch(a)
						{
						case 1:
							appiumdriver.findElement(By.tagName(value)).click();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							appiumdriver.findElement(By.tagName(value)).clear();
							report.updateTestCaseLog("  cleared   ",value.toString(),Status.PASS);
							break;
						case 3:
							appiumdriver.findElement(By.tagName(value)).sendKeys(sendkeys);
							report.updateTestCaseLog("  Typed :  :",value.toString(),Status.PASS);
							break;
						case 4:	
							Select select = new Select(appiumdriver.findElement(By.tagName(value)));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown :  : ",value.toString(),Status.PASS);
							break;
						case 5:
							appiumdriver.findElement(By.tagName(value)).submit();
							report.updateTestCaseLog("  Submitted :  : ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							appiumdriver.findElement(By.tagName(value)).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("  Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);

						}
					}}
			break;
			case Selenium_Desktop:
			case  RemoteWebDriver:		
			case MobileLabs:
				present="true";
				for(int i=0;isElementPresent(By.tagName(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
				if(isElementPresent(By.tagName(value))==true)
				{
					System.out.println("Element Found :"+value.toString());
						
						switch(a)
						{
						case 1:
							webdriver.findElement(By.tagName(value)).click();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							webdriver.findElement(By.tagName(value)).clear();
							report.updateTestCaseLog("  cleared   ",value.toString(),Status.PASS);
							break;
						case 3:
							webdriver.findElement(By.tagName(value)).sendKeys(sendkeys);
							report.updateTestCaseLog("  Typed :  :",value.toString(),Status.PASS);
							break;
						case 4:	
							Select select = new Select(webdriver.findElement(By.tagName(value)));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown :  : ",value.toString(),Status.PASS);
							break;
						case 5:
							webdriver.findElement(By.tagName(value)).submit();
							report.updateTestCaseLog("  Submitted :  : ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("  Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
								
						}
				}}
			break;
			case Perfecto:
				present="true";
				for(int i=0;isElementPresent(By.tagName(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
						System.out.println("Element Found :"+value.toString());

						switch(a)
						{
						case 1:
							perfectodriver.findElement(By.tagName(value)).click();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							perfectodriver.findElement(By.tagName(value)).clear();
							report.updateTestCaseLog("  cleared   ",value.toString(),Status.PASS);
							break;
						case 3:
							perfectodriver.findElement(By.tagName(value)).sendKeys(sendkeys);
							report.updateTestCaseLog("  Typed :  :",value.toString(),Status.PASS);
							break;
						case 4:	
							Select select = new Select(perfectodriver.findElement(By.tagName(value)));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown :  : ",value.toString(),Status.PASS);
							break;
						case 5:
							perfectodriver.findElement(By.tagName(value)).submit();
							report.updateTestCaseLog("  Submitted :  : ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							perfectodriver.findElement(By.tagName(value)).isDisplayed();
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
		catch(NoSuchElementException ex)
		{
			exceptionmessage = "Element not found in the page";
			report.updateTestCaseLog(exceptionmessage,"",Status.FAIL);
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
	}

	//Name
	/**
	 * Function to identify the Name of the object and set value to the object and run corresponding elements
	 * @param value The unique value of the object
	 * @param action The action to be performed on the object selected
	 * @param sendkeys The parameter to be passed during execution
	 */
	public static void Name(final String value,String action,String sendkeys) 
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
				present="true";
				for(int i=0;isElementPresent(By.name(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.name(value))==true)
					{
						System.out.println("Element Found :"+value.toString());
						switch(a)
						{
						case 1:
							appiumdriver.findElement(By.name(value)).click();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							appiumdriver.findElement(By.name(value)).clear();
							report.updateTestCaseLog("  cleared   ",value.toString(),Status.PASS);
							break;
						case 3:
							appiumdriver.findElement(By.name(value)).sendKeys(sendkeys);
							report.updateTestCaseLog("  Typed :  : ",value.toString(),Status.PASS);
							break;
						case 4:
							Select select = new Select(appiumdriver.findElement(By.name(value)));
							select.selectByValue(sendkeys);
							report.updateTestCaseLog("selected the dropdown  : ",value.toString(),Status.PASS);
							break;
						case 5:
							appiumdriver.findElement(By.name(value)).submit();
							report.updateTestCaseLog("  Submitted    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							appiumdriver.findElement(By.name(value)).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("  Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);

						}

					}
				}
			break;
			case Selenium_Desktop:
			case  RemoteWebDriver:
			case MobileLabs:
				present="true";
				for(int i=0;isElementPresent(By.name(value))==false;i++)
				{
					if(i>=60)
					{
						present="false";
						report.updateTestCaseLog("Element Not Found",value.toString(),Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
				if(isElementPresent(By.name(value))==true)
				{
					System.out.println("Element Found :"+value.toString());
					switch(a)
						{
						case 1:
							webdriver.findElement(By.name(value)).click();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							webdriver.findElement(By.name(value)).clear();
							report.updateTestCaseLog("  cleared   ",value.toString(),Status.PASS);
							break;
						case 3:
							webdriver.findElement(By.name(value)).sendKeys(sendkeys);
							report.updateTestCaseLog("  Typed :  : ",value.toString(),Status.PASS);
							break;
						case 4:
							Select select = new Select(webdriver.findElement(By.name(value)));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("selected the dropdown  : ",value.toString(),Status.PASS);
							break;
						case 5:
							webdriver.findElement(By.name(value)).submit();
							report.updateTestCaseLog("  Submitted    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("  Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
								
						}
						
					}
				}
			break;
			case Perfecto:
				present="true";
				for(int i=0;isElementPresent(By.name(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
						System.out.println("Element Found :"+value.toString());
						switch(a)
						{
						case 1:
							perfectodriver.findElement(By.name(value)).click();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							perfectodriver.findElement(By.name(value)).clear();
							report.updateTestCaseLog("  cleared   ",value.toString(),Status.PASS);
							break;
						case 3:
							perfectodriver.findElement(By.name(value)).sendKeys(sendkeys);
							report.updateTestCaseLog("  Typed :  : ",value.toString(),Status.PASS);
							break;
						case 4:
							Select select = new Select(perfectodriver.findElement(By.name(value)));
							select.selectByValue(sendkeys);
							report.updateTestCaseLog("selected the dropdown  : ",value.toString(),Status.PASS);
							break;
						case 5:
							perfectodriver.findElement(By.name(value)).submit();
							report.updateTestCaseLog("  Submitted    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							perfectodriver.findElement(By.name(value)).isDisplayed();
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
			exceptionmessage = "Element not found in the page";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);					

		}
	}	

	//Partial LInk Text
	/**
	 * Function to identify the Partial linktext of the object and set value to the object and run corresponding elements
	 * @param value The unique value of the object
	 * @param action The action to be performed on the object selected
	 * @param sendkeys The parameter to be passed during execution
	 */	
	public static void partialLinkText(final String value,String action,String sendkeys) 
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
				present="true";
				System.out.println(value);	
				for(int i=0;isElementPresent(By.partialLinkText(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.partialLinkText(value))==true)
					{

						System.out.println("Element Found :"+value.toString());


						switch(a)
						{
						case 1:
							appiumdriver.findElement(By.partialLinkText(value)).click();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							appiumdriver.findElement(By.partialLinkText(value)).clear();
							report.updateTestCaseLog("  cleared   ",value.toString(),Status.PASS);
							break;
						case 3:
							appiumdriver.findElement(By.partialLinkText(value)).sendKeys(sendkeys);
							report.updateTestCaseLog("  Typed :  : ",value.toString(),Status.PASS);
							break;
						case 4:
							Select select = new Select(appiumdriver.findElement(By.partialLinkText(value)));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString(),Status.PASS);
							break;
						case 5:
							appiumdriver.findElement(By.partialLinkText(value)).submit();
							report.updateTestCaseLog("  Submitted :  : ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							appiumdriver.findElement(By.partialLinkText(value)).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
						}
					}
				}
			break;
			case Selenium_Desktop:
			case  RemoteWebDriver:		
			case MobileLabs:
				 present="true";
					for(int i=0;isElementPresent(By.partialLinkText(value))==false;i++)
					{
						if(i>=65)
						{
							present="false";
							report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
							break;
						}
					}	
					if(present.equals("true"))
					{
					if(isElementPresent(By.partialLinkText(value))==true)
					{
			
						System.out.println("Element Found :"+value.toString());
			
				
							switch(a)
							{
							case 1:
								webdriver.findElement(By.partialLinkText(value)).click();
								report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
								try{callMeToWait(3000);}catch(Exception e){}
								break;
							case 2:
								webdriver.findElement(By.partialLinkText(value)).clear();
								report.updateTestCaseLog("  cleared   ",value.toString(),Status.PASS);
								break;
							case 3:
								webdriver.findElement(By.partialLinkText(value)).sendKeys(sendkeys);
								report.updateTestCaseLog("  Typed :  : ",value.toString(),Status.PASS);
								break;
							case 4:
								Select select = new Select(webdriver.findElement(By.partialLinkText(value)));
								select.selectByVisibleText(sendkeys);
								report.updateTestCaseLog("  selected the dropdown    ",value.toString(),Status.PASS);
								break;
							case 5:
								webdriver.findElement(By.partialLinkText(value)).submit();
								report.updateTestCaseLog("  Submitted :  : ",value.toString(),Status.PASS);
								try{callMeToWait(3000);}catch(Exception e){}
								break;
							default :
								report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
							}
							}}
			break;
			case Perfecto:
				present="true";
				System.out.println(value);	
				for(int i=0;isElementPresent(By.partialLinkText(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{

						System.out.println("Element Found :"+value.toString());


						switch(a)
						{
						case 1:
							perfectodriver.findElement(By.partialLinkText(value)).click();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							perfectodriver.findElement(By.partialLinkText(value)).clear();
							report.updateTestCaseLog("  cleared   ",value.toString(),Status.PASS);
							break;
						case 3:
							perfectodriver.findElement(By.partialLinkText(value)).sendKeys(sendkeys);
							report.updateTestCaseLog("  Typed :  : ",value.toString(),Status.PASS);
							break;
						case 4:
							Select select = new Select(perfectodriver.findElement(By.partialLinkText(value)));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString(),Status.PASS);
							break;
						case 5:
							perfectodriver.findElement(By.partialLinkText(value)).submit();
							report.updateTestCaseLog("  Submitted :  : ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							perfectodriver.findElement(By.partialLinkText(value)).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
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
		catch(NoSuchElementException ex)
		{
			exceptionmessage = "Element not found in the page";
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
	}

	public static void intoFrame(String name)
	{
		switch(DriverScript.toolName){
		case Appium:
			appiumdriver.switchTo().frame(name);
		break;
		case Selenium_Desktop:
		case  RemoteWebDriver:	
		case MobileLabs:		
			webdriver.switchTo().frame(name);
		break;
		case Perfecto:
			perfectodriver.switchTo().frame(name);
		break;
		default: report.updateTestCaseLog(" Please enter a valid ToolName in GlobalSettings ","",Status.FAIL); break;
		}		
	}

	public static void outofFrame(String name1)
	{
		switch(DriverScript.toolName){
		case Appium:
			appiumdriver.switchTo().frame(name1);
		break;
		case Selenium_Desktop:
		case  RemoteWebDriver:		
		case MobileLabs:
			webdriver.switchTo().frame(name1);
		break;
		case Perfecto:
			perfectodriver.switchTo().frame(name1);
		break;
		default: report.updateTestCaseLog(" Please enter a valid ToolName in GlobalSettings ","",Status.FAIL); break;
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
	//                                                        
	public static void getLogCat(String emulatoractioner,String logFileStorePathWithFileName)
	{

		try {
			File logCatFile=new File(logFileStorePathWithFileName);
			if(!logCatFile.exists())
			{
				logCatFile.mkdirs();
			}
			Process pr = Runtime.getRuntime().exec("cmd /c adb -s " + emulatoractioner + " logcat");
			OutputStream os = new FileOutputStream(logCatFile);
			InputStream in = (InputStream) pr.getInputStream();
			byte[] buffer = new byte[4096];
			for (int n; (n = in.read(buffer)) != -1;) {

				os.write(buffer, 0, n);


			}
			in.close();
			os.close();
			pr.destroy();
		} catch (IOException ex) {
			ex.printStackTrace();
		}


	}
	//                                                          -



	protected void executeTestCase() throws InterruptedException {
		// TODO Auto-generated method stub

	}


	public static void findElementsByClassName(String value,int index,String action,String sendkeys){

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
				present="true";
				for(int i=0;isElementPresent(By.name(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.name(value))==true)
					{
						System.out.println("Element Found :"+value.toString());

						switch(a)
						{
						case 1:
							appiumdriver.findElementsByClassName(value).get(index).click();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							appiumdriver.findElementsByClassName(value).get(index).clear();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							break;
						case 3:
							appiumdriver.findElementsByClassName(value).get(index).sendKeys(sendkeys);
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							break;
						case 4:
							Select select = new Select(appiumdriver.findElementsByClassName(value).get(index));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString()+index,Status.PASS);
							break;
						case 5:
							appiumdriver.findElementsByClassName(value).get(index).submit();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							appiumdriver.findElementsByClassName(value).get(index).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
						}		
					}
				}
			break;
			case Selenium_Desktop:
			case  RemoteWebDriver:	
			case MobileLabs:
			case  Perfecto:
				report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
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
			exceptionmessage = "Element not found in the page";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);					

		}

	}


	public static void findElementByAccessibilityId(String value,int index,String action,String sendkeys){

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
				present="true";
				for(int i=0;isElementPresent(By.name(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.name(value))==true)
					{
						System.out.println("Element Found :"+value.toString());

						switch(a)
						{
						case 1:
							appiumdriver.findElementByAccessibilityId(value).click();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							appiumdriver.findElementByAccessibilityId(value).clear();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							break;
						case 3:
							appiumdriver.findElementByAccessibilityId(value).sendKeys(sendkeys);
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							break;
						case 4:
							Select select = new Select(appiumdriver.findElementByAccessibilityId(value));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString(),Status.PASS);
							break;
						case 5:
							appiumdriver.findElementByAccessibilityId(value).submit();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							appiumdriver.findElementByAccessibilityId(value).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
						}		
					}
				}
			break;
			case Selenium_Desktop:		
			case  RemoteWebDriver:	
			case MobileLabs:
			case Perfecto:
				report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
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
			exceptionmessage = "Element not found in the page";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);					

		}

	}

	/*public static void findElementByAndroidUIAutomator(String value,int index,String action,String sendkeys){

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
				present="true";
				for(int i=0;isElementPresent(By.name(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.name(value))==true)
					{
						System.out.println("Element Found :"+value.toString());


						switch(a)
						{
						case 1:
							driver.findElementByAndroidUIAutomator(value).click();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							driver.findElementByAndroidUIAutomator(value).clear();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							break;
						case 3:
							driver.findElementByAndroidUIAutomator(value).sendKeys(sendkeys);
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							break;
						case 4:
							Select select = new Select(driver.findElementByAndroidUIAutomator(value));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString()+index,Status.PASS);
							break;
						case 5:
							driver.findElementByAndroidUIAutomator(value).submit();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							driver.findElementByAndroidUIAutomator(value).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
						}		

					}
				}
			break;
			case Selenium_Desktop:		
			case  RemoteWebDriver:
		case MobileLabs:
			case Perfecto:		
				report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
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
			exceptionmessage = "Element not found in the page";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);					

		}
	}*/

	public static void findElementByClassName(String value,String action,String sendkeys){

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
				present="true";
				for(int i=0;isElementPresent(By.name(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.name(value))==true)
					{
						System.out.println("Element Found :"+value.toString());


						switch(a)
						{
						case 1:
							appiumdriver.findElementByClassName(value).click();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							appiumdriver.findElementByClassName(value).clear();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							break;
						case 3:
							appiumdriver.findElementByClassName(value).sendKeys(sendkeys);
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							break;
						case 4:
							Select select = new Select(appiumdriver.findElementByClassName(value));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString(),Status.PASS);
							break;
						case 5:
							appiumdriver.findElementByClassName(value).submit();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							appiumdriver.findElementByClassName(value).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
						}		
					}
				}
			break;
			case Selenium_Desktop:			
			case  RemoteWebDriver:	
			case MobileLabs:
			case Perfecto:
				report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
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
			exceptionmessage = "Element not found in the page";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);					

		}
	}

	public static void findElementByCssSelector(String value,int index,String action,String sendkeys){

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
				present="true";
				for(int i=0;isElementPresent(By.name(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.name(value))==true)
					{
						System.out.println("Element Found :"+value.toString());


						switch(a)
						{
						case 1:
							appiumdriver.findElementByCssSelector(value).click();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							appiumdriver.findElementByCssSelector(value).clear();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							break;
						case 3:
							appiumdriver.findElementByCssSelector(value).sendKeys(sendkeys);
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							break;
						case 4:
							Select select = new Select(appiumdriver.findElementByCssSelector(value));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString()+index,Status.PASS);
							break;
						case 5:
							appiumdriver.findElementByCssSelector(value).submit();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							appiumdriver.findElementByCssSelector(value).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
						}		
					}
				}
			break;
			case Selenium_Desktop:		
			case  RemoteWebDriver:		
			case MobileLabs:
			case Perfecto:
				report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
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
			exceptionmessage = "Element not found in the page";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);					

		}
	}

	public static void findElementById(String value,String action,String sendkeys){

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
				present="true";
				for(int i=0;isElementPresent(By.name(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.name(value))==true)
					{
						System.out.println("Element Found :"+value.toString());


						switch(a)
						{
						case 1:
							appiumdriver.findElementById(value).click();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							appiumdriver.findElementById(value).clear();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							break;
						case 3:
							appiumdriver.findElementById(value).sendKeys(sendkeys);
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							break;
						case 4:
							Select select = new Select(appiumdriver.findElementById(value));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString(),Status.PASS);
							break;
						case 5:
							appiumdriver.findElementById(value).submit();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							appiumdriver.findElementById(value).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
						}	
					}
				}
			break;
			case Selenium_Desktop:		
			case  RemoteWebDriver:	
			case MobileLabs:
			case Perfecto:
				report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
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
			exceptionmessage = "Element not found in the page";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);					

		}
	}

	/*public static void findElementByIosUIAutomation(String value,String action,String sendkeys){

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
				present="true";
				for(int i=0;isElementPresent(By.name(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.name(value))==true)
					{
						System.out.println("Element Found :"+value.toString());


						switch(a)
						{
						case 1:
							driver.findElementByIosUIAutomation(value).click();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							driver.findElementByIosUIAutomation(value).clear();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							break;
						case 3:
							driver.findElementByIosUIAutomation(value).sendKeys(sendkeys);
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							break;
						case 4:
							Select select = new Select(driver.findElementByIosUIAutomation(value));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString(),Status.PASS);
							break;
						case 5:
							driver.findElementByIosUIAutomation(value).submit();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							driver.findElementByIosUIAutomation(value).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
						}	
					}
				}
			break;
			case Selenium_Desktop:		
			case  RemoteWebDriver:		
		case MobileLabs:
			case Perfecto:
				report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
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
			exceptionmessage = "Element not found in the page";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);					

		}
	}*/

	
	
	public static void findElementByLinkText(String value,String action,String sendkeys){

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
				present="true";
				for(int i=0;isElementPresent(By.name(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.name(value))==true)
					{
						System.out.println("Element Found :"+value.toString());


						switch(a)
						{
						case 1:
							appiumdriver.findElementByLinkText(value).click();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							appiumdriver.findElementByLinkText(value).clear();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							break;
						case 3:
							appiumdriver.findElementByLinkText(value).sendKeys(sendkeys);
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							break;
						case 4:
							Select select = new Select(appiumdriver.findElementByLinkText(value));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString(),Status.PASS);
							break;
						case 5:
							appiumdriver.findElementByLinkText(value).submit();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							appiumdriver.findElementByLinkText(value).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
						}		
					}
				}
			break;
			case Selenium_Desktop:			
			case  RemoteWebDriver:
			case MobileLabs:
			case Perfecto:
				report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
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
			exceptionmessage = "Element not found in the page";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);					

		}
	}

	public static void findElementByName(String value,String action,String sendkeys){

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
				present="true";
				for(int i=0;isElementPresent(By.name(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.name(value))==true)
					{
						System.out.println("Element Found :"+value.toString());


						switch(a)
						{
						case 1:
							appiumdriver.findElementByName(value).click();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							appiumdriver.findElementByName(value).clear();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							break;
						case 3:
							appiumdriver.findElementByName(value).sendKeys(sendkeys);
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							break;
						case 4:
							Select select = new Select(appiumdriver.findElementByName(value));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString(),Status.PASS);
							break;
						case 5:
							appiumdriver.findElementByName(value).submit();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							appiumdriver.findElementByName(value).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
						}		
					}
				}
			break;
			case Selenium_Desktop:		
			case  RemoteWebDriver:	
			case MobileLabs:
			case Perfecto:
				report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
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
			exceptionmessage = "Element not found in the page";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);					

		}
	}

	public static void findElementByPartialLinkText(String value,String action,String sendkeys){

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
				present="true";
				for(int i=0;isElementPresent(By.name(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.name(value))==true)
					{
						System.out.println("Element Found :"+value.toString());


						switch(a)
						{
						case 1:
							appiumdriver.findElementByPartialLinkText(value).click();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							appiumdriver.findElementByPartialLinkText(value).clear();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							break;
						case 3:
							appiumdriver.findElementByPartialLinkText(value).sendKeys(sendkeys);
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							break;
						case 4:
							Select select = new Select(appiumdriver.findElementByPartialLinkText(value));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString(),Status.PASS);
							break;
						case 5:
							appiumdriver.findElementByPartialLinkText(value).submit();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							appiumdriver.findElementByPartialLinkText(value).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
						}		
					}
				}
			break;
			case Selenium_Desktop:		
			case  RemoteWebDriver:	
			case MobileLabs:
			case Perfecto:
				report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
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
			exceptionmessage = "Element not found in the page";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);					

		}
	}

	public static void findElementByXPath(String value,String action,String sendkeys){

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
				present="true";
				for(int i=0;isElementPresent(By.name(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.name(value))==true)
					{
						System.out.println("Element Found :"+value.toString());


						switch(a)
						{
						case 1:
							appiumdriver.findElementByXPath(value).click();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							appiumdriver.findElementByXPath(value).clear();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							break;
						case 3:
							appiumdriver.findElementByXPath(value).sendKeys(sendkeys);
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							break;
						case 4:
							Select select = new Select(appiumdriver.findElementByXPath(value));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString(),Status.PASS);
							break;
						case 5:
							appiumdriver.findElementByXPath(value).submit();
							report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							appiumdriver.findElementByXPath(value).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
						}		
					}
				}
			break;
			case Selenium_Desktop:	
			case  RemoteWebDriver:
			case MobileLabs:
			case Perfecto:			
				report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
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
			exceptionmessage = "Element not found in the page";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);					

		}
	}

	public static void findElementsByAccessibilityId(String value,int index,String action,String sendkeys){

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
				present="true";
				for(int i=0;isElementPresent(By.name(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.name(value))==true)
					{
						System.out.println("Element Found :"+value.toString());


						switch(a)
						{
						case 1:
							appiumdriver.findElementsByAccessibilityId(value).get(index).click();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							appiumdriver.findElementsByAccessibilityId(value).get(index).clear();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							break;
						case 3:
							appiumdriver.findElementsByAccessibilityId(value).get(index).sendKeys(sendkeys);
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							break;
						case 4:
							Select select = new Select(appiumdriver.findElementsByAccessibilityId(value).get(index));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString()+index,Status.PASS);
							break;
						case 5:
							appiumdriver.findElementsByAccessibilityId(value).get(index).submit();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							appiumdriver.findElementsByAccessibilityId(value).get(index).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
						}		}
				}
			break;
			case Selenium_Desktop:			
			case  RemoteWebDriver:	
			case MobileLabs:
			case Perfecto:
				report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
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
			exceptionmessage = "Element not found in the page";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);					

		}
	}

	/*public static void findElementsByAndroidUIAutomator(String value,int index,String action,String sendkeys){

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
				present="true";
				for(int i=0;isElementPresent(By.name(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.name(value))==true)
					{
						System.out.println("Element Found :"+value.toString());


						switch(a)
						{
						case 1:
							driver.findElementsByAndroidUIAutomator(value).get(index).click();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							driver.findElementsByAndroidUIAutomator(value).get(index).clear();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							break;
						case 3:
							driver.findElementsByAndroidUIAutomator(value).get(index).sendKeys(sendkeys);
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							break;
						case 4:
							Select select = new Select(driver.findElementsByAndroidUIAutomator(value).get(index));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString()+index,Status.PASS);
							break;
						case 5:
							driver.findElementsByAndroidUIAutomator(value).get(index).submit();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							driver.findElementsByAndroidUIAutomator(value).get(index).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
						}		}
				}
			break;
			case Selenium_Desktop:		
			case  RemoteWebDriver:		
		case MobileLabs:
			case Perfecto:
				report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
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
			exceptionmessage = "Element not found in the page";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);					

		}
	}*/

	public static void findElementsById(String value,int index,String action,String sendkeys){

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
				present="true";
				for(int i=0;isElementPresent(By.name(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.name(value))==true)
					{
						System.out.println("Element Found :"+value.toString());


						switch(a)
						{
						case 1:
							appiumdriver.findElementsById(value).get(index).click();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							appiumdriver.findElementsById(value).get(index).clear();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							break;
						case 3:
							appiumdriver.findElementsById(value).get(index).sendKeys(sendkeys);
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							break;
						case 4:
							Select select = new Select(appiumdriver.findElementsById(value).get(index));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString()+index,Status.PASS);
							break;
						case 5:
							appiumdriver.findElementsById(value).get(index).submit();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							appiumdriver.findElementsById(value).get(index).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
						}		}
				}
			break;
			case Selenium_Desktop:		
			case  RemoteWebDriver:	
			case MobileLabs:
			case Perfecto:	
				report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
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
			exceptionmessage = "Element not found in the page";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);					

		}
	}

/*	public static void findElementsByIosUIAutomation(String value,int index,String action,String sendkeys){

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
				present="true";
				for(int i=0;isElementPresent(By.name(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.name(value))==true)
					{
						System.out.println("Element Found :"+value.toString());


						switch(a)
						{
						case 1:
							driver.findElementsByIosUIAutomation(value).get(index).click();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							driver.findElementsByIosUIAutomation(value).get(index).clear();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							break;
						case 3:
							driver.findElementsByIosUIAutomation(value).get(index).sendKeys(sendkeys);
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							break;
						case 4:
							Select select = new Select(driver.findElementsByIosUIAutomation(value).get(index));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString()+index,Status.PASS);
							break;
						case 5:
							driver.findElementsByIosUIAutomation(value).get(index).submit();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							driver.findElementsByIosUIAutomation(value).get(index).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
						}		}
				}
			break;
			case Selenium_Desktop:			
			case  RemoteWebDriver:
		case MobileLabs:
			case Perfecto:	
				report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
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
			exceptionmessage = "Element not found in the page";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);					

		}
	}*/

	public static void findElementsByLinkText(String value,int index,String action,String sendkeys){

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
				present="true";
				for(int i=0;isElementPresent(By.name(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.name(value))==true)
					{
						System.out.println("Element Found :"+value.toString());


						switch(a)
						{
						case 1:
							appiumdriver.findElementsByLinkText(value).get(index).click();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							appiumdriver.findElementsByLinkText(value).get(index).clear();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							break;
						case 3:
							appiumdriver.findElementsByLinkText(value).get(index).sendKeys(sendkeys);
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							break;
						case 4:
							Select select = new Select(appiumdriver.findElementsByLinkText(value).get(index));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString()+index,Status.PASS);
							break;
						case 5:
							appiumdriver.findElementsByLinkText(value).get(index).submit();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							appiumdriver.findElementsByLinkText(value).get(index).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
						}		
					}
				}
			break;
			case Selenium_Desktop:			
			case  RemoteWebDriver:	
			case MobileLabs:
			case Perfecto:
				report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
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
			exceptionmessage = "Element not found in the page";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);					

		}
	}

	public static void findElementsByName(String value,int index,String action,String sendkeys){

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
				present="true";
				for(int i=0;isElementPresent(By.name(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.name(value))==true)
					{
						System.out.println("Element Found :"+value.toString());


						switch(a)
						{
						case 1:
							appiumdriver.findElementsByName(value).get(index).click();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							appiumdriver.findElementsByName(value).get(index).clear();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							break;
						case 3:
							appiumdriver.findElementsByName(value).get(index).sendKeys(sendkeys);
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							break;
						case 4:
							Select select = new Select(appiumdriver.findElementsByName(value).get(index));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString()+index,Status.PASS);
							break;
						case 5:
							appiumdriver.findElementsByName(value).get(index).submit();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							appiumdriver.findElementsByName(value).get(index).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
						}		}
				}
			break;
			case Selenium_Desktop:		
			case  RemoteWebDriver:		
			case MobileLabs:
			case Perfecto:
				report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
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
			exceptionmessage = "Element not found in the page";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);					

		}
	}

	public static void findElementsByPartialLinkText(String value,int index,String action,String sendkeys){

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
			a=5;
		}
		String exceptionmessage;
		try
		{
			switch(DriverScript.toolName){
			case Appium:
				present="true";
				for(int i=0;isElementPresent(By.name(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.name(value))==true)
					{
						System.out.println("Element Found :"+value.toString());


						switch(a)
						{
						case 1:
							appiumdriver.findElementsByPartialLinkText(value).get(index).click();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							appiumdriver.findElementsByPartialLinkText(value).get(index).clear();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							break;
						case 3:
							appiumdriver.findElementsByPartialLinkText(value).get(index).sendKeys(sendkeys);
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							break;
						case 4:
							Select select = new Select(appiumdriver.findElementsByPartialLinkText(value).get(index));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString()+index,Status.PASS);
							break;
						case 5:
							appiumdriver.findElementsByPartialLinkText(value).get(index).submit();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							appiumdriver.findElementsByPartialLinkText(value).get(index).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
						}		}
				}
			break;
			case Selenium_Desktop:	
			case  RemoteWebDriver:	
			case MobileLabs:
			case Perfecto:		
				report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
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
			exceptionmessage = "Element not found in the page";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);					

		}
	}

	public static void findElementsByXPath(String value,int index,String action,String sendkeys){

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
			a=5;
		}
		String exceptionmessage;
		try
		{
			switch(DriverScript.toolName){
			case Appium:
				present="true";
				for(int i=0;isElementPresent(By.name(value))==false;i++)
				{
					if(i>=65)
					{
						present="false";
						report.updateTestCaseLog("  Please verify  whether the entered element is Valid","",Status.FAIL);
						break;
					}
				}	
				if(present.equals("true"))
				{
					if(isElementPresent(By.name(value))==true)
					{
						System.out.println("Element Found :"+value.toString());


						switch(a)
						{
						case 1:
							appiumdriver.findElementsByXPath(value).get(index).click();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							appiumdriver.findElementsByXPath(value).get(index).clear();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							break;
						case 3:
							appiumdriver.findElementsByXPath(value).get(index).sendKeys(sendkeys);
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							break;
						case 4:
							Select select = new Select(appiumdriver.findElementsByXPath(value).get(index));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown    ",value.toString()+index,Status.PASS);
							break;
						case 5:
							appiumdriver.findElementsByXPath(value).get(index).submit();
							report.updateTestCaseLog("  clicked   ",value.toString()+index,Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 6:
							appiumdriver.findElementsByXPath(value).get(index).isDisplayed();
							report.updateTestCaseLog("  Verified    ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
						}		}
				}
			break;
			case Selenium_Desktop:		
			case  RemoteWebDriver:		
			case MobileLabs:
			case Perfecto:
				report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
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
			exceptionmessage = "Element not found in the page";
			report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
			ex.printStackTrace();
			throw new FrameworkException(exceptionmessage);					

		}
	}


	//.....................Gestures.....................//

	public static void lockScreen(int seconds){
		switch(DriverScript.toolName){
		case Appium:
			try{
				appiumdriver.lockScreen(seconds);
				report.updateTestCaseLog("  lockScreen   ","",Status.PASS);
			}catch(Exception e){
				report.updateTestCaseLog("Action not performed!!!!!!!","",Status.FAIL);
			}
		break;
		case Selenium_Desktop:		
		case  RemoteWebDriver:	
		case MobileLabs:
		case Perfecto:	
			report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
		break;
		default: report.updateTestCaseLog(" Please enter a valid ToolName in GlobalSettings ","",Status.FAIL); break;
		}
		
	}

	public static void pinch(WebElement element){
		switch(DriverScript.toolName){
		case Appium:
			try{
				appiumdriver.pinch(element);
				report.updateTestCaseLog("  pinch   ","",Status.PASS);
			}catch(Exception e){
				report.updateTestCaseLog("Action not performed!!!!!!!","",Status.FAIL);
			}
		break;
		case Selenium_Desktop:		
		case  RemoteWebDriver:	
		case MobileLabs:
		case Perfecto:	
			report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
		break;
		default: report.updateTestCaseLog(" Please enter a valid ToolName in GlobalSettings ","",Status.FAIL); break;
		}
		
	}

	public static void pinch(int x,int y){
		switch(DriverScript.toolName){
		case Appium:
			try{
				appiumdriver.pinch(x,y);
				report.updateTestCaseLog("  pinch   ","",Status.PASS);
			}catch(Exception e){
				report.updateTestCaseLog("Action not performed!!!!!!!","",Status.FAIL);
			}
		break;
		case Selenium_Desktop:			
		case  RemoteWebDriver:	
		case MobileLabs:
		case Perfecto:
			report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
		break;
		default: report.updateTestCaseLog(" Please enter a valid ToolName in GlobalSettings ","",Status.FAIL); break;
		}
		
	}

	public static void rotate(ScreenOrientation orientation){
		switch(DriverScript.toolName){
		case Appium:
			try{
				appiumdriver.rotate(orientation);
				report.updateTestCaseLog("  rotated   ","",Status.PASS);
			}catch(Exception e){
				report.updateTestCaseLog("Action not performed!!!!!!!","",Status.FAIL);
			}
		break;
		case Selenium_Desktop:			
		case  RemoteWebDriver:	
		case MobileLabs:
		case Perfecto:
			report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
		break;
		default: report.updateTestCaseLog(" Please enter a valid ToolName in GlobalSettings ","",Status.FAIL); break;
		}
		
	}

	/*public static void scrollTo(String text){
		switch(DriverScript.toolName){
		case Appium:
			try{
				driver.scrollTo(text);
				report.updateTestCaseLog("  scrollTo   ",text,Status.PASS);
			}catch(Exception e){
				report.updateTestCaseLog("Action not performed!!!!!!!","",Status.FAIL);
			}
		break;
		case Selenium_Desktop:	
			case  RemoteWebDriver:	
		case MobileLabs:
			case Perfecto:		
			report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
		break;
		default: report.updateTestCaseLog(" Please enter a valid ToolName in GlobalSettings ","",Status.FAIL); break;
		}
		
	}*/

	/*public static void scrollToExact(String text){
		switch(DriverScript.toolName){
		case Appium:
			try{
				driver.scrollToExact(text);
				report.updateTestCaseLog("  scrollToExact   ",text,Status.PASS);
			}catch(Exception e){
				report.updateTestCaseLog("Action not performed!!!!!!!","",Status.FAIL);
			}
		break;
		case Selenium_Desktop:
			case  RemoteWebDriver:	
			case Perfecto:			
			report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
		break;
		default: report.updateTestCaseLog(" Please enter a valid ToolName in GlobalSettings ","",Status.FAIL); break;
		}
		
	}*/

	/*public static void shake(){
		switch(DriverScript.toolName){
		case Appium:
			try{
				driver.shake();
				report.updateTestCaseLog("  shake   ","",Status.PASS);
			}catch(Exception e){
				report.updateTestCaseLog("Action not performed!!!!!!!","",Status.FAIL);
			}
		break;
		case Selenium_Desktop:			
			case  RemoteWebDriver:	
			case Perfecto:
			report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
		break;
		default: report.updateTestCaseLog(" Please enter a valid ToolName in GlobalSettings ","",Status.FAIL); break;
		}
		
	}*/

	public static void shake(int startx,int starty,int endx,int endy,int duration){
		switch(DriverScript.toolName){
		case Appium:
			try{
				appiumdriver.swipe(startx, starty, endx, endy, duration);
				report.updateTestCaseLog("  swipe   ","",Status.PASS);
			}catch(Exception e){
				report.updateTestCaseLog("Action not performed!!!!!!!","",Status.FAIL);
			}
		break;
		case Selenium_Desktop:			
		case  RemoteWebDriver:
		case MobileLabs:
		case Perfecto:	
			report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
		break;
		default: report.updateTestCaseLog(" Please enter a valid ToolName in GlobalSettings ","",Status.FAIL); break;
		}
		
	}

	public static void zoom(WebElement element){
		switch(DriverScript.toolName){
		case Appium:
			try{
				appiumdriver.zoom(element);
				report.updateTestCaseLog("  zoom   ",element.toString(),Status.PASS);
			}catch(Exception e){
				report.updateTestCaseLog("Action not performed!!!!!!!","",Status.FAIL);
			}
		break;
		case Selenium_Desktop:		
		case  RemoteWebDriver:	
		case MobileLabs:
		case Perfecto:	
			report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
		break;
		default: report.updateTestCaseLog(" Please enter a valid ToolName in GlobalSettings ","",Status.FAIL); break;
		}
		
	}

	public static void zoom(int x,int y){
		switch(DriverScript.toolName){
		case Appium:
			try{
				appiumdriver.zoom(x, y);
				report.updateTestCaseLog("  zoom   ","",Status.PASS);
			}catch(Exception e){
				report.updateTestCaseLog("Action not performed!!!!!!!","",Status.FAIL);
			}
		break;
		case Selenium_Desktop:		
		case  RemoteWebDriver:	
		case MobileLabs:
		case Perfecto:	
			report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
		break;
		default: report.updateTestCaseLog(" Please enter a valid ToolName in GlobalSettings ","",Status.FAIL); break;
		}
		
	}
	
	public static String forwardport,uploadcommand,connectcommand,FilePath,sendport,AppName,ReleaseCommand,DeviceName=null;  
	static Runtime runtime;
	static DesiredCapabilities caps;	
	
	public static void setupMobileLab()
	{
		try {
			GetPropertiesFile properties = new GetPropertiesFile();
			try {
				properties.getPropValues();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if(!properties.DeviceNameAndroid.equals(""))
			{    		
				DeviceName = "\"" + properties.DeviceNameAndroid + "\"";   
				sendport = properties.AndroidPort;
				FilePath = properties.AndroidAPKFilePath;
				AppName =  "\"" + "WebDriver" + "\""; // Name of the application displayed on DC Web UI
				caps = DesiredCapabilities.android();
			}
			if(!properties.DeviceNameiOS.equals(""))
			{    		
				DeviceName = "\"" + properties.DeviceNameiOS + "\"";   
				sendport = properties.iOSPort;
				FilePath = properties.iOSApplicationPath;
				AppName =  "\"" + "iWebDriver" + "\""; // Name of the application displayed on DC Web UI    		
				caps = DesiredCapabilities.ipad();
			}    

			///**********************************forward local port to the device specified at remote port***********************************************/
			forwardport = properties.DownloadPath + "MobileLabs.DeviceConnect.Cli.exe " + properties.hostIP + " " + properties.userName + " " + properties.password + " -device " + DeviceName + " -forward " + properties.Available_prt + " " + sendport;

			///**********************************upload command to upload the OS specific application***********************************************///
			uploadcommand = properties.DownloadPath + "MobileLabs.DeviceConnect.Cli.exe " + properties.hostIP + " " + properties.userName + " " + properties.password  + " -upload " + FilePath;
			runtime = Runtime.getRuntime(); 		 
			runtime.exec("cmd /c " + uploadcommand).getInputStream();		

			runtime.exec("cmd /c " + forwardport).getInputStream();

			///**********************************Connect to device with uploaded application***********************************************/
			connectcommand = properties.DownloadPath + "MobileLabs.DeviceConnect.Cli.exe " + "" + properties.hostIP + " " + properties.userName + " " + properties.password  + " -device " + DeviceName + " -orientation " + properties.Orientation + " -scale 50 -retain -install " + AppName + " -autoconnect " + AppName;
			runtime.exec("cmd /c " + connectcommand).getInputStream();
			try {
				Thread.sleep(50000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			URL url = new URL("http://localhost:" + properties.Available_prt + "/wd/hub");
			//Script1.remoteDriver = new RemoteWebDriver(url, caps);      
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static MobileDriver setupPerfectoDriver(MobileDriver MobileDriver,IMobileWebDriver driver) {
		// TODO Auto-generated method stub
		MobileDriver = new MobileDriver();
		String DeviceID = DriverScript.testParameters.getDeviceName();
		MobileDriver.getDevice(DeviceID).getDOMDriver(MobileBrowserType.DEFAULT).get(Allocator.ApplicationUrl);		
		driver = MobileDriver.getDevice(DeviceID).getDOMDriver();
		ElementsAction.mobiledriver=MobileDriver;
		DriverScript.perfectodriver=perfectodriver=driver;
		return MobileDriver;
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

	public static void getPerfectoVisualDriver(){
		switch(DriverScript.toolName){
		case Perfecto:
			report.updateTestCaseLog("Driver Switched to Visual Driver ","",Status.PASS);
			ElementsAction.perfectodriver= ElementsAction.mobiledriver.getDevice(DriverScript.testParameters.getDeviceName()).getVisualDriver();
			break;
		default:
			report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
			break;
		}
	}
	
	public static void getPerfectoNativeDriver(){
		switch(DriverScript.toolName){
		case Perfecto:
			report.updateTestCaseLog("Driver Switched to Native Driver ","",Status.PASS);
			ElementsAction.perfectodriver=  ElementsAction.mobiledriver.getDevice(DriverScript.testParameters.getDeviceName()).getNativeDriver();
			break;
		default:
			report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
			break;
		}
	}
	
	public static void getPerfectoDOMDriver(){
		switch(DriverScript.toolName){
		case Perfecto:
			report.updateTestCaseLog("Driver Switched to DOM Driver ","",Status.PASS);
			ElementsAction.perfectodriver=  ElementsAction.mobiledriver.getDevice(DriverScript.testParameters.getDeviceName()).getDOMDriver();
			break;
		default:
			report.updateTestCaseLog("Action not valid for the selected tool","",Status.FAIL);
			break;
		}
	}

}
