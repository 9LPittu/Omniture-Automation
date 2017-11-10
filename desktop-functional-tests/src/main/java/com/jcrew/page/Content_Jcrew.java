package com.jcrew.page;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
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

import com.jcrew.listeners.Reporter;
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
		driver.get(url);
		long finish = System.currentTimeMillis();
		totalTime = finish - start;
		// Reporter.addStepLog("Total time taken to load page: " + totalTime);
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
			// Reporter.addStepLog("Response code for the URL: " + code);
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
				// driver.navigate().to(url);
				// Util.waitForPageFullyLoaded(driver);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();
				code = connection.getResponseCode();
				Reporter.addStepLog("Image URL: " + list.get(j) + "," + "Response code for the Image URL: " + code);
				/*
				 * if (code != 200) { failedUrl = list.get(j); }
				 */
				/*
				 * if (readImageText(list.get(j), j).contains(brokenImage)) { failedUrl =
				 * list.get(j); Reporter.addStepLog("Broken Image URL is: " + failedUrl); }
				 */
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
		List<String> urlListTwo = new ArrayList<String>();
		Iterator<String> itr = linkUrl.iterator();
		while (itr.hasNext()) {
			for (int j = 0; j < linkUrl.size(); j++) {
				String s = itr.next();
				urlListTwo.add(s);
			}
		}
		return urlListTwo;
	}

	public List<String> urlListOne() throws Exception {
		String country = reader.getProperty("country");
		String envUrl = reader.getProperty("url");
		System.out.println("Country is: " + country);
		System.out.println("Environment url is: " + envUrl);
		driver.get("hrrps://www.jcrew.com");
		Util.waitForPageFullyLoaded(driver);
		List<WebElement> links = driver.findElements(By.tagName("a"));
		linkUrl = new HashSet<String>();
		for (WebElement link : links) {
			linkUrl.add(link.getAttribute("href"));
		}
		List<String> urlListOne = new ArrayList<String>();
		Iterator<String> itr = linkUrl.iterator();
		while (itr.hasNext()) {
			for (int j = 0; j < linkUrl.size(); j++) {
				String s = itr.next();
				urlListOne.add(s);
			}
		}
		return urlListOne;
	}

	public List<String> appendUrl(List<String> urlOne, List<String> urlTwo) throws Exception {
		Collection<String> similar = new HashSet<String>(urlOne);
		Collection<String> different = new HashSet<String>();
		different.addAll(urlOne);
		different.addAll(urlTwo);
		similar.retainAll(urlTwo);
		different.removeAll(similar);
		Iterator<String> iterator = different.iterator();
		while (iterator.hasNext()) {
			for (int j = 0; j <= different.size(); j++) {
				String s = iterator.next();
				urlOne.add(s);
			}
		}
		System.out.println("Size of append:  " + urlOne.size());
		return urlOne;
	}

	public List<String> finalUrl() throws Exception {
		List<String> firstUrl = new ArrayList<String>();
		List<String> secondUrl = new ArrayList<String>();
		List<String> finalUrl = new ArrayList<String>();
		firstUrl = urlListOne();
		System.out.println("First url list size:  " + firstUrl.size());
		for (int i = 0; i < firstUrl.size(); i++) {
			if (firstUrl.get(i) != null && firstUrl.get(i).contains("http")) {
				secondUrl = urlListTwo(firstUrl.get(i));
				System.out.println("Second url list size:  " + secondUrl.size());
				finalUrl = appendUrl(firstUrl, secondUrl);
			}
		}
		return finalUrl;
	}

	public void writeIntoExcel() throws Exception {
		File src = new File(
				System.getProperty("user.dir") + "\\ContentTestingSheet\\Content_testing_template_Jcrew.xlsx");
		List<String> excelWriting = new ArrayList<String>();
		excelWriting = finalUrl();
		FileInputStream fis;
		fis = new FileInputStream(src);
		XSSFWorkbook wb = new XSSFWorkbook(fis);
		XSSFSheet sheet1 = wb.getSheetAt(0);
		for (int j = 0; j < excelWriting.size(); j++) {
			sheet1.getRow(j).getCell(1).setCellValue(excelWriting.get(j));
			FileOutputStream fout = new FileOutputStream(src);
			wb.write(fout);
		}
		wb.close();
	}

	public void readAndWriteResultsIntoExcel() throws Exception {
		writeIntoExcel();
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
			if (!dataValue.isEmpty()) {
				urlStatus = responseCode(dataValue);
				totalTime = pageLoadTime(dataValue);
				File f = new File(System.getProperty("user.dir") + "\\properties\\contextchooser.properties");
				Properties prop = new Properties();
				FileInputStream inputFile = new FileInputStream(f);
				prop.load(inputFile);
				String imageReading = prop.getProperty("imageReading");
				if (imageReading.equals("true")) {
					imageURLStatus = imageResponse();
					sheet1.getRow(i).getCell(4).setCellValue(imageURLStatus);
				}
				Reporter.addStepLog("URL is: " + dataValue + "," + "Response code is: " + urlStatus + ","
						+ "Total time to page load: " + totalTime);
			}
			sheet1.getRow(i).getCell(1).setCellValue(dataValue);
			sheet1.getRow(i).getCell(2).setCellValue(totalTime);
			sheet1.getRow(i).getCell(3).setCellValue(urlStatus);
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
				// Assert.fail();
			}
		}
	}

	public String readImageText(String imageUrl, int i) throws Exception {
		String destinationFile = System.getProperty("user.dir") + "\\ImageReading\\savedImage.jpg";
		// saveImage(imageUrl, destinationFile);
		downloadFile(imageUrl, destinationFile);
		String imgPath = destinationFile;
		Content_Jcrew t = new Content_Jcrew();
		String res = t.getImgText(imgPath, "eng");
		return res;
	}

}
