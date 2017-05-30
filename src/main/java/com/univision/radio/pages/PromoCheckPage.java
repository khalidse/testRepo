package com.univision.radio.pages;

import java.io.IOException;
import org.jbehave.web.selenium.FluentWebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import jo.aspire.automation.logger.EnvirommentManager;
import junit.framework.Assert;

public class PromoCheckPage extends FluentWebDriverPage{

	public PromoCheckPage(WebDriverProvider driverProvider) {
		super(driverProvider);
	}
	
	
	public void go() throws IOException {
		get(EnvirommentManager.getInstance().getProperty("FEURL"));
		getDriverProvider().get().manage().window().maximize();

	}
	
	public void ClickOn(String element) throws InterruptedException
	{
		findElement(By.cssSelector(EnvirommentManager.getInstance().getProperty(element))).click();
	}
	
	public void ContentTitle()
	{
		WebElement frontTitle = findElement(By.cssSelector(EnvirommentManager.getInstance().getProperty("ContentTitle")));
		Assert.assertTrue(frontTitle.getText().equals(findElement(By.cssSelector(EnvirommentManager.getInstance().getProperty("PromoTitle")))));
	}
	
	public void UnivisionSite()
	{
		String url = getCurrentUrl();
		Assert.assertEquals("http://www.univision.com/", url);
	}
	
	public void ScrollPageDown()
	{
		executeScript("window.scrollBy(0,1000)");
	}
	
	public void NoticiasPage()
	{
		String url = getCurrentUrl();
		Assert.assertEquals("http://www.univision.com/noticias", url);
	}
	
	public void AuthorTitle()
	{
		WebElement author = findElement(By.cssSelector(EnvirommentManager.getInstance().getProperty("AuthorTitle")));
		Assert.assertTrue(author.getText().equals(findElement(By.cssSelector(EnvirommentManager.getInstance().getProperty("ContentAuthor")))));
	}
	
}
