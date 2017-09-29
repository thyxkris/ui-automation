package infrastructure;

import org.openqa.selenium.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by makri on 15/06/2017.
 */
public class KWebElement implements WebElement {
    public KWebElement(WebElement webElement) {
        this.webElement = webElement;
    }

    private WebElement webElement;


    public WebElement getWebElement() {
        return this.webElement;
    }

    @Override
    public void click() {
        this.webElement.click();
    }

    @Override
    public void submit() {
        this.webElement.submit();
    }

    @Override
    public void sendKeys(CharSequence... charSequences) {
        this.webElement.sendKeys(charSequences);
    }

    @Override
    public void clear() {
        this.webElement.clear();
    }

    @Override
    public String getTagName() {
        return this.webElement.getTagName();
    }

    @Override
    public String getAttribute(String s) {
        try {
            return this.webElement.getAttribute(s);
        } catch (org.openqa.selenium.WebDriverException e)

        {
            if (e.toString().contains("No buffer space available")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return this.getAttribute(s);
            } else {
                throw e;
            }
        }
    }

    @Override
    public boolean isSelected() {
        return this.webElement.isSelected();
    }

    @Override
    public boolean isEnabled() {
        return this.webElement.isEnabled();
    }

    @Override
    public String getText() {
        return this.webElement.getText();
    }

    @Override
    public List<WebElement> findElements(By by) {
        try {
            return this.webElement.findElements(by);
        } catch (org.openqa.selenium.WebDriverException e)

        {
            if (e.toString().contains("No buffer space available")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return this.findElements(by);
            } else {
                throw e;
            }
        }
    }


    public List<KWebElement> findKElements(By by) {
        try {
            List<WebElement> webElements = this.webElement.findElements(by);
            List<KWebElement> kWebElements = new ArrayList<KWebElement>();
            for (WebElement webElement : webElements) {
                kWebElements.add(new KWebElement(webElement));
            }

            return kWebElements;
        }catch(org.openqa.selenium.StaleElementReferenceException e)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return this.findKElements(by);
        } catch (org.openqa.selenium.WebDriverException e)

        {
            if (e.toString().contains("No buffer space available")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return this.findKElements(by);
            } else {
                throw e;
            }
        }

    }


    @Override
    public KWebElement findElement(By by) {
        //  this.webElement = this.webElement.findElement(by);
        //   return new KWebElement(this.webElement.findElement(by));

        try {
            return new KWebElement(this.webElement.findElement(by));
        }
        catch(org.openqa.selenium.StaleElementReferenceException e)
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
           return this.findElement(by);
        }
        catch (org.openqa.selenium.WebDriverException e)
        {
            if (e.toString().contains("No buffer space available")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return findElement(by);
            } else if (e.toString().contains("no such session")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return findElement(by);
            } else {
                throw e;
            }
        }
    }


    @Override
    public boolean isDisplayed() {
        return this.webElement.isDisplayed();
    }

    @Override
    public Point getLocation() {
        return this.webElement.getLocation();
    }

    @Override
    public Dimension getSize() {
        return this.webElement.getSize();
    }

    @Override
    public Rectangle getRect() {
        return this.webElement.getRect();
    }

    @Override
    public String getCssValue(String s) {
        return this.webElement.getCssValue(s);
    }

    @Override
    public <X> X getScreenshotAs(OutputType<X> outputType) throws WebDriverException {
        return this.webElement.getScreenshotAs(outputType);
    }
}
