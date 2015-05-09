package com.example.tests;

import org.testng.annotations.Test;

public class SettingsTest extends TestBase {
  
  @Test
  public void test1() throws Exception {
    mail.openMainPage();
    mail.openSettings();
    mail.selectLanguage("Русский");
    mail.selectTZ("(GMT +04:00) Moscow, St. Petersburg, Volgograd");
    mail.setTimeFormat24h();
    mail.saveSettings();
    mail.gotoInbox();
  }
  
  @Test(dependsOnMethods = {"test1"})
  public void tes2() throws Exception {
    mail.openMainPage();
    mail.openSettings();
    mail.selectLanguage("Русский");
    mail.selectTZ("(GMT +04:00) Moscow, St. Petersburg, Volgograd");
    mail.setTimeFormat24h();
    mail.saveSettings();
    mail.gotoInbox();
  }
  
}
