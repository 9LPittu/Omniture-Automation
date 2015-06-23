package functionallibraries;

import org.openqa.selenium.By;

import supportlibraries.ElementsAction;
import supportlibraries.ReusableLibrary;
import supportlibraries.ScriptHelper;


/**
 * Functional Library class
 * @author Cognizant
 */
public class Jcrew extends ReusableLibrary
{
	/**
	 * Constructor to initialize the functional library
	 * @param scriptHelper The {@link ScriptHelper} object passed from the {@link DriverScript}
	 */
	int deptFlag=0;
	public Jcrew(ScriptHelper scriptHelper)
	{
		super(scriptHelper);
	}
	
	public void MenuHamburger()
	{
		//Click on Menu Hamburger
		ElementsAction.xpath(".//*[@class='js-primary-nav__link--menu primary-nav__link']", "click", "");
	}
	
	public void SelectDepartment()
	{
		String IterationData=dataTable.getData("General_Data","Iteration");
		String Main_Menu = dataTable.getData("General_Data","DepartmentName");
		//Click on Department Name
		System.out.println(IterationData+"it");
		if (IterationData.equals("1"))
		{
			ElementsAction.linkText(Main_Menu, "click", "");
			deptFlag=1;
		}
		else
		{	
			String departmentName=ElementsAction.webdriver.findElement(By.className("menu__item")).getText();
			System.out.println("DepartmentName inside if iteraation"+departmentName);
			if((departmentName.toLowerCase()).equals(Main_Menu.toLowerCase()))
			{
				deptFlag=1;
			}
			else
			{
				ElementsAction.webdriver.findElement(By.cssSelector("button.btn--back")).click();
				ElementsAction.linkText(Main_Menu, "click", "");
				deptFlag=1;
			}
				}
	}
	
	public void SelectCategory()
	{
		String Sub_Menu = dataTable.getData("General_Data","CategoryName");
		ElementsAction.linkText(Sub_Menu, "click", "");
	}
	
	
	
	public void FindaStore()
	{  
		/*
		 * Using ElementsAction function with the unique property to perform the Specified Actions 
		 * Please Hover on the function to know about ElementsAction parameters
		 */
		//String PostalCode = dataTable.getData("RegisterUser_Data","PostalCode"); 
		ElementsAction.id("findastore", "click", "");
		ElementsAction.id("t4hr_rx", "click", "");
		ElementsAction.id("tc", "click", "");
		/*ElementsAction.id("locator", "entertext", PostalCode); // Test Data parameterization
		ElementsAction.id("un_findStore_btn", "click", "");
		ElementsAction.partialLinkText("5625 N RIDGE AVE", "click", "");
		ElementsAction.id("logo" , "click", "");*/
	}
	
	public void Shop()
	{
		/*
		 * Using ElementsAction function with the unique property to perform the Specified Actions 
		 * Please Hover on the function to know about ElementsAction parameters
		 */
		ElementsAction.id("shop", "click", "");
		ElementsAction.partialLinkText("Baby, Kids & Toys", "click", "");
		ElementsAction.partialLinkText("Baby Food & Formula", "click", "");		
		ElementsAction.partialLinkText("Natural & Organic Baby Food & Formula", "click", "");
		/*ElementsAction.partialLinkText("Similac Advance Organic Complete Nutrition Powder","click", "");
		ElementsAction.id("txtQuantity", "clear", "");
		ElementsAction.id("txtQuantity", "entertext", "2");
		ElementsAction.partialLinkText("Home", "click","");*/
					
	}
	
	public void Search()
	{
		/*
		 * Using ElementsAction function with the unique property to perform the Specified Actions 
		 * Please Hover on the function to know about ElementsAction parameters
		 */		
		ElementsAction.className("data_field", "click", "");
		ElementsAction.className("data_field", "entertext", "band aid");
	}	

}