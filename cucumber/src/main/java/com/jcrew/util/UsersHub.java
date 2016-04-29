package com.jcrew.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UsersHub {
	
	private static final UsersHub usersHub = new UsersHub();
	
	private final Properties userReader = new Properties();
    private final Logger logger = LoggerFactory.getLogger(UsersHub.class);
    private List<List<String>> userCredentials = new ArrayList(); 

	UsersHub(){
		try{
			loadUserProperties();	
		}
		catch(Exception e){
			logger.error("Unable to load the user properties file");
		}
	}
	
	private void loadUserProperties() throws IOException{		
				
		FileInputStream inputFile = new FileInputStream("user.properties");
		userReader.load(inputFile);
	}
	
	public static UsersHub getUsersHub(){
		return usersHub;
	}
	
	private void getUsers(){
		
		
		
		
		
	}
		
}