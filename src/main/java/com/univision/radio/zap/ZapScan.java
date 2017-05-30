package com.univision.radio.zap;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import net.continuumsecurity.proxy.ScanningProxy;
import net.continuumsecurity.proxy.Spider;
import net.continuumsecurity.proxy.ZAProxyScanner;

/**
 * @author Martin Schneider
 */
public class ZapScan {

	private static Logger LOGGER = Logger.getLogger("ZAP");

	/** CONFIGURATION */
	private final static String BASE_URL = "http://localhost:8080";
	private final static String USERNAME = "admin";
	private final static String PASSWORD = "admin";
	private static final String USERNAME_FIELD = "usernameField";
	private static final String PASSWORD_FIELD = "passwordField";
	private static final String LOGIN_BUTTON = "loginButton";
	//private static final String SUCCESSFUL_LOGIN_PATTERN = "Log In Successful";
	private static final String DRIVER = "htmlunit"; // options are htmlunit, firefox and chrome, htmlunit is default
	
	private final static String ZAP_PATH = "zap/zap.sh";
	
	private final static String ZAP_APIKEY = "zapapisecret";
	private final static String MEDIUM = "MEDIUM";
	private final static String HIGH = "HIGH";
	

	private ZapManager zapManager;
	private ScanningProxy zapScanner;
	private Spider zapSpider;
	private WebDriver driver;
//	private SeleniumDriverFactory driverFactory = new SeleniumDriverFactory();
	
	private final static String[] policyNames = { "directory-browsing", "cross-site-scripting", "sql-injection",
			"path-traversal", "remote-file-inclusion", "server-side-include", "script-active-scan-rules",
			"server-side-code-injection", "external-redirect", "crlf-injection" };
	static int currentScanID;

	
//	public  void s(String args[]) throws Exception
//	{
//
//		instance.setup();
//	//	instance.scan();
//		//instance.cleanup();
//	}
//	
	
	public void scan()
	{
		// LOGIN
	//	login();

		// SPIDERING
		LOGGER.info("Spidering...");
		zapSpider.setThreadCount(5);
		zapSpider.setMaxDepth(5);
		zapSpider.setPostForms(false);
		zapSpider.spider(BASE_URL, null, false, null);
		int spiderID = zapSpider.getLastSpiderScanId();
		for (String url : zapSpider.getSpiderResults(spiderID)) {
			LOGGER.info("Found URL: " + url);
		}
		LOGGER.info("Spider done.");

		// SCANNING
		setAlertAndAttackStrength();
		zapScanner.setEnablePassiveScan(true);
		LOGGER.info("Scanning...");
		zapScanner.scan(BASE_URL);
		currentScanID = zapScanner.getLastScannerScanId();
		int complete = 0;
		while (complete < 100) {
            complete = zapScanner.getScanProgress(currentScanID);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOGGER.error(e);
            }
        }
		LOGGER.info("Scanning done.");
	}
	
	public void setup(String proxyHost, int proxyPort) throws Exception {
		zapManager = ZapManager.getInstance();
		//TODO: port issue 
		proxyPort = zapManager.startZAP(ZAP_PATH, proxyHost, proxyPort); 
		zapScanner = new ZAProxyScanner(proxyHost, proxyPort, ZAP_APIKEY);
		zapScanner.clear();
		zapSpider = (Spider) zapScanner;
		LOGGER.info("Created client to ZAP API");
	//	driver = driverFactory.createDriver(DRIVER, ZAP_PROXYHOST, zapProxyPort);
	}

	public void cleanup(String proxyHost, int proxyPort) throws IOException {
		//driver.quit();
		FileUtils.writeByteArrayToFile(new File("report.html"), zapScanner.getHtmlReport());
		zapManager.stopZap(proxyHost, proxyPort);
	}

	private void setAlertAndAttackStrength() {
		for (String policyName : policyNames) {
			String ids = enableZapPolicy(policyName);
			for (String id : ids.split(",")) {
				zapScanner.setScannerAlertThreshold(id, MEDIUM);
				zapScanner.setScannerAttackStrength(id, HIGH);
			}
		}
	}

	private String enableZapPolicy(String policyName) {
		String scannerIds = null;
		switch (policyName.toLowerCase()) {
		case "directory-browsing":
			scannerIds = "0";
			break;
		case "cross-site-scripting":
			scannerIds = "40012,40014,40016,40017";
			break;
		case "sql-injection":
			scannerIds = "40018";
			break;
		case "path-traversal":
			scannerIds = "6";
			break;
		case "remote-file-inclusion":
			scannerIds = "7";
			break;
		case "server-side-include":
			scannerIds = "40009";
			break;
		case "script-active-scan-rules":
			scannerIds = "50000";
			break;
		case "server-side-code-injection":
			scannerIds = "90019";
			break;
		case "remote-os-command-injection":
			scannerIds = "90020";
			break;
		case "external-redirect":
			scannerIds = "20019";
			break;
		case "crlf-injection":
			scannerIds = "40003";
			break;
		case "source-code-disclosure":
			scannerIds = "42,10045,20017";
			break;
		case "shell-shock":
			scannerIds = "10048";
			break;
		case "remote-code-execution":
			scannerIds = "20018";
			break;
		case "ldap-injection":
			scannerIds = "40015";
			break;
		case "xpath-injection":
			scannerIds = "90021";
			break;
		case "xml-external-entity":
			scannerIds = "90023";
			break;
		case "padding-oracle":
			scannerIds = "90024";
			break;
		case "el-injection":
			scannerIds = "90025";
			break;
		case "insecure-http-methods":
			scannerIds = "90028";
			break;
		case "parameter-pollution":
			scannerIds = "20014";
			break;
		default:
			throw new RuntimeException("No policy found for: " + policyName);
		}
		zapScanner.setEnableScanners(scannerIds, true);
		return scannerIds;
	}

	private void login() {
		driver.get(BASE_URL);
		LOGGER.trace(driver.getPageSource());
		driver.findElement(By.id(USERNAME_FIELD)).clear();
		driver.findElement(By.id(USERNAME_FIELD)).sendKeys(USERNAME);
		driver.findElement(By.id(PASSWORD_FIELD)).clear();
		driver.findElement(By.id(PASSWORD_FIELD)).sendKeys(PASSWORD);
		driver.findElement(By.id(LOGIN_BUTTON)).click();
	}
}
