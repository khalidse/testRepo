package com.univision.radio.pages;

import org.jbehave.web.selenium.WebDriverProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jo.aspire.automation.logger.EnvirommentManager;



/**
 * PageFactory that is used to instantiate desired page object
 */

public class PageFactory {
	
	private WebDriverProvider webDriverProvider;
	public PageFactory(WebDriverProvider webDriverProvider) {
		EnvirommentManager.setInitialClass(this.getClass());
		this.webDriverProvider = webDriverProvider;
	}
	public WebDriverProvider getDriverProvider(){
		return webDriverProvider;
	}

	public StepsPage newSteps() {
		return new StepsPage(webDriverProvider);
	}

	public PromoCheckPage newPromoCheckPage() {
		return new PromoCheckPage(webDriverProvider);
	}
	
	public BasePage newBasePage() {
		return new BasePage(webDriverProvider);
	}
	
	public NavigationPage newNavigationPage() {
		return new NavigationPage(webDriverProvider);
	}
	
	public HeaderPage newHeaderPage() {
		return new HeaderPage(webDriverProvider);
	}
	
	
}
