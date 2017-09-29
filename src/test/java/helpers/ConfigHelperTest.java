package helpers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by makri on 26/06/2017.
 */
public class ConfigHelperTest {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void getInstance() throws Exception {
        ConfigHelper configHelper1 = ConfigHelper.getInstance();
        ConfigHelper configHelper2 = ConfigHelper.getInstance();
        Assert.assertEquals(configHelper1, configHelper2);
    }

    @Test
    public void getString() throws Exception {
        Assert.assertEquals("${execution.env}", ConfigHelper.getString("execution.env"));
    }

    @Test
    public void getInt() throws Exception {
        System.out.println(ConfigHelper.getInt("pageload.wait.time"));
        Assert.assertEquals(20, ConfigHelper.getInt("pageload.wait.time"));
    }


    @Test
    public void getTestResourcesFolderPath() throws Exception {
        Assert.assertEquals("C:\\source\\fcl-ui-automation\\target\\test-classes", ConfigHelper.getTestResourcesFolderPath());
    }

    @Test
    public void getCurrentWorkingDir() throws Exception {
        Assert.assertEquals("C:\\source\\fcl-ui-automation", ConfigHelper.getCurrentWorkingDir());
    }

    @Test
    public void getUserDir() throws Exception {
        Assert.assertEquals("C:\\source\\fcl-ui-automation", ConfigHelper.getUserDir());
    }

    @Test
    public void getTemplateDir() throws Exception {
        Assert.assertEquals("C:\\source\\fcl-ui-automation\\target\\test-classes\\templates\\", ConfigHelper.getTemplateDir());
    }

    @Test
    public void getProjectBaseDir() throws Exception {
        Assert.assertEquals("C:/source/fcl-ui-automation", ConfigHelper.getProjectBaseDir());
    }

    @Test
    public void getBrowserType() throws Exception {

        Assert.assertEquals("chrome", ConfigHelper.getBrowserType());
    }

    @Test
    public void getTestEnv() throws Exception {
        Assert.assertEquals("${execution.env}", ConfigHelper.getTestEnv());
    }

    @Test
    public void getSeleniumGridURL() throws Exception {
        Assert.assertEquals("127.0.0.1", ConfigHelper.getSeleniumGridURL());
    }

    @Test
    public void getPageLoadWaitTime() throws Exception {
        Assert.assertEquals((Integer) 20, ConfigHelper.getPageLoadWaitTime());

    }

    @Test
    public void getPageLoadWaitTimeString() throws Exception {
        Assert.assertEquals("20s", ConfigHelper.getPageLoadWaitTimeString());
    }

    @Test
    public void getElementWaitTime() throws Exception {
        Assert.assertEquals((Integer) 15, ConfigHelper.getElementWaitTime());
    }

    @Test
    public void getElementWaitTimeString() throws Exception {
        Assert.assertEquals("15s", ConfigHelper.getElementWaitTimeString());
    }

    @Test
    public void getImplicitWaitTime() throws Exception {
        Assert.assertEquals((Integer) (50), ConfigHelper.getImplicitWaitTime());
    }

    @Test
    public void getImplicitWaitTimeString() throws Exception {
        Assert.assertEquals("50million seconds", ConfigHelper.getImplicitWaitTimeString());
    }


}
