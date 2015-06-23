package supportlibraries;

import io.appium.java_client.AppiumDriver;

import java.io.File;
import java.io.IOException;


import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;



import allocator.Allocator;

import com.cognizant.framework.FrameworkException;
import com.cognizant.framework.Report;
import com.cognizant.framework.ReportSettings;
import com.cognizant.framework.ReportTheme;
import com.perfectomobile.selenium.api.IMobileWebDriver;


/**
 * Class to extend the reporting features of the framework
 * @author Cognizant
 */
public class SeleniumReport extends Report
{
	private AppiumDriver appiumdriver;
	private WebDriver webdriver;
	private IMobileWebDriver perfectodriver;
	/**
	 * Function to set the {@link WebDriver} object
	 * @param driver The {@link WebDriver} object
	 */
	public void setDriver(WebDriver driver)
	{
		this.webdriver = driver;
	}
	
	public void setDriver(AppiumDriver driver)
	{
		this.appiumdriver = driver;
	}
	
	public void setDriver(IMobileWebDriver driver)
	{
		this.perfectodriver = driver;
	}




	/**
	 * Constructor to initialize the Report
	 * @param reportSettings The {@link ReportSettings} object
	 * @param reportTheme The {@link ReportTheme} object
	 */
	public SeleniumReport(ReportSettings reportSettings, ReportTheme reportTheme)
	{
		super(reportSettings, reportTheme);
	}

	@Override
	protected void takeScreenshot(String screenshotPath)
	{
		File scrFile = null;

		switch(DriverScript.toolName){
		case Appium:
			if(!Allocator.platformToExecute.equalsIgnoreCase("iOS")){
				if (appiumdriver == null) {
					throw new FrameworkException("Report.driver is not initialized!");
				}

				if(Allocator.platformToExecute.equals("iOS") && Allocator.isDevice.equals("true")){
					WebDriver augmentedDriver = new Augmenter().augment(appiumdriver);
					scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
				}
				else
				{
					scrFile = ((TakesScreenshot) appiumdriver).getScreenshotAs(OutputType.FILE);
				}
				try {
					FileUtils.copyFile(scrFile, new File(screenshotPath), true);
				} catch (IOException e) {
					e.printStackTrace();
					throw new FrameworkException("Error while writing screenshot to file");
				}
			}else {
				//System.out.println("Iphone Execution");
			}
			break;
		case Selenium_Desktop:
			scrFile = ((TakesScreenshot) webdriver).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(scrFile, new File(screenshotPath), true);
			} catch (IOException e) {
				e.printStackTrace();
				throw new FrameworkException("Error while writing screenshot to file");
			}
			break;
		case RemoteWebDriver:
		case MobileLabs:
			WebDriver augmentedDriver = new Augmenter().augment(webdriver);
			scrFile = ((TakesScreenshot) augmentedDriver).getScreenshotAs(OutputType.FILE);
			try {
				FileUtils.copyFile(scrFile, new File(screenshotPath), true);
			} catch (IOException e) {
				e.printStackTrace();
				throw new FrameworkException("Error while writing screenshot to file");
			}
			break;
		case Seetest: break;
		case Others: break;
		case Perfecto: 
			/*MobileScreenshotOptions screenshotOptions1 = new MobileScreenshotOptions();
			screenshotOptions1.setRepositoryKey("screenshotPath");
			byte[] bytes1 = ElementsAction.mobiledriver.getDevice(DriverScript.testParameters.getDeviceName()).getScreenshotAs(OutputType.BYTES, screenshotOptions1);
			
				
			try {
				BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes1));			
				ImageIO.write(bufferedImage, "png", new File(screenshotPath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			
			/*try {
	            final BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(bytes1));
	            ImageIO.write(bufferedImage, "jpg", new File(screenshotPath));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }*/
			
			
			/*try {
			InputStream in = new ByteArrayInputStream(bytes1);
			BufferedImage bImageFromConvert = ImageIO.read(in);			
				ImageIO.write(bImageFromConvert, "jpg", new File(screenshotPath));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			/*try {
				FileOutputStream fileOuputStream = new FileOutputStream(screenshotPath); 		    
				fileOuputStream.write(bytes1);			
				fileOuputStream.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
			
			/*try {
				FileUtils.copyFile(scrFile, new File(screenshotPath), true);
			} catch (IOException e) {
				e.printStackTrace();
				throw new FrameworkException("Error while writing screenshot to file");
			}*/
			break;
		default: break;
		}

	
	}
}