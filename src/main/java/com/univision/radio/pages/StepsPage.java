package com.univision.radio.pages;

import org.apache.tools.ant.property.GetProperty;
import org.jbehave.web.selenium.FluentWebDriverPage;
import org.jbehave.web.selenium.WebDriverPage;
import org.jbehave.web.selenium.WebDriverProvider;
import org.mockserver.model.HttpRequest;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.seleniumhq.selenium.fluent.FluentWebElement;
import org.zaproxy.zap.CustomProxyListener;

import io.appium.java_client.pagefactory.Widget;
import jo.aspire.automation.logger.AspireLog4j;
import jo.aspire.automation.logger.EnvirommentManager;
import jo.aspire.automation.logger.Log4jLevels;
import jo.aspire.generic.MockServerProxy;
import jo.aspire.generic.StateHelper;
import jo.aspire.web.automationUtil.BrowserAlertHelper;
import jo.aspire.web.automationUtil.DriverProvider;
import junit.framework.Assert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.openqa.selenium.By.cssSelector;
import static org.seleniumhq.selenium.fluent.Period.secs;

/**
 * Page object defining the home page
 */
public class StepsPage extends FluentWebDriverPage {
	public Connection con;
	BrowserAlertHelper test = new BrowserAlertHelper();

	public StepsPage(WebDriverProvider driverProvider) {
		super(driverProvider);

		// TODO Auto-generated constructor stub
	}

	/**
	 * Default Selector within the class
	 */
	private By Version = cssSelector("td.mh22-text a");
	private final int CONST_WAIT_LOWER_VALUE = 30;

	public FluentWebElement getWorkSmartVersion() {
		return within(secs(CONST_WAIT_LOWER_VALUE)).link(Version);
	}

	public String checkVersinoTextFromHeader() {
		return getWorkSmartVersion().getText().toString();
	}



	public void goFF() throws IOException, InterruptedException {
		assertTrue(false);
		DriverProvider dp = new DriverProvider();

		WebDriver webd = dp.initialize("chrome");

		webd.get("https://www.mkyong.com/");

		System.out.println("INSIDE NEW WEB DRIVER ");

		Thread.sleep(5000);

		webd.close();
		get("http://aspire.jo/");
		getDriverProvider().get().manage().window().maximize();
		Thread.sleep(5000);

	}
// I don't believe this method is needed - Raed
//	public void goAspire() throws IOException {
//		get("http://google.jo/");
//	}

	public boolean isJavascriptEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public void verifyNetworkData() {
		System.out.println("------------- start ---------");
		System.out.println(
				MockServerProxy.proxy.retrieveAsJSON(HttpRequest.request().withHeader("Host", "www.aspire.jo")));
		System.out.println("------------- end ---------");
	}

	public boolean dbOpenConn() throws ClassNotFoundException, SQLException {

		Class.forName("com.mysql.jdbc.Driver");
		try {
			// Create Connection to DB
			con = DriverManager.getConnection("jdbc:mysql://172.17.100.33/AUTOMATION_DASHBOARD", "roott", "root");
			System.err.println("CONNECT TO DATABASE");
		}

		catch (Exception e) {
			e.printStackTrace();
			System.err.println("ERROR!!! Cannot connect");
			return false;
		}

		return true;
	}

	public boolean testLogger() throws SQLException, ClassNotFoundException, Exception {
		// System.err.println("getval1 f= " +
		// StateHelper.getStoryState("testMyVal"));
		ArrayList<String> rowList = new ArrayList<String>();
		dbOpenConn();
		try {
			// Create Statement Object
			System.err.println("error ---- " + con);
			Statement stmt = con.createStatement();
			// Execute the SQL Query. Store results in ResultSet
			ResultSet rs = stmt.executeQuery("SELECT * FROM AUTOMATION_DASHBOARD.LOGS limit 1");
			System.err.println("res" + rs);
			System.err.println("Executing the query please wait ...");
			ResultSetMetaData rsmd = null;
			rsmd = (ResultSetMetaData) rs.getMetaData();
			int columnsCount = rsmd.getColumnCount();

			for (int j = 1; j <= columnsCount; j++) {
				System.err.print(rsmd.getColumnName(j) + " || ");
			}
			System.out.print("\n");
			System.out.print("\n");
			System.out.print("\n");
			// While Loop to iterate through all data and print results
			rowList.clear();
			while (rs.next()) {
				for (int i = 1; i <= columnsCount; i++) {
					String Data = rs.getString(i).trim();
					rowList.add(Data.toLowerCase());
					// System.err.print(Data + " || ");
				}
				System.out.print("\n");
			}
			// if (rowList == null) {}
			if (rowList.size() == 0) {
				rowList.add("empty");
				con.close();
				// return rowList;
			} else {
				con.close();
				// return rowList;
			}
		}

		catch (Exception e) {
			AspireLog4j.setLoggerMessageLevel("\n DB error ", Log4jLevels.INFO, e);
			e.printStackTrace();
			// System.err.println("ERROR!!! Please check the query statement");
			con.close();
			// return null;
		}

		return false;

	}

// I think we can move this method to Base Page later - Raed
	public void clickOnElement(String element) throws IOException {
		WebDriverWait wait = new WebDriverWait(getDriverProvider().get(), 40);
		wait.until(ExpectedConditions
				.elementToBeClickable(By.cssSelector(EnvirommentManager.getInstance().getProperty(element))));
		WebDriver driver = getDriverProvider().get();
		Actions action = new Actions(driver);
		action.moveToElement(findElement(By.cssSelector(EnvirommentManager.getInstance().getProperty(element)))).click()
				.perform();
	}

	
	public void alert() throws IOException, InterruptedException {
		Thread.sleep(7000);
		clickOnElement("Close");
		Thread.sleep(4000);
		boolean x1 = test.isDialogPresent(getDriverProvider().get());
		String val = test.getBrowserAlertText(getDriverProvider().get());
		test.declineBrowserAlert(getDriverProvider().get());
		Thread.sleep(5000);
		System.out.println("my data = " + x1 + val);
	}
	
	
	
	
}