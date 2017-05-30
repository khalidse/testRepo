package com.univision.radio.steps;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.hamcrest.Matchers;
import org.jbehave.core.annotations.*;
import org.jbehave.core.model.Narrative;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.parosproxy.paros.network.HttpMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.zaproxy.zap.CustomProxyListener;

import com.univision.radio.pages.*;

import cucumber.api.java.Before;
import jo.aspire.automation.logger.AspireLog4j;
import jo.aspire.automation.logger.Log4jLevels;

import static org.hamcrest.MatcherAssert.assertThat;

public class Steps2 extends BaseSteps {

	public Steps2() {
		super();
	}

	public Steps2(PageFactory pageFactory) {
		super(pageFactory);
	}

//	@cucumber.api.java.en.Given("I am on login page")
//	@Given("I am on login page")
//	public void BrightspotLoginPage() throws IOException {
//		// getStepsPage().logUserOut();
//		getStepsPage().go();
//
//		System.out.println("######################################################hooolaaa");
//		for (HttpMessage msg : CustomProxyListener.allRequests) {
//			System.out.println(msg.getRequestHeader().getURI().toString());
//		}
//		System.out.println("######################################################hooolaaa");
//
//	}

	@When("Click on close and do some functionalities with the alert")
	public void alert() throws IOException, InterruptedException {
		getStepsPage().alert();
	}





	@Given("user login with '$username' user name and '$password' password.")
	public void Login(@Named("username") String username, @Named("password") String password)
			throws IOException, InterruptedException {

		System.out.println("######################################################hooolaaa");
		for (HttpMessage msg : CustomProxyListener.allRequests) {
			System.out.println(msg.getRequestHeader().getURI().toString());
		}
		System.out.println("######################################################hooolaaa");

	}

}
