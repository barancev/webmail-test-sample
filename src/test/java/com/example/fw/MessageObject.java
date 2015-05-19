package com.example.fw;

import static com.example.fw.UIMap3.*;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;

public class MessageObject extends WebDriverCommons {

  private WebDriverCommons manager;
  
  @FindBys(
      {@FindBy(css = "div#layout_3pane"),
       @FindBy(css = "div.wm_message_pane_border"),
       @FindBy(css = "div.wm_message_headers"),
       @FindBy(tagName = "div"),
       @FindBy(tagName = "span")})
  public WebElement subject;

  @FindBy(css = "div#layout_3pane div.wm_message_pane_border span.wm_message_from")
  public WebElement from;

  @FindBy(css = "div#layout_3pane div.wm_message")
  public WebElement content;

  public MessageObject(WebDriverCommons webMailManager) throws Exception {
    super(webMailManager.properties);
    this.manager = webMailManager;
  }
  
  public String getSubject() {
    String layout = "3pane";
    return driver.findElement(UIMap.get(layout).MESSAGE_SUBJECT).getText();
  }

}
