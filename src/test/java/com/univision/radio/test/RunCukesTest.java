package com.univision.radio.test;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
		//features = "src/main/stories/0099-ECommerce-FeatureFiles",
	//	glue = {"aspire.Ecommerce.steps","com.cdi.igs.adapter.cucumber"},
		features = "src/test/resources/features",
		glue = {"aspire.com.steps"},
		dryRun = false,
		strict = false 
		
)
public class RunCukesTest {
}