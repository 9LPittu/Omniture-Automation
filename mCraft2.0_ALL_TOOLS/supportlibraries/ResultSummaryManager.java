package supportlibraries;

import java.io.File;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Date;
import java.util.Properties;


import com.cognizant.framework.FrameworkParameters;
import com.cognizant.framework.ReportSettings;
import com.cognizant.framework.ReportTheme;
import com.cognizant.framework.ReportThemeFactory;
import com.cognizant.framework.Settings;
import com.cognizant.framework.TimeStamp;
import com.cognizant.framework.Util;
import com.cognizant.framework.ReportThemeFactory.Theme;



/**
 * Abstract class that manages the result summary creation during a batch execution
 * @author Cognizant
 */
public abstract class ResultSummaryManager
{
	/**
	 * The {@link SeleniumReport} object used for managing the result summary
	 */
	protected static SeleniumReport summaryReport;
	
	private static ReportSettings reportSettings;
	private static String reportPath;
	private static String timeStamp;
	
	private static Date overallStartTime, overallEndTime;
	// All the above variables have been marked as static
	// so that they will maintain their state across multiple threads
	
	/**
	 * The {@link Properties} object with settings loaded from the framework properties file
	 */
	public static Properties properties;
	/**
	 * The {@link FrameworkParameters} object
	 */
	protected FrameworkParameters frameworkParameters =
									FrameworkParameters.getInstance();
	
	
	protected void setRelativePath()
	{
		String relativePath = new File(System.getProperty("user.dir")).getAbsolutePath();
		if(relativePath.contains("supportlibraries")) {
			relativePath = new File(System.getProperty("user.dir")).getParent();
		}
		frameworkParameters.setRelativePath(relativePath);
	}
	
	protected void initializeTestBatch()
	{
		overallStartTime = Util.getCurrentTime();
		
		properties = Settings.getInstance();
	}
	
	protected void initializeSummaryReport(String runConfiguration, int nThreads)
	{
		frameworkParameters.setRunConfiguration(runConfiguration);
		timeStamp = TimeStamp.getInstance();
		
		initializeReportSettings();
		ReportTheme reportTheme =
				ReportThemeFactory.getReportsTheme(Theme.valueOf("CLASSIC"));
		
		summaryReport = new SeleniumReport(reportSettings, reportTheme);
		
		summaryReport.initialize();
		summaryReport.initializeResultSummary();
		createResultSummaryHeader(nThreads);
	}
	
	protected void initializeReportSettings()
	{
		reportPath = frameworkParameters.getRelativePath() +
						Util.getFileSeparator() + "Results" +
						Util.getFileSeparator() + timeStamp;
		reportSettings = new ReportSettings(reportPath, "");
		
		reportSettings.setDateFormatString(properties.getProperty("DateFormatString"));
		reportSettings.setProjectName(properties.getProperty("ProjectName"));
		reportSettings.generateExcelReports = Boolean.parseBoolean("False");
		reportSettings.generateHtmlReports = Boolean.parseBoolean("True");
		reportSettings.linkTestLogsToSummary = true;
	}
	
	protected void createResultSummaryHeader(int nThreads)
	{
		summaryReport.addResultSummaryHeading(reportSettings.getProjectName() +
											" - " +	" Automation Execution Result Summary");
		summaryReport.addResultSummarySubHeading(Util.getCurrentFormattedTime(properties.getProperty("DateFormatString")),
				properties.getProperty("OnError"),properties.getProperty("RunConfiguration"),nThreads,111111111,222222222);
		
		summaryReport.addResultSummaryTableHeadings();
	}
	
	protected void setupErrorLog() throws FileNotFoundException
	{
		String errorLogFile = reportPath + Util.getFileSeparator() + "ErrorLog.txt";
		System.setErr(new PrintStream(new FileOutputStream(errorLogFile)));
	}
	
	protected void wrapUp() throws IOException
	{

		
		
		overallEndTime = Util.getCurrentTime();;
		String totalExecutionTime =
				Util.getTimeDifference(overallStartTime, overallEndTime);
		summaryReport.addResultSummaryFooter(totalExecutionTime);
       // PDFGenerator.createPDF(reportPath + "/HTML Results/Summary.Html", reportPath + "/HTML Results/Summary.pdf");
      //  Emulators.restart();
        /* if(!(properties.getProperty("OpeningEmulator").toString().equals("Automatic")  &&!Allocator.platformToExecute.equals("iOS")))
		{
		  Emulators.killEmulator();
	    } 		*/

	}
	

	
	protected void launchResultSummary()
	{
		if (reportSettings.generateHtmlReports) {
			try {
				Runtime.getRuntime().exec("RunDLL32.EXE shell32.dll,ShellExec_RunDLL " +
												reportPath + "/HTML Results/Summary.Html");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if (reportSettings.generateExcelReports) {
			try {
				Runtime.getRuntime().exec("RunDLL32.EXE shell32.dll,ShellExec_RunDLL " +
												reportPath + "/Excel Results/Summary.xls");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}