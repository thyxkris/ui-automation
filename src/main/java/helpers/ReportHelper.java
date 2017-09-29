package helpers;

import org.apache.logging.log4j.LogManager;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by makri on 27/09/2017.
 */
public class ReportHelper {
    private String reportFolder;
    private String suffix;

    public ReportHelper(String reportFolder, String suffix) {
        this.reportFolder = reportFolder;
        this.suffix = suffix;

    }

    public void gen() {

        List<String> jsonFiles = new ArrayList<>();
       // String reportFolder = ConfigHelper.getCurrentWorkingDir()+ File.separator+ConfigHelper.getString("parallel.output.directory");
        //ParallelOutputDirectory;
        org.apache.logging.log4j.Logger logger = LogManager.getLogger();
        logger.info(reportFolder.toString());
        File dir = new File(reportFolder);
        logger.info(dir.toString());
        for (File file : dir.listFiles()) {
            if (file.getName().endsWith(this.suffix )) {
                jsonFiles.add(file.getAbsoluteFile().getPath());
            }
        }

        File reportOutputDirectory = new File(reportFolder);
        String buildNumber = "1";
        String projectName = "cucumberProject";
        boolean runWithJenkins = false;
        boolean parallelTesting = true;

        if (System.getProperty("buildNumber") != null) {
            buildNumber = System.getProperty("buildNumber");
        }
        if (System.getProperty("cucumberProject") != null) {
            projectName = System.getProperty("cucumberProject");
        }
        if (System.getProperty("runWithJenkins") != null) {
            runWithJenkins = Boolean.valueOf(System.getProperty("runWithJenkins"));
        }
        if (System.getProperty("parallelTesting") != null) {
            parallelTesting = Boolean.valueOf(System.getProperty("parallelTesting"));
        }

        Configuration configuration = new Configuration(reportOutputDirectory, projectName);
// optional configuration
        configuration.setParallelTesting(parallelTesting);
        configuration.setRunWithJenkins(runWithJenkins);
        configuration.setBuildNumber(buildNumber);

// addidtional metadata presented on main page

        configuration.addClassifications("Test Env", helpers.ConfigHelper.getTestEnv());
        configuration.addClassifications("implicit wait time", helpers.ConfigHelper.getImplicitWaitTimeString());
        configuration.addClassifications("element wait time", helpers.ConfigHelper.getElementWaitTimeString());
        configuration.addClassifications("pageload wait time", helpers.ConfigHelper.getPageLoadWaitTimeString());
        // configuration.addClassifications("selenium grid url", libraries.helpers.ConfigHelper.getSeleniumGridURL());
        configuration.addClassifications("attempts.times", helpers.ConfigHelper.getAttemptCount().toString());

        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);

        Reportable result = reportBuilder.generateReports();
    }

}
