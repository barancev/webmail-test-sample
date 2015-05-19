package com.example.fw;

import java.util.List;
import java.util.NoSuchElementException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;

public class FolderAssistant extends WebDriverCommons {

  private WebDriverCommons manager;

  public FolderAssistant(WebDriverCommons webMailManager) throws Exception {
    super(webMailManager.properties);
    this.manager = webMailManager;
  }

  public FolderAssistant openFolder(String folderName) {
    List<WebElement> folderElements = driver.findElements(
        By.cssSelector("div#layout_3pane div.wm_folders_list > div"));
    for (WebElement element : folderElements) {
      if (element.getAttribute("id").endsWith("#@%" + folderName)) {
      //if (element.getText().startsWith(folderName)) {
        element.findElement(By.tagName("a")).click();
        return this;
      };
    }
    throw new NoSuchElementException("No folder with name starting with " + folderName);
  }

  public int countMessages() {
    return getMessageElements().size();
  }

  private List<WebElement> getMessageElements() {
    return driver.findElements(
        By.cssSelector("div#layout_3pane div.wm_inbox_lines > table > tbody > tr"));
  }

  public MessageObject openMessageByIndex(int i) throws Exception {
    getMessageElements().get(i).findElements(By.tagName("td")).get(5).click();
    MessageObject messageObject = new MessageObject(manager);
    PageFactory.initElements(
        new AjaxElementLocatorFactory(driver, 30),
        messageObject);
    return messageObject;
  }

}
