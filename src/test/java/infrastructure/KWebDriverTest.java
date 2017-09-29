package infrastructure;

import helpers.ConfigHelper;
import helpers.TestConstantData;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.HasInputDevices;

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.Set;

/**
 * Created by makri on 14/06/2017.
 */
public class KWebDriverTest {
    WebDriver webDriver;
    KWebDriver KWebDriver;
    private final By LOCATOR_PRIVACY = By.partialLinkText("Privacy");

    @Before
    public void setUp() throws Exception {
        webDriver = new ChromeDriver();
        KWebDriver = new KWebDriver(webDriver);
    }

    @After
    public void tearDown() throws Exception {
        webDriver.quit();
        KWebDriver.quit();
    }

    @Test
    public void executeScript1() throws Exception {
        KWebDriver.navigate().to("http://www.google.com");
        KWebDriver.setBrowserResolution(800, 200);
        webDriver.navigate().to("http://www.google.com");
        webDriver.manage().window().setSize(new Dimension(800, 200));

        WebElement element = webDriver.findElement(LOCATOR_PRIVACY);
        Assert.assertEquals(((JavascriptExecutor) webDriver).executeScript("arguments[0].scrollIntoView(true);", element), KWebDriver.executeScript("arguments[0].scrollIntoView(true);", element));
    }

    @Ignore
    @Test
    public void executeAsyncScript() throws Exception {
        KWebDriver.navigate().to("http://www.google.com");
        KWebDriver.setBrowserResolution(800, 200);
        webDriver.navigate().to("http://www.google.com");
        webDriver.manage().window().setSize(new Dimension(800, 200));
        WebElement element = webDriver.findElement(LOCATOR_PRIVACY);
        Assert.assertEquals(((JavascriptExecutor) webDriver).executeAsyncScript("window.setTimeout(arguments[arguments.length - 1], 500);"), KWebDriver.executeAsyncScript("window.setTimeout(arguments[arguments.length - 1], 500);"));
    }

    @Test
    public void getKeyboard() throws Exception {
        Assert.assertEquals(KWebDriver.getKeyboard(), ((HasInputDevices) webDriver).getKeyboard());
    }

    @Test
    public void getMouse() throws Exception {
        Assert.assertEquals(KWebDriver.getMouse(), ((HasInputDevices) webDriver).getMouse());
    }

    @Test
    public void get() throws Exception {
        KWebDriver.get("http://www.google.com");
        Assert.assertEquals(KWebDriver.getTitle(), "Google");
        KWebDriver.get(URL_GOOGLE);
        Assert.assertEquals(KWebDriver.getCurrentUrl(), URL_GOOGLE);
    }

    @Test
    public void getCurrentUrl() throws Exception {
        KWebDriver.get(URL_GOOGLE);
        Assert.assertEquals(KWebDriver.getCurrentUrl(), URL_GOOGLE);

    }

    @Test
    public void getTitle() throws Exception {
        KWebDriver.get("http://www.google.com");
        Assert.assertEquals(KWebDriver.getTitle(), "Google");
    }

    @Test
    public void findElementsCLASSNAME() throws Exception {
        KWebDriver.get(DEMOQA);
        webDriver.get(DEMOQA);

        
        Assert.assertEquals(KWebDriver.findElements(By.className("menu-registration-container")), webDriver.findElements(By.className("menu-registration-container")));

    }

    @Test
    public void findElementsXPATH() throws Exception {
        KWebDriver.get(DEMOQA);
        webDriver.get(DEMOQA);

        Assert.assertEquals(webDriver.findElements(By.xpath("//aside[contains(@id,'nav_menu-')]")), KWebDriver.findElements(By.xpath("//aside[contains(@id,'nav_menu-')]")));
    }

    private final String DEMOQA = "http://demoqa.com/";

    @Test
    public void findElementXPATH() throws Exception {
        KWebDriver.get(DEMOQA);
        webDriver.get(DEMOQA);

        Assert.assertEquals(webDriver.findElement(By.xpath("//aside[contains(@id,'nav_menu-')]")), KWebDriver.findElement(By.xpath("//aside[contains(@id,'nav_menu-')]")));
    }

    @Test
    public void getPageSource() throws Exception {
        KWebDriver.get(DEMOQA);
        webDriver.get(DEMOQA);
        Assert.assertEquals(webDriver.getPageSource(), KWebDriver.getPageSource());
    }

    @Rule
    public final ExpectedException exception = ExpectedException.none();


    @Test
    public void close() throws Exception {
        KWebDriver.close();
        exception.expect(org.openqa.selenium.NoSuchSessionException.class);
        exception.expectMessage("no such session");
        webDriver.close();
        Assert.assertNotNull(webDriver);

    }

    @Test
    public void quit() throws Exception {
        KWebDriver.quit();
        //no exception is throw
        webDriver.quit();
        exception.expect(org.openqa.selenium.NoSuchSessionException.class);
        exception.expectMessage("Session ID is null. Using WebDriver after calling quit()?");
        KWebDriver.getWindowHandles();
        Assert.assertNotNull(webDriver);
    }

    @Test
    public void getWindowHandles() throws Exception {

        Assert.assertEquals(KWebDriver.getWindowHandles(), webDriver.getWindowHandles());
    }

    @Test
    public void getWindowHandle() throws Exception {
        Assert.assertEquals(KWebDriver.getWindowHandle(), webDriver.getWindowHandle());
    }

    @Test
    public void switchTo() throws Exception {
        KWebDriver.get(DEMOQA);
        KWebDriver.clickButton(By.partialLinkText("Frames and windows"));
        KWebDriver.clickButton(By.partialLinkText("Frameset"));
        String winHandleBefore = KWebDriver.getWindowHandle();
        KWebDriver.clickButton(By.partialLinkText("Open Frameset Window"));

        Set<String> myWindowHandles = KWebDriver.getWindowHandles();
        String winHandleAfter = null;

        for (String s : myWindowHandles) {
            if (!s.equals(winHandleBefore)) {
                winHandleAfter = s;
            }
        }
        Assert.assertEquals("Frames and windows | Demoqa", KWebDriver.getTitle());
        KWebDriver.switchTo().window((winHandleAfter));
        Assert.assertEquals("HTML Frames - Example 1", KWebDriver.getTitle());

    }

    private final String URL_GOOGLE = "https://www.google.com.au/?gws_rd=ssl";

    @Test
    public void navigate() throws Exception {
        KWebDriver.navigate().to(URL_GOOGLE);
        Assert.assertEquals(KWebDriver.getCurrentUrl(), URL_GOOGLE);
    }

    @Test
    public void manage() throws Exception {
        Assert.assertEquals(KWebDriver.manage().getClass(), webDriver.manage().getClass());
    }

    @Test
    public void waitForPage1() throws Exception {
        KWebDriver.navigate().to(URL_GOOGLE);
        Instant starts = Instant.now();
        KWebDriver.waitForPage(By.name("nonsense"));
        Instant ends = Instant.now();
        Assert.assertEquals(TestConstantData.pageLoadWaitTime + " = " + Duration.between(starts, ends).getSeconds(), TestConstantData.pageLoadWaitTime, Duration.between(starts, ends).getSeconds());

    }

    @Ignore
    @Test
    public void waitForPageLoad() throws Exception {

        Instant starts = Instant.now();
        KWebDriver.navigate().to("http://127.0.0.2");
        KWebDriver.waitForPageLoad();
        Instant ends = Instant.now();
        Assert.assertEquals(TestConstantData.pageLoadWaitTime + " = " + Duration.between(starts, ends).getSeconds(), TestConstantData.pageLoadWaitTime, Duration.between(starts, ends).getSeconds());
    }


    @Test
    public void clickButtonInvisiableByLocator() throws Exception {
        KWebDriver.navigate().to(DEMOQA);

        StringBuilder sb = new StringBuilder();
        sb.append("var elems = document.getElementsByClassName('menu');for (var i=0;i<elems.length;i+=1){elems[i].style.display = 'none'; }");
        KWebDriver.executeScript(sb.toString());

        exception.expect(org.openqa.selenium.NoSuchElementException.class);
        exception.expectMessage("no such element: Unable to locate element: {\"method\":\"partial link text\",\"selector\":\"Registration");
        KWebDriver.findElement(By.partialLinkText("Registration")).click();


        KWebDriver.clickButton(By.partialLinkText("Registration"));
        Assert.assertEquals("Registration | Demoqa", KWebDriver.getTitle());
    }

    @Test
    public void clickButtonInvisibleByWebElement() throws Exception {
        KWebDriver.navigate().to(DEMOQA);
        WebElement webElement = KWebDriver.findElement(By.partialLinkText("Registration"));
        StringBuilder sb = new StringBuilder();
        sb.append("var elems = document.getElementsByClassName('menu');for (var i=0;i<elems.length;i+=1){elems[i].style.display = 'none'; }");
        KWebDriver.executeScript(sb.toString());


        exception.expect(org.openqa.selenium.ElementNotVisibleException.class);
        exception.expectMessage("element not visible");
        webElement.click();


        KWebDriver.clickButton(webElement);
        Assert.assertEquals("Registration | Demoqa", KWebDriver.getTitle());
    }


    @Ignore
    @Test
    public void selectDateFromDatepicker() throws Exception {

    }

    @Test
    public void takeScreenShot() throws Exception {
        KWebDriver.navigate().to(DEMOQA);
        KWebDriver.takeScreenShot(ConfigHelper.getCurrentWorkingDir() + File.separator + "1.png");
        Assert.assertNotNull(new File(ConfigHelper.getCurrentWorkingDir() + File.separator + "1.png"));
    }

    @Test
    public void takeScreenShot1() throws Exception {
        KWebDriver.navigate().to(DEMOQA);
        Assert.assertNotNull(new File(KWebDriver.takeScreenShot()));
    }

    @Test
    public void getScreenshotAs() throws Exception {
        KWebDriver.navigate().to(DEMOQA);
        webDriver.navigate().to(DEMOQA);
        String screenshotBase64_KWebDriver = KWebDriver.getScreenshotAs(OutputType.BASE64);
        String screenshotBase64_webdriver = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.BASE64);
        Assert.assertEquals(screenshotBase64_KWebDriver, screenshotBase64_webdriver);
    }

    @Test
    public void checkDisplayedByElement() throws Exception {
        KWebDriver.navigate().to(DEMOQA);
        WebElement webElement = KWebDriver.findElement(By.partialLinkText("Registration"));
        Assert.assertTrue(KWebDriver.checkDisplayed(webElement));
        StringBuilder sb = new StringBuilder();
        sb.append("var elems = document.getElementsByClassName('menu');for (var i=0;i<elems.length;i+=1){elems[i].style.display = 'none'; }");
        KWebDriver.executeScript(sb.toString());

        Assert.assertFalse(KWebDriver.checkDisplayed(webElement));

    }

    @Test
    public void checkDisplayedByLocator() throws Exception {
        KWebDriver.navigate().to(DEMOQA);
        By locatorWebElement =  By.partialLinkText("Registration");
        Assert.assertTrue(KWebDriver.checkDisplayed(locatorWebElement));
        StringBuilder sb = new StringBuilder();
        sb.append("var elems = document.getElementsByClassName('menu');for (var i=0;i<elems.length;i+=1){elems[i].style.display = 'none'; }");
        KWebDriver.executeScript(sb.toString());
        Assert.assertFalse(KWebDriver.checkDisplayed(locatorWebElement));
    }

    @Test
    public void scrollViewToWebElement() throws Exception {
        KWebDriver.navigate().to(DEMOQA);
        KWebDriver.setBrowserResolution(800,200);
        By locatorWebElement =  By.partialLinkText("ToolsQA");
        KWebDriver.scrollViewToWebElement(locatorWebElement);
        KWebDriver.findElement(locatorWebElement).click();

    }

    @Test
    public void scrollViewToWebElement1() throws Exception {
        KWebDriver.navigate().to(DEMOQA);
        KWebDriver.setBrowserResolution(800,200);
        By locatorWebElement =  By.partialLinkText("ToolsQA");
        WebElement webElement = KWebDriver.findElement(locatorWebElement);
         KWebDriver.scrollViewToWebElement(webElement);
        KWebDriver.findElement(locatorWebElement).click();
    }

    @Test
    public void setBrowserResolution() throws Exception {
        KWebDriver.manage().window().maximize();
        Random r = new Random();

        Integer width = (int)(Math.random() *(KWebDriver.manage().window().getSize().getWidth()+1-200)+200);
        Integer height = (int)(Math.random() *(KWebDriver.manage().window().getSize().getHeight()+1-200)+200);

        KWebDriver.setBrowserResolution(width,height);
        Assert.assertEquals("width",(Integer)KWebDriver.manage().window().getSize().getWidth(),width);
        Assert.assertEquals("height",(Integer)KWebDriver.manage().window().getSize().getHeight(),height);
    }




    @Test
    public void checkPresenceOf() throws Exception {
        KWebDriver.navigate().to(DEMOQA);

        Assert.assertTrue(KWebDriver.checkPresenceOf(By.partialLinkText("Registration")));
        Assert.assertTrue(KWebDriver.checkDisplayed(By.partialLinkText("Registration")));

        StringBuilder sb = new StringBuilder();
        sb.append("var elems = document.getElementsByClassName('menu');for (var i=0;i<elems.length;i+=1){elems[i].style.display = 'none'; }");
        KWebDriver.executeScript(sb.toString());

        Assert.assertFalse(KWebDriver.checkPresenceOf(By.partialLinkText("Registration")));
        Assert.assertFalse(KWebDriver.checkDisplayed(By.partialLinkText("Registration")));
    }

    @Test
    public void checkPresenceOf1() throws Exception {
        KWebDriver.navigate().to(DEMOQA);
        WebElement webElement =  KWebDriver.findElement(By.partialLinkText("Registration"));

        Assert.assertTrue(KWebDriver.checkPresenceOf(webElement));
        Assert.assertTrue(KWebDriver.checkDisplayed(webElement));

        StringBuilder sb = new StringBuilder();
        sb.append("var elems = document.getElementsByClassName('menu');for (var i=0;i<elems.length;i+=1){elems[i].style.display = 'none'; }");
        KWebDriver.executeScript(sb.toString());

        Assert.assertTrue(KWebDriver.checkPresenceOf(webElement));
        Assert.assertFalse(KWebDriver.checkDisplayed(webElement));
    }


    @Test
    public void KWebDriver() {
        WebDriver wd = new ChromeDriver();
        KWebDriver webDriver = new KWebDriver(wd);
        Assert.assertNotNull(webDriver);
        webDriver.quit();
    }


    @Test
    public void waitForElementClickableTest() {
        WebDriver wd = new ChromeDriver();
        KWebDriver webDriver = new KWebDriver(wd);

        Instant starts = Instant.now();
        Assert.assertFalse(webDriver.waitForElementClickable(By.cssSelector("nothing")));
        Instant ends = Instant.now();
        webDriver.quit();
        Assert.assertEquals(TestConstantData.elementWaitTime, Duration.between(starts, ends).getSeconds());
    }

    @Test
    public void waitForElementInvisible() {
        WebDriver wd = new ChromeDriver();
        KWebDriver webDriver = new KWebDriver(wd);

        Instant starts = Instant.now();
        Assert.assertFalse(webDriver.waitForElementInvisible(By.cssSelector("body")));
        Instant ends = Instant.now();
        webDriver.quit();
        Assert.assertEquals(TestConstantData.elementWaitTime + " = " + Duration.between(starts, ends).getSeconds(), TestConstantData.elementWaitTime, Duration.between(starts, ends).getSeconds());
    }

    @Test
    public void waitForElementInvisibleByElement() {
        WebDriver wd = new ChromeDriver();
        KWebDriver webDriver = new KWebDriver(wd);
        WebElement webElement = KWebDriver.findElement(By.cssSelector("body"));
        Instant starts = Instant.now();
        Assert.assertFalse(webDriver.waitForElementInvisible(webElement));
        Instant ends = Instant.now();
        webDriver.quit();
        Assert.assertEquals(TestConstantData.elementWaitTime + " = " + Duration.between(starts, ends).getSeconds(), TestConstantData.elementWaitTime, Duration.between(starts, ends).getSeconds());
    }

    @Test
    public void waitForElementVisible() {
        WebDriver wd = new ChromeDriver();
        KWebDriver webDriver = new KWebDriver(wd);

        Instant starts = Instant.now();
        Assert.assertFalse(webDriver.waitForElementVisible(By.cssSelector("nothing")));
        Instant ends = Instant.now();
        webDriver.quit();
        Assert.assertEquals(TestConstantData.elementWaitTime + " = " + Duration.between(starts, ends).getSeconds(), TestConstantData.elementWaitTime, Duration.between(starts, ends).getSeconds());
    }

    @Test
    public void waitForPage() {
        WebDriver wd = new ChromeDriver();
        KWebDriver webDriver = new KWebDriver(wd);

        Instant starts = Instant.now();
        Assert.assertFalse(webDriver.waitForPage(By.cssSelector("nothing")));
        Instant ends = Instant.now();
        webDriver.quit();
        Assert.assertEquals(TestConstantData.pageLoadWaitTime + " = " + Duration.between(starts, ends).getSeconds(), TestConstantData.pageLoadWaitTime, Duration.between(starts, ends).getSeconds());

    }

}
