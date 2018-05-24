package infrastructure;


import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.Point;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Set;

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

    public String resetTitle(String title) {
        this.title = title;
        return this.title;
    }


    //foundamental initialization - parameters include scenario context, page title, expected url, expected language and country
    public <T extends KBaseContext> void setup(T scenarioContext, String title, String url, String language, String country) {
        if (logger == null) {
            logger = scenarioContext.getLogger();
        }
        logger.info(scenarioContext.getScenario().getName() + scenarioContext.getScenario().getSourceTagNames() + scenarioContext.getScenario().getClass());
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
        scenarioContext.getScenario().embed(scenarioContext.getWebDriver().getScreenshotAs(OutputType.BYTES), "image/png");
    }

    //it seems it should not be here since it's called pageLoad
    public void waitForPageLoad(By by) throws Exception {
        logger.info("logger.info(driver.getTitle());" + by.toString());
        int retryTimes = helpers.ConfigHelper.getAttemptCount();
        int millionSecondsToSleepInPageLoad = helpers.ConfigHelper.getPageLoadWaitTime() / retryTimes * 1000;
        while (driver.findElements(by).size() == 0) {

            if (retryTimes > 0) {
                logger.info("logger.info(driver.getTitle());" + by.toString());
                --retryTimes;
                driver.checkPresenceOf(by);
                Thread.sleep(millionSecondsToSleepInPageLoad);

            } else {
                logger.info(driver.getTitle());
                throw new TimeoutException("wait for page Load attemps too many times and still cannot get the expected page, the test is terminated");
            }

        }
        scenarioContext.getScenario().write(this.title + " is loaded");
        scenarioContext.getScenario().embed(scenarioContext.getWebDriver().getScreenshotAs(OutputType.BYTES), "image/png");
    }

    public boolean goBack() {
        driver.navigate().back();
        return true;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage dimg = dimg = new BufferedImage(newW, newH, img.getType());
        Graphics2D g = dimg.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0, newW, newH, 0, 0, w, h, null);
        g.dispose();
        return dimg;
    }

    public String takeScreenShot(boolean isTakenOndisk, boolean inEmbeded, KWebElement webElement, int deviationX, int deviationY, double percentageOfReduction, String extraInfo) {
        try {

            BufferedImage img = ImageIO.read(scenarioContext.getWebDriver().getScreenshotAs(OutputType.FILE));
            BufferedImage dest = null;
            BufferedImage biCompressed = null;

            if (webElement != null) {
                Point p = webElement.getLocation();
                scenarioContext.getScenario().write("x= " + p.getX() + " y= " + p.getY());

                int width = webElement.getSize().getWidth();
                int height = webElement.getSize().getHeight();
                scenarioContext.getScenario().write("width= " + width + " height= " + height);


                dest = img.getSubimage(p.getX(), p.getY(), deviationX + width, deviationY + height);
                biCompressed = resize(dest, (int) (percentageOfReduction * (deviationX + width)), (int) (percentageOfReduction * (deviationY + height)));
                dest.flush();
            } else {
                //take the full window
                biCompressed = resize(img, (int) (percentageOfReduction * driver.manage().window().getSize().getWidth()), (int) (percentageOfReduction * driver.manage().window().getSize().getHeight()));
            }


            if (inEmbeded) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(biCompressed, "png", baos);
                scenarioContext.getScenario().embed(baos.toByteArray(), "image/png");
                baos.flush();
                baos = null;
            }
            if (isTakenOndisk) {
                driver.takeScreenShot(biCompressed, extraInfo);
            }

            img.flush();
            biCompressed.flush();
            img = null;
            biCompressed = null;


            return "screenshot taken";//
        } catch (Exception e) {
            logger.info(e.getMessage() + ' ' + e.getStackTrace());
            return "cannot save screenshot";
        } catch (java.lang.OutOfMemoryError error) {
            logger.info(error.getMessage() + ' ' + error.getStackTrace());
            return "Memory leak due to the bug in webdriver, no more screenshots";
        }
    }


    public String takeScreenShot(boolean isTakenOndisk) {
        return takeScreenShot(isTakenOndisk, true, null, 0, 0, 1, null);
    }

    public String takeScreenShot() {
        return takeScreenShot(false);
    }

    public String formatCurrency(double amount, String language, String country) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(new Locale(language, country));
        return currencyFormatter.format(amount);
    }

    public String formatCurrency(double amount) {
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(this.locale);
        return currencyFormatter.format(amount);
    }

    public void sleep(int timeInMillionSeconds) {
        try {
            Thread.sleep(timeInMillionSeconds);
        } catch (InterruptedException e) {
            // Restore the interrupted status
            logger.info(e.getMessage()+' '+ e.getStackTrace());
            Thread.currentThread().interrupt();
        }
    }


}
