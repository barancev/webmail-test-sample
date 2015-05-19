package com.example.fw;

import org.openqa.selenium.By;
import org.openqa.selenium.support.pagefactory.ByChained;

public class UIMap3 {
  
  public static final By MESSAGE_SUBJECT
    = By.cssSelector("div#layout_3pane div.wm_message_pane_border > div.wm_message_headers > div > span");
  
  public static final By CHAINED
    = new ByChained(By.id("layout_3pane"), By.className("wm_message_pane_border"),
        By.className("wm_message_headers"), By.tagName("div"), By.tagName("span"));

}
