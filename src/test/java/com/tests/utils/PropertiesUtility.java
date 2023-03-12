package com.tests.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.test.constants.SourcePath;

public class PropertiesUtility {
	
	public static String readPropertyData(String key) throws IOException {
			FileInputStream fis=new FileInputStream(SourcePath.CONFIG_PROPERTIES_PATH);
			Properties ob=new Properties();
			ob.load(fis);
			String value=ob.getProperty(key);
			return value;
		}
	}

