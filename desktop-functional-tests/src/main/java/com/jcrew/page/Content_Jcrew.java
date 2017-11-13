package com.jcrew.page;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.jcrew.listners.Reporter;
import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.Util;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;

@SuppressWarnings("static-access")
public class Content_Jcrew {
	private String imgText;
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
		Reporter.addStepLog("Country is: " + country);
		Reporter.addStepLog("Environment url is: " + envUrl);
		driver.get(envUrl);
		try {
			emailCapture.click();
		} catch (Exception e) {
			// System.out.println("Error message: " + e.getMessage());
		}
		try {
			startShopping.click();
		} catch (Exception e) {
			// System.out.println("Error message: " + e.getMessage());
		}

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
		System.out.println("Total no.of images:  " + list.size());
		for (int j = 0; j < list.size(); j++) {
			URL url;
			String brokenImage = "A GREAT IMAGE IS ON ITS WAY.PLEASE POP BACK LATER.";
			try {
				url = new URL(list.get(j));
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();
				code = connection.getResponseCode();
				Reporter.addStepLog("Image URL: " + list.get(j) + "," + "Response code for the Image URL: " + code);
				if (readImageText(list.get(j), j).contains(brokenImage)) {
					failedUrl = list.get(j);
				}
			} catch (Exception e) {
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
		System.out.println("Second Url size: " + urlListTwo.size());
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

	public void readAndWriteResultsIntoExcel() throws Exception {
		File src = new File(
				System.getProperty("user.dir") + "\\ContentTestingSheet\\Content_testing_template_Jcrew.xlsx");
		FileInputStream fis;
		fis = new FileInputStream(src);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet1 = wb.getSheetAt(0);
		File f = new File(System.getProperty("user.dir") + "\\properties\\contextchooser.properties");
		Properties prop = new Properties();
		FileInputStream inputFile = new FileInputStream(f);
		prop.load(inputFile);
		List<String> excelWriting = new ArrayList<String>();
		String getUrls = prop.getProperty("websiteCrolling");
		String homePageUrl = prop.getProperty("homePageUrl");
		if (getUrls.equalsIgnoreCase("true")) {
			excelWriting = getAllLinks();
			for (int j = 1; j < excelWriting.size(); j++) {
				sheet1.getRow(j).getCell(1).setCellValue(excelWriting.get(j));
				FileOutputStream fout = new FileOutputStream(src);
				wb.write(fout);
			}
		}
		if (homePageUrl.equalsIgnoreCase("true")) {
			excelWriting = getHomePageUrl();
			for (int j = 1; j < excelWriting.size(); j++) {
				sheet1.getRow(j).getCell(1).setCellValue(excelWriting.get(j));
				FileOutputStream fout = new FileOutputStream(src);
				wb.write(fout);
			}
		}
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
			if (!dataValue.isEmpty()) {
				urlStatus = responseCode(dataValue);
				totalTime = pageLoadTime(dataValue);
				sheet1.getRow(i).getCell(1).setCellValue(dataValue);
				sheet1.getRow(i).getCell(2).setCellValue(totalTime);
				sheet1.getRow(i).getCell(3).setCellValue(urlStatus);
				String imageReading = prop.getProperty("imageReading");
				if (imageReading.equals("true")) {
					imageURLStatus = imageResponse();
					sheet1.getRow(i).getCell(4).setCellValue(imageURLStatus);
				}
				Reporter.addStepLog("URL is: " + dataValue + "," + "Response code is: " + urlStatus + ","
						+ "Total time to page load: " + totalTime);
			}

			if (failedUrl != null) {
				String failedImageUrl = sheet1.getRow(i).getCell(5).getStringCellValue();
				sheet1.getRow(i).getCell(5).setCellValue(failedImageUrl + "\n" + failedUrl);
			}
			FileOutputStream fout = new FileOutputStream(src);
			wb.write(fout);
		}
		wb.close();
	}

	public static void saveImage(String imageUrl, String destinationFile) throws Exception {
		URL url = new URL(imageUrl);
		InputStream is = url.openStream();
		OutputStream os = new FileOutputStream(destinationFile);

		byte[] b = new byte[2048];
		int length;

		while ((length = is.read(b)) != -1) {
			os.write(b, 0, length);
		}

		is.close();
		os.close();
	}

	public String getImgText(String imageLocation, String language) {
		File image = new File(imageLocation);
		Tesseract1 tessInst = new Tesseract1();
		try {
			tessInst.setLanguage(language);
			imgText = tessInst.doOCR(image);
			return imgText;
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
			return "Error while reading image";
		}
	}

	public String getImgText(String imageLocation) {
		File image = new File(imageLocation);
		Tesseract tessInst = new Tesseract();
		try {
			imgText = tessInst.doOCR(image);
			return imgText;
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
			return "Error while reading image";
		}
	}

	public void downloadFile(String srcUrl, String destLocFilePath) {
		if (srcUrl.contains("http")) {
			try {
				File file = new File(destLocFilePath);
				URL myUrl = new URL(srcUrl);
				FileUtils.copyURLToFile(myUrl, file);
			} catch (Exception e) {
				Reporter.addStepLog("Unable download file from url: " + srcUrl + " so skipping this file comparision");
			}
		}
	}

	public String readImageText(String imageUrl, int i) throws Exception {
		String destinationFile = System.getProperty("user.dir") + "\\ImageReading\\savedImage.jpg";
		// saveImage(imageUrl, destinationFile);
		if (imageUrl.contains("jpg")) {
			downloadFile(imageUrl, destinationFile);
		}
		String imgPath = destinationFile;
		Content_Jcrew t = new Content_Jcrew();
		String res = t.getImgText(imgPath, "eng");
		return res;
	}

}
