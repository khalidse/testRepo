package com.univision.radio.steps;

import java.io.IOException;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.univision.radio.pages.PageFactory;



public class NavigationSteps extends BaseSteps {
	
	public NavigationSteps(PageFactory pageFactory) {
		super(pageFactory);
	}
	

	@Given("user navigate to FEURL")
	public void Navigation (String element) throws IOException{
		getNavigationPage().Navigation(element);
	}
	
	
@Then("the web app page should display with the page header")
public void pageOpen (){
	getNavigationPage().pageOpen();
	
}


@Given("user navigate to FEURL on Mobile")
public void NavigationOnMobile(String element) throws InterruptedException, IOException{
	
	getNavigationPage().NavigationOnMobile(element);
}


@Given("user navigate to FEURL on Tablet")
public void NavigationOnTablet (String element) throws IOException, InterruptedException{
	getNavigationPage().NavigationOnTable(element);
	
}


}



