package com.jcrew.cucumber.util.e2e;

import com.jcrew.cucumber.util.E2EPropertyReader;
import com.jcrew.cucumber.util.ExcelUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Map;

public class E2EMasterExecutor {
	
	public static E2EPropertyReader e2ePropertyReader = E2EPropertyReader.getPropertyReader();
	public static File targetDirectory;
	public static File reportsDirectory = null;
	
	public static void main(String[] args) {	
		
		//delete the existing reports directory and create a new directory
		targetDirectory = new File(System.getProperty("user.dir") + File.separator + "target");
		String ftpPath = e2ePropertyReader.getProperty("jenkins.ftp.path");
		
		if(System.getProperty("os.name").toLowerCase().contains("windows")){
			reportsDirectory = new File(e2ePropertyReader.getProperty("windows.e2e.reports.dir"));
		}
		else{			
			reportsDirectory	 = new File(ftpPath + "e2e_reports");
		}
		
		deleteDirectory(reportsDirectory);
		reportsDirectory.mkdir();
		
		//read how many E2E scripts are selected for execution
		ExcelUtils masterReader = null;
		try {
			String masterSheetName = "E2E_Master.xls";
			if(System.getProperty("os.name").toLowerCase().contains("windows")){				
				masterReader = new ExcelUtils(e2ePropertyReader.getProperty(System.getProperty("user.dir")+"windows.e2e.testdata.dir") + File.separator + masterSheetName, "Master", "");
			}
			else{
				masterReader = new ExcelUtils(ftpPath + masterSheetName, "Master", "");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		checkRunnerSelection(masterReader);
		
		scrubTestDataSheets(masterReader);
		
		//loop through each record and store the data which are marked for execution as 'YES'
		Map<String, Object> masterMap = null;
		String executionRunners;
		String testDataFiles;
		int threadCount;
		boolean iterate = false;
		
		Calendar calendar = Calendar.getInstance();
        long startTime = calendar.getTimeInMillis();
		do{
			executionRunners = "";
			testDataFiles = "";
			threadCount = 0;
			
			for(int i = masterReader.getSearchTextFirstRowNum();i<=masterReader.getSearchTextLastRowNum();i++){			
				try {
					masterMap = masterReader.getDataFromExcel(i);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				if(((String)masterMap.get("Execute")).equalsIgnoreCase("YES")){				
					String testdataFile = (String) masterMap.get("Testdata File Name");
					String runner = (String) masterMap.get("Runner");
					
					if(!testdataFile.contains(".xls")){
						testdataFile += ".xls";
					}			
					
					if(isTestDataRowAvailableForExecution(testdataFile)){
						executionRunners += runner + ",";
						testDataFiles += testdataFile + ",";
						threadCount++;
					}				
				}
			}
			
			if(!executionRunners.isEmpty()){
				executeMavenCommand(executionRunners, testDataFiles, threadCount);
			}
			
			Calendar calendar1 = Calendar.getInstance();
	        long currentTime = calendar1.getTimeInMillis();
			long timeDifference = currentTime - startTime;
			
			//waiting for maximum of 1 hour for execution to continue before terminating
            iterate = timeDifference <= 3600000;
			
		}while(!executionRunners.isEmpty() && iterate);
	}
	
	public static void deleteDirectory(File directory){

		  if(directory.isDirectory()) {
			  File[] files=directory.listFiles();	
	          for(int i=0;i<files.length;i++){	
				deleteDirectory(files[i]);	
			  }
			  
	          if(directory.exists()){
					directory.delete();
			  }
		  }
		  else{
			if(directory.exists()){
				directory.delete();
			}
		  }
	}
	
	public static boolean isTestDataRowAvailableForExecution(String testdataFile){
		
		boolean isRowAvailable = false;
		
		//Read the test data sheet for the E2E scenario
		ExcelUtils testDataReader = null;
		try {
			if(System.getProperty("os.name").toLowerCase().contains("windows")){
				testDataReader = new ExcelUtils(e2ePropertyReader.getProperty(System.getProperty("user.dir")+"windows.e2e.testdata.dir") + File.separator + testdataFile, "Testdata", "");
			}
			else{				
				testDataReader = new ExcelUtils(e2ePropertyReader.getProperty("jenkins.ftp.path") + testdataFile, "Testdata", "");
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}				
		
		//Loop through each record in the test data sheet and execute the runner
		Map<String, Object> testdataRowMap = null;
		int maxRowCount = Integer.parseInt(e2ePropertyReader.getProperty("e2e.testdata.sheet.max.rows"));
		int j = testDataReader.getSearchTextFirstRowNum();
		
		while(j<=maxRowCount && !isRowAvailable){
			try{
				testdataRowMap = testDataReader.getDataFromExcel(j);
				if (((String) testdataRowMap.get("Execute")).equalsIgnoreCase("YES") && !((String) testdataRowMap.get("Execution Completed")).equalsIgnoreCase("YES")) {
					isRowAvailable = true;
				}
				j++;
			}
			catch(Exception e){
				j++;
			}
		}
		
		return isRowAvailable;
	}
	
	public static void executeMavenCommand(String executionRunners, String testDataFiles, int threadCount){
		String[] command = new String[3];
		
		if(System.getProperty("os.name").toLowerCase().contains("windows")){
			command[0] = "cmd";
			command[1] = "/c";					
		}
		else{
			command[0] = "sh";
			command[1] = "-c";
		}
		
		String mavenExecutablePath = System.getenv("M2_HOME") + File.separator + "bin" + File.separator + "mvn";
		if(mavenExecutablePath.contains(" ")){
		   mavenExecutablePath = "\"" + mavenExecutablePath + "\"";
		}
		
		String environment = System.getProperty("environment");
		String viewport = System.getProperty("viewport");
		boolean isRemoteExecution = Boolean.parseBoolean(System.getProperty("remote.execution"));
		boolean isStepScreenshotRequired = Boolean.parseBoolean(System.getProperty("take.step.screenshot"));
		
		command[2] = mavenExecutablePath +  " test -Denvironment=" + environment + " -Dviewport=" + viewport + " -Dremote.execution=" + isRemoteExecution + 
    	             " -Dtest=" + executionRunners + " -Dtake.step.screenshot=" + isStepScreenshotRequired + " -Pparallel -Dthread.count=" + threadCount;
		
		ProcessExecutor processExecutor = new ProcessExecutor();
		Process p = processExecutor.executeCommand(command);
		
		if(p!=null){			
			String[] arrTestDataFiles = testDataFiles.split(",");
			for(int i=0;i<arrTestDataFiles.length;i++){
				String testdataFile = arrTestDataFiles[i];
				
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
	
	public static void scrubTestDataSheets(ExcelUtils masterReader){
		Map<String, Object> masterMap = null;
		for(int i = masterReader.getSearchTextFirstRowNum();i<=masterReader.getSearchTextLastRowNum();i++){			
			try {
				  masterMap = masterReader.getDataFromExcel(i);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(((String)masterMap.get("Execute")).equalsIgnoreCase("YES")){				
				String testdataFile = (String) masterMap.get("Testdata File Name");
				
				ExcelUtils testDataReader = null;
				try {
					if(System.getProperty("os.name").toLowerCase().contains("windows")){
						testDataReader = new ExcelUtils(e2ePropertyReader.getProperty(System.getProperty("user.dir")+"windows.e2e.testdata.dir") + File.separator + testdataFile, "Testdata", "");
					}
					else{				
						testDataReader = new ExcelUtils(e2ePropertyReader.getProperty("jenkins.ftp.path") + testdataFile, "Testdata", "");
				    }
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				scrubTestDataSheet(testDataReader);
			}
		}
	}
	
	public static void scrubTestDataSheet(ExcelUtils testDataReader){
		for(int j=testDataReader.getSearchTextFirstRowNum();j<=testDataReader.getSearchTextLastRowNum();j++){
			try {
				testDataReader.setCellValueInExcel(j, "Execution Completed", "");
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
	
	public static void checkRunnerSelection(ExcelUtils excelReader){
 		Map<String, Object> masterMap = null;
 		int runnersCount = 0;
 		
 		for(int i = excelReader.getSearchTextFirstRowNum();i<=excelReader.getSearchTextLastRowNum();i++){
 			try {
 				  masterMap = excelReader.getDataFromExcel(i);
 			} catch (FileNotFoundException e) {
 				e.printStackTrace();
 			} catch (IOException e) {
 				e.printStackTrace();
 			}

 			if(((String)masterMap.get("Execute")).equalsIgnoreCase("YES")){
 				runnersCount++;
 			}
 		}

 		//terminate execution if no scenarios are selected for execution
 		if(runnersCount==0){
 			try {
 				throw new Exception("Mark atleast one scenario as 'Yes' for execution in E2E Master sheet. Terminating execution as no scenarios are selected!!!");
 			} catch (Exception e) {				
 				e.printStackTrace();
 			}
 		}
 	}
}