package com.jcrew.page;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.velocity.util.introspection.LinkingUberspector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.Util;

@SuppressWarnings("static-access")
public class Content_Jcrew {
	private WebDriver driver;
	private String dataValue;
	private long totalTime;
	private int urlStatus;
	private int imageURLStatus;
	ArrayList<String> list;
	public int code;
	private final PropertyReader reader = PropertyReader.getPropertyReader();
	DriverFactory driverFactory = new DriverFactory();
	Util util = new Util();
    String failedUrl = null;
    HashSet<String> linkUrl;

	@FindBy(xpath = "//div/a[contains(text(),'START SHOPPING')]")
	private WebElement startShopping;
	@FindBy(xpath = "//div[@class='email-capture--close modal-capture--close js-email-capture--close']/span[@class='icon-close']")
	private WebElement emailCapture;

	public String contextChooser() throws Exception {
		File f = new File(System.getProperty("user.dir") + "\\properties\\contextchooser.properties");
		Properties prop = new Properties();
		FileInputStream inputFile = new FileInputStream(f);
		prop.load(inputFile);
		String cCode = null;
		String country = prop.getProperty("locale");
		String[] locales = Locale.getISOCountries();
		for (String countryCode : locales) {
			Locale obj = new Locale("", countryCode);
			if (obj.getDisplayCountry().equalsIgnoreCase(country)) {
				cCode = obj.getCountry();
				break;
			}
		}
		return cCode;
	}

	public void goesToHomePage() {
		driver = driverFactory.getDriver();
		String country = reader.getProperty("country");
		String envUrl = reader.getProperty("url");
		System.out.println("Country is: " + country);
		System.out.println("Environment url is: " + envUrl);
		driver.get(envUrl);
		try {
			emailCapture.click();
		} catch (Exception e) {
			System.out.println("Error message:  " + e.getMessage());
		}
		try {
			startShopping.click();
		} catch (Exception e) {
			System.out.println("Error message:  " + e.getMessage());
		}

	}

	public long pageLoadTime(String url) {
		long start = System.currentTimeMillis();
		driver.get(url);
		long finish = System.currentTimeMillis();
		totalTime = finish - start;
		return totalTime;
	}

	public int responseCode(String responseURL) {
		try {
			URL url;
			url = new URL(responseURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			code = connection.getResponseCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return code;
	}

	public int imageResponse() {
		int code = 0;
		List<WebElement> imgs = driver.findElements(By.xpath("//img[contains(@src,'https')]"));
		list = new ArrayList<String>();
		for (WebElement img : imgs) {
			list.add(img.getAttribute("src"));
		}
		System.out.println("Total no.of images:  "+list.size());
		for (int j = 0; j < list.size(); j++) {
			URL url;
			try {
				url = new URL(list.get(j));
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();
				code = connection.getResponseCode();
				if (code != 200) {
					failedUrl = list.get(j);
				}
			} catch (Exception e) {
			}
		}
		return code;
	}

	public void getLinkUrl() throws Exception {
		driver.navigate().to("https://www.jcrew.com");
		Util.waitForPageFullyLoaded(driver);
		List<WebElement> links = driver.findElements(By.tagName("a"));
		linkUrl=new HashSet<String>();  
		for (WebElement link : links) {
			linkUrl.add(link.getAttribute("href"));
		}
		File src = new File(
				System.getProperty("user.dir") + "\\ContentTestingSheet\\Content_testing_template_Jcrew.xlsx");
		FileInputStream fis;
		fis = new FileInputStream(src);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet1 = wb.getSheetAt(0);
		List<String> url = new ArrayList<String>();
		Iterator<String> itr=linkUrl.iterator(); 
		  while(itr.hasNext()){ 
			  for(int j=1;j<=linkUrl.size();j++) {
			  String s = itr.next();
			  url.add(s);
			}
		  }  
		  for(int j=1;j<url.size();j++) {
			  sheet1.getRow(j).getCell(1).setCellValue(url.get(j));
			  FileOutputStream fout = new FileOutputStream(src);
			  wb.write(fout);
		  }
		wb.close();
		
	}

	public void readAndWriteResultsIntoExcel() throws Exception {
		getLinkUrl();	
		File src = new File(
				System.getProperty("user.dir") + "\\ContentTestingSheet\\Content_testing_template_Jcrew.xlsx");
		FileInputStream fis;
		fis = new FileInputStream(src);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet1 = wb.getSheetAt(0);
		int rowCount = sheet1.getLastRowNum();
		for (int i = 1; i < rowCount; i++) {
			if (util.getEnvironment().equalsIgnoreCase("gold")) {
				dataValue = sheet1.getRow(i).getCell(1).getStringCellValue().replace("www", "or");
			} else if (util.getEnvironment().equalsIgnoreCase("production")) {
				dataValue = sheet1.getRow(i).getCell(1).getStringCellValue();
			}
			String country = contextChooser();
			if (country.equalsIgnoreCase("in") && dataValue.contains(".jcrew.com")) {
				String context[] = dataValue.split(".com");
				dataValue = context[0].concat(".com/").concat("in").concat(context[1]);
			} else if (country.equalsIgnoreCase("cn") && dataValue.contains(".jcrew.com")) {
				String context[] = dataValue.split(".com");
				dataValue = context[0].concat(".com/").concat("cn").concat(context[1]);
			} else if (country.equalsIgnoreCase("au") && dataValue.contains(".jcrew.com")) {
				String context[] = dataValue.split(".com");
				dataValue = context[0].concat(".com/").concat("au").concat(context[1]);
			}
			if(!dataValue.isEmpty()) {
			System.out.println(urlStatus = responseCode(dataValue));
			System.out.println(totalTime = pageLoadTime(dataValue));
			imageURLStatus = imageResponse();
			}
			sheet1.getRow(i).getCell(1).setCellValue(dataValue);
			sheet1.getRow(i).getCell(2).setCellValue(totalTime);
			sheet1.getRow(i).getCell(3).setCellValue(urlStatus);
			sheet1.getRow(i).getCell(4).setCellValue(imageURLStatus);
			if(sheet1.getRow(i).getCell(5).getStringCellValue()!=null) {
				String failedImageUrl = sheet1.getRow(i).getCell(5).getStringCellValue();
				sheet1.getRow(i).getCell(5).setCellValue(failedImageUrl+"\n"+failedUrl);
			}
			FileOutputStream fout = new FileOutputStream(src);
			wb.write(fout);
		}
		wb.close();
	}

}
