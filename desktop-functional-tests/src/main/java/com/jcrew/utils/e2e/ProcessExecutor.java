package com.jcrew.utils.e2e;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessExecutor {
	
	public Process executeCommand(String[] command){
	
		Process p = null;
		try {
			p = Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//log the messages that are generated during process execution
		printLogMessages(p);
		
		return p;
	}
	
	public static void printLogMessages(Process p){
		
		Logger logger = LoggerFactory.getLogger(ProcessExecutor.class);
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;
        
		try {
			line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        while (line != null) {
        	logger.info(line);
            try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        
        BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
        BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
        String error;
        
        try {
			while ((error = stdError.readLine()) != null) {
				logger.info(error);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        try {
			while ((error = stdInput.readLine()) != null) {
				logger.info(error);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}	
}
