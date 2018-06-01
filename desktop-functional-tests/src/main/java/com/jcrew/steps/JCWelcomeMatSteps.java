package com.jcrew.steps;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.jcrew.page.JCWelcomeMatPage;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.Xls_Reader;

import cucumber.api.DataTable;
import cucumber.api.java.en.When;

public class JCWelcomeMatSteps {
	DriverFactory df = new DriverFactory();
	WebDriver driver = df.getDriver();
	public static Map<String, Map<String, String>> xlsDataMap = new HashMap<String, Map<String, String>>();
	JCWelcomeMatPage jcwp = new JCWelcomeMatPage();

	@When("^User should validate welcomeMat corresponding to country lists$")
	public void User_should_validate_welcomeMat() {
		JCWelcomeMatPage jcwp = new JCWelcomeMatPage();
		Map<String, Map<String, String>> temp = jcwp.readJCWelcomeMatSheet(System.getProperty("user.dir")+"\\ContentTestingSheet\\WelcomeMat.xlsx");

		jcwp.verifyWelcomeMatDetails(temp);
	}

	@When("^temp$")
	public void temp(DataTable coun) {
		List<List<String>> tab = coun.raw();
		Workbook wb = new XSSFWorkbook();
		for (List<String> list : tab) {
			System.out.println(list+":::"+getLocaleForCountryName(list.get(0)));
		
	        
	        //add a new sheet to the workbook
	       /* Sheet sheet1 = wb.createSheet("Sheet1");
	         
	        //add 2 row to the sheet
	        Row row1 = sheet1.createRow(0);
	    
	         
	        //create cells in the rows
	        Cell row1col1 = row1.createCell(0);
	        Cell row1col2 = row1.createCell(1);
	        Cell row1col3 = row1.createCell(2);

	         
	        //add data to the cells
	        row1col1.setCellValue("row1 col1");
	        row1col2.setCellValue("row1 col2");
	        row1col3.setCellValue("row1 col3");
	*/
		}
		/*Workbook wb = new XSSFWorkbook();
        
        //add a new sheet to the workbook
        Sheet sheet1 = wb.createSheet("Sheet1");
         
        //add 2 row to the sheet
        Row row1 = sheet1.createRow(0);
    
         
        //create cells in the rows
        Cell row1col1 = row1.createCell(0);
        Cell row1col2 = row1.createCell(1);
        Cell row1col3 = row1.createCell(2);

         
        //add data to the cells
        row1col1.setCellValue("row1 col1");
        row1col2.setCellValue("row1 col2");
        row1col3.setCellValue("row1 col3");

 
*/
         
        
      /*  String fileName = new SimpleDateFormat("yyyyMMddHHmm'.txt'").format(new Date());
        
     
        //write the excel to a file
        try {
            FileOutputStream fileOut = new FileOutputStream(System.getProperty("user.dir")+"/ContentTestingSheet/"+fileName+"_WelcomeMatResult.xlsx");
            wb.write(fileOut);
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
             
        System.out.println("File created!!");
		
	/*	Xls_Reader xls = new Xls_Reader(System.getProperty("user.dir")+"\\ContentTestingSheet\\WelcomeMat.xlsx");
		xls.addSheet("firstRun");
		xls.addColumn("firstRun","Counry");
		xls.addColumn("firstRun","Status");
		xls.addColumn("firstRun","Comments");
		xls.setCellData("firstRun","Counry" ,2, "Canada");
		xls.setCellData("firstRun","Status" ,2, "Passed");
		xls.setCellData("firstRun","Comments" ,2, "NA");*/

	}

	@When("^User input the \"([^\"]*)\" specific URL$")
	public void User_input_the_Specific_Url(String countryName) {
		String url = PropertyReader.getPropertyReader().getProperty("jcrew.production", "http://www.jcrew.com/");
		String countryUrl = url + getLocaleForCountryName(countryName);
		driver.manage().window().maximize();
		driver.get(countryUrl);
		StartSteps.scenario.write("URL used for acessing Jcrew site for " + countryName + " is " + countryUrl);

	}

	@When("^valiate welcome mat window is avilable$")
	public void validate_welcome_mat_window() {

		Set<String> winHandles = driver.getWindowHandles();
		for (String string : winHandles) {
			driver.switchTo().window(string);
			StartSteps.scenario.write("Switched to Wlecome Mat Winow of windowID " + string);

		}
	}

	@When("^verify \"([^\"]*)\" in \\\"([^\\\"]*)\\\" welcome Mat$")
	public void verify_in_welcome_Mat(String windowText, String countryName) throws InterruptedException {
	System.out.println(	System.getProperty("user.dir"));
		xlsDataMap = jcwp.readJCWelcomeMatSheet(System.getProperty("user.dir")+"\\ContentTestingSheet\\WelcomeMat.xlsx");

		String commonXpath = "c-header__welcomemat--";
		String canadaXpath = "c-header__welcomematCanada--";

		String validation = windowText;
		switch (validation) {
		case "Header Text":
			String headerText = driver.findElement(By.cssSelector("." + commonXpath + "header")).getText();
			StartSteps.scenario.write("Text got for " + validation + " from window is ::" + headerText);
			String headerTextXls = "";

			headerTextXls = xlsDataMap.get(countryName).get("Header Text");

					StartSteps.scenario.write("Text gor from xls file " + headerTextXls + " ");
			Assert.assertEquals(headerText.toUpperCase(), headerTextXls.toUpperCase());
			break;
		case "Shipping Details":
		case "Local Currency Details":
		
			String bodyText = "";
			if (countryName.equalsIgnoreCase("Canada")) {
				bodyText = driver.findElement(By.cssSelector("." + canadaXpath + "body")).getText();
				System.out.println(bodyText);
				String te = driver.findElement(By.cssSelector("." + commonXpath + "body")).getText();
				bodyText = bodyText + "\n" + te;
				System.out.println(te);

			} else {
				bodyText = driver.findElement(By.cssSelector("." + commonXpath + "body")).getText();
			}
			
			String shippingDetailsTextXls = "";
			String localCurrencyDetailsTextXls = "";
			
			bodyText = bodyText.toLowerCase();
			shippingDetailsTextXls = xlsDataMap.get(countryName).get("Shipping Details").toLowerCase();
			localCurrencyDetailsTextXls = xlsDataMap.get(countryName).get("Local Currency Details").toLowerCase();
			/*System.out.println(bodyText);
			System.out.println(shippingDetailsTextXls);
			System.out.println(shippingDetailsTextXls);
			System.out.println(bodyText.contains(shippingDetailsTextXls));
			System.out.println(bodyText.contains(localCurrencyDetailsTextXls));*/
			if (bodyText.contains(shippingDetailsTextXls)
					&& bodyText.contains(localCurrencyDetailsTextXls)) {
				StartSteps.scenario.write("Data from application Window " + bodyText);
				StartSteps.scenario.write("Data from Xls file " + shippingDetailsTextXls  +"\n"+ localCurrencyDetailsTextXls
					);
				Assert.assertTrue("VALIDATION GOT SUCCESS FOR SHIPPING DETAILS AND LOCAL CURRENCY DETAILS", true);
			} else {
				StartSteps.scenario.write("Data from application Window " + bodyText);
				StartSteps.scenario.write("Data from Xls file " + shippingDetailsTextXls +"\n"+ localCurrencyDetailsTextXls
						);
				Assert.assertTrue("VALIDATION FAILED", false);
			}
			break;
		case "Contact Details":
			String CDbodyText = "";
			if (countryName.equalsIgnoreCase("Canada")) {
				CDbodyText = driver.findElement(By.cssSelector("." + canadaXpath + "body")).getText();
				System.out.println(CDbodyText);
				String te = driver.findElement(By.cssSelector("." + commonXpath + "body")).getText();
				CDbodyText = CDbodyText + "\n" + te;
				System.out.println(te);

			} else {
				CDbodyText = driver.findElement(By.cssSelector("." + commonXpath + "body")).getText();
			
			}
			String contactDetailsTextXls = "";
			CDbodyText = CDbodyText.toLowerCase();
		
			contactDetailsTextXls = xlsDataMap.get(countryName).get("Contact Details").toLowerCase();
			
			if (CDbodyText.split("local currency")[1].contains(contactDetailsTextXls)) {
				StartSteps.scenario.write("Data from application Window :\n" + CDbodyText);
				StartSteps.scenario.write("Data from Xls file \n" 
						+ contactDetailsTextXls);
				Assert.assertTrue("VALIDATION GOT SUCCESS FOR SHIPPING DETAILS AND LOCAL CURRENCY DETAILS", true);
			} else {
				StartSteps.scenario.write("Data from application Window " + CDbodyText);
				StartSteps.scenario.write("Data from Xls file " 
						+ contactDetailsTextXls);
				Assert.assertTrue("VALIDATION FAILED", false);
			}
			break;
		case "Disclaimer":
			/*try {
			
				Robot robot = new Robot();
				Thread.sleep(2000);
				robot.keyPress(KeyEvent.VK_F11);
			} catch (AWTException e) {
				System.err.println("Exception (ignored) " + e.toString());
			}*/
			String termsText = "";
			//StartSteps.scenario.write("Text got for " + validation + " from window is ::" + termsText);
			String terms1_Canada = "By clicking \"start shopping,\" you agree to our Terms of Use and Privacy Policy, including the use";
			String terms2_Canada = "the transfer of your personal information to the United States, a jurisdiction that may not provide an equivalent level of data protection to the laws in your home country.";
			String disclaimerTextXls = xlsDataMap.get(countryName).get("Disclaimer");
			
			if (countryName.equalsIgnoreCase("Canada")) {
				try {
					
					Robot robot = new Robot();
					Thread.sleep(2000);
					robot.keyPress(KeyEvent.VK_F11);
				} catch (AWTException e) {
					System.err.println("Exception (ignored) " + e.toString());
				}
				termsText = driver.findElement(By.cssSelector("." + commonXpath + "terms")).getText();
				if (termsText.contains(terms1_Canada) && termsText.contains(terms2_Canada)) {
					StartSteps.scenario.write("Data from application Window:" + termsText);
					StartSteps.scenario.write("Data from xlsx file: " + disclaimerTextXls);
					Assert.assertTrue("Disclaimer Text got verified", true);
				} else {
					
					StartSteps.scenario.write("Data from application Window :" + termsText);
					StartSteps.scenario.write("Data from xlsx file:" + disclaimerTextXls);
					Assert.assertTrue("Disclaimer Text verifiecation got failed", false);

				}
			} else {
				termsText = driver.findElement(By.cssSelector("." + commonXpath + "terms")).getText();
				Assert.assertEquals(termsText.toUpperCase().trim(), disclaimerTextXls.toUpperCase().trim());
				StartSteps.scenario.write("Data from application Window:" + termsText);
				StartSteps.scenario.write("Data from xlsx file: " + disclaimerTextXls);
			}
			break;
		default:
			StartSteps.scenario.write("make sure switch case condition is mathes with existing loop");
		}

	}

	@When("^verify \"([^\"]*)\" button is avilable in welocme Mat$")
	public void verify_button_is_avilable(String buttonName) {
		if (buttonName.equalsIgnoreCase("START SHOPPING")) {
			StartSteps.scenario.write("start shopping button validation");
			Assert.assertTrue("START SHOPPING Button is avilable",
					driver.findElement(By.xpath(".//button[contains(text(),'START SHOPPING')]")).isDisplayed());
		} else {
			StartSteps.scenario.write("Take me to the U.S. site validation");
			Assert.assertTrue("Take me to the U.S. site Button is avilable", driver
					.findElement(By.xpath(".//button[contains(text(),'Take me to the U.S. site')]")).isDisplayed());

			}
	}

	/**
	 * Method for returning Locale value for given country name.
	 * 
	 * @param countryName
	 * @return
	 */
	public String getLocaleForCountryName(String countryName) {
		Map<String, String> countries = new HashMap<>();
		for (String iso : Locale.getISOCountries()) {
			Locale l = new Locale("", iso);
			countries.put(l.getDisplayCountry(), iso);
		}
	return countries.get(countryName);
	}

}
