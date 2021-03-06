package com.jcrew.steps;

import com.jcrew.pojo.User;
import com.jcrew.pojo.Product;

import com.jcrew.utils.DriverFactory;
import com.jcrew.utils.ExcelUtils;
import com.jcrew.utils.StateHolder;
import com.jcrew.utils.UsersHub;
import com.jcrew.utils.Util;
import com.jcrew.utils.ScreenshotGenerator;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by nadiapaolagarcia on 3/28/16.
 */
public class FinalSteps {

    private final Logger logger = LoggerFactory.getLogger(StartSteps.class);
    private DriverFactory driverFactory = new DriverFactory();
    private WebDriver driver = driverFactory.getDriver();
    private final StateHolder stateHolder = StateHolder.getInstance();

    @AfterStep
    public void afterStep(Scenario scenario) {
        if (!scenario.isFailed()) {
            try {

                if (driver != null && "true".equalsIgnoreCase(System.getProperty("take.step.screenshot"))) {
                	byte[] screenshot = ScreenshotGenerator.getScreenshot(driver);
                    scenario.embed(screenshot, "image/png");
                }

            } catch (WebDriverException e) {
                logger.error("An exception happened when taking step screenshot after step", e);
            }
        }
    }

    @After
    public void quitDriver(Scenario scenario) throws Exception {
    	String data;

        if (scenario.isFailed()) {
            logger.debug("Taking screenshot of scenario {}", scenario.getName());
            try {
            	final byte[] screenshot = ScreenshotGenerator.getScreenshot(driver);
                scenario.embed(screenshot, "image/png");
                
                String log = Util.logBrowserErrors(driver);
                scenario.embed(log.getBytes(), "text/plain");
                
                data = getExecutionDetails();
                if (!data.isEmpty()) {
                    scenario.embed(data.getBytes(), "text/plain");
                }
                
                if(stateHolder.hasKey("userObject")){
                	User user = stateHolder.get("userObject");
                	String userName = "Email address: " + user.getEmail();
                	scenario.embed(userName.getBytes(), "text/plain");
                }
                
            } catch (WebDriverException e) {
                logger.error("An exception happened when taking step screenshot after scenario", e);
            }
        }

        driverFactory.destroyDriver();
        
        UsersHub userHub = UsersHub.getInstance();
        userHub.releaseUserCredentials();        
        userHub.releaseE2EUserCredentials();
        
        //capture E2E order details
        String orderTestData = "";
        orderTestData = captureE2EDetails();        
        if(!orderTestData.isEmpty()){
        	logger.info("E2E scenario details:\n" + orderTestData);
        	scenario.embed(orderTestData.getBytes(), "text/plain");
        }
        
        if(stateHolder.hasKey("e2eUserObject")){
        	User user = stateHolder.get("e2eUserObject");
        	String userCredentials = "email address/password:" + user.getEmail() + "/" + user.getPassword();
        	scenario.embed(userCredentials.getBytes(), "text/plain");
        }
        
        stateHolder.clear();
    }
    
    private String getExecutionDetails() {
        String products = "";
        String userDetails = "";

        if (stateHolder.hasKey("toBag")) {
            List<Product> inBag = stateHolder.getList("toBag");
            for (Product c : inBag) {
                products += c.getName() + "\t" +
                        	c.getItemNumber() + "\t" +
                            c.getColor() + "\t" +
                            c.getSize() + "\t" +
                            c.getPrice() + "\n";
            }

            if (!products.isEmpty()) {
                products = "PRODUCT NAME\t\t\t\tITEM NUMBER\tCOLOR\tSIZE\tPRICE\n" + products + "\n";
            }
        }
        
        if (stateHolder.hasKey("userObject")) {
            User user = stateHolder.get("userObject");
            userDetails = user.getEmail() + "\t" + user.getFirstName() + "\t" + user.getLastName() + "\n";
            userDetails = "User Name\t\t\tFirst Name\tLast Name\n" + userDetails + "\n";
        }
        return products + userDetails;
    }
    
    private String captureE2EDetails() throws Exception{
    	
    	String orderTestData = "";
    	
    	if(stateHolder.hasKey("testdataRowMap")){
        	ExcelUtils testdataReader = stateHolder.get("excelObject");
        	int rowNumber = stateHolder.get("excelrowno");
        	try {
				testdataReader.setCellValueInExcel(rowNumber, "Execution Completed", "Yes");
			} catch (Exception e) {
				throw new Exception("Failed to set value in excel for the column 'Execution Completed'");
			}
        	
        	String orderNumber = "";
        	if(stateHolder.hasKey("orderNumber")){
        		orderNumber = stateHolder.get("orderNumber");
        		if(orderNumber.isEmpty()){
        			orderNumber = "Order is not placed!";
        		}
        	}
        	else{
        		orderNumber = "Order is not placed!";
        	}
        	
        	try {
				testdataReader.setCellValueInExcel(rowNumber, "Order Number", orderNumber);				
			} catch (Exception e) {
				throw new Exception("Failed to set value in excel for the column 'Order Number'");
			}
        	
    		try {
    			DateFormat dateFormat = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
        		String currentDateTime = dateFormat.format(new Date());
				testdataReader.setCellValueInExcel(rowNumber, "LastUpdated_DateTime", currentDateTime);
			} catch (Exception e) {
				throw new Exception("Failed to set value in excel for the column 'LastUpdated_DateTime'");
			}
        	
        	try {
        		String e2eErrorMessages = stateHolder.get("e2e_error_messages");
				testdataReader.setCellValueInExcel(rowNumber, "Additional Error Details", e2eErrorMessages);
			} catch (Exception e) {
				throw new Exception("Failed to set value in excel for the column 'Additional Error Details'");
			}
        	
        	if(stateHolder.hasKey("e2eUserObject")){
	        	try {
	        		User user = stateHolder.get("e2eUserObject");
	        		String userCredentials = "";
                	userCredentials = user.getEmail() + "/" + user.getPassword();
					testdataReader.setCellValueInExcel(rowNumber, "User Credentials", userCredentials);
				} catch (Exception e) {
					throw new Exception("Failed to set value in excel for the column 'User Credentials'");
				}
        	}
        	
        	try {
				testdataReader.writeAndSaveExcel();
			} catch (IOException e) {
				throw new Exception("Failed to save the excel file!!");
			}
        	
        	orderTestData +=  "Order Number = " + orderNumber + "\n";
        	
        	Map<String, Object> testdataRowMap = stateHolder.get("testdataRowMap");
        	testdataRowMap.remove("Order Number");
        	testdataRowMap.remove("Execute");
        	testdataRowMap.remove("Execution Completed");
        	
        	Iterator<Map.Entry<String, Object>> entries = testdataRowMap.entrySet().iterator();
        	while (entries.hasNext()) {
        	    Map.Entry<String, Object> entry = (Entry<String, Object>) entries.next();
        	    String key = (String) entry.getKey();
        	    String value = (String) entry.getValue();
        	    orderTestData += key + " = " + value + "\n";
        	}       	
        }
    	
    	return orderTestData;
    }
}