package infrastructure;

import org.openqa.selenium.*;
import org.openqa.selenium.remote.RemoteWebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makri on 15/06/2017.
 */
public class KRemoteWebElement extends RemoteWebElement {



    @Override
    public void click() {
        super.click();
    }

    @Override
    public void submit() {
        super.submit();
    }

    @Override
    public void sendKeys(CharSequence... charSequences) {
        super.sendKeys(charSequences);
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public String getTagName() {
        return super.getTagName();
    }

    @Override
    public String getAttribute(String s) {

        try {
            return super.getAttribute(s);
        }
        catch (org.openqa.selenium.WebDriverException e)

        {
            if(e.toString().contains("No buffer space available")) {
                return this.getAttribute(s);
            }else {
                throw e;
            }
        }
    }

    @Override
    public boolean isSelected() {
        return super.isSelected();
    }

    @Override
    public boolean isEnabled() {
        return super.isEnabled();
    }

    @Override
    public String getText() {
        return super.getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        return super.findElements(by);
    }


    public List<KRemoteWebElement> findKElements(By by) {
        List<WebElement> webElements =  super.findElements(by);
        List<KRemoteWebElement> kRemoteWebElements = new ArrayList<KRemoteWebElement>();
        for(WebElement webElement:webElements) {
            kRemoteWebElements.add((KRemoteWebElement) webElement);
        }

        return kRemoteWebElements;
    }


    @Override
    public KRemoteWebElement findElement(By by) {
       return (KRemoteWebElement) super.findElement(by);

    }


    @Override
    public boolean isDisplayed() {
        return super.isDisplayed();
    }

    @Override
    public Point getLocation() {
        return super.getLocation();
    }

    @Override
    public Dimension getSize() {
        return super.getSize();
    }

    @Override
    public Rectangle getRect() {
        return super.getRect();
    }

    @Override
    public String getCssValue(String s) {
        return super.getCssValue(s);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return super.getScreenshotAs(outputType);
    }
}
