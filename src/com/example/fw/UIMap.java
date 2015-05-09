package com.example.fw;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;

public class UIMap {

  public By MESSAGE_SUBJECT;

  static UIMap uimap2;
  static UIMap uimap3;
  
  static {
    uimap2 = new UIMap();
    uimap3 = new UIMap();
    
    uimap3.MESSAGE_SUBJECT =
        new ByChained(
            By.cssSelector("div#layout_3pane"),
            By.cssSelector("div.wm_message_pane_border"),
            By.cssSelector("div.wm_message_headers"),
            By.tagName("div"),
            By.tagName("span"));
  }
  
  public static UIMap get(String layout) {
    if (layout.equals("3pane")) {
      return uimap3;
    } else {
      return uimap2;
    }
  }

  protected static By uimap(Properties map, String key) {
    String locator = map.getProperty(key);
    if (locator.startsWith("xpath:")) {
      return By.xpath(locator.substring("xpath:".length()));
    } else if (locator.startsWith("css:")) {
      return By.cssSelector(locator.substring("css:".length()));
    } else {
      throw new Error("Unrecognized locator " + locator);
    }
  }
  
}
