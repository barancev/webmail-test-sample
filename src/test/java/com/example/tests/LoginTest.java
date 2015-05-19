package com.example.tests;

import org.testng.annotations.Test;

public class LoginTest extends TestBase {

  @Test
  public void testUntitled() throws Exception {
    mail.openMainPage();
    mail.login("user1@localhost", "user1");
    mail.logout();
  }

}
