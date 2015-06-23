package supportlibraries;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.omg.CORBA.portable.InputStream;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.Status;

public class ElementsActionSelenium extends ReusableLibrary {

	public ElementsActionSelenium(ScriptHelper scriptHelper) {
		super(scriptHelper);
		// TODO Auto-generated constructor stub
	}
	
	public static WebDriver firefoxdriver;
	public static WebDriverWait myWait;

	public static Date date=new Date();
	public static String fileOrFolderPath;
	public static String Folder;
	public static String CreateFolder;
	public static String devicetype;
	private static String present;
	
		
	public static void setDriver(WebDriver firefoxdriver,SeleniumReport report)
	{
		ElementsActionSelenium.firefoxdriver=DriverScript.webdriver;
		ElementsActionSelenium.report=report;
	}
	

	
	//BY SENDING ID	id
	/**
	 * Function to identify the id of the object and set value to the object and run corresponding elements
	 * @param value The unique value of the object
	 * @param num The action to be performed on the object selected
	 * @param sendkeys The parameter to be passed during execution
	 */
			
	public static void id(final String value,String num,String sendkeys)
	{
	// Here a value is getting set based on the action required

    	int a=0;
		if (num=="click")
		{
			 a=1;
		}
		else if(num=="clear")
		{
			a=2;
		}
		else if(num=="entertext")
		{
			a=3;
		}
		else if( num=="dropdown")
		{
			a=4;
		}
		else if(num=="submit")
		{
			a=5;
		}		
	
	
	
			String exceptionmessage;
			try
			{
		     present = "true";
			for(int i=0;isElementPresent(By.id(value))==false;i++)
			{
				
				if(i>=60)
				{
					report.updateTestCaseLog("Please verify  whether the entered element is Valid",value.toString(),Status.FAIL);
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
							firefoxdriver.findElement(By.id(value)).click();
							report.updateTestCaseLog("  clicked  ",value.toString(),Status.PASS);
							} 
							
							catch (Exception e)
							{
								throw new FrameworkException(value.toString() +"Not Clicked", "failed to click");
							}

							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							firefoxdriver.findElement(By.id(value)).clear();
							report.updateTestCaseLog("  cleared  ",value.toString(),Status.PASS);
							break;
						case 3:
							firefoxdriver.findElement(By.id(value)).sendKeys(sendkeys);
							report.updateTestCaseLog("  Typed   ",value.toString(),Status.PASS);
							break;
						case 4:
							Select select = new Select(firefoxdriver.findElement(By.id(value)));
							select.selectByVisibleText(sendkeys);
							report.updateTestCaseLog("  selected the dropdown :",value.toString(),Status.PASS);
							break;
						case 5:
							firefoxdriver.findElement(By.id(value)).submit();
							report.updateTestCaseLog("  Submitted  ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						default :
							report.updateTestCaseLog(" Please enter a valid Action ","",Status.FAIL);
								
						}
				}
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
		try {
			return firefoxdriver.findElement(by).isDisplayed();
			//return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	//This function is not used anywhere This is for closing the popups
	public static boolean clickIfElementPresent(By by) {
		try {
			firefoxdriver.findElement(by);	
			firefoxdriver.findElement(by).click();
			return true;
		} catch (Exception e) {
			
			return false;
		}
	}

	//LINK TEXT linktext
	/**
	 * Function to identify the link text of the object and set value to the object and run corresponding elements
	 * @param value The unique value of the object
	 * @param num The action to be performed on the object selected
	 * @param sendkeys The parameter to be passed during execution
	 */
	public static void linkText(final String value,String num,String sendkeys) 
	{		
	
		int a=0;
		if (num=="click")
		{
			 a=1;
		}
		else if(num=="clear")
		{
			a=2;
		}
		else if(num=="entertext")
		{
			a=3;
		}
		else if( num=="dropdown")
		{
			a=4;
		}
		else if(num=="submit")
		{
			a=5;
		}
		
			String exceptionmessage;
			try
			{
			
			present = "true";	
			for(int i=0;isElementPresent(By.linkText(value))==false;i++)
			{
				if(i>=60)
				{
					present = "false";	
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
					firefoxdriver.findElement(By.linkText(value)).click();
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
					firefoxdriver.findElement(By.linkText(value)).clear();
					report.updateTestCaseLog("cleared",value.toString(),Status.PASS);
					
					break;
				case 3:
					//EnterValue
					firefoxdriver.findElement(By.linkText(value)).sendKeys(sendkeys);
					report.updateTestCaseLog("  Typed :  :",value.toString(),Status.PASS);
					break;
				case 4:
					//Dropdown Selection
					Select select = new Select(firefoxdriver.findElement(By.linkText(value)));
					select.selectByVisibleText(sendkeys);
					report.updateTestCaseLog("  selected the dropdown   ",value.toString(),Status.PASS);
					break;
				case 5:
					//submit
					firefoxdriver.findElement(By.linkText(value)).submit();
					report.updateTestCaseLog("  Submitted   ",value.toString(),Status.PASS);
					break;
				default :
					report.updateTestCaseLog("  Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
						
				}
				
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
	 * @param num The action to be performed on the object selected
	 * @param sendkeys The parameter to be passed during execution
	 */
	public static void className(final String value,String num,String sendkeys) 
		{
			int a=0;
			if (num=="click")
			{
				 a=1;
			}
			else if(num=="clear")
			{
				a=2;
			}
			else if(num=="entertext")
			{
				a=3;
			}
			else if( num=="dropdown")
			{
				a=4;
			}
			else if(num=="submit")
			{
				a=5;
			}
			
			
			String exceptionmessage;
			try
			{
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
							firefoxdriver.findElement(By.className(value)).click();
							System.out.println(value.toString()+" clicked");
							report.updateTestCaseLog("  clicked  ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						case 2:
							//clear
							firefoxdriver.findElement(By.className(value)).clear();
							report.updateTestCaseLog("  cleared  ",value.toString(),Status.PASS);
							break;
						case 3:
							//EnterValue
							firefoxdriver.findElement(By.className(value)).sendKeys(sendkeys);
							report.updateTestCaseLog("  Typed   ",value.toString(),Status.PASS);
							break;
						case 4:
							//Dropdown Selection
							Select select = new Select(firefoxdriver.findElement(By.className(value)));
							select.selectByVisibleText(sendkeys);
							System.out.println("Selecting "+sendkeys.toString()+" from drop down list of "+value.toString());
							report.updateTestCaseLog("  selected the dropdown  ",value.toString(),Status.PASS);
							break;
						case 5:
							//screenshot.getScreenShot();
							firefoxdriver.findElement(By.className(value)).submit();
							report.updateTestCaseLog("  Submitted  ",value.toString(),Status.PASS);
							try{callMeToWait(3000);}catch(Exception e){}
							break;
						
						default :
							report.updateTestCaseLog("  Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
								
						}
				}
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
	 * @param num The action to be performed on the object selected
	 * @param sendkeys The parameter to be passed during execution
	 */
	public static void cssSelector(final String value,String num,String sendkeys) 
		{
			int a=0;
			if (num=="click")
			{
				 a=1;
			}
			else if(num=="clear")
			{
				a=2;
			}
			else if(num=="entertext")
			{
				a=3;
			}
			else if( num=="dropdown")
			{
				a=4;
			}
			else if(num=="submit")
			{
				a=5;
			}
			String  exceptionmessage;
			try
			{
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
						firefoxdriver.findElement(By.cssSelector(value)).click();
						report.updateTestCaseLog("clicked",value.toString(),Status.PASS);
						try{callMeToWait(3000);}catch(Exception e){}
						break;
					case 2:
						firefoxdriver.findElement(By.cssSelector(value)).clear();
						report.updateTestCaseLog("cleared",value.toString(),Status.PASS);
						break;
					case 3:
						firefoxdriver.findElement(By.cssSelector(value)).sendKeys(sendkeys);
						report.updateTestCaseLog("Typed",value.toString(),Status.PASS);
						break;
					case 4:
						Select select = new Select(firefoxdriver.findElement(By.cssSelector(value)));
						select.selectByVisibleText(sendkeys);
						report.updateTestCaseLog("  selected the dropdown    ",value.toString(),Status.PASS);
						break;
					case 5:
						firefoxdriver.findElement(By.cssSelector(value)).submit();
						report.updateTestCaseLog("  Submitted :  : ",value.toString(),Status.PASS);
						try{callMeToWait(3000);}catch(Exception e){}
						break;
					default :
						report.updateTestCaseLog("  Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
							
					}
		}}}
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
	 * @param num The action to be performed on the object selected
	 * @param sendkeys The parameter to be passed during execution
	 */
	public static void xpath(final String value,String num,String sendkeys) 
		{
			int a=0;
			if (num=="click")
			{
				 a=1;
			}
			else if(num=="clear")
			{
				a=2;
			}
			else if(num=="entertext")
			{
				a=3;
			}
			else if( num=="dropdown")
			{
				a=4;
			}
			else if(num=="submit")
			{
				a=5;
			}
			try
			{
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
			if(isElementPresent(By.xpath(value))==true)
			{
				
				System.out.println("Element Found :"+value.toString());
					switch(a)
					{
					case 1:
						firefoxdriver.findElement(By.xpath(value)).click();
						report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
						try{callMeToWait(3000);}catch(Exception e){}
						break;
					case 2:
						firefoxdriver.findElement(By.xpath(value)).clear();
						report.updateTestCaseLog("  cleared   ",value.toString(),Status.PASS);
						break;
					case 3:
						firefoxdriver.findElement(By.xpath(value)).sendKeys(sendkeys);
						report.updateTestCaseLog("  Typed :  : ",value.toString(),Status.PASS);
						break;
					case 4:	
						Select select = new Select(firefoxdriver.findElement(By.xpath(value)));
						select.selectByVisibleText(sendkeys);
						report.updateTestCaseLog("  selected the dropdown    ",value.toString(),Status.PASS);						
						break;
					case 5:
						firefoxdriver.findElement(By.xpath(value)).submit();
						report.updateTestCaseLog("  Submitted :  : ",value.toString(),Status.PASS);
						try{callMeToWait(3000);}catch(Exception e){}
						break;
					default :
						report.updateTestCaseLog("  Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
							
					}
			}}
			}
			catch(ElementNotVisibleException ex)
			{
		    
				String exceptionmessage = "Element not found in the page";
				report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
				ex.printStackTrace();
			    throw new FrameworkException(exceptionmessage);	
			    
			    
			}
			catch(UnhandledAlertException ex)
			{

			    String exceptionmessage = "Alert is present,handle it";
				report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
				ex.printStackTrace();
			    throw new FrameworkException(exceptionmessage);				    
			}
			catch(NoSuchElementException ex)
			{
			    
			    String exceptionmessage = "Element not found in the page";
				report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
				ex.printStackTrace();
			    throw new FrameworkException(exceptionmessage);				    
			    
				
			}
			catch(UnreachableBrowserException ex)
			{
			    
			    String exceptionmessage = "Problem Encountered in Webdriver";
				report.updateTestCaseLog( exceptionmessage,ex.getMessage(),Status.FAIL);
				ex.printStackTrace();
			    throw new FrameworkException(exceptionmessage);				    
			}	
				}
	//TagName
	/**
	 * Function to identify the tagname of the object and set value to the object and run corresponding elements
	 * @param value The unique value of the object
	 * @param num The action to be performed on the object selected
	 * @param sendkeys The parameter to be passed during execution
	 */
	public static void TagName(final String value,String num,String sendkeys) 
		{
			int a=0;
			if (num=="click")
			{
				 a=1;
			}
			else if(num=="clear")
			{
				a=2;
			}
			else if(num=="entertext")
			{
				a=3;
			}
			else if( num=="dropdown")
			{
				a=4;
			}
			else if(num=="submit")
			{
				a=5;
			}
			String exceptionmessage;
			try
			{
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
						firefoxdriver.findElement(By.tagName(value)).click();
						report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
						try{callMeToWait(3000);}catch(Exception e){}
						break;
					case 2:
						firefoxdriver.findElement(By.tagName(value)).clear();
						report.updateTestCaseLog("  cleared   ",value.toString(),Status.PASS);
						break;
					case 3:
						firefoxdriver.findElement(By.tagName(value)).sendKeys(sendkeys);
						report.updateTestCaseLog("  Typed :  :",value.toString(),Status.PASS);
						break;
					case 4:	
						Select select = new Select(firefoxdriver.findElement(By.tagName(value)));
						select.selectByVisibleText(sendkeys);
						report.updateTestCaseLog("  selected the dropdown :  : ",value.toString(),Status.PASS);
						break;
					case 5:
						firefoxdriver.findElement(By.tagName(value)).submit();
						report.updateTestCaseLog("  Submitted :  : ",value.toString(),Status.PASS);
						try{callMeToWait(3000);}catch(Exception e){}
						break;
					default :
						report.updateTestCaseLog("  Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
							
					}
			}}}
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
	 * @param num The action to be performed on the object selected
	 * @param sendkeys The parameter to be passed during execution
	 */
	public static void Name(final String value,String num,String sendkeys) 
		{	
			int a=0;
			if (num=="click")
			{
				 a=1;
			}
			else if(num=="clear")
			{
				a=2;
			}
			else if(num=="entertext")
			{
				a=3;
			}
			else if( num=="dropdown")
			{
				a=4;
			}
			else if(num=="submit")
			{
				a=5;
			}
			String exceptionmessage;
			try
			{
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
						firefoxdriver.findElement(By.name(value)).click();
						report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
						try{callMeToWait(3000);}catch(Exception e){}
						break;
					case 2:
						firefoxdriver.findElement(By.name(value)).clear();
						report.updateTestCaseLog("  cleared   ",value.toString(),Status.PASS);
						break;
					case 3:
						firefoxdriver.findElement(By.name(value)).sendKeys(sendkeys);
						report.updateTestCaseLog("  Typed :  : ",value.toString(),Status.PASS);
						break;
					case 4:
						Select select = new Select(firefoxdriver.findElement(By.name(value)));
						select.selectByVisibleText(sendkeys);
						report.updateTestCaseLog("selected the dropdown  : ",value.toString(),Status.PASS);
						break;
					case 5:
						firefoxdriver.findElement(By.name(value)).submit();
						report.updateTestCaseLog("  Submitted    ",value.toString(),Status.PASS);
						try{callMeToWait(3000);}catch(Exception e){}
						break;
					default :
						report.updateTestCaseLog("  Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
							
					}
					
				}
			}}
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
	 * @param num The action to be performed on the object selected
	 * @param sendkeys The parameter to be passed during execution
	 */	
	public static void partialLinkText(final String value,String num,String sendkeys) 
		{
			int a=0;
			if (num=="click")
			{
				 a=1;
			}
			else if(num=="clear")
			{
				a=2;
			}
			else if(num=="entertext")
			{
				a=3;
			}
			else if( num=="dropdown")
			{
				a=4;
			}
			else if(num=="submit")
			{
				a=5;
			}
			String exceptionmessage;
			try
			{
				
		     System.out.println(value);	
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
						firefoxdriver.findElement(By.partialLinkText(value)).click();
						report.updateTestCaseLog("  clicked   ",value.toString(),Status.PASS);
						try{callMeToWait(3000);}catch(Exception e){}
						break;
					case 2:
						firefoxdriver.findElement(By.partialLinkText(value)).clear();
						report.updateTestCaseLog("  cleared   ",value.toString(),Status.PASS);
						break;
					case 3:
						firefoxdriver.findElement(By.partialLinkText(value)).sendKeys(sendkeys);
						report.updateTestCaseLog("  Typed :  : ",value.toString(),Status.PASS);
						break;
					case 4:
						Select select = new Select(firefoxdriver.findElement(By.partialLinkText(value)));
						select.selectByVisibleText(sendkeys);
						report.updateTestCaseLog("  selected the dropdown    ",value.toString(),Status.PASS);
						break;
					case 5:
						firefoxdriver.findElement(By.partialLinkText(value)).submit();
						report.updateTestCaseLog("  Submitted :  : ",value.toString(),Status.PASS);
						try{callMeToWait(3000);}catch(Exception e){}
						break;
					default :
						report.updateTestCaseLog("Please enter a valid integer input between 1-4!!!!!!!","",Status.FAIL);
					}
					}}
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
			firefoxdriver.switchTo().frame(name);
		}
		
		public static void outofFrame(String name1)
		{
			firefoxdriver.switchTo().frame(name1);
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
		public static void getLogCat(String emulatorNumer,String logFileStorePathWithFileName)
	    {

	            try {
	                File logCatFile=new File(logFileStorePathWithFileName);
	                if(!logCatFile.exists())
	                {
	                    logCatFile.mkdirs();
	                }
	                 Process pr = Runtime.getRuntime().exec("cmd /c adb -s " + emulatorNumer + " logcat");
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
		


}
