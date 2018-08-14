package com.jcrew.page;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.jcrew.utils.Xls_Reader;

import junit.framework.Assert;

@SuppressWarnings("deprecation")
public class JCWelcomeMatPage {
	

    public Map<String,Map<String,String>> readJCWelcomeMatSheet(String xlsLocation) {
		Xls_Reader xls = new Xls_Reader(xlsLocation);
		Map<String,Map<String,String>> xlsDataMap = new HashMap<String,Map<String,String>>();

		int rowCount = xls.getRowCount("Welcome Mat Details");
		//"/Users/admin/Desktop/WelcomeMat.xlsx"
		////System.out.println(xlsLocation, true);
//		for(int i=2;i<=rowCount;i++){
//			xls.setCellData("Welcome Mat Details", "Status", i,"PASSED");
//		}
		for(int i=2;i<=rowCount;i++){

		Map<String,String> countryDataMap = new HashMap<String,String>();
		String country =	xls.getCellData("Welcome Mat Details", "Country", i);
		String headerText =	xls.getCellData("Welcome Mat Details", "Header Text", i);
		String shippingDetails =	xls.getCellData("Welcome Mat Details", "Shipping Details", i);
		String localCurrencyDetails =	xls.getCellData("Welcome Mat Details", "Local Currency Details", i);
		String contactDetails =	xls.getCellData("Welcome Mat Details", "Contact Details", i);
		String disclaimer =	xls.getCellData("Welcome Mat Details", "Disclaimer", i);
		countryDataMap.put("Header Text", headerText);
		countryDataMap.put("Shipping Details", shippingDetails);
		countryDataMap.put("Local Currency Details", localCurrencyDetails);
		countryDataMap.put("Contact Details", contactDetails);
		countryDataMap.put("Disclaimer", disclaimer);
		System.out.println(countryDataMap);
		xlsDataMap.put(country, countryDataMap);
		
		
		}
		return xlsDataMap;
	}
	
	/**
	 * Method for validating welcome mat functionality
	 * @param xlsDataMap 
	 */
	@SuppressWarnings("unused")
	public void verifyWelcomeMatDetails(Map<String, Map<String, String>> xlsDataMap) {
		Set<String> countryNames = xlsDataMap.keySet();
		String[] countryArr = countryNames.toArray(new String[countryNames.size()]);

		//String[] countryArr = (String[]) countryNames.toArray(), countryNames.size());
		//System.out.println(GPXFILES1[0]);
		Iterator<String> i = countryNames.iterator();
		//while(i.hasNext()&&!(i.next()==null)) {
		for(int j=0;j<countryArr.length;j++) {
		//String countryName = i.next();
		//System.out.println("Country Name under Testing : "+countryName,true);
		System.setProperty("webdriver.chrome.driver", "/users/admin/Downloads/chromedriver");
		WebDriver driver = new ChromeDriver();
		String countryUrl = "https://www.jcrew.com/"+getLocaleForCountryName(countryArr[j]);
		//System.out.println("URL For "+countryName+" to access Jscrew site : "+countryUrl, true);
		driver.get(countryUrl);
		Set<String> winHandles = driver.getWindowHandles();
		for (String string : winHandles) {
			//System.out.println("Window handle ID: "+string,true);
			driver.switchTo().window(string);
		}
		String commonXpath = "c-header__welcomemat--";
		String canadaXpath = "c-header__welcomematCanada--";
		String[] reqFieldsForValidation = {"header","body","terms","start shopping","Take me to U.S. site"};
		for (String string : reqFieldsForValidation) {
			String text = driver.findElement(By.cssSelector("."+commonXpath+""+string)).getText();
			System.out.println("Text got for "+string+" from window is ::"+text);
			String validation = string;
			switch(validation) {
			case "header":
				String headerText = driver.findElement(By.cssSelector("."+commonXpath+""+string)).getText();
				System.out.println("Text got for "+string+" from window is ::"+headerText);
				String headerTextXls = xlsDataMap.get(countryArr[j]).get("Header Text");
				System.out.println("Text gor from xls file "+headerTextXls+" ");
				//Assert.assertEquals(headerText, headerTextXls);
				break;
			case "body":
				String bodyText="";
				if(countryArr[j].equalsIgnoreCase("Canada")) {
					 bodyText = driver.findElement(By.cssSelector("."+canadaXpath+""+string)).getText();
					 bodyText = bodyText+driver.findElement(By.cssSelector("."+commonXpath+""+string)).getText();
				}else {
					 bodyText = driver.findElement(By.cssSelector("."+commonXpath+""+string)).getText();
				}
				System.out.println("Text got for "+string+" from window is ::"+bodyText);
				System.out.println("Text got for "+string+" from window is ::"+bodyText);
				String shippingDetailsTextXls = xlsDataMap.get(countryArr[j]).get("Shipping Details");
				String localCurrencyDetailsTextXls = xlsDataMap.get(countryArr[j]).get("Local Currency Details");
				String contactDetailsTextXls = xlsDataMap.get(countryArr[j]).get("Contact Details");
				if(bodyText.trim().contains(shippingDetailsTextXls.trim())&&bodyText.trim().contains(localCurrencyDetailsTextXls.trim())) {
					//System.out.println("Data from application Window "+bodyText,true);
					//System.out.println("Data from Xls file "+shippingDetailsTextXls+localCurrencyDetailsTextXls+contactDetailsTextXls,true);
					Assert.assertTrue("VALIDATION GOT SUCCESS FOR SHIPPING DETAILS AND LOCAL CURRENCY DETAILS", true);
					}else {
					//System.out.println("Data from application Window "+bodyText,true);
					//System.out.println("Data from Xls file "+shippingDetailsTextXls+localCurrencyDetailsTextXls+contactDetailsTextXls,true);
					Assert.assertTrue("VALIDATION FAILED",false);
				}
				break;
			case "terms":
				String termsText = driver.findElement(By.cssSelector("."+commonXpath+""+string)).getText();
				//System.out.println("Text got for "+string+" from window is ::"+termsText,true);
				String disclaimerTextXls = xlsDataMap.get(countryArr[j]).get("Disclaimer");
				Assert.assertEquals(termsText.toUpperCase().trim(), disclaimerTextXls.toUpperCase().trim());
				break;
			case "start shopping":
				//String buttonOne = driver.findElement(By.xpath(".//button[contains(text(),'START SHOPPING')]")).getText();
				//System.out.println("start shopping button validation",true);
				Assert.assertTrue("START SHOPPING Button is avilable", driver.findElement(By.xpath(".//button[contains(text(),'START SHOPPING')]")).isDisplayed());
				break;
			case "Take me to U.S. site":
				System.out.println("Take me to the U.S. site validation");
				//String buttonTwo = driver.findElement(By.xpath(".//button[contains(text(),'Take me to the U.S. site')]")).getText();
				Assert.assertTrue("Take me to the U.S. site Button is avilable", driver.findElement(By.xpath(".//button[contains(text(),'Take me to the U.S. site')]")).isDisplayed());
				
				//System.out.println(buttonTwo+" is avilable");
				break;
			default:
				System.out.println("make sure switch case condition is mathes with existing loop");
			}
			
		}
		//countryArr[j]="";
		driver.close();
		driver.quit();
		
		}
	}
	
	/**
	 * Method for returning Locale value for given country name.
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
