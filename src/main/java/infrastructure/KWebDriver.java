package infrastructure;

import helpers.ConfigHelper;
import helpers.TestConstantData;
import helpers.WebElementExtension;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.HasInputDevices;
import org.openqa.selenium.interactions.Keyboard;
import org.openqa.selenium.interactions.Mouse;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Kris Ma on 21/05/2017.
 */
public class KWebDriver implements JavascriptExecutor, HasInputDevices, WebDriver {

    private final JavascriptExecutor javaScriptExecutorImplementation;
    private final HasInputDevices hasInputDevicesImplementation;
    private final WebElementExtension webElementExtension;
    private WebDriver driver;
    private Logger logger = null;


    public KWebDriver(WebDriver wd) {
        this.driver = wd;
        this.javaScriptExecutorImplementation = (JavascriptExecutor) wd;
        this.hasInputDevicesImplementation = (HasInputDevices) wd;
        this.webElementExtension = new WebElementExtension(wd);
    }
    //boolean hasQuit = false;

    public boolean hasQuit() {
        return ((RemoteWebDriver) driver).getSessionId() == null;
    }

    public Logger setLogger(Logger logger) {
        return this.logger = logger;
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public Logger getLogger() {
        if (logger == null) {
            return LogManager.getLogger();
        }
        return this.logger;
    }

    @Override
    public Object executeScript(String s, Object... objects) {
        return this.javaScriptExecutorImplementation.executeScript(s, objects);
    }

    @Override
    public Object executeAsyncScript(String s, Object... objects) {
        try {
            return this.javaScriptExecutorImplementation.executeAsyncScript(s, objects);
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return executeAsyncScript(s, objects);
            }
            throw e;
        }
    }

    @Override
    public Keyboard getKeyboard() {

        try {
            return this.hasInputDevicesImplementation.getKeyboard();
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return getKeyboard();
            }
            throw e;
        }
    }

    @Override
    public Mouse getMouse() {
        try {
            return this.hasInputDevicesImplementation.getMouse();
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return getMouse();
            }
            throw e;
        }
    }

    @Override
    public void get(String s) {
        this.driver.get(s);
    }

    @Override
    public String getCurrentUrl() {
        return this.driver.getCurrentUrl();
    }

    @Override
    public String getTitle() {
        try {
            return driver.getTitle();
        } catch (UnhandledAlertException f) {
            try {
                Alert alert = driver.switchTo().alert();
                String alertText = alert.getText();
                logger.info("Alert data: " + alertText);
                alert.accept();
                return getTitle();
            } catch (NoAlertPresentException e) {
                return getTitle();
            }
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return getTitle();
            }
            throw e;
        }
    }

    @Override
    public List<WebElement> findElements(By by) {

        try {
            return driver.findElements(by);
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return findElements(by);
            } else {
                throw e;
            }


        }

    }

    public List<KWebElement> findKElements(By by) {

        try {

            List<WebElement> webElements = driver.findElements(by);
            List<KWebElement> kRemoteWebElements = new ArrayList<KWebElement>();
            for (WebElement webElement : webElements) {
                kRemoteWebElements.add(new KWebElement(webElement));
            }

            return kRemoteWebElements;
            // return driver.findElements(by);
        } catch (Exception e) {
            if (e.toString().contains("No buffer space available")) {
                this.getLogger().info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return findKElements(by);
            } else {
                throw e;
            }


        }

    }

    //    public List<KRemoteWebElement> findKRemoteWebElements(By by) {
//
//        try {
//
//            List<WebElement> webElements =  driver.findElements(by);
//            List<KRemoteWebElement> kRemoteWebElements = new ArrayList<KRemoteWebElement>();
//            for(WebElement webElement:webElements) {
//                kRemoteWebElements.add((KRemoteWebElement) webElement);
//            }
//
//            return kRemoteWebElements;
//           // return driver.findElements(by);
//        } catch (org.openqa.selenium.WebDriverException e) {
//            if (e.toString().contains("No buffer space available")) {
//                logger.info(e.toString());
//                try {
//                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
//                } catch (InterruptedException e1) {
//                    e1.printStackTrace();
//                }
//                return findKRemoteWebElements(by);
//            } else {
//                throw e;
//            }
//
//
//        }
//
//    }
    @Override
    public WebElement findElement(By by) {
        try {
            return driver.findElement(by);
        } catch (NullPointerException e) {
            getLogger().info("cannot find " + by.toString() + " error: " + e.toString());
            return null;
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            getLogger().info(e.toString());
            return this.findElement(by);
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return findElement(by);
            }
            throw e;
        }
    }

    public KWebElement findKElement(By by) {
        try {
            scrollViewToWebElement(by);
            return new KWebElement(driver.findElement(by));
        } catch (NullPointerException e) {
            getLogger().info("cannot find " + by.toString() + " error: " + e.toString());
            return null;
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            getLogger().info(e.toString());
            return this.findKElement(by);
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return findKElement(by);
            }
            throw e;
        }
    }

    @Override
    public String getPageSource() {
        return driver.getPageSource();
    }

    @Override
    public void close() {
        driver.close();
    }

    @Override
    public void quit() {
        try {
            driver.quit();
        } catch (org.openqa.selenium.WebDriverException e) {

            logger.info(e.toString());
        }
    }

    @Override
    public Set<String> getWindowHandles() {
        return this.driver.getWindowHandles();
    }

    @Override
    public String getWindowHandle() {
        return driver.getWindowHandle();
    }

    @Override
    public TargetLocator switchTo() {
        return driver.switchTo();
    }

    @Override
    public Navigation navigate() {
        return driver.navigate();
    }

    @Override
    public Options manage() {
        try {
            return driver.manage();
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return manage();
            }
            throw e;
        }
    }

    public boolean waitForPage(By locator) {

        try {
            return webElementExtension.waitForPage(locator);
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return waitForPage(locator);
            }
            throw e;
        }
    }


    public void waitForPageLoad() {
        try {
            webElementExtension.waitForPageLoad();
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                waitForPageLoad();
            } else {
                throw e;
            }
        }

    }


    public boolean waitForElementVisible(By locator) {

        try {
            return webElementExtension.waitForElementVisible(locator);
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return waitForElementVisible(locator);
            }
            throw e;
        }
    }

    public boolean waitForElementVisible(WebElement element) {
        try {
            return webElementExtension.waitForElementVisible(element);
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return waitForElementVisible(element);
            }
            throw e;
        }

    }


    public boolean waitForElementClickable(By locator) {

        try {
            return webElementExtension.waitForElementClickable(locator);
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return waitForElementClickable(locator);
            }
            throw e;
        }
    }

    public boolean waitForElementClickable(WebElement element) {
        try {
            return webElementExtension.waitForElementClickable(element);
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return waitForElementClickable(element);
            }
            throw e;
        }
    }

    public void clickButton(By locator) throws InterruptedException {

        try {
            scrollViewToWebElement(locator);
            findElement(locator).click();

        } catch (UnhandledAlertException f) {
            try {
                Alert alert = driver.switchTo().alert();
                String alertText = alert.getText();
                logger.info("Alert data: " + alertText);
                alert.accept();
                clickButton(locator);
            } catch (NoAlertPresentException e) {
                clickButton(locator);
            }
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            try {
                getLogger().info(e.toString());
                WebDriverWait wait = new WebDriverWait(driver, TestConstantData.elementWaitTime);
                wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
                clickButton(locator);
            } catch (Exception e1) {
                getLogger().info(e1.toString());
                clickButton(locator);//return to the entry
            }
        } catch (Exception e) {
            if (e.toString().contains("Other element would receive the click")) {// it means some other ads blocking
                try {
                    getLogger().info(e.toString());
                    Thread.sleep(2000);
                    scrollViewToWebElement(locator);
                    executeScript("arguments[0].click();", driver.findElement(locator));

                } catch (Exception e1) {
                    getLogger().info(e1.toString());
                    clickButton(locator);//return to the entry
                }
            }
        }

    }

    public void clickButton(WebElement webElement) throws InterruptedException {

        try {
            FluentWait fluentWait = new FluentWait(driver).withTimeout(ConfigHelper.getAttemptCount() * ConfigHelper.getElementWaitTime(), SECONDS).pollingEvery(50, MILLISECONDS);
            fluentWait.until(ExpectedConditions.visibilityOf(webElement));
            fluentWait.until(ExpectedConditions.elementToBeClickable(webElement));
            scrollViewToWebElement(webElement);
            webElement.click();
        } catch (UnhandledAlertException f) {
            try {
                Alert alert = driver.switchTo().alert();
                String alertText = alert.getText();
                System.out.println("Alert data: " + alertText);
                alert.accept();
                clickButton(webElement);
            } catch (NoAlertPresentException e) {
                clickButton(webElement);
            }
        } catch (org.openqa.selenium.StaleElementReferenceException e) {

            getLogger().info(e.toString());
            getLogger().info(webElement.toString());
            //it means it's been clicked
            try {
                webElement.click();
            } catch (Exception e3) {
                logger.info(e3.toString());
                throw e3;
            }

        } catch (org.openqa.selenium.WebDriverException e1) {
            if (e1.toString().contains("Other element would receive the click")) {
                // it means some other ads blocking
                try {
                    logger.info(e1.toString());
                    scrollViewToWebElement(webElement);
                    executeScript("arguments[0].click();", webElement);
                } catch (Exception e2) {
                    logger.info(e2.toString());
                    //   clickButton(webElement);//return to the entry
                }
            }
        } catch (Exception e3) {
            getLogger().info(e3.toString());
            getLogger().info(webElement.toString());
            waitForElementVisible(webElement);
            clickButton(webElement);
        }
    }

    public void clickButton(KWebElement webElement) {

        try {
            FluentWait fluentWait = new FluentWait(driver).withTimeout(ConfigHelper.getAttemptCount() * ConfigHelper.getElementWaitTime(), SECONDS).pollingEvery(50, MILLISECONDS);
            fluentWait.until(ExpectedConditions.visibilityOf(webElement.getWebElement()));
            fluentWait.until(ExpectedConditions.elementToBeClickable(webElement.getWebElement()));
            scrollViewToWebElement(webElement.getWebElement());
            webElement.click();
        } catch (org.openqa.selenium.StaleElementReferenceException e) {

            getLogger().info(e.getLocalizedMessage() + ' ' + e.getStackTrace());
            getLogger().info(webElement.toString());
            throw e;

        } catch (org.openqa.selenium.WebDriverException e1) {
            if (e1.toString().contains("Other element would receive the click")) {
                // it means some other ads blocking
                try {
                    logger.info(e1.toString());
                    scrollViewToWebElement(webElement.getWebElement());
                    executeScript("arguments[0].click();", webElement.getWebElement());
                } catch (Exception e2) {
                    logger.info(e2.toString());
                    //   clickButton(webElement);//return to the entry
                    executeScript("arguments[0].click();", ((KWebElement) webElement.getWebElement()).getWebElement());
                }
            }
        } catch (Exception e3) {
            getLogger().info(e3.toString());
            getLogger().info(webElement.toString());
            waitForElementVisible(webElement.getWebElement());
            clickButton(webElement);
        }
    }

    //take photoshot
    public String takeScreenShot(String prefixName) throws IOException {

        return webElementExtension.takeScreenShot(prefixName);
    }

    public String takeScreenShot(BufferedImage bufferedImage, String extraInfo) throws IOException {

        try {
            return webElementExtension.takeScreenShot(bufferedImage, extraInfo);
        } catch (Exception e) {
            logger.info(e.getMessage());
            throw e;
        }
    }

    public String takeScreenShot() throws IOException {

        return webElementExtension.takeScreenShot();
    }

    public <X> X getScreenshotAs(OutputType<X> outputType) {
        try {
            return ((TakesScreenshot) driver).getScreenshotAs(outputType);
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return getScreenshotAs(outputType);
            }
            throw e;
        }

    }


    public boolean checkDisplayed(By by) {
        return (checkPresenceOf(by) && webElementExtension.checkDisplayed(by));
    }

    public boolean checkDisplayed(WebElement webElement) {
        return (checkPresenceOf(webElement) && webElementExtension.checkDisplayed(webElement));
    }

    public void scrollViewToWebElement(By locator) {
        webElementExtension.scrollViewToWebElement(locator);
    }

    public void scrollViewToWebElement(WebElement webElement) {
        try {
            webElementExtension.scrollViewToWebElement(webElement);
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                scrollViewToWebElement(webElement);
            }
            throw e;
        }
    }

    public void setBrowserResolution(int width, int height) {
        Dimension WindowSize = new Dimension(width, height);
        driver.manage().window().setSize(WindowSize);
    }


    public boolean waitForElementInvisible(By interstitial) {
        try {
            return webElementExtension.waitForElementInvisible(interstitial);

        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return waitForElementInvisible(interstitial);
            }
            throw e;
        }
    }

    public boolean waitForElementInvisible(WebElement webElement) {
        try {
            return webElementExtension.waitForElementInvisible(webElement);

        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return waitForElementInvisible(webElement);
            }
            throw e;
        }
    }


    public boolean checkPresenceOf(By by) {
        try {
            return (driver.findElements(by).size() > 0);
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return checkPresenceOf(by);
            }
            throw e;
        }
    }

    public boolean checkPresenceOf(WebElement webElement) {
        try {
            return (webElement != null);
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                logger.info(e.toString());
                try {
                    Thread.sleep(ConfigHelper.getImplicitWaitTime());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return checkPresenceOf(webElement);
            }
            throw e;
        }
    }


}
