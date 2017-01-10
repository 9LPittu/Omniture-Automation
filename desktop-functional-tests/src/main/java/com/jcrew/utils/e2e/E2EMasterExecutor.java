package com.jcrew.utils.e2e;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jcrew.utils.ExcelUtils;
import com.jcrew.utils.e2e.E2EExecutorService;

public class E2EMasterExecutor {
	
	public static void main(String[] args) {
		
		Properties properties = new Properties();
		FileInputStream propertiesFile = null;
		try {
			propertiesFile = new FileInputStream(System.getProperty("user.dir") + File.separator + "properties/e2e.properties");
			properties.load(propertiesFile);
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		//delete the existing reports directory and create a new directory
		File targetDirectory = new File(System.getProperty("user.dir") + File.separator + "target");
		String ftpPath = properties.getProperty("jenkins.ftp.path");
		
		File reportsDirectory = null;
		if(System.getProperty("os.name").toLowerCase().contains("windows")){
			reportsDirectory = new File(properties.getProperty("windows.e2e.reports.dir"));
		}
		else{			
			reportsDirectory = new File(ftpPath + "e2e_reports");
		}
		
		deleteDirectory(reportsDirectory);
		reportsDirectory.mkdir();
		
		//read how many E2E scripts are selected for execution
		ExcelUtils masterReader = null;
		try {
			if(System.getProperty("os.name").toLowerCase().contains("windows")){
				masterReader = new ExcelUtils(properties.getProperty("windows.e2e.testdata.dir") + File.separator + "E2E_Master.xls", "Master", "");
			}
			else{
				masterReader = new ExcelUtils(ftpPath + "E2E_Master.xls", "Master", "");
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//identify number of threads to be created for parallel execution
		int threadCount;
		threadCount = getThreadCount(masterReader);
				
		ExecutorService executor = Executors.newFixedThreadPool(threadCount);
		
		//loop through each record and store the data which are marked for execution as 'YES'
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
				String runner = (String) masterMap.get("Runner");
				String environment = (String) masterMap.get("Environment");
				String viewport = (String) masterMap.get("Viewport");
				boolean remoteExecution = (boolean) masterMap.get("Remote Execution");
				boolean stepScreenshotRequired = (boolean) masterMap.get("Step Screenshot");				
				boolean isDesktop = (boolean) masterMap.get("isDesktop");
				
				if(!testdataFile.contains(".xls")){
					testdataFile += ".xls";
				}			

				Runnable worker = new E2EExecutorService(targetDirectory, reportsDirectory, testdataFile, runner, environment, viewport, remoteExecution, stepScreenshotRequired, isDesktop);
				executor.execute(worker);				
			}
		}
		
		executor.shutdown();
		//wait until all threads are finish
		while (!executor.isTerminated()) {
		}
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
	
	public static int getThreadCount(ExcelUtils excelReader){
		
		Map<String, Object> masterMap = null;
		int threadCount = 0;
		for(int i = excelReader.getSearchTextFirstRowNum();i<=excelReader.getSearchTextLastRowNum();i++){
			try {
				masterMap = excelReader.getDataFromExcel(i);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(((String)masterMap.get("Execute")).equalsIgnoreCase("YES")){
				threadCount++;
			}
		}
		
		//terminate execution if no scenarios are selected for execution 
		if(threadCount==0){
			try {
				throw new Exception("Mark atleast one scenario as 'Yes' for execution in E2E Master sheet. Terminating execution as no scenarios are selected!!!");
			} catch (Exception e) {				
				e.printStackTrace();
			}
		}
		
		return threadCount;
	}	
}