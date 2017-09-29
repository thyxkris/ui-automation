package helpers;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by makri on 16/05/2017.
 */


public class ConfigHelper {

    private static ConfigHelper instance;
    private Config conf;
    private static ClassLoader classLoader;

    protected ConfigHelper() {
        classLoader = getClass().getClassLoader();
        conf = ConfigFactory.load(classLoader, System
                .getProperty("project.properties", "project.properties"));
    }

    public static synchronized ConfigHelper getInstance() {
        if (instance == null) {
            instance = new ConfigHelper();
        }
        return instance;
    }

    public static String getString(String key) {
        return ConfigHelper.getInstance().conf.getString(key).replace("\"","").replace("'","");
    }

    public static int getInt(String key) {
        return ConfigHelper.getInstance().conf.getInt(key);
    }
    public static Integer getAttemptCount() {
        return getInt("attempts.times");
    }

    public static String getTestResourcesFolderPath() {

        File file = new File(classLoader.getResource("").getFile());
        return file.getAbsolutePath();
    }

    public static String getCurrentWorkingDir() {

        Path currentRelativePath = Paths.get("");
        return currentRelativePath.toAbsolutePath().toString();

    }

    public static String getUserDir() {
        return System.getProperty("user.dir");
    }

    public static String getTemplateDir() {
        return ConfigHelper.getTestResourcesFolderPath() + File.separator + "templates" + File.separator;

    }

    public static String getProjectBaseDir() {
        return getString("project.basedir");
    }

    public static String getBrowserType() {
        return getString("browser.type");
    }
    public static String getTestEnv() {
        return getString("execution.env");
    }
    public static String getSeleniumGridURL() {
        return getString("selenium.grid.url");
    }

    public static Integer getPageLoadWaitTime() {
        return getInt("pageload.wait.time");
    }

    public static String getPageLoadWaitTimeString() {
        return getInt("pageload.wait.time") + "s";
    }

    public static Integer getElementWaitTime() {
        return getInt("element.wait.time");
    }

    public static String getElementWaitTimeString() {
        return getInt("element.wait.time") + "s";
    }

    public static Integer getImplicitWaitTime() {
        return getInt("normal.wait.interval");
    }

    public static String getImplicitWaitTimeString() {
        return getInt("normal.wait.interval") + "million seconds";
    }


}