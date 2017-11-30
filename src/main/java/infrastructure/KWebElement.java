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
        try {
            this.webElement.click();
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            this.webElement.click();
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                this.webElement.click();
            } else if (e.toString().contains("no such session")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                this.webElement.click();
            } else {
                throw e;
            }
        }
    }

    @Override
    public void submit() {
        this.webElement.submit();
    }

    @Override
    public void sendKeys(CharSequence... charSequences) {

        try {
            this.webElement.sendKeys(charSequences);
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            this.webElement.sendKeys(charSequences);
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                this.webElement.sendKeys(charSequences);
            } else if (e.toString().contains("no such session")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                this.webElement.sendKeys(charSequences);
            } else {
                throw e;
            }
        }
    }

    @Override
    public void clear() {
        try {
            this.webElement.clear();
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            this.webElement.clear();
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                this.webElement.clear();
            } else if (e.toString().contains("no such session")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                this.webElement.clear();
            } else {
                throw e;
            }
        }
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

        try {
            return this.webElement.isSelected();
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return this.webElement.isSelected();
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return this.webElement.isSelected();
            } else if (e.toString().contains("no such session")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return this.webElement.isSelected();
            } else {
                throw e;
            }
        }
    }

    @Override
    public boolean isEnabled() {
        try {
            return this.webElement.isEnabled();
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return this.webElement.isEnabled();
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return this.webElement.isEnabled();
            } else if (e.toString().contains("no such session")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return this.webElement.isEnabled();
            } else {
                throw e;
            }
        }
    }

    @Override
    public String getText() {
        try {
            return this.webElement.getText();
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return this.webElement.getText();
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return this.webElement.getText();
            } else if (e.toString().contains("no such session")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return this.webElement.getText();
            } else {
                throw e;
            }
        }
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
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
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
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return this.findElement(by);
        } catch (org.openqa.selenium.WebDriverException e) {
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
        try {
            return this.webElement.isDisplayed();
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return this.webElement.isDisplayed();
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return this.webElement.isDisplayed();
            } else if (e.toString().contains("no such session")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return this.webElement.isDisplayed();
            } else {
                throw e;
            }
        }
    }

    @Override
    public Point getLocation() {

        try {
            return this.webElement.getLocation();
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return this.webElement.getLocation();
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return this.webElement.getLocation();
            } else if (e.toString().contains("no such session")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return this.webElement.getLocation();
            } else {
                throw e;
            }
        }
    }

    @Override
    public Dimension getSize() {

        try {
            return this.webElement.getSize();
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            return this.webElement.getSize();
        } catch (org.openqa.selenium.WebDriverException e) {
            if (e.toString().contains("No buffer space available")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return this.webElement.getSize();
            } else if (e.toString().contains("no such session")) {
                try {
                    Thread.sleep(20000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                return this.webElement.getSize();
            } else {
                throw e;
            }
        }
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
