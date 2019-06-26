package com.jcrew.page;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import javax.imageio.ImageIO;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.jcrew.listners.Reporter;
//import com.jcrew.page.ExcelReading.Category;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.Util;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

@SuppressWarnings({"static-access","unused"}) 
public class Content_Jcrew {
	private WebDriver driver;
	private String dataValue;
	private long totalTime;
	private int urlStatus;
	private int imageURLStatus;
	ArrayList<String> list;
	public int code;
	String filePath;
	int urlCol = 1;
	int pageLoadTimeCol = 2;
	int urlStatusCol = 3;
	int imageStatusCol = 4;
	int failedURLCol = 5;
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
		File f = new File("properties/contextchooser.properties");
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
		Reporter.addStepLog("Country is: " + country);
		Reporter.addStepLog("Environment url is: " + envUrl);
		driver.get(envUrl);
	}

	public long pageLoadTime(String url) {
		long start = System.currentTimeMillis();
		driver.navigate().to(url);
		long finish = System.currentTimeMillis();
		totalTime = finish - start;
		return totalTime;
	}

	public int responseCode(String responseURL) {
		try {
			URL url;
			url = new URL(responseURL);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			code = connection.getResponseCode();
		} catch (Exception e) {
		}
		return code;
	}

	public int imageResponse() throws Exception {
		int code = 0;
		
		try {
			WebElement view = driver.findElement(By.xpath("(//div/ul/li[4]/span/a[contains(text(),'View')])[1]"));
			if(view.isDisplayed())
				view.click();
		}catch (Exception e) {
		}
		Thread.sleep(5000);
		List<WebElement> imgs = driver.findElements(By.xpath("//img[contains(@src,'https')]"));
		list = new ArrayList<String>();
		for (WebElement img : imgs) {
			list.add(img.getAttribute("src"));
		}
		System.out.println("Total no.of images:  " + list.size());
		for (int j = 0; j < list.size(); j++) {
			URL url;
			try {
				url = new URL(list.get(j));
				//driver.navigate().to(url);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();
				code = connection.getResponseCode();
				Reporter.addStepLog("Image URL: " + list.get(j) + "," + "Response code for the Image URL: " + code);
				boolean brokenImge = testImageComparison(list.get(j));
				if (brokenImge) {
					failedUrl = list.get(j);
					System.out.println("Broken image Url" + failedUrl);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return code;
	}

	public List<String> urlListTwo(String urlOne) throws Exception {
		driver.navigate().to(urlOne);
		Util.waitForPageFullyLoaded(driver);
		HashSet<String> url;
		List<WebElement> links = driver.findElements(By.tagName("a"));
		url = new HashSet<String>();
		for (WebElement link : links) {
			url.add(link.getAttribute("href"));
		}
		List<String> urlListTwo = new ArrayList<String>(url);
		return urlListTwo;
	}

	public List<String> getHomePageUrl() {
		String envUrl = reader.getProperty("url");
		driver.get(envUrl);
		Util.waitForPageFullyLoaded(driver);
		List<WebElement> links = driver.findElements(By.tagName("a"));
		linkUrl = new HashSet<String>();
		for (WebElement link : links) {
			linkUrl.add(link.getAttribute("href"));
		}
		List<String> urlListOne = new ArrayList<String>(linkUrl);
		return urlListOne;
	}

	public List<String> getAllLinks() throws Exception {
		List<String> second = new ArrayList<String>();
		String envUrl = reader.getProperty("url");
		driver.get(envUrl);
		Util.waitForPageFullyLoaded(driver);
		List<WebElement> links = driver.findElements(By.tagName("a"));
		linkUrl = new HashSet<String>();
		for (WebElement link : links) {
			linkUrl.add(link.getAttribute("href"));
		}
		List<String> urlListOne = new ArrayList<String>(linkUrl);
		List<String> appendUrl = new ArrayList<String>();
		for (int i = 0; i < urlListOne.size(); i++) {
			if (urlListOne.get(i) != null && urlListOne.get(i).contains("http")) {
				second = urlListTwo(urlListOne.get(i));
				HashSet<String> similar = new HashSet<String>(urlListOne);
				HashSet<String> different = new HashSet<String>();
				different.addAll(urlListOne);
				different.addAll(second);
				similar.retainAll(second);
				different.removeAll(similar);
				appendUrl.addAll(different);
			}
		}
		return appendUrl;
	}

	public enum Category {
		HomePage("HomePage"), Women("Women"), Men("Men"), Girls("Girls"), Boys(
				"Boys");

		private final String name;

		public String getName() {
			return name;
		}

		Category(String s) {
			name = s;
		}

	}

	@SuppressWarnings("resource")
	public List<String> excel() throws Exception {
		String jcrew_filePath = "ContentTestingSheet/JcrewContentRegressiontestingSheet.xlsx";
		String factory_filePath = "ContentTestingSheet/FactoryContentRegressiontestingSheet.xlsx";
		String madewell_filePath = "ContentTestingSheet/MadewellContentRegressiontestingSheet.xlsx";
		File f = new File("properties/contextchooser.properties");
		Properties prop = new Properties();
		FileInputStream inputFile = new FileInputStream(f);
		prop.load(inputFile);
		if (prop.getProperty("brand").equalsIgnoreCase("jcrew")) {
			filePath = jcrew_filePath;
		}
		if (prop.getProperty("brand").equalsIgnoreCase("factory")) {
			filePath = factory_filePath;
		}
		if (prop.getProperty("brand").equalsIgnoreCase("madewell")) {
			filePath = madewell_filePath;
		}
		ArrayList<String> list = new ArrayList<String>();

		for (Category cat : Category.values()) {
			list.add(cat.getName());
		}

		ArrayList<String> excelReading = new ArrayList<String>();
		for (int k = 0; k < list.size(); k++) {
			File src = new File(filePath);
			FileInputStream fis = new FileInputStream(src);
			XSSFWorkbook wb = new XSSFWorkbook(fis);
			XSSFSheet sheet1 = wb.getSheet(list.get(k));
			int i = sheet1.getLastRowNum();
			for (int j = 0; j <= i; j++) {
				if (sheet1.getRow(j).getCell(urlCol).getStringCellValue()
						.startsWith("https://"))
					excelReading.add(sheet1.getRow(j).getCell(urlCol)
							.getStringCellValue());
			}
		}
		return excelReading;
	}

	public void readAndWriteResultsIntoExcel() throws Exception {
		String jcrew_filePath = "ContentTestingSheet/Content_testing_template_Jcrew.xlsx";
		String factory_filePath = "ContentTestingSheet/Content_testing_template_Factory.xlsx";
		String madewell_filePath = "ContentTestingSheet/Content_testing_template_Madewell.xlsx";
		File f = new File(System.getProperty("user.dir")
				+ "\\properties\\contextchooser.properties");
		Properties prop = new Properties();
		FileInputStream inputFile = new FileInputStream("properties/contextchooser.properties");
		prop.load(inputFile);
		if (prop.getProperty("brand").equalsIgnoreCase("jcrew")) {
			filePath = jcrew_filePath;
		}
		if (prop.getProperty("brand").equalsIgnoreCase("factory")) {
			filePath = factory_filePath;
		}
		if (prop.getProperty("brand").equalsIgnoreCase("madewell")) {
			filePath = madewell_filePath;
		}
		File src = new File(filePath);
		FileInputStream fis = new FileInputStream(src);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet1 = wb.getSheetAt(0);
		List<String> excelWriting = new ArrayList<String>();
		String getUrls = prop.getProperty("websiteCrolling");
		String homePageUrl = prop.getProperty("homePageUrl");
		String excelReading = prop.getProperty("excelReading");
		if (getUrls.equalsIgnoreCase("true")) {
			excelWriting = getAllLinks();
			for (int j = 1; j < excelWriting.size(); j++) {
				sheet1.getRow(j).getCell(urlCol).setCellValue(excelWriting.get(j));
				FileOutputStream fout = new FileOutputStream(src);
				wb.write(fout);
			}
		} else if (homePageUrl.equalsIgnoreCase("true")) {
			excelWriting = getHomePageUrl();
			for (int j = 1; j < excelWriting.size(); j++) {
				sheet1.getRow(j).getCell(urlCol).setCellValue(excelWriting.get(j));
				FileOutputStream fout = new FileOutputStream(src);
				wb.write(fout);
			}
		} else if (excelReading.equalsIgnoreCase("true")) {
			excelWriting = excel();
			for (int j = 1; j < excelWriting.size(); j++) {
				sheet1.getRow(j).getCell(urlCol).setCellValue(excelWriting.get(j));
				FileOutputStream fout = new FileOutputStream(src);
				wb.write(fout);
			}
		}
		int rowCount = sheet1.getLastRowNum();
		for (int i = 1; i < rowCount; i++) {
			if (util.getEnvironment().equalsIgnoreCase("gold")) {
				dataValue = sheet1.getRow(i).getCell(urlCol).getStringCellValue()
						.replace("www", "uat");
			} else if (util.getEnvironment().equalsIgnoreCase("production")) {
				dataValue = sheet1.getRow(i).getCell(urlCol).getStringCellValue();
			}
			String country = contextChooser();
			if (country.equalsIgnoreCase("in")
					&& dataValue.contains(".jcrew.com")) {
				String context[] = dataValue.split(".com");
				dataValue = context[0].concat(".com/").concat("in")
						.concat(context[1]);
			} else if (country.equalsIgnoreCase("cn")
					&& dataValue.contains(".jcrew.com")) {
				String context[] = dataValue.split(".com");
				dataValue = context[0].concat(".com/").concat("cn")
						.concat(context[1]);
			} else if (country.equalsIgnoreCase("au")
					&& dataValue.contains(".jcrew.com")) {
				String context[] = dataValue.split(".com");
				dataValue = context[0].concat(".com/").concat("au")
						.concat(context[1]);
			}
			if (!dataValue.isEmpty()) {
				urlStatus = responseCode(dataValue);
				totalTime = pageLoadTime(dataValue);
				sheet1.getRow(i).getCell(urlCol).setCellValue(dataValue);
				sheet1.getRow(i).getCell(pageLoadTimeCol).setCellValue(totalTime);
				sheet1.getRow(i).getCell(urlStatusCol).setCellValue(urlStatus);
				Reporter.addStepLog("URL is: " + dataValue + ","
						+ "Response code is: " + urlStatus + ","
						+ "Total time for page load: " + totalTime);
				String imageReading = prop.getProperty("imageReading");
				if (imageReading.equals("true")) {
					imageURLStatus = imageResponse();
					sheet1.getRow(i).getCell(imageStatusCol).setCellValue(imageURLStatus);
				}
			}

			if (failedUrl != null) {
				String failedImageUrl = sheet1.getRow(i).getCell(5)
						.getStringCellValue();
				sheet1.getRow(i).getCell(failedURLCol)
						.setCellValue(failedImageUrl + "\n" + failedUrl);
				Reporter.addStepLog("Broken image URL is: " + failedImageUrl);
			}
			FileOutputStream fout = new FileOutputStream(src);
			wb.write(fout);
		}
		wb.close();
	}

	public boolean testImageComparison(String webUrl) throws Exception {
				
		File fileInput = new File("ImageReading/Image.png");
		URL url = new URL(webUrl);
		BufferedImage bufileInput = ImageIO.read(fileInput);
		DataBuffer dafileInput = bufileInput.getData().getDataBuffer();
		int sizefileInput = dafileInput.getSize();
		BufferedImage bufileOutPut = ImageIO.read(url);
		DataBuffer dafileOutPut = bufileOutPut.getData().getDataBuffer();
		int sizefileOutPut = dafileOutPut.getSize();
		Boolean matchFlag = true;
		if (sizefileInput == sizefileOutPut) {
			for (int j = 0; j < sizefileInput; j++) {
				if (dafileInput.getElem(j) != dafileOutPut.getElem(j)) {
					matchFlag = false;
					break;
				}
			}
		} else
			matchFlag = false;
		return matchFlag;
	}

}
