package com.univision.radio.steps;

import java.io.IOException;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.univision.radio.pages.PageFactory;



public class PromoSteps extends BaseSteps {
	
	public PromoSteps(PageFactory pageFactory) {
		super(pageFactory);
	}
	
	//PromoCheck-Navigate to URL - Hadeel
	@Given("user on FrontEnd page")
	public void goToFontEnd() throws IOException
	{
		getPromoCheckPage().go();
	}	
	
	//PromoCheck-Click on any selector where given - Hadeel
	@When("user click on $selector")
	public void ClickOn(String element) throws InterruptedException
	{
		getPromoCheckPage().ClickOn(element);
	}
	
	//PromoCheck-checking content title is displaying - Hadeel
	@Then("contentTitle should display")
	public void CheckTitle()
	{
		getPromoCheckPage().ContentTitle();
	}
	
	//Scroll Down - Hadeel
	@When("user scroll down")
	public void ScrollDown()
	{
		getPromoCheckPage().ScrollPageDown();
	}
	
	
	@Then("user should redirect to UnivisionSite")
	public void UnivisionSite()
	{
		getPromoCheckPage().UnivisionSite();
	}
	
	@Then("user should redirect to NoticiasHomepage")
	public void NoticiasHomepage()
	{
		getPromoCheckPage().NoticiasPage();
	}
	
	
}
