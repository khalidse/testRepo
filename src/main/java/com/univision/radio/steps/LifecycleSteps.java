package com.univision.radio.steps;

import org.jbehave.core.annotations.AfterScenario;
import org.jbehave.core.annotations.AfterScenario.Outcome;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.AfterStory;
import org.jbehave.core.annotations.BeforeScenario;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.BeforeStory;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.ScenarioType;
import org.jbehave.core.parsers.StoryTransformer;
import org.jbehave.web.selenium.WebDriverProvider;
import org.junit.After;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriverException;
import org.springframework.beans.factory.annotation.Autowired;

import com.aspire.automationReport.AspireReport;
import com.aspire.automationReport.StoriesStatusCounter;
import com.univision.radio.pages.PageFactory;

import cucumber.api.java.Before;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import jo.aspire.automation.logger.EnvirommentManager;
import jo.aspire.generic.GenericHelper;
import jo.aspire.generic.StateHelper;

public class LifecycleSteps {
	@Autowired
	private PageFactory pageFactory = null;

	public LifecycleSteps() {
	}

	public LifecycleSteps(PageFactory pageFactory) {
		this.pageFactory = pageFactory;
	}

	@BeforeStories
	public void runBeforeAllStories() {
		try {
			// do something
		} catch (WebDriverException e) {
			e.printStackTrace();
		}
	}

	@BeforeStory
	public void runBeforeEachStory() {
		try {

			AspireReport.getInstance().setCurrentStoryStartDate();
			// do somthing
		} catch (WebDriverException e) {
			e.printStackTrace();
		}
	}

	@Before
	public void beforeCucmberScenario() {

		pageFactory.getDriverProvider().initialize();
	}

	@AfterStory
	public void runAfterEachStory() {
		try {

			StateHelper.clearStoryState();
			
			AspireReport.getInstance().setCurrentStoryEndDate();

			AspireReport.getInstance().printEveryThing();
			// AspireReport.getInstance().allowWriteOnDatabase();
		} catch (WebDriverException e) {
			e.printStackTrace();
		}
	}

	@cucumber.api.java.After
	public void afterCucmberScenario() {
		pageFactory.getDriverProvider().end();
	}

	@AfterStories
	public void runAfterStories() throws IOException {

		StateHelper.clearStoryState();
		try {
			AspireReport.getInstance().printEveryThing();
		

		} catch (WebDriverException e) {
			e.printStackTrace();
		}

		StoriesStatusCounter storiesStatusCounter = AspireReport.getInstance().getReportDataManager().getTestCounters();
		int passedTestCount = storiesStatusCounter.getPassed();
		int failedTestCount = storiesStatusCounter.getFailed();
		int skippedTestCount = storiesStatusCounter.getSkipped();
		int totalTestCount = passedTestCount + failedTestCount + skippedTestCount;
		PrintWriter writer = new PrintWriter(AspireReport.getInstance().getReportDataManager().getReportPath()
				+ File.separator + "High_Level_Results.txt", "UTF-8");
		writer.println("tc_ran =  " + totalTestCount);
		writer.println("tc_passed = " + passedTestCount);
		writer.println("tc_failed = " + failedTestCount);
		writer.println("tc_skipped = " + skippedTestCount);
		writer.close();

		if (EnvirommentManager.getInstance().getProperty("sendEmailWithAttachments").equals("true")) 
		{
 			GenericHelper.sendEmailWithAttachments(
					
					EnvirommentManager.getInstance().getProperty("email_attachmentExtension"),AspireReport.getInstance().getReportDataManager().getReportPath());
 		}

		AspireReport.getInstance().allowWriteOnDatabase();
	}

	@AfterScenario(uponType = ScenarioType.EXAMPLE)
	public void runAfterEachScenario1() {
		AspireReport.getInstance().setCurrentScenarioEndDate();
		AspireReport.getInstance().printEveryThing();
	}

	@BeforeScenario(uponType = ScenarioType.EXAMPLE)
	public void beforeEachExampleScenario() {
		AspireReport.getInstance().setCurrentScenarioStartDate();
	}

	@AfterScenario
	public void runAfterEachScenario() {
		AspireReport.getInstance().setCurrentScenarioEndDate();
		AspireReport.getInstance().printEveryThing();
		StateHelper.clearScenarioState();
		boolean isRunOnSauce = Boolean.parseBoolean(EnvirommentManager.getInstance().getProperty("useSouceLabs"));
		if (!isRunOnSauce) {
			deleteAllCookies();
		}

	}

	@BeforeScenario
	public void runBeforeEachScenario() {
		AspireReport.getInstance().setCurrentScenarioStartDate();
		StateHelper.clearScenarioState();
		boolean isRunOnSauce = Boolean.parseBoolean(EnvirommentManager.getInstance().getProperty("useSouceLabs"));
		if (!isRunOnSauce) {
			deleteAllCookies();
		}

	}

	public void deleteAllCookies() {

	}

}
