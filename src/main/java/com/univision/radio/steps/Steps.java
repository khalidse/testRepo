package com.univision.radio.steps;

import java.io.IOException;
import java.sql.SQLException;

import org.hamcrest.Matchers;
import org.jbehave.core.annotations.*;
import org.jbehave.core.model.Narrative;
import org.parosproxy.paros.network.HttpMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.zaproxy.zap.CustomProxyListener;

import com.univision.radio.pages.*;

import cucumber.api.java.Before;

import static org.hamcrest.MatcherAssert.assertThat;

public class Steps extends BaseSteps {

	public Steps() {
		super();
	}

	public Steps(PageFactory pageFactory) {
		super(pageFactory);
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