package infrastructure;


import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by makri on 29/06/2017.
 */
public class Base {

    protected KBaseContext scenarioContext;
    protected static Logger logger;
    protected KWebDriver driver;
    protected String title = "";
    protected Locale locale = null;

    public <T extends KBaseContext> Base(T scenarioContext, String title) {
        setup(scenarioContext, title, null, "en", "AU");
    }

    public <T extends KBaseContext> Base(T scenarioContext, String title, String url) {

        setup(scenarioContext, title, url, "en", "AU");
    }
    public String resetTitle(String title)
    {
        this.title = title;
        return this.title;
    }
    //foundamental initialization - parameters include scenario context, page title, expected url, expected language and country
    public <T extends KBaseContext> void setup(T scenarioContext, String title, String url, String language, String country) {
        if (logger == null) {
            logger = scenarioContext.getLogger();
        }
        logger.info(scenarioContext.getScenario().getName()+scenarioContext.getScenario().getSourceTagNames()+scenarioContext.getScenario().getClass());
        logger.info(this.getClass() + " start to initialize");

        this.driver = scenarioContext.getWebDriver();
        this.scenarioContext = (T) scenarioContext;
        this.title = title;

        if (null != url) {
            //it means to change the url
            this.driver.navigate().to(url);
        }

        if (null == scenarioContext.getLocale()) {
            scenarioContext.setLocale(new Locale(language, country));
        }
        this.locale = scenarioContext.getLocale();

        logger.info(this.getClass() + " finish to initialize");

    }



    public void clickButtonAndContinue(By by, Integer attemptCursor, Integer attemptCount, By[] pops) throws Exception {
        {
            logger.info((attemptCount - attemptCursor) + " times tried");

            if (this.isExpectedPage()) {
                driver.clickButton(by);

                if (attemptCursor > 0) {


                    if ((pops != null) && (pops.length > 0)) {

                        for (By pop : pops) {
                            if (driver.checkPresenceOf(pop)) {
                                driver.clickButton(pop);
                            }
                        }
                    }
                    Thread.sleep(helpers.TestConstantData.NORMAL_WAIT_INTERNAL);
                    clickButtonAndContinue(by, --attemptCursor, attemptCount, pops);


                } else {

                    throw new TimeoutException("the button " + by + " has been clicked over " + attemptCount + " times, an exception is thrown");
                }
            } else {
                logger.info("after " + (attemptCount - attemptCursor) + " times , the page is changed");
            }
        }
    }

    public void clickButtonAndContinue(WebElement webElement, Integer attemptCursor, Integer attemptCount, By[] pops) throws Exception {

        {
            logger.info((attemptCount - attemptCursor) + " times tried");
            driver.clickButton(webElement);
            if (this.isExpectedPage()) {
                Thread.sleep(helpers.TestConstantData.NORMAL_WAIT_INTERNAL);
                if (attemptCursor > 0) {

                    if ((pops != null) && (pops.length > 0)) {
                        for (By pop : pops) {
                            if (driver.checkPresenceOf(pop)) {
                                driver.clickButton(pop);
                            }
                        }
                    }
                    clickButtonAndContinue(webElement, --attemptCursor, attemptCount, pops);


                } else {

                    throw new TimeoutException("the button " + webElement.toString() + " has been clicked over " + attemptCount + " times, an exception is thrown");
                }
            } else {
                logger.info("after " + (attemptCount - attemptCursor) + " times , the page is changed");
            }
        }
    }

    //click button and make sure the button will disappear after, if necessary pops will be clicked too, in order to make the continuation
    public void clickButtonAndContinue(By by, By[] pops) throws Exception {

        clickButtonAndContinue(by, helpers.ConfigHelper.getAttemptCount(), helpers.ConfigHelper.getAttemptCount(), pops);
    }

    //click button and make sure the button will disappear after, a variety of exceptions are handled in order to make the continuation
    public void clickButtonAndContinue(By by) throws Exception {
        clickButtonAndContinue(by, helpers.ConfigHelper.getAttemptCount(), helpers.ConfigHelper.getAttemptCount(), null);

    }

    //click button (webelement) and make sure the button will disappear after, if necessary pops will be clicked too, in order to make the continuation
    public void clickButtonAndContinue(WebElement webElement, By[] pops) throws Exception {
        clickButtonAndContinue(webElement, helpers.ConfigHelper.getAttemptCount(), helpers.ConfigHelper.getAttemptCount(), pops);

    }

    public boolean isExpectedPage() {
        if (title != null) {
            return driver.getTitle().toLowerCase().contains(this.title.toLowerCase());
        } else

        {
            return true;
        }
    }

    //it seems it should not be here since it's called pageLoad
    public void waitForPageLoad() throws Exception {
        logger.info("logger.info(driver.getTitle());" + driver.getTitle());
        int retryTimes = helpers.ConfigHelper.getAttemptCount();
        int millionSecondsToSleepInPageLoad = helpers.ConfigHelper.getPageLoadWaitTime() / retryTimes * 1000;
        while (!this.isExpectedPage()) {

            if (retryTimes > 0) {
                logger.info("logger.info(driver.getTitle());" + driver.getTitle());
                --retryTimes;
                driver.waitForPageLoad();
                Thread.sleep(millionSecondsToSleepInPageLoad);

            } else {
                logger.info(driver.getTitle());
                throw new TimeoutException("wait for page Load attemps too many times and still cannot get the expected page, the test is terminated");
            }

        }
        scenarioContext.getScenario().write(this.title + " is loaded");
        scenarioContext.getScenario().embed(scenarioContext.getWebDriver().getScreenshotAs(OutputType.BYTES),"image/png");
    }

    public boolean goBack() {
        driver.navigate().back();
        return true;
    }

    public String takeScreenShot() {
        try {
            final byte[] foto = scenarioContext.getWebDriver().getScreenshotAs(OutputType.BYTES);
            scenarioContext.getScenario().embed(foto, "image/png");
            driver.takeScreenShot();
            return  "screenshot taken";//
        } catch (Exception e) {
            logger.info(e.toString());
            return "fail";
        }
    }

    public String formatCurrency(double amount, String language, String country) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale(language, country));
        return currencyFormatter.format(amount);
    }

    public String formatCurrency(double amount) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(this.locale);
        return currencyFormatter.format(amount);
    }

}
