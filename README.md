# ui-automation
a java base ui automation framework supporting cucumber/rest-assured/concordian, with pretty reporting as well

ui-automation is an automation BDD testing framework/solution based on cucumber-jvm, java. It provides the following features:

1.  **ability to be executed parallel up to multi-threads after easy configuration.**
2.  **rerun function is integrated with team city in order to deal with flaky test cases.**
3.  ****ability** to be configured via maven and team-city so no need of different branches for different testing scenarios**
4.  enhanced robust and stability due to the wrapper on both web-driver and web-element.
5.  **automatically mapping step definitions to the page of model**
6.  configurable reports on different format: html, log, json , etc.


# Tutorials

1.  how to create a POM （page object model) for a special page object
    1.  in the folder of pages, create a new java class with the naming pattern XXXPageModel (the key word "PageModel" is important to auto-mapping)
    2.  and implement the constructor as below (assuming it's TestPageModel)  

        <pre><span style="color: rgb(0,0,128);">public class</span> TestPageModel <span style="color: rgb(0,0,128);">extends</span> BasePageModel {  
        <span style="color: rgb(0,0,128);">public</span> TestPageModel(ScenarioContext scenarioContext) <span style="color: rgb(0,0,128);">throws</span> Exception {  
        <span style="color: rgb(0,0,128);">String title = "the title of the page model that you are going to create"; //if it's set to null, it's not going to check it, useful for components.</span>
        <span style="color: rgb(0,0,128);">super</span>(scenarioContext, title);  
            }  
        }</pre>

    3.  now it's ready to add locators and methods when necessary
    4.  tips: for one component that may be used potentially in multiple pages, it's better to put it in to a component class , rather than creating many locators/methods on a single page.  

2.  how to create a step definition page for BDD implementation and later script writing
    1.  create a new step definition file, under the convention of XXXX**PageStepsDef** (**the part of XXX should be EXACTLY the same as that in XXXPageModel**), in our case it will be TestPageStepsDef  

    2.  currently there are two mandatory steps need to be done. one is to create a default constructor, one is to override IShouldBeOnThePage() method, where we will put validation methods when necessary.  

        public class TestPageStepsDef extends BaseStepsDef {  
        public TestPageStepsDef(ScenarioContext scenarioContext) throws Throwable {  
        super(scenarioContext);  
        }

        @Override  
        public void IShouldBeOnThePage() throws Throwable {

        }  
        }

    3.  add the object of XXXPageModel in to the BaseStepsDef everytime when adding a new pom, in our case, it's TestPageModel.  

        public abstract class BaseStepsDef {

        //declare page models, everytime when adding a new page, must declare it here  
        protected TestPageModel testPageModel;

    4.  it's ready to add business steps into this file.
    5.  tips:
        1. when a common step can be reused via multiple steps, it's advised to put it in to a separate step definition file, such as CommonStepsDef  
        2.  for setup/tearUp functions, they should be also put in this common steps def file.

3.  now we can call the functions created in any step definitions in the feature files.
    1.  in intellij ,there is a plugin cucumber can facilitate using cucumber.
    2.  after having it installed, one can use gerhkin lanugage to write steps
    3.  the IDE will suggest there is no function linked to this step. follow the instructions and choose the correct stepsDef file to put the newly created function in.
    4.  it looks like the following  

     

    5.  now a simple cucumber project is ready to run
    
    # Best practice when using the framework

1.  for one component that may be used potentially in multiple pages, it's better to put it in to a component class, then include the component in the class of POM, rather than creating many locators/methods on a single page.
2.  when a common step can be reused via multiple steps, it's advised to put it in to a separate step definition file, such as CommonStepsDef  

    <pre>  
    <span style="color: rgb(0,0,128);">private TestPageStepsDef</span> test<span style="color: rgb(25,72,166);">PageStepsDef</span>; <span style="color: rgb(128,128,128);"></span> <span style="color: rgb(128,128,128);">  
    </span><span style="color: rgb(0,0,128);">public</span> CommonStepsDef(ScenarioContext scenarioContext) <span style="color: rgb(0,0,128);">throws</span> Throwable {  
    <span style="color: rgb(0,0,128);">super</span>(scenarioContext);  
    }  

    <span style="color: rgb(51,129,255);">@Override  
    </span><span style="color: rgb(0,0,128);">public void</span> IShouldBeOnThePage() <span style="color: rgb(0,0,128);">throws</span> Exception {  
    //empty, since there is no such a pom  
    }</pre>

    setup/tear-up functions should be also put in commonStepsDef file.

3.  use findElementBy.cssSelector rather xpath as much as possible.
4.  when triggering click event, use clickButton method from Base class, it has couple of overridden methods that can accept By/WebElement object. (donot use webElement.click() because in order to succeed in clicking, many preconditions and exceptions have to be assured, the button should be existent,displayed, clickable, unblocked from other popup windows, also thread safe; moreover, clickButton function will trigger a re-click if it fails the first time)
5.  use findKElement and findKElements to get the object of KWebElement(s); don't use the original findElement(s) in webdriver as there are many fatal but unhanded exceptions may occur. Always use KWebElement and KWebdriver instead of original WebElement/Webdriver.
6.  also when trying to get the current Webdriver, always call scenarioContext.getWebdriver() to get it
7.  put all the testing data (specific for a single test case) to the scenarioContext by get/set method. ScenarioContext is a hashMap object, so you can call ResetTestingData anytime to clean up the testing data
8.  when trying to take screenShot, default method is to put the screen shot directly to the html report by calling the method takeScreenShot() from Base class
9.  when logging something there are two ways
    1.  to log the info into the log.txt by calling  
        logger.info(....)
    2.  to log the info into the final html report by calling

        <pre><span>scenarioContext</span>.getScenario().write(.....)</pre>



# How to use maven to run the project
 

# How to implement rerun testing methodology via TeamCity
 
