package com.jcrew.utils.e2e;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.jcrew.utils.ExcelUtils;
import com.jcrew.utils.PropertyReader;

public class E2EExecutorService implements Runnable {
	
	File targetDirectory;
	File reportsDirectory;
	String testdataFile;
	String runner;
	String environment;
	String viewport; 
	boolean remoteExecution;
	boolean stepScreenshotRequired;
	boolean isDesktop;
	
	private PropertyReader propertyReader = PropertyReader.getPropertyReader();
	
	public E2EExecutorService(File targetDirectory, File reportsDirectory, String testdataFile, String runner, String environment, String viewport, boolean remoteExecution, boolean stepScreenshotRequired, boolean isDesktop){
		this.targetDirectory = targetDirectory;
		this.reportsDirectory = reportsDirectory;
		this.testdataFile = testdataFile;
		this.runner = runner;
		this.environment = environment;
		this.viewport = viewport;
		this.remoteExecution = remoteExecution;
		this.stepScreenshotRequired = stepScreenshotRequired;
		this.isDesktop = isDesktop;		
	}

	@Override
	public void run() {
		
		//Read the test data sheet for the E2E scenario
		ExcelUtils testDataReader = null;
		try {
			testDataReader = new ExcelUtils(propertyReader.getProperty("windows.e2e.testdata.dir") + File.separator + testdataFile, "Testdata", "");
		} catch (IOException e) {
			e.printStackTrace();
		}				
		cleanTestDataSheet(testDataReader);
		
		Map<String, Object> testdataRowMap = null;
		try {
			testDataReader = new ExcelUtils(propertyReader.getProperty("windows.e2e.testdata.dir") + File.separator + testdataFile, "Testdata", "");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Loop through each record in the test data sheet and execute the runner 
		for(int j=testDataReader.getSearchTextFirstRowNum();j<=testDataReader.getSearchTextLastRowNum();j++){
			try {
				testdataRowMap = testDataReader.getDataFromExcel(j);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(((String)testdataRowMap.get("Execute")).equalsIgnoreCase("YES") && !((String)testdataRowMap.get("Execution Completed")).equalsIgnoreCase("YES")){					
				
				String[] command = new String[3];
				
				if(System.getProperty("os.name").toLowerCase().contains("windows")){
					command[0] = "cmd";
					command[1] = "/c";						
					command[2] = propertyReader.getProperty("windows.box.maven.path");
				}
				else{
					command[0] = "sh";
					command[1] = "-c";
					command[2] = propertyReader.getProperty("jenkins.box.maven.path");
				}
				
				command[2] += " test -Denvironment=" + environment + " -Dviewport=" + viewport + " -Dremote.execution=" + remoteExecution + 
					 	      " -Dtest=" + runner + " -Dtake.step.screenshot=" + stepScreenshotRequired + " -Dis.desktop=" + isDesktop;
				
				ProcessExecutor processExecutor = new ProcessExecutor();
				Process p = processExecutor.executeCommand(command);
				
				if(p!=null){
					//Generate the report
					String sourceReportFileName = testdataFile.replaceAll(".xls", "") + ".json";
					String targetReportFileName = testdataFile.replaceAll(".xls", "") + "_" + System.currentTimeMillis() + ".json";
					
					File sourceDirReportFile = new File(targetDirectory + File.separator + sourceReportFileName);
					File targetDirReportFile = new File(reportsDirectory + File.separator + targetReportFileName);
					
					try {
						FileUtils.copyFile(sourceDirReportFile, targetDirReportFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	public static void cleanTestDataSheet(ExcelUtils testDataReader){
		for(int j=testDataReader.getSearchTextFirstRowNum();j<=testDataReader.getSearchTextLastRowNum();j++){
			try {
				testDataReader.setCellValueInExcel(j, "Execution Completed", "");
				testDataReader.setCellValueInExcel(j, "Order Number", "");
				testDataReader.setCellValueInExcel(j, "LastUpdated_DateTime", "");
				testDataReader.setCellValueInExcel(j, "Additional Error Details", "");
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}		
		
		try {
			testDataReader.writeAndSaveExcel();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}