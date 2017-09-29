package pages;

import infrastructure.Base;
import libraries.infrastructure.ScenarioContext;

/**
 * Created by makri on 29/06/2017.
 */
public class BaseModel extends Base{

    protected ScenarioContext scenarioContext;


    public BaseModel(ScenarioContext scenarioContext, String title) {
        super(scenarioContext, title);
        this.scenarioContext = scenarioContext;
    }

    public BaseModel(ScenarioContext scenarioContext, String title, String url) {
        super(scenarioContext, title, url);
        this.scenarioContext = scenarioContext;

    }


}
