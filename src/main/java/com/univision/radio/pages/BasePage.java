package com.univision.radio.pages;

import java.io.IOException;
import org.jbehave.web.selenium.FluentWebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import jo.aspire.automation.logger.EnvirommentManager;
import junit.framework.Assert;

public class BasePage extends FluentWebDriverPage{

	public BasePage(WebDriverProvider driverProvider) {
		super(driverProvider);
	}
	
	public void NavigateToRWA(String element) throws IOException {
		getDriverProvider().get().manage().deleteAllCookies();
		get(EnvirommentManager.getInstance().getProperty(element));
		getDriverProvider().get().manage().window().maximize();

	}
	
	public void switchtoMobile() throws InterruptedException {

		//iphone6
		Dimension mobiledimension = new Dimension(375, 667);
		getDriverProvider().get().manage().window().setSize(mobiledimension);
		getDriverProvider().get().navigate().refresh();
		
        WaitPageToLoad();
	}
	
	
   //Switch to Tablet Breakpoint-div:Dima
    public void switchtoTablet() throws InterruptedException {

		//iPad
		Dimension Tabletdimension = new Dimension(768, 1024);
		getDriverProvider().get().manage().window().setSize(Tabletdimension);
		getDriverProvider().get().navigate().refresh();
		
        WaitPageToLoad();
	}
	
	
		
	
	
public void WaitPageToLoad() throws InterruptedException {
		
		boolean pageLoaded = false;
		
		int counter = 0;
		
		while (!pageLoaded) {

			System.out.println(counter);
			
			if (counter == 30 || executeScript("return document.readyState").toString().equals("complete")) {

			////////to stop the browser loading	// executeScript("window.stop();");
				pageLoaded = true;

			}
			counter++;
		}
	}

}
	
	

