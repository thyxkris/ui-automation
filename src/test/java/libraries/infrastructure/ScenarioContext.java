package libraries.infrastructure;

import infrastructure.KBaseContext;
import pages.BaseModel;

/**
 * Created by Kris Ma on 21/05/2017.
 */
public class ScenarioContext extends KBaseContext {


    public <T extends BaseModel> T getCurrentPage() {
        return (T) this.get("currentPage");
    }

    public <T extends BaseModel> T setCurrentPage(T currentPage) {
        this.put("currentPage", currentPage);
        return (T) this.get("currentPage");
    }


}
