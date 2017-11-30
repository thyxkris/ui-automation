package helpers;

import java.io.File;
import java.io.IOException;

/**
 * Created by makri on 26/10/2017.
 */
public class AutoReport  {
    public static void main(String args[]) throws IOException {

        ReportHelper reportHelper;
        String htmlReportFolder = ConfigHelper.getCurrentWorkingDir() + File.separator + args[0];//ConfigHelper.getString("parallel.output.directory");
        System.out.println("htmlReportFolder "+htmlReportFolder);
        String jsonsFolderFirstRun = ConfigHelper.getCurrentWorkingDir() + File.separator + args[1];//ConfigHelper.getString("first.run.directory");
        System.out.println("jsonsFolderFirstRun "+jsonsFolderFirstRun);
        String jsonsFolderRerun = ConfigHelper.getCurrentWorkingDir() + File.separator + args[2];//ConfigHelper.getString("rerun.directory");
        System.out.println("jsonsFolderRerun "+jsonsFolderRerun);

        if (null == args[2] || args[2].isEmpty() || args[2].equals("null")) {


            reportHelper = new ReportHelper(htmlReportFolder, jsonsFolderFirstRun, ".json");
        } else {



            reportHelper = new ReportHelper(jsonsFolderRerun, jsonsFolderRerun, ".json");
            reportHelper.gen();

            reportHelper = new ReportHelper(htmlReportFolder, jsonsFolderFirstRun, ".json");
            reportHelper.mergeList(jsonsFolderRerun, jsonsFolderFirstRun);
        }

        reportHelper.gen();
    }
}
