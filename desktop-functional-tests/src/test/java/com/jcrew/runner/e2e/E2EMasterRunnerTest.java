package com.jcrew.runner.e2e;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.jcrew.utils.ExcelUtils;
import com.jcrew.utils.PropertyReader;
import com.jcrew.utils.e2e.E2EExecutorService;

public class E2EMasterRunnerTest {
	
	public static void main(String[] args) {
		
		//delete the existing reports directory and create a new directory
		File targetDirectory = new File(System.getProperty("user.dir") + File.separator + "target");
		String ftpPath = getFtpPath();
		
		File reportsDirectory = null;
		if(System.getProperty("os.name").toLowerCase().contains("windows")){
			reportsDirectory = new File(File.listRoots()[0].getAbsolutePath() + File.separator + "e2e_reports");
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
				masterReader = new ExcelUtils(File.listRoots()[0].getAbsolutePath() + File.separator + "E2E_Testdata" + File.separator + "E2E_Master.xls", "Master", "");
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
		int threadCount = getThreadCount(masterReader);
		
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

		  if(directory.isDirectory())
		  {
			  File[] files=directory.listFiles();	
	          for(int i=0;i<files.length;i++)
			  {	
				deleteDirectory(files[i]);	
			  }	
			  
	          if(directory.exists()){
					directory.delete();
			  }
		  }
		  else
		  {
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
	
	public static String getFtpPath(){
		PropertyReader propertyReader = PropertyReader.getPropertyReader();
		String ftpPath = propertyReader.getProperty("jenkins.ftp.path");
		return ftpPath;
	}
}