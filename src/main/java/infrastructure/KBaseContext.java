package infrastructure;

import cucumber.api.Scenario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Name;
import java.awt.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.Set;

/**
 * Created by Kris Ma on 21/05/2017.
 */
public abstract class KBaseContext extends HashMap<String, Object> {

    //private Dictionary hashMap = new Hashtable();
    private HashMap<String, Object> hashMap = new HashMap<String, Object>();


    @Override
    public int size() {
        return this.hashMap.size();
    }

    @Override
    public boolean isEmpty() {
        return this.hashMap.isEmpty();
    }

    @Override
    public Object get(Object key) {
        return hashMap.get(key);
    }

    @Override
    public Object put(String key, Object value) {
        return this.hashMap.put(key, value);
    }

    @Override
    public Object remove(Object key) {
        return this.hashMap.remove(key);
    }

    private Scenario scenario;

    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    protected String threadId;
    protected static int count = 0;
    protected Logger logger;
    protected KWebDriver kWebDriver;

    public KBaseContext resetTestData() {
        for (String s : this.keySet()) {
            this.remove(s);
        }
        return this;
    }

    public Locale getLocale() {
        return (Locale) this.get("locale");
    }

    public Locale setLocale(Locale locale) {
        this.put("locale", locale);
        return (Locale) this.get("locale");
    }

    public Logger getLogger() {
        if (null == this.logger) {
            this.logger = LogManager.getLogger(getThreadId());
        }
        return logger;
    }

    public Logger getLogger(String name) {
        //if (null == this.logger||!this.logger.getName().equals(name)) {
        this.logger = LogManager.getLogger(name);
        //}
        return logger;
    }

    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    public String getThreadId() {
        if (this.threadId == null) {
            threadId = Long.toString(Thread.currentThread().getId());//+ "count :" + Integer.toString(count++));
        }
        count++;
        return "threadId" + " " + Integer.toString(count);
    }

    public void dispose() {

        resetTestData();
        getWebDriver().quit();
        if (!getWebDriver().hasQuit()) {
            getWebDriver().quit();
        }


    }

    public KWebDriver getWebDriver() {

        return this.kWebDriver;
    }

    public void setWebDriver(KWebDriver kWebDriver) {

        this.kWebDriver = kWebDriver;
    }


}
