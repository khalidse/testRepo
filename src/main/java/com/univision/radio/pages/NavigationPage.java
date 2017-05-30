package com.univision.radio.pages;

import java.io.IOException;
import org.jbehave.web.selenium.FluentWebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import jo.aspire.automation.logger.EnvirommentManager;
import junit.framework.Assert;

public class NavigationPage extends BasePage{

	public NavigationPage(WebDriverProvider driverProvider) {
		super(driverProvider);
	}
	
	public void Navigation(String element) throws IOException {
		NavigateToRWA(element);

	}
	
	
	public void pageOpen(){
		
		Assert.assertTrue(findElement(By.cssSelector(EnvirommentManager.getInstance().getProperty("header"))).isDisplayed());

	
	}

	
	
	public void NavigationOnMobile(String element) throws InterruptedException, IOException{
		NavigateToRWA(element); switchtoMobile();
	}
	
	
	public void NavigationOnTable(String element) throws IOException, InterruptedException{
		NavigateToRWA(element); switchtoTablet();
	}
	
	
}

	
	
	


