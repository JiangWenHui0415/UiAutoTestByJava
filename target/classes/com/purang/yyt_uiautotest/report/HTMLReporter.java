package com.purang.yyt_uiautotest.report;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Collection;
import java.util.Map;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;
import org.apache.velocity.VelocityContext;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestNGMethod;
import org.testng.Reporter;
import org.testng.IClass;
import org.testng.ITestResult;
import org.testng.IResultMap;
import org.testng.xml.XmlSuite;
import com.purang.yyt_uiautotest.report.AbstractReporter;
import com.purang.yyt_uiautotest.report.ReportNGException;
import com.purang.yyt_uiautotest.report.TestClassComparator;
import com.purang.yyt_uiautotest.report.TestMethodComparator;
import com.purang.yyt_uiautotest.report.TestResultComparator;

/****************************************************************************************
 * Enhanced HTML reporter for TestNG that uses Velocity templates to generate
 * its output.
 * 
 * @author Daniel Dyer
 ****************************************************************************************/
public class HTMLReporter extends AbstractReporter {
	private static final String FRAMES_PROPERTY = "com.purang.yyt_uiautotest.report.frames";

	private static final String TEMPLATES_PATH = "com/purang/yyt_uiautotest/report/templates/html/";
	private static final String INDEX_FILE = "index.html";
	private static final String SUITES_FILE = "suites.html";
	private static final String OVERVIEW_FILE = "overview.html";
	private static final String GROUPS_FILE = "groups.html";
	private static final String RESULTS_FILE = "results.html";
	private static final String CHRONOLOGY_FILE = "chronology.html";
	private static final String OUTPUT_FILE = "output.html";
	private static final String CUSTOM_STYLE_FILE = "custom.css";

	private static final String SUITE_KEY = "suite";
	private static final String SUITES_KEY = "suites";
	private static final String GROUPS_KEY = "groups";
	private static final String RESULT_KEY = "result";
	private static final String FAILED_CONFIG_KEY = "failedConfigurations";
	private static final String SKIPPED_CONFIG_KEY = "skippedConfigurations";
	private static final String FAILED_TESTS_KEY = "failedTests";
	private static final String SKIPPED_TESTS_KEY = "skippedTests";
	private static final String PASSED_TESTS_KEY = "passedTests";
	private static final String METHODS_KEY = "methods";

	private static final String REPORT_DIRECTORY = "summary";

	private static final Comparator<ITestNGMethod> METHOD_COMPARATOR = new TestMethodComparator();
	private static final Comparator<ITestResult> RESULT_COMPARATOR = new TestResultComparator();
	private static final Comparator<IClass> CLASS_COMPARATOR = new TestClassComparator();

	public HTMLReporter() {
		super(TEMPLATES_PATH);
	}

	/****************************************************************************************
	 * Generates a set of HTML files that contain data about the outcome of the
	 * specified test suites.
	 * 
	 * @param suites
	 *            Data about the test runs.
	 * @param outputDirectoryName
	 *            The directory in which to create the report.
	 ****************************************************************************************/
	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectoryName) {
		removeEmptyDirectories(new File(outputDirectoryName));

		boolean useFrames = System.getProperty(FRAMES_PROPERTY, "true").equals("true");
		File outputDir = new File(outputDirectoryName);
		
		if (!outputDir.exists()) {
			outputDir.mkdir();
		}
		
		File outputDirectory = new File(outputDirectoryName, REPORT_DIRECTORY);
		outputDirectory.mkdir();

		try {
			if (useFrames) {
				createFrameset(outputDirectory);
			}
			createOverview(suites, outputDirectory, !useFrames);
			createSuiteList(suites, outputDirectory);
			createGroups(suites, outputDirectory);
			createResults(suites, outputDirectory);
			createLog(outputDirectory);
			copyResources(outputDirectory);
		} catch (Exception ex) {
			throw new ReportNGException("Failed generating HTML report.", ex);
		}
	}

	/****************************************************************************************
	 * Create the index file that sets up the frameset.
	 * 
	 * @param outputDirectory
	 *            The target directory for the generated file(s).
	 ****************************************************************************************/
	private void createFrameset(File outputDirectory) throws Exception {
		VelocityContext context = createContext();
		generateFile(new File(outputDirectory, INDEX_FILE), INDEX_FILE + TEMPLATE_EXTENSION, context);
	}

	private void createOverview(List<ISuite> suites, File outputDirectory, boolean isIndex) throws Exception {
		VelocityContext context = createContext();
		context.put(SUITES_KEY, suites);
		generateFile(new File(outputDirectory, isIndex ? INDEX_FILE : OVERVIEW_FILE), OVERVIEW_FILE
				+ TEMPLATE_EXTENSION, context);
	}

	/****************************************************************************************
	 * Create the navigation frame.
	 * 
	 * @param outputDirectory
	 *            The target directory for the generated file(s).
	 ****************************************************************************************/
	private void createSuiteList(List<ISuite> suites, File outputDirectory) throws Exception {
		VelocityContext context = createContext();
		context.put(SUITES_KEY, suites);
		generateFile(new File(outputDirectory, SUITES_FILE), SUITES_FILE + TEMPLATE_EXTENSION, context);
	}

	/****************************************************************************************
	 * Generate a results file for each test in each suite.
	 * 
	 * @param outputDirectory
	 *            The target directory for the generated file(s).
	 ****************************************************************************************/
	private void createResults(List<ISuite> suites, File outputDirectory) throws Exception {
		int index = 1;
		for (ISuite suite : suites) {
			int index2 = 1;
			for (ISuiteResult result : suite.getResults().values()) {
				VelocityContext context = createContext();
				context.put(RESULT_KEY, result);
				context.put(FAILED_CONFIG_KEY, sortByTestClass(result.getTestContext().getFailedConfigurations()));
				context.put(SKIPPED_CONFIG_KEY, sortByTestClass(result.getTestContext().getSkippedConfigurations()));
				context.put(FAILED_TESTS_KEY, sortByTestClass(result.getTestContext().getFailedTests()));
				context.put(SKIPPED_TESTS_KEY, sortByTestClass(result.getTestContext().getSkippedTests()));
				context.put(PASSED_TESTS_KEY, sortByTestClass(result.getTestContext().getPassedTests()));
				String fileName = String.format("suite%d_test%d_%s", index, index2, RESULTS_FILE);
				generateFile(new File(outputDirectory, fileName), RESULTS_FILE + TEMPLATE_EXTENSION, context);
				++index2;
			}
			++index;
		}
	}

	@SuppressWarnings({ "unused", "deprecation" })
	private void createChronology(List<ISuite> suites, File outputDirectory) throws Exception {
		int index = 1;
		for (ISuite suite : suites) {
			List<ITestNGMethod> methods = new ArrayList<ITestNGMethod>(suite.getInvokedMethods());
			if (!methods.isEmpty()) {
				VelocityContext context = createContext();
				context.put(SUITE_KEY, suite);
				context.put(METHODS_KEY, methods);
				String fileName = String.format("suite%d_%s", index, CHRONOLOGY_FILE);
				generateFile(new File(outputDirectory, fileName), CHRONOLOGY_FILE + TEMPLATE_EXTENSION, context);
			}
			++index;
		}
	}

	/****************************************************************************************
	 * Group test methods by class and sort alphabetically.
	 ****************************************************************************************/
	private SortedMap<IClass, List<ITestResult>> sortByTestClass(IResultMap results) {
		SortedMap<IClass, List<ITestResult>> sortedResults = new TreeMap<IClass, List<ITestResult>>(CLASS_COMPARATOR);
		for (ITestResult result : results.getAllResults()) {
			List<ITestResult> resultsForClass = sortedResults.get(result.getTestClass());
			if (resultsForClass == null) {
				resultsForClass = new ArrayList<ITestResult>();
				sortedResults.put(result.getTestClass(), resultsForClass);
			}
			int index = Collections.binarySearch(resultsForClass, result, RESULT_COMPARATOR);
			if (index < 0) {
				index = Math.abs(index + 1);
			}
			resultsForClass.add(index, result);
		}
		return sortedResults;
	}

	/****************************************************************************************
	 * Generate a groups list for each suite.
	 * 
	 * @param outputDirectory
	 *            The target directory for the generated file(s).
	 ****************************************************************************************/
	private void createGroups(List<ISuite> suites, File outputDirectory) throws Exception {
		int index = 1;
		for (ISuite suite : suites) {
			SortedMap<String, SortedSet<ITestNGMethod>> groups = sortGroups(suite.getMethodsByGroups());
			if (!groups.isEmpty()) {
				VelocityContext context = createContext();
				context.put(SUITE_KEY, suite);
				context.put(GROUPS_KEY, groups);
				String fileName = String.format("suite%d_%s", index, GROUPS_FILE);
				generateFile(new File(outputDirectory, fileName), GROUPS_FILE + TEMPLATE_EXTENSION, context);
			}
			++index;
		}
	}

	/****************************************************************************************
	 * Generate a groups list for each suite.
	 * 
	 * @param outputDirectory
	 *            The target directory for the generated file(s).
	 ****************************************************************************************/
	private void createLog(File outputDirectory) throws Exception {
		if (!Reporter.getOutput().isEmpty()) {
			VelocityContext context = createContext();
			generateFile(new File(outputDirectory, OUTPUT_FILE), OUTPUT_FILE + TEMPLATE_EXTENSION, context);
		}
	}

	/****************************************************************************************
	 * Sorts groups alphabetically and also sorts methods within groups
	 * alphabetically (class name first, then method name). Also eliminates
	 * duplicate entries.
	 ****************************************************************************************/
	private SortedMap<String, SortedSet<ITestNGMethod>> sortGroups(Map<String, Collection<ITestNGMethod>> groups) {
		SortedMap<String, SortedSet<ITestNGMethod>> sortedGroups = new TreeMap<String, SortedSet<ITestNGMethod>>();
		for (Map.Entry<String, Collection<ITestNGMethod>> entry : groups.entrySet()) {
			SortedSet<ITestNGMethod> methods = new TreeSet<ITestNGMethod>(METHOD_COMPARATOR);
			methods.addAll(entry.getValue());
			sortedGroups.put(entry.getKey(), methods);
		}
		return sortedGroups;
	}

	/****************************************************************************************
	 * Reads the CSS and JavaScript files from the JAR file and writes them to
	 * the output directory.
	 * 
	 * @param outputDirectory
	 *            Where to put the resources.
	 * @throws IOException
	 *             If the resources can't be read or written.
	 ****************************************************************************************/
	private void copyResources(File outputDirectory) throws IOException {
		copyClasspathResource(outputDirectory, "report.css", "report.css");
		copyClasspathResource(outputDirectory, "report.js", "report.js");
		copyClasspathResource(outputDirectory, "sorttable.js", "sorttable.js");
		File customStylesheet = META.getStylesheetPath();
		if (customStylesheet != null) {
			copyFile(outputDirectory, customStylesheet, CUSTOM_STYLE_FILE);
		}
	}
}
