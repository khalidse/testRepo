package com.univision.radio.pages;

import java.io.IOException;
import org.jbehave.web.selenium.FluentWebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.twitter.conversions.string;

import jo.aspire.automation.logger.EnvirommentManager;
import junit.framework.Assert;

public class HeaderPage extends BasePage{

	public HeaderPage(WebDriverProvider driverProvider) {
		super(driverProvider);
	}
	

	public void go(String element) throws IOException {
		NavigateToRWA(element);
	}
	
	
	
	
public void checkHeader(){
		
		Assert.assertTrue(findElement(By.cssSelector(EnvirommentManager.getInstance().getProperty("header"))).isDisplayed());
	}
	
	

//HeaderCheck- check display for any selector where given -Dima
 public void display(String headerElement) throws InterruptedException ,IOException
 {
	findElement(By.cssSelector(EnvirommentManager.getInstance().getProperty(headerElement))).isDisplayed(); 
 }

}

	
	


