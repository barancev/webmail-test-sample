package com.example.fw;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class MessageSender extends WebDriverCommons {

  private final WebMailManager manager;

  public MessageSender(WebMailManager webMailManager) throws Exception {
    super(webMailManager.properties);
    this.manager = webMailManager;
    By newMessageFormLocator = By.id("wm_new_message");
    waitElementVisible(newMessageFormLocator);
  }

  public MessageSender to(String address) {
    findAllFields().get(0).sendKeys(address);
    return this;
  }

  public MessageSender withSubject(String subject) {
    findAllFields().get(3).sendKeys(subject);
    return this;
  }

  public MessageSender withAttached(final File file) throws InterruptedException {
    waitElementVisible(By.id("fileuploader_click_to_attach")).click();
    new Actions(driver).sendKeys(file.getAbsolutePath() + Keys.ENTER).perform();
    assertThat(10, equalTo(10));
    return this;
  }

  private List<WebElement> findAllFields() {
    return driver.findElements(By.cssSelector("table#wm_new_message input.wm_input"));
  }

  public MessageSender withText(String text) {
    driver.switchTo().frame(driver.findElement(By.id("EditorFrame")));
    driver.findElement(By.tagName("body")).sendKeys(text);
    driver.switchTo().defaultContent();
    return this;
  }

  public WebMailManager send() {
    waitElementVisible(By.className("wm_send_message_button")).click();
    waitElementInvisible(By.id("wm_new_message"));
    return manager;
  }

}
