package com.univision.radio.steps;

import java.io.IOException;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import com.twitter.conversions.string;
import com.twitter.util.Throw;
import com.univision.radio.pages.PageFactory;



public class HeaderSteps extends BaseSteps {
	
	public HeaderSteps(PageFactory pageFactory) {
		super(pageFactory);
	}
	
	@Given("User is on $RWA front end")
	public void go (String element) throws IOException{
		getHeaderPage().NavigateToRWA(element);
		
	}
	

	

@When("User check the RWA homepage header")
public void checkHeader(){
	getHeaderPage().checkHeader();
	test12
	kjsadh
	lahjdl
}

//HeaderCheck- check display for any selector where given -Dima
@Then("User should see the $selector") 
public void display(String headerElement) throws InterruptedException, IOException
{
	getHeaderPage().display(headerElement);
}

}



