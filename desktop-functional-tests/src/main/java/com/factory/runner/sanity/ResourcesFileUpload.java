package com.factory.runner.sanity;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import junit.framework.TestCase;
/**
 * @author
 */
public class ResourcesFileUpload extends TestCase {
	private static String ROOT_FOLDER =System.getProperty("user.dir");// "D:\\Users\\boby.jo\\Desktop\\rtww_code\\RTWW_Cucumber\\desktop-functional-tests";
	
	private void copy_folder(String folder) {
		try {
			File src_dir = new File(ROOT_FOLDER+"\\" + folder);
			File dest_dir = new File(folder);
			FileUtils.copyDirectory(src_dir, dest_dir);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public void test_copy_folder() {
	    // Copy all resource folders needed to the RTWW runtime folder
	    copy_folder("features");
	    copy_folder("resources");
	    copy_folder("properties");
	}

}