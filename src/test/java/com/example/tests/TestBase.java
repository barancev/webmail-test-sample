package com.example.tests;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.example.fw.WebMailManager;

public class TestBase {

  protected WebMailManager mail;

  @BeforeClass
  @Parameters({"configFile"})
  public void setUp(@Optional String configFile) throws Exception {
    if (configFile == null) {
      configFile = System.getProperty("configFile", "/config.properties");
    }
    Properties properties = new Properties();
    try {
      properties.load(TestBase.class.getResourceAsStream(configFile));
    } catch (IOException e) {
      // ignore and use default properties
    }
    mail = new WebMailManager(properties);
    mail.openMainPage();
  }
  
  @BeforeMethod
  public void login() {
    mail.login("user1@localhost", "user1");
  }

  //@AfterMethod
  //public void takeScreenshotOnFailure(ITestResult result) {
  //  if (! result.isSuccess()) {
  //    mail.takeScreenshot(result.getName());
  //  }
  //}
  
  public void log(String text) {
    System.out.println(text);
  }

  @AfterClass
  public void tearDown() throws Exception {
    mail.logout();
    mail.quit();
  }

  @BeforeGroups(groups = {"admin"})
  public void loginAsAdmin() {
    
  }
}
