package com.example.tests;

import java.io.File;

import org.testng.annotations.Test;

public class MoveMessageToTrash extends TestBase {
  
  @Test(groups = "Failed")
  public void testUntitled() throws Exception {
    mail.gotoInbox();
    mail.moveSomeMessageToTrash();
  }

}
