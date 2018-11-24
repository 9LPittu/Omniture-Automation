package cucumber.com.jcrew.stepdefs;

import com.jcrew.cucumber.container.DomPOJO;
import com.jcrew.cucumber.util.ExcelUtils;
import com.jcrew.cucumber.util.StateHolder;
import com.jcrew.cucumber.view.DomView;
import com.jcrew.helper.BrowserDriver;
import com.jcrew.helper.GenericMethods;
import com.jcrew.helper.PropertyLoader;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by Venkat on 8/9/17.
 */
@SuppressWarnings("unused")
public class InitializeViews {
	
    protected WebDriver driver = null;
    
	
	private static boolean dunit = false;
    private final static StateHolder stateHolder = StateHolder.getInstance();
    @Before("@Initialize")
    public void beforeScenario() throws Throwable {
        init();
        /*JiraStepDef jiraDef = new JiraStepDef();
        jiraDef.I_m_on_the_Jira_login_page();
        jiraDef.i_login_Jcrew_Jira_application_as_and("geetha.natarajan.cft@jcrew.com", "Jcrew901722");*/
    }
    private void init() throws Throwable {
        driver = BrowserDriver.getCurrentDriver(PropertyLoader.getBrowserType());
        DomView.init();
    }
    @After("@Initialize")
    public void afterScenario(Scenario scenario) throws Exception {
        BrowserDriver.close();
        DomPOJO.cleanPojo();
        List<String> errors = new ArrayList<>();
        GenericMethods.setAssertFails(errors);
        String orderTestData = "";
        orderTestData = captureE2EDetails();        
        if(!orderTestData.isEmpty()){
        	//System.out.println("E2E scenario details:\n" + orderTestData);
        	scenario.embed(orderTestData.getBytes(), "text/plain");
        }
        stateHolder.clear();
    }
public static String captureE2EDetails() throws Exception{
    	
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
        	try {
        		
				DomView orderStatus = stateHolder.get("orderStatus");
        		String finalStatus = stateHolder.get("orderStatus");
        		testdataReader.setCellValueInExcel(rowNumber, "Status", finalStatus);
			} catch (Exception e) {
				throw new Exception("Failed to set value in excel for the column 'Status'");
			}
        	try {
				testdataReader.writeAndSaveExcel();
			} catch (IOException e) {
				throw new Exception("Failed to save the excel file!!");
			}
        	orderTestData +=  "Order Number = " + orderNumber + "\n";
        	Map<String, Object> testdataRowMap = stateHolder.get("testdataRowMap");
        	//testdataRowMap.remove("Order Number");
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