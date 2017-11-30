package helpers;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;
import net.masterthought.cucumber.Reportable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by makri on 27/09/2017.
 */
public class ReportHelper {
    private String reportFolder;
    private String jsonFolder;
    private String suffix;
    private String buildNumber;
    private String projectName;
    private boolean runWithJenkins;
    private boolean parallelTesting;
    Configuration configuration;
    Logger logger;


    public ReportHelper(String reportFolder, String suffix) {

        this.suffix = suffix;
        init(reportFolder, reportFolder, "1", "CucumberProject", false, true);
    }


    public ReportHelper(String reportFolder, String jsonFolder, String suffix) {

        this.suffix = suffix;
        init(reportFolder, jsonFolder, "1", "CucumberProject", false, true);
    }

    public ReportHelper(String reportFolder, String jsonFolder, String suffix, String buildNumber, String projectName, boolean runWithJenkins, boolean parallelTesting) {
        this.suffix = suffix;
        init(reportFolder, jsonFolder, buildNumber, projectName, runWithJenkins, parallelTesting);

    }



    public void copyFiles(String srcPath, String destPath, String suffix) throws IOException {
        List<String> jsonFiles = new ArrayList<>();
        org.apache.logging.log4j.Logger logger = LogManager.getLogger();

        File dir = new File(srcPath);
        logger.info(dir.toString());
        for (File file : dir.listFiles()) {
            if (file.getName().endsWith(this.suffix)) {

                logger.info(file.getAbsoluteFile().getPath());
                String dest = destPath + File.separator + file.getName();
                Files.copy(FileSystems.getDefault().getPath(file.getAbsoluteFile().getPath()), FileSystems.getDefault().getPath(dest), StandardCopyOption.REPLACE_EXISTING);

                logger.info(FileSystems.getDefault().getPath(destPath) + " is created");
                logger.info("---- below is the files in the final report folder");

                File dirDest = new File(destPath);
                for (File fileCopied : dirDest.listFiles()) {
                    logger.info(fileCopied.getAbsoluteFile().getPath());
                }

            }
        }


    }

    public void gen() throws IOException {
        List<String> jsonFiles = new ArrayList<>();
        HashMap<String, String> jsons = getJsonListFromFolder(this.jsonFolder);
        for (Map.Entry<String, String> json : jsons.entrySet()) {

            jsonFiles.add(json.getKey());

        }

        genFromFileList(jsonFiles);
    }

    public void init(String reportFolder, String jsonFolder, String buildNumber, String projectName, boolean runWithJenkins, boolean parallelTesting) {
        logger = LogManager.getLogger();
        File reportOutputDirectory = new File(reportFolder);
        this.buildNumber = buildNumber;
        this.projectName = projectName;
        this.runWithJenkins = runWithJenkins;
        this.parallelTesting = parallelTesting;
        this.reportFolder = reportFolder;
        this.jsonFolder = jsonFolder;

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

        configuration = new Configuration(reportOutputDirectory, projectName);
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
    }

    public void genFromFileList(List<String> jsonFiles) {


        ReportBuilder reportBuilder = new ReportBuilder(jsonFiles, configuration);

        Reportable result = reportBuilder.generateReports();
    }

    public HashMap<String, String> getJsonListFromFolder(String jsonFolder) throws IOException {

        List<String> jsonFilePaths = new ArrayList<>();
        List<String> jsonFileContents = new ArrayList<>();

        HashMap<String, String> jsons = new HashMap<>();
        File dir = new File(jsonFolder);
        int i = 0;
        for (File file : dir.listFiles()) {
            if (file.getName().endsWith(this.suffix)) {
                //  jsonFilePaths.add(file.getAbsoluteFile().getPath());
                jsons.put(file.getAbsoluteFile().getPath(), new String(Files.readAllBytes(Paths.get(file.getAbsoluteFile().getPath()))));
                //  jsonFileContents.add(i, new String(Files.readAllBytes(Paths.get(file.getAbsoluteFile().getPath()))));
                i++;
            }
        }
        //return jsonFileContents;
        return jsons;
    }

    public void writeDateToFile(String filePath, String data) {
        BufferedWriter bw = null;
        FileWriter fw = null;

        try {

            File file = new File(filePath);

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();

            } else {
                file.delete();
                file.createNewFile();
            }

            // true = append file
            fw = new FileWriter(file.getAbsoluteFile(), true);
            bw = new BufferedWriter(fw);

            bw.write(data);

            //  System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }
        }

    }

    public void mergeList(String SrcFolderPath, String DestFolderPath) throws IOException {


        HashMap<String, String> jsonSrcContents = getJsonListFromFolder(SrcFolderPath);
        HashMap<String, String> jsonDestContents = getJsonListFromFolder(DestFolderPath);

        int indexFeature = 0;
        for (Map.Entry<String, String> jsonDestFeature : jsonDestContents.entrySet()) {
            for (Map.Entry<String, String> jsonSrcFeature : jsonSrcContents.entrySet()) {
                //jsonDestContents.set(indexFeature, mergeFeature(jsonDestFeature, jsonSrcFeature));
                System.out.println(jsonDestFeature.getKey());
                System.out.println(jsonSrcFeature.getKey());

                jsonDestFeature.setValue(mergeFeature(jsonDestFeature.getValue(), jsonSrcFeature.getValue()));
            }

            writeDateToFile(jsonDestFeature.getKey(), jsonDestFeature.getValue());
            indexFeature++;
        }


    }

    public String mergeFeature(String jsonDestFeature, String jsonSrcFeature) {
        List<String> scenarioDestList = JsonPath.read(jsonDestFeature, "$[*].elements[*]");
        List<String> scenarioSrcList = JsonPath.read(jsonSrcFeature, "$[*].elements[*]");
        DocumentContext dcDest = JsonPath.parse(jsonDestFeature);
        DocumentContext dcSrc = JsonPath.parse(jsonSrcFeature);

        for (int destIndex = 0; destIndex < scenarioDestList.size(); destIndex++) {

            for (int srcIndex = 0; srcIndex < scenarioSrcList.size(); srcIndex++) {

                String src = dcSrc.read("$[0].elements[" + srcIndex + "].id");
                String dest = dcDest.read("$[0].elements[" + destIndex + "].id");
                if (src.toString().equals(dest.toString())) {

                    String jayPathSrc = "$[0].elements[" + srcIndex + "]";
                    System.out.println(jayPathSrc);
                    String value = JsonPath.read(jsonSrcFeature, jayPathSrc).toString();
                    // System.out.println(value);
                    //documentContext.delete(jayPath);
                    // System.out.println(jsonDestFeature);
                    String jayPath = "$[0].elements[" + destIndex + "]";
                    System.out.println(jayPath);

                    jsonDestFeature = JsonPath.parse(jsonDestFeature).set(jayPath, JsonPath.read(jsonSrcFeature, jayPathSrc)).json().toString();
                    // System.out.println(jsonDestFeature);
                    break;
                }
            }
        }
        //  jsonDestFeature = documentContext.jsonString() ;
        return jsonDestFeature;
    }


}
