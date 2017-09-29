package infrastructure;


import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.MalformedURLException;

/**
 * Created by makri on 14/06/2017.
 */
public class DriversFactoryTest {



    @Test
    public void ChromeTest() throws MalformedURLException {

        WebDriver webDriver = DriversFactory.getDriver("chrome",null);
        Assert.assertEquals(webDriver.getClass(), ChromeDriver.class);
        webDriver.quit();

    }

    @Test(expected = java.lang.IllegalStateException.class)
    public void IETest() throws MalformedURLException {
//Set all zones to the same protected mode, enabled or disabled should not matter.
        WebDriver webDriver = DriversFactory.getDriver("ie",null);
        Assert.assertNull(webDriver.getClass());//, InternetExplorerDriver.class);
        webDriver.quit();

    }

    @Test(expected = java.lang.IllegalStateException.class)
    public void PhantomTest() throws MalformedURLException {

        WebDriver webDriver = DriversFactory.getDriver("phantomjs",null);
        Assert.assertNull(webDriver.getClass());//, PhantomJSDriver.class);
        webDriver.quit();

    }


    @Test(expected = java.lang.IllegalStateException.class)
    public void EdgeTest() throws MalformedURLException {


        WebDriver webDriver = DriversFactory.getDriver("edge",null);
        Assert.assertNull(webDriver.getClass());

        webDriver.quit();

    }


    @Test(expected = java.lang.IllegalStateException.class)
    public void FireFoxTest() throws MalformedURLException {

        WebDriver webDriver = DriversFactory.getDriver("firefox",null);
        Assert.assertNull(webDriver.getClass());//, FirefoxDriver.class);

        webDriver.quit();
    }

    @Ignore
    @Test(expected = java.lang.IllegalStateException.class)
    public void SafariTest() throws MalformedURLException {
        //org.openqa.selenium.WebDriverException: SafariDriver requires Safari 10 running on OSX El Capitan or greater.

        WebDriver webDriver = DriversFactory.getDriver("safari",null);
        Assert.assertNull(webDriver.getClass());//, SafariDriver.class);
        webDriver.quit();

    }
}
