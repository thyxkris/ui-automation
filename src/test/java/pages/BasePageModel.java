package pages;

import infrastructure.KWebDriver;

import libraries.infrastructure.ScenarioContext;
import org.apache.logging.log4j.Logger;

/**
 * Created by makri on 17/05/2017.
 */
public class BasePageModel extends BaseModel {

    protected static Logger logger;
    protected KWebDriver driver;


    public BasePageModel(ScenarioContext scenarioContext, String title) throws Exception {
        super(scenarioContext, title, null);

        setup(scenarioContext);

    }

    public BasePageModel(ScenarioContext scenarioContext, String title, String url) throws Exception {
        super(scenarioContext, title, url);
        setup(scenarioContext);
    }


    public void setup(ScenarioContext scenarioContext) throws Exception {
        waitForPageLoad();
        this.scenarioContext = scenarioContext;
        this.driver = scenarioContext.getWebDriver();
        //components initilizations

        if (logger == null) {
            logger = scenarioContext.getLogger();
        }
         logger.info(this.getClass() + " finish to initialize  after super's setup");

    }






}
