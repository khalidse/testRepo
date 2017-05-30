
package com.univision.radio.test;

import static java.util.Arrays.asList;
import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;
import static org.jbehave.core.reporters.Format.CONSOLE;
import static org.jbehave.web.selenium.WebDriverHtmlOutput.WEB_DRIVER_HTML;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import jo.aspire.automation.logger.AspireLog4j;
import jo.aspire.automation.logger.EnvirommentManager;
import jo.aspire.automation.logger.Log4jLevels;
import jo.aspire.generic.MockServerProxy;
import jo.aspire.web.automationUtil.DriverProvider;
import org.apache.commons.lang3.StringUtils;
import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.embedder.Embedder;
import org.jbehave.core.embedder.MetaFilter;
import org.jbehave.core.embedder.StoryControls;
import org.jbehave.core.embedder.StoryManager;
import org.jbehave.core.failures.FailingUponPendingStep;
import org.jbehave.core.failures.PendingStepStrategy;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.model.Meta;
import org.jbehave.core.model.Scenario;
import org.jbehave.core.model.Story;
import org.jbehave.core.reporters.CrossReference;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.spring.SpringApplicationContextFactory;
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.jbehave.web.selenium.ContextView;
import org.jbehave.web.selenium.LocalFrameContextView;
import org.jbehave.web.selenium.SeleniumConfiguration;
import org.jbehave.web.selenium.SeleniumContext;
import org.jbehave.web.selenium.SeleniumContextOutput;
import org.jbehave.web.selenium.SeleniumStepMonitor;
import org.springframework.context.ApplicationContext;
import com.aspire.automationReport.AspireReport;
import com.aspire.automationReport.ReportDataManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.univision.radio.zap.ZapScan;

import org.apache.commons.io.FileUtils;

/**
 * Class which provides the link between the JBehave's executor framework
 * (called Embedder) and the textual stories.
 */
// @RunWith(JUnitReportingRunner.class)
public class StoriesTest extends JUnitStories {
	private static boolean rerunFailedFlag = false;

	// private String[] browsers;
	// static boolean isRunOnSauce;
	// static String[] sauceBrowsers;
	PendingStepStrategy pendingStepStrategy = new FailingUponPendingStep();
	CrossReference crossReference = new CrossReference().withJsonOnly().withPendingStepStrategy(pendingStepStrategy)
			.withOutputAfterEachStory(true).excludingStoriesWithNoExecutedScenarios(true);
	ContextView contextView = new LocalFrameContextView().sized(640, 80).located(10, 10);
	SeleniumContext seleniumContext = new SeleniumContext();
	SeleniumStepMonitor stepMonitor = new SeleniumStepMonitor(contextView, seleniumContext,
			crossReference.getStepMonitor());
	Format[] formats = new Format[] { new SeleniumContextOutput(seleniumContext), CONSOLE, WEB_DRIVER_HTML,
			AspireReport.Aspire_Report };
	StoryReporterBuilder reporterBuilder = new StoryReporterBuilder().withFailureTrace(true)
			.withFailureTraceCompression(true).withDefaultFormats().withFormats(formats)
			.withCrossReference(crossReference);

	static String storiesPathToRun = "*";

	@Override
	public Configuration configuration() {
		return new SeleniumConfiguration().useSeleniumContext(seleniumContext)
				.usePendingStepStrategy(pendingStepStrategy)
				.useStoryControls(new StoryControls().doResetStateBeforeScenario(true)).useStepMonitor(stepMonitor)
				.useStoryLoader(new LoadFromClasspath(StoriesTest.class)).useStoryReporterBuilder(reporterBuilder);
	}

	@Override
	public InjectableStepsFactory stepsFactory() {
		ApplicationContext context = new SpringApplicationContextFactory("steps.xml").createApplicationContext();
		return new SpringStepsFactory(configuration(), context);
	}

	@Override
	protected List<String> storyPaths() {
		return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()).getFile(),
				asList("**/" + System.getProperty("storyFilter", "Promo Check-TC 003-Promo Tag is clickable") + ".story"), null);

	}

	protected List<String> failedStoryPaths(List<String> stories) {
		ArrayList<String> storiesToRun = new ArrayList<String>();

		for (String story : stories) {
			storiesToRun.add("**/" + story + ".story");
		}
		return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()).getFile(), storiesToRun, null);
	}

	public void startStories(Embedder embedder, Boolean runFailed) {
		try {
			if (runFailed) {
				embedder.runStoriesAsPaths(
						failedStoryPaths(AspireReport.getInstance().getReportDataManager().getFailedStories()));
			} else {
				embedder.runStoriesAsPaths(storyPaths());
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// embedder.generateCrossReference();
		}
	}

	private JsonArray drivers;

	public static JsonElement convertFileToJSON(String filePath) {
		JsonElement jsonElement = null;
		try {
			JsonParser parser = new JsonParser();
			jsonElement = parser.parse(new FileReader(filePath));
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.print(ex);
		}
		return jsonElement;
	}

	@Override
	public void run() throws Throwable {

		// ******* Don't Erase this line used to initialize environment manager
		// class ***** //
		jo.aspire.automation.logger.EnvirommentManager.setInitialClass(this.getClass());

		// Method used to Set Threading to true or false based on Threads
		// properties value

		checkThreadsValue();

		// Screenshot property
		AspireReport.getInstance().getReportDataManager().setPreScreenshotEnabled(true);
		AspireReport.getInstance().getReportDataManager().setFailedScreenshotEnabled(true);
		AspireReport.getInstance().getReportDataManager().setPostScreenshotEnabled(true);
		AspireReport.getInstance().getReportDataManager().setDeleteScreenshotsForPassedScenarios(true);
		// Report Information to be added
		AspireReport.getInstance().getReportDataManager().setBuildNumber("apple");
		AspireReport.getInstance().getReportDataManager().setPlatformName("OS X EL Capitan");
		AspireReport.getInstance().getReportDataManager().setPlatformVersion("10.11.6");

		/******** Start Change result folder structure **************/

		// Change result folder structure
		// Each folder should contains 4 files :
		// 1-html report
		// 2-screenshots folder
		// 3-High_Level_Results.txt
		// 4-log file
		// HTML report naming convention:
		// Automation_Report_BuildNumber-" + buildName + "" + browser + ""
		// +dateAndTime + ".html
		// Wiki page :
		// https://github.com/AspireInfotech/Automation-framework/wiki/Change-result-folder-structure
		// Ticket Number AF-95
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat("EEE, d MMM yyyy - hh,,mm,,ss");

		// Set Report folder and report name time zone //

		ft = ReportDataManager.setDateFormatWithTimeZone(ft);

		String buildName = System.getProperty("buildName") != null ? System.getProperty("buildName") : "XYZ";
		String dateAndTime = ft.format(date);
		dateAndTime = dateAndTime.replace(",,", " ");
		AspireReport.getInstance().getReportDataManager()
				.setReportPath(AspireReport.getInstance().getReportDataManager().getReportPath() + buildName + "_"
						+ dateAndTime + File.separator);
		createBuildFolder();

		/*********** End Change result folder structure *******************/

		drivers = convertFileToJSON(this.getClass().getResource("/configs/drivers.json").getPath()).getAsJsonArray();
		int executableBrowsers = 0;
		for (JsonElement dr : drivers) {

			JsonObject currentDriver = dr.getAsJsonObject();
			if (Boolean.parseBoolean(currentDriver.get("execute").getAsString())) {
				executableBrowsers++;
				DriverProvider.setDriverToRun(currentDriver);

				EnvirommentManager.setInitialClass(this.getClass());

				boolean isAnalytics = Boolean
						.parseBoolean(currentDriver.get("local").getAsJsonObject().get("isProxy").getAsString());
				boolean isSecurityTest = Boolean
						.parseBoolean(currentDriver.get("local").getAsJsonObject().get("isSecurityTest").getAsString());
				String proxyHost = currentDriver.get("local").getAsJsonObject().get("proxy").getAsJsonObject()
						.get("proxyHost").getAsString();
				int proxyPort = Integer.parseInt(currentDriver.get("local").getAsJsonObject().get("proxy")
						.getAsJsonObject().get("proxyPort").getAsString());

				ZapScan zap = new ZapScan();
				if (isSecurityTest) {
					zap.setup(proxyHost, proxyPort);
				}

				if (isAnalytics) {
					MockServerProxy proxy = new MockServerProxy();
					proxy.startProxy(proxyPort);

				}
				boolean rerunFailed = Boolean
						.parseBoolean(EnvirommentManager.getInstance().getProperty("rerunFailedStories"));
				rerunFailedFlag = rerunFailed;
				int rerunCount = 0;
				if (rerunFailed) {
					rerunCount = Integer.parseInt(EnvirommentManager.getInstance().getProperty("rerunCount"));
				}

				dateAndTime = ft.format(date);
				dateAndTime = dateAndTime.replace(",,", " ");

				String ReportName = "Automation_Report_BuildNumber-" + buildName + "_" + dateAndTime + ".html";
				AspireReport.getInstance().getReportDataManager().setReportFileName(ReportName);

				AspireReport.getInstance().getReportDataManager().setReportTitle("Aspire");
				AspireReport.getInstance().getReportDataManager().setReportSubTitle("testing report");
				AspireReport.getInstance().getReportDataManager().setTitle("Aspire");

				// boolean isRemote =
				// Boolean.parseBoolean(currentDriver.get("isRemote").getAsString());
				String browser = currentDriver.get("name").getAsString().toLowerCase();

				// if (!isRemote) { // if local not remote
				Embedder embedder = getEmbedder();
				embedder.systemProperties().setProperty("browser", browser);
				ReportName = "Automation_Report_BuildNumber-" + buildName + "-" + browser + "_" + dateAndTime + ".html";
				AspireReport.getInstance().getReportDataManager().setReportFileName(ReportName);
				skipScenariosList(embedder);
				startStories(embedder, false);
				if (rerunFailed) {
					rerunFailedStories(rerunCount);
				}

				if (isSecurityTest) {
					zap.scan();
					zap.cleanup(proxyHost, proxyPort);
				}
				/*******
				 * Get Latest log file and copy it into XYZ folder and then
				 * delete it from logs directory
				 *******/
				File latestLogFile = getLatestLogFile();
				if (copyLogFile(latestLogFile, latestLogFile.getName())) {
					AspireLog4j.setLoggerMessageLevel("Latest Log file (" + latestLogFile.getName() + ") copied",
							Log4jLevels.DEBUG);

					deleteLatestLogFile(latestLogFile.getName());

				} else {
					AspireLog4j.setLoggerMessageLevel("Latest Log file not copied to bulid folder ", Log4jLevels.WARN);
				}

				/******* End log file deletion and copying *******/

			}

		}

		if (executableBrowsers == 0) {

			AspireLog4j.setLoggerMessageLevel(
					"No executable brwosers set as true inisde drivers.json \n  Run Terminated", Log4jLevels.INFO);

		}

	}

	private Embedder getEmbedder() {
		Embedder embedder = null;
		useEmbedder(new Embedder());
		useConfiguration(null);
		useStepsFactory(null);
		embedder = configuredEmbedder();

		if (EnvirommentManager.getInstance().getProperty("STORY_TIMEOUT") != null
				&& StringUtils.isNumeric(EnvirommentManager.getInstance().getProperty("STORY_TIMEOUT"))) {
			embedder.embedderControls().useStoryTimeouts(EnvirommentManager.getInstance().getProperty("STORY_TIMEOUT"));
		} else {
			embedder.embedderControls().useStoryTimeouts("**/*-Long*:1500,**/***:600");
		}

		embedder.embedderControls().doGenerateViewAfterStories(false);
		embedder.useMetaFilters(getMetaData());
		int threads = Integer.parseInt(EnvirommentManager.getInstance().getProperty("Threads"));
		embedder.embedderControls().useThreads(threads);
		return embedder;
	}

	private void rerunFailedStories(int rerunCount) {
		Embedder embedder = null;
		for (int j = 1; j <= rerunCount; j++) {
			if (AspireReport.getInstance().getReportDataManager().getFailedStories() != null
					&& AspireReport.getInstance().getReportDataManager().getFailedStories().size() > 0) {
				embedder = getEmbedder();
				// embedder.embedderControls().useStoryTimeouts("**/*-Long*:30000,**/***:15000");
				startStories(embedder, true);
			}
		}

	}

	public List<String> getMetaData() {
		String metaData = System.getProperty("metaData");
		if (metaData == null) {
			metaData = EnvirommentManager.getInstance().getProperty("metaData");
		}
		ArrayList<String> metaDataList = new ArrayList<String>();
		if (metaData != null && !metaData.isEmpty()) {
			metaDataList.addAll(asList(metaData.split(",")));
		}
		metaDataList.add("-skip");
		return metaDataList;
	}

	public static void skipScenariosList(Embedder embedder) {
		ArrayList<Scenario> skipScenarios = new ArrayList<Scenario>();
		StoryManager storyManager = embedder.storyManager();
		List<String> stories = new StoryFinder().findPaths(codeLocationFromClass(StoriesTest.class).getFile(),
				asList("**/" + System.getProperty("storyFilter", storiesPathToRun) + ".story"), null);
		for (String storyPath : stories) {
			Story story = storyManager.storyOfPath(storyPath);
			if (new MetaFilter("skip").allow(story.getMeta())) {
				for (Scenario scenario : story.getScenarios()) {
					// scenario also inherits meta from story
					Meta inherited = scenario.getMeta().inheritFrom(story.getMeta());
					if (new MetaFilter("+skip").allow(inherited)) {
						System.out.println("this is skipped:" + scenario.getTitle());
						skipScenarios.add(scenario);
						// remove comment on break line if you want the skip
						// over whole story
						// break;
					}
				}
			}
		}
		AspireReport.getInstance().getReportDataManager().addSkippedScenarios(skipScenarios);
		// AspireReport.getInstance().printEveryThing();
	}

	/**
	 * Method used to Set Threading to true or false based on Threads
	 */
	public void checkThreadsValue() {

		EnvirommentManager propsUtil = EnvirommentManager.getInstance();
		String threads = propsUtil.getProperty("Threads");
		if (StringUtils.isNumeric(threads)) {
			if (Integer.parseInt(threads) > 1)
				AspireReport.getInstance().getReportDataManager().setThreading(true);
			else if (Integer.parseInt(threads) == 1)
				AspireReport.getInstance().getReportDataManager().setThreading(false);
			else {
				AspireLog4j.setLoggerMessageLevel("Threads value can't be 0 run terminated ", Log4jLevels.ERROR);
				System.exit(0);
			}
		}

		else {

			AspireLog4j.setLoggerMessageLevel("Threads value can't be null - run terminated ", Log4jLevels.ERROR);
			System.exit(0);

		}

	}

	/**
	 * Method used to copy log file from old path to new path
	 * 
	 * @param latestLogFile
	 * 
	 *            Latest log file from logs directory
	 * @param latestLogFileName
	 * 
	 *            Latest log file Name from logs directory
	 * 
	 * @return
	 * 
	 * 		boolean to indicate if the log file copied or not
	 */

	private boolean copyLogFile(File latestLogFile, String latestLogFileName) {
		boolean isLogFileCopied = false;
		try {

			FileUtils.copyFile(latestLogFile, new File(ReportDataManager.getReportPath() + latestLogFileName));
			isLogFileCopied = true;
		} catch (Exception exception) {

			AspireLog4j.setLoggerMessageLevel("Error During Copy Log File", Log4jLevels.ERROR, exception);

		}
		return isLogFileCopied;
	}

	/**
	 * Method used to pick latest log file from the logs directory
	 * 
	 * @return
	 * 
	 * 		Latest log file from logs directory
	 */
	private File getLatestLogFile() {
		File dir = new File(System.getProperty("user.dir") + File.separator + "logs");
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile;
	}

	/**
	 * Delete log file if its moved successfully
	 * 
	 * @param Latest
	 *            log file name to be deleted
	 */
	private void deleteLatestLogFile(String latestLogFileName) {
		try {

			Files.delete(Paths.get(
					System.getProperty("user.dir") + File.separator + "logs" + File.separator + latestLogFileName));

		} catch (IOException exception) {
			AspireLog4j.setLoggerMessageLevel("Error During delete log file (" + latestLogFileName + ") ",
					Log4jLevels.ERROR, exception);

		}
	}

	/**
	 * Method used to create build folder if its not exist
	 */
	private void createBuildFolder() {

		try {

			File reportFolder = new File(AspireReport.getInstance().getReportDataManager().getReportPath());

			if (!reportFolder.exists())

			{

				reportFolder.mkdir();
			}
		} catch (Exception ex) {

			AspireLog4j.setLoggerMessageLevel("Error During Create build folder", Log4jLevels.ERROR, ex);

		}
	}

}
