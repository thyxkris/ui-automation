package infrastructure;


import helpers.ConfigHelper;
import org.apache.logging.log4j.LogManager;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by macked on 29/03/2017.
 */

public class DriversFactory {


    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger();

    public static WebDriver getDriver() {

        return getDriver(ConfigHelper.getString("browser.type").toLowerCase(), ConfigHelper.getString("selenium.grid.url"));

    }


    //the goal is default value is remoteWebdriver and could be configured as whatever ip
    //local standalone mode should be configured as it's local so apparantely it's easier to configure.
    public static WebDriver getDriver(String driverType, String seleniuGridUrl) {
        WebDriver driver = null;
        //for specify download folder
        //String downloadFilepath = ConfigHelper.getDownloadFolder();
        switch (driverType) {
            case "browserstack":
                String USERNAME = "otis8";
                String AUTOMATE_KEY = "5ZpdSfAmCEpyvwrF8BTq";
                //  final String URL = "http://otis8:5ZpdSfAmCEpyvwrF8BTq@hub.browserstack.com:80/wd/hub";
                String URL = "http://" + USERNAME + ":" + AUTOMATE_KEY + "@hub.browserstack.com:80/wd/hub";
                if (!ConfigHelper.getString("browserstack.url").isEmpty()) {
                    URL = ConfigHelper.getString("browserstack.url");
                    logger.info(URL);
                }

                //browserstack.debug=true
                DesiredCapabilities caps = new DesiredCapabilities();
                if (!ConfigHelper.getString("browserstack.os").isEmpty()) {
                    caps.setCapability("os", ConfigHelper.getString("browserstack.os"));
                    logger.info("browserstack.os"+ConfigHelper.getString("browserstack.os"));
                }
                if (!ConfigHelper.getString("browserstack.debug").isEmpty()) {
                    caps.setCapability("browserstack.debug", ConfigHelper.getString("browserstack.debug"));
                    logger.info("browserstack.debug"+ConfigHelper.getString("browserstack.debug"));
                }
                if (!ConfigHelper.getString("browserstack.os_version").isEmpty()) {
                    caps.setCapability("os_version", ConfigHelper.getString("browserstack.os_version"));
                    logger.info("browserstack.os_version"+ ConfigHelper.getString("browserstack.os_version"));
                }
                if (!ConfigHelper.getString("browserstack.browserName").isEmpty()) {
                    caps.setCapability("browserName", ConfigHelper.getString("browserstack.browserName"));
                    logger.info("browserstack.browserName"+ConfigHelper.getString("browserstack.browserName"));
                }
                if (!ConfigHelper.getString("browserstack.browser_version").isEmpty()) {
                    caps.setCapability("browser_version", ConfigHelper.getString("browserstack.browser_version"));
                    logger.info("browserstack.browser_version"+ ConfigHelper.getString("browserstack.browser_version"));
                }
                if (!ConfigHelper.getString("browserstack.browserstack.local").isEmpty()) {
                    caps.setCapability("browserstack.local", ConfigHelper.getString("browserstack.browserstack.local"));
                    logger.info("browserstack.local"+ ConfigHelper.getString("browserstack.browserstack.local"));
                }
                if (!ConfigHelper.getString("browserstack.browserstack.debug").isEmpty()) {
                    caps.setCapability("browserstack.debug", ConfigHelper.getString("browserstack.browserstack.debug"));
                    logger.info("browserstack.debug"+ ConfigHelper.getString("browserstack.browserstack.debug"));
                }
                if (!ConfigHelper.getString("browserstack.browserstack.console").isEmpty()) {
                    caps.setCapability("browserstack.console", ConfigHelper.getString("browserstack.browserstack.console"));
                    logger.info("browserstack.console"+ ConfigHelper.getString("browserstack.browserstack.console"));
                }
                if (!ConfigHelper.getString("browserstack.browserstack.networkLogs").isEmpty()) {
                    caps.setCapability("browserstack.networkLogs", ConfigHelper.getString("browserstack.browserstack.networkLogs"));
                    logger.info("browserstack.networkLogs"+ ConfigHelper.getString("browserstack.browserstack.networkLogs"));
                }
                if (!ConfigHelper.getString("browserstack.browserstack.video").isEmpty()) {
                    caps.setCapability("browserstack.video", ConfigHelper.getString("browserstack.browserstack.video"));
                    logger.info("browserstack.video"+ ConfigHelper.getString("browserstack.browserstack.video"));
                }
                if (!ConfigHelper.getString("browserstack.browserstack.timezone").isEmpty()) {
                    caps.setCapability("browserstack.timezone", ConfigHelper.getString("browserstack.browserstack.timezone"));
                    logger.info("browserstack.timezone"+ ConfigHelper.getString("browserstack.browserstack.timezone"));
                }
                if (!ConfigHelper.getString("browserstack.resolution").isEmpty()) {
                    caps.setCapability("resolution", ConfigHelper.getString("browserstack.resolution"));

                    logger.info("resolution"+ ConfigHelper.getString("browserstack.resolution"));
                }
                if (!ConfigHelper.getString("browserstack.selenium_version").isEmpty()) {
                    caps.setCapability("selenium_version", ConfigHelper.getString("browserstack.selenium_version"));
                    logger.info("selenium_version"+ ConfigHelper.getString("browserstack.selenium_version"));
                }
                if (!ConfigHelper.getString("browserstack.device").isEmpty()) {
                    caps.setCapability("device", ConfigHelper.getString("browserstack.device"));
                    logger.info("device"+ ConfigHelper.getString("browserstack.device"));
                }
                if (!ConfigHelper.getString("browserstack.realMobile").isEmpty()) {
                    caps.setCapability("realMobile", ConfigHelper.getString("browserstack.realMobile"));
                    logger.info("realMobile"+ ConfigHelper.getString("browserstack.realMobile"));
                }
                if (!ConfigHelper.getString("browserstack.browserstack.appium_version").isEmpty()) {
                    caps.setCapability("browserstack.appium_version", ConfigHelper.getString("browserstack.browserstack.appium_version"));
                    logger.info("browserstack.appium_version"+ ConfigHelper.getString("browserstack.browserstack.appium_version"));
                }
                if (!ConfigHelper.getString("browserstack.deviceOrientation").isEmpty()) {
                    caps.setCapability("deviceOrientation", ConfigHelper.getString("browserstack.deviceOrientation"));
                    logger.info("deviceOrientation"+ ConfigHelper.getString("browserstack.deviceOrientation"));

                }
                // WebDriver driver = null;
                try {
                    driver = new RemoteWebDriver(new URL(URL), caps);
                    logger.info("browserStack is " + driverType + " driver, pointing " + seleniuGridUrl, caps + " is generated");
                } catch (MalformedURLException malformedURLException) {
                    logger.info(malformedURLException.toString());
                    return null;
                }
                break;
            case "chrome":
                //final  ChromeOptions chromeOptions = new ChromeOptions();
                HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                chromePrefs.put("profile.default_content_setting_values.geolocation", 1);
                chromePrefs.put("profile.default_content_settings.popups", 0);
                chromePrefs.put("download.prompt_for_download", "false");
                chromePrefs.put("download.directory_upgrade", "true");
                chromePrefs.put("plugins.always_open_pdf_externally", "true");
                chromePrefs.put("download.default_directory", ConfigHelper.getDownloadFolder());
                chromePrefs.put("plugins.plugins_disabled", new String[] {
                        "Adobe Flash Player",
                        "Chrome PDF Viewer"
                });
                chromePrefs.put("pdfjs.disabled", true);
                ChromeOptions chromeOptions = new ChromeOptions();
                if (ConfigHelper.getString("chrome.headless").toLowerCase().contains("t")||ConfigHelper.getString("chrome.headless").toLowerCase().contains("y")) {
                    chromePrefs.put("profile.default_content_setting_values.geolocation", 1);
                    chromeOptions.addArguments("--headless");
                   // chromeOptions.addArguments("--user-data-dir=C:\\Users\\Admin\\AppData\\Local\\Google\\Chrome\\User Data");
                    //C:\Users\Admin\AppData\Local\Google\Chrome\User Data
                    logger.info("chrome headless mode adopted");
                }
                chromeOptions.setExperimentalOption("prefs", chromePrefs);
                chromeOptions.addArguments("start-maximized");
                chromeOptions.addArguments("disable-infobars");
                chromeOptions.addArguments("--test-type");

                DesiredCapabilities cap = DesiredCapabilities.chrome();
                cap.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR, UnexpectedAlertBehaviour.IGNORE);
                cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                cap.setCapability(ChromeOptions.CAPABILITY, chromeOptions);



                if ((null == seleniuGridUrl) || (seleniuGridUrl.equals("")|| seleniuGridUrl.equals("127.0.0.1"))) {

                    driver = new ChromeDriver(cap);

                    logger.info(driverType + " driver is generated locally");
                } else {
                    try {

                        driver = new RemoteWebDriver(new URL(ConfigHelper.getString("selenium.grid.url")), cap);
                        logger.info("remoteWebdriver is " + driverType + " driver, pointing " + seleniuGridUrl, cap + " is generated");
                    } catch (MalformedURLException malformedURLException) {
                        logger.info(malformedURLException.toString());
                        return null;
                    }
                }
                break;
            case "firefox":
                if ((null == seleniuGridUrl) || (seleniuGridUrl.equals(""))) {
                    FirefoxOptions options = new FirefoxOptions();
                    driver = new FirefoxDriver(options);
                    logger.info(driverType + " driver is generated locally");
                } else {
                    try {
                        DesiredCapabilities capability = DesiredCapabilities.firefox();
                        driver = new RemoteWebDriver(new URL(ConfigHelper.getString("selenium.grid.url")), capability);
                        logger.info("remoteWebdriver is " + driverType + " driver, pointing " + seleniuGridUrl, capability + " is generated");
                    } catch (MalformedURLException malformedURLException) {
                        logger.info(malformedURLException.toString());
                        return null;
                    }
                }
                break;
            case "edge":
                if ((null == seleniuGridUrl) || (seleniuGridUrl.equals(""))) {
                    EdgeOptions options = new EdgeOptions();
                    driver = new EdgeDriver(options);
                    logger.info(driverType + " driver is generated locally");
                } else {
                    try {
                        DesiredCapabilities capability = DesiredCapabilities.edge();
                        driver = new RemoteWebDriver(new URL(ConfigHelper.getString("selenium.grid.url")), capability);
                        logger.info("remoteWebdriver is " + driverType + " driver, pointing " + seleniuGridUrl, capability + " is generated");
                    } catch (MalformedURLException malformedURLException) {
                        logger.info(malformedURLException.toString());
                        return null;
                    }
                }
                break;
            case "phantomjs":
                if ((null == seleniuGridUrl) || (seleniuGridUrl.equals(""))) {
                    driver = new PhantomJSDriver();
                    logger.info(driverType + " driver is generated locally");
                } else {
                    try {
                        DesiredCapabilities capability = DesiredCapabilities.edge();
                        driver = new RemoteWebDriver(new URL(ConfigHelper.getString("selenium.grid.url")), capability);
                        logger.info("remoteWebdriver is " + driverType + " driver, pointing " + seleniuGridUrl, capability + " is generated");
                    } catch (MalformedURLException malformedURLException) {
                        logger.info(malformedURLException.toString());
                        return null;
                    }
                }
                driver = new PhantomJSDriver();
                break;
            case "ie":
                if ((null == seleniuGridUrl) || (seleniuGridUrl.equals(""))) {
                    InternetExplorerOptions options = new InternetExplorerOptions();
                    driver = new InternetExplorerDriver();
                    logger.info(driverType + " driver is generated locally");
                } else {
                    try {
                        DesiredCapabilities capability = DesiredCapabilities.internetExplorer();
                        driver = new RemoteWebDriver(new URL(ConfigHelper.getString("selenium.grid.url")), capability);
                        logger.info("remoteWebdriver is " + driverType + " driver, pointing " + seleniuGridUrl, capability + " is generated");
                    } catch (MalformedURLException malformedURLException) {
                        logger.info(malformedURLException.toString());
                        return null;
                    }
                }

                break;
            case "safari":
                if ((null == seleniuGridUrl) || (seleniuGridUrl.equals(""))) {
                    SafariOptions options = new SafariOptions();
                    driver = new SafariDriver(options);
                    logger.info(driverType + " driver is generated locally");
                } else {
                    try {
                        DesiredCapabilities capability = DesiredCapabilities.safari();
                        driver = new RemoteWebDriver(new URL(ConfigHelper.getString("selenium.grid.url")), capability);
                        logger.info("remoteWebdriver is " + driverType + " driver, pointing " + seleniuGridUrl, capability + " is generated");
                    } catch (MalformedURLException malformedURLException) {
                        logger.info(malformedURLException.toString());
                        return null;
                    }
                }
                break;
        }
        return driver;
    }


}
