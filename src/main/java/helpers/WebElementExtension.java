package helpers;

import infrastructure.KWebElement;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Function;

public class WebElementExtension {
    WebDriver driver;

    public WebElementExtension(WebDriver wd) {
        this.driver = wd;
    }

    public boolean waitForPage(By locator) {
        WebDriverWait driverWait = new WebDriverWait(this.driver, TestConstantData.pageLoadWaitTime);
        try {
            driverWait.until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }


    public void waitForPageLoad() {
        Wait<WebDriver> wait = new WebDriverWait(this.driver, ConfigHelper.getPageLoadWaitTime());
        wait.until(new Function<WebDriver, Boolean>() {
            public Boolean apply(WebDriver driver) {
                return String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState")).equals("complete");
            }
        });
    }


    public boolean waitForElementVisible(By locator) {
        WebDriverWait driverWait = new WebDriverWait(this.driver, TestConstantData.elementWaitTime);
        try {
            driverWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (WebDriverException e) {
            return false;
        }
        return true;
    }

    public boolean waitForElementVisible(WebElement webElement) {
        WebDriverWait driverWait = new WebDriverWait(this.driver, TestConstantData.elementWaitTime);
        try {
            driverWait.until(ExpectedConditions.visibilityOf(webElement));
        } catch (WebDriverException e) {
            return false;
        }
        return true;
    }


    public boolean waitForElementClickable(By locator) {
        WebDriverWait driverWait = new WebDriverWait(this.driver, TestConstantData.elementWaitTime);
        try {
            driverWait.until(ExpectedConditions.elementToBeClickable(locator));
        } catch (WebDriverException e) {
            return false;
        }
        return true;
    }

    public boolean waitForElementClickable(WebElement webElement) {
        WebDriverWait driverWait = new WebDriverWait(this.driver, TestConstantData.elementWaitTime);
        try {
            driverWait.until(ExpectedConditions.elementToBeClickable(webElement));
        } catch (WebDriverException e) {
            return false;
        }
        return true;
    }

    public void clickButton(By locator) {
        WebElement element = this.driver.findElement(locator);
        element.click();
    }

    public void clickButton(WebElement webElement) {

        webElement.click();
    }

    //take photoshot
    public String takeScreenShot(String prefixName) throws IOException {

        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        String ScreenshotPath = prefixName;
        // Now you can do whatever you need to do with it, for example copy somewhere
        FileUtils.copyFile(scrFile, new File(prefixName));

        return ScreenshotPath;
    }

    public String takeScreenShot(String path, BufferedImage bufferedImage) throws IOException {

        File scrFile = new File(path);

        ImageIO.write(bufferedImage, "png", scrFile);

        bufferedImage.flush();

        return path;
    }

    public String takeScreenShot() throws IOException {

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();

        File directory = new File(ConfigHelper.getCurrentWorkingDir() + File.separator + "imgs");
        if (!directory.exists()) {
            directory.mkdir();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }

        String ScreenshotPath = directory.getAbsolutePath() + File.separator +
                Thread.currentThread() + '-' + dateFormat.format(date) + ".jpg";


        return takeScreenShot(ScreenshotPath);

    }

    public String takeScreenShot(BufferedImage bufferedImage, String extraInfo) throws IOException {


        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
        Date date = new Date();

        File directory = new File(ConfigHelper.getCurrentWorkingDir() + File.separator + "imgs");
        if (!directory.exists()) {
            directory.mkdir();
            // If you require it to make the entire directory path including parents,
            // use directory.mkdirs(); here instead.
        }
        String ScreenshotPath = "";
        if (null == extraInfo || extraInfo.isEmpty()) {
            ScreenshotPath = directory.getAbsolutePath() + File.separator + Thread.currentThread() + "-" + dateFormat.format(date) + ".jpg";
        } else {
            ScreenshotPath = directory.getAbsolutePath() + File.separator + Thread.currentThread() + "-" + extraInfo + "-" + dateFormat.format(date) + ".jpg";
        }


        return takeScreenShot(ScreenshotPath, bufferedImage);

    }

    public boolean checkDisplayed(By by) {
        return (this.driver.findElement(by).isDisplayed());
    }

    public boolean checkDisplayed(WebElement webElement) {

        try {
            return webElement.isDisplayed();
        } catch (StaleElementReferenceException e) {
            return false;
        }

    }

    public void scrollViewToWebElement(By locator) {
        WebElement element = driver.findElement(locator);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void scrollViewToWebElement(WebElement webElement) {

        if (webElement.getClass().equals(KWebElement.class)) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", ((KWebElement) webElement).getWebElement());
        } else {

            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement);
        }
    }

    public void scrollViewToWebElement(KWebElement webElement) {


        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", webElement.getWebElement());
    }

    public boolean waitForElementInvisible(By locator) {
        WebDriverWait driverWait = new WebDriverWait(this.driver, TestConstantData.elementWaitTime);
        try {
            driverWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean waitForElementInvisible(WebElement webElement) {
        WebDriverWait driverWait = new WebDriverWait(this.driver, TestConstantData.elementWaitTime);
        try {
            driverWait.until(ExpectedConditions.invisibilityOf(webElement));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }


}
