package com.univision.radio.steps;

import static org.openqa.selenium.By.cssSelector;
import static org.openqa.selenium.By.id;

import org.openqa.selenium.By;

import jo.aspire.automation.logger.EnvirommentManager;



public class Selectors {



	public static By Log_Out_Link() {
		return cssSelector(EnvirommentManager.getInstance().getProperty("Sign_Out_Link"));
		
	}
		 
	 
}
	
	
	
	
