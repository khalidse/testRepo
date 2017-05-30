package com.univision.radio.steps;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.univision.radio.pages.HeaderPage;
import com.univision.radio.pages.NavigationPage;
import com.univision.radio.pages.PageFactory;
import com.univision.radio.pages.StepsPage;
import com.univision.radio.pages.PromoCheckPage;
@ContextConfiguration(
		//"classpath:/aspire/com/steps/cucumber.xml"
		  "file:cucumber.xml"
		)
public class BaseSteps {

	
	@Autowired 
	PageFactory pageFactory=null;
	private StepsPage steps;
	private PromoCheckPage promocheckpage;
	private NavigationPage NavigationPage;
	private HeaderPage HeaderPage;
	public BaseSteps() {
	
	}
	public BaseSteps(PageFactory pageFactory) {
		this.pageFactory = pageFactory;
		System.out.println("hola2");

	}
	public StepsPage getStepsPage()
	{
		if(steps == null){
			steps = pageFactory.newSteps();
		}
		return steps;
			
	}

	public PromoCheckPage getPromoCheckPage()
	{
		if(promocheckpage == null){
			promocheckpage = pageFactory.newPromoCheckPage();
		}
		return promocheckpage;
			
	}
	
	
	public NavigationPage getNavigationPage()
	{
		if(NavigationPage == null){
			NavigationPage = pageFactory.newNavigationPage();
		}
		return NavigationPage;
			
	}

	public HeaderPage getHeaderPage()
	{
		if(HeaderPage == null){
			HeaderPage = pageFactory.newHeaderPage();
		}
		return HeaderPage;
			
	}		
	
}
