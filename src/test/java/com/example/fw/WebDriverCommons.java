package com.example.fw;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;
import ru.stqa.selenium.factory.WebDriverFactory;

public class WebDriverCommons {

  private static final long TIMEOUT = 10;

  protected final Properties properties;

  protected WebDriver driver;

  protected String baseUrl;

  private Properties uimap;

  public WebDriverCommons(Properties properties) throws Exception {
    this.properties = properties;

    DesiredCapabilities capabilities = new DesiredCapabilities();
    for (String name : properties.stringPropertyNames()) {
      capabilities.setCapability(name, properties.getProperty(name));
    }
    driver = WebDriverFactory.getDriver(capabilities);

    baseUrl = properties.getProperty("baseUrl", "http://localhost/");
    
    uimap = new Properties();
    uimap.load(WebDriverCommons.class.getResourceAsStream(
        properties.getProperty("uimap", "/uimap.properties")));
  }

  protected By uimap(String key) {
    String locator = uimap.getProperty(key);
    if (locator.startsWith("xpath:")) {
      return By.xpath(locator.substring("xpath:".length()));
    } else if (locator.startsWith("css:")) {
      return By.cssSelector(locator.substring("css:".length()));
    } else {
      throw new Error("Unrecognized locator " + locator);
    }
  }
  
  public void clearAndType(By locator, String text) {
    WebElement input = waitElementVisible(locator);
    //input.clear();
    input.sendKeys(Keys.CONTROL + "a");
    input.sendKeys(Keys.BACK_SPACE);
    input.sendKeys(text);
  }

  public static void sleep(int timeout) {
    try {
      Thread.sleep(timeout);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void tryToClickTwice(By locator) {
    try {
      waitElementClickable(locator).click();
    } catch (StaleElementReferenceException e) {
      waitElementClickable(locator).click();
    }
  }

  public WebElement waitElementVisible(By locator) {
    return waitFor(ExpectedConditions.visibilityOfElementLocated(locator));
  }

  public boolean waitElementInvisible(By locator) {
    return waitFor(ExpectedConditions.invisibilityOfElementLocated(locator));
  }

  public WebElement waitElementClickable(By locator) {
    return waitFor(ExpectedConditions.elementToBeClickable(locator));
  }

  public WebElement waitOneOfElementsVisible(By locator1, By locator2) {
    return waitFor(visibilityOfAnyElementLocated(locator1, locator2));
  }

  public <Type> Type waitFor(ExpectedCondition<Type> expectedCondition) {
    return new WebDriverWait(driver, TIMEOUT).until(expectedCondition);
  }

  public ExpectedCondition<WebElement> visibilityOfAnyElementLocated(final By locator1, final By locator2) {
    return new ExpectedCondition<WebElement>() {
      public WebElement apply(WebDriver driver) {
        WebElement element1 = elementIfVisible(locator1);
        if (element1 != null) {
          return element1;
        }
        WebElement element2 = elementIfVisible(locator2);
        if (element2 != null) {
          return element2;
        }
        return null;
      }
    };
  }

  private WebElement elementIfVisible(By locator) {
    try {
      WebElement element = driver.findElement(locator);
      return element != null && element.isDisplayed() ? element : null;
    } catch (NoSuchElementException e) {
      return null;
    }
  }

  public boolean isElementPresent(By by) {
    try {
      waitElementVisible(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  public void takeScreenshot(String id) {
    File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
    // copy this file to the screenshots dir and rename
    Reporter.log(id + ": " + "<a href=\"" + screenshot.getAbsolutePath() + "\">screenshot</a>");
  }

}
