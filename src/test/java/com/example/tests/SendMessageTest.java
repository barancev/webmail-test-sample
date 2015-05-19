package com.example.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.example.fw.MessageObject;

public class SendMessageTest extends TestBase {
  
  private static final String INBOX = "INBOX";

  @DataProvider
  public Iterator<Object[]> mailDataFromFile() throws Exception {
    List<Object[]> list = new ArrayList<Object[]>();
    File file = new File("maildata.txt");
    BufferedReader reader = new BufferedReader(new FileReader(file));
    String s = reader.readLine();
    while (s != null) {
      String to = s;
      String subject = reader.readLine();
      String text = "";
      s = reader.readLine();
      while (s != null && ! s.equals(".")) {
        text = text + s;
        s = reader.readLine();
      }
      list.add(new Object[] {
          to, subject, text 
      });
      s = reader.readLine();
    }
    return list.iterator();
  }
  
  @DataProvider
  public Iterator<Object[]> mailData() {
    List<Object[]> list = new ArrayList<Object[]>();
    for (int i = 0; i < 3; i++) {
      list.add(new Object[] {
         "user@localhost", randomString(10), randomString(100) 
      });
    }
    return list.iterator();
  }
  
  private String randomString(int len) {
    Random rnd = new Random();
    String s = "";
    for (int i = 0; i < len; i++) {
      s = s + (char) (32 + rnd.nextInt(95));
    }
    return s;
  }

  @Test(dataProvider = "mailDataFromFile")
  public void testUntitled(String address, String subject, String text) throws Exception {
    // save state
    mail.gotoInbox();
    int before = mail.openFolder(INBOX).countMessages();
    
    // do actions
    mail.startNewMessage()
      .to(address)
      .withSubject("" + System.currentTimeMillis() + subject)
      //.withAttached(new File("attachment.txt"))
      .withText(text)
      .send();

    // perform checks
    assertThat(checkNewMail(INBOX, before), is(true));
    MessageObject message = mail.openFolder(INBOX).openMessageByIndex(0);
    assertThat(message.from.getText(), equalTo(address));
    assertThat(message.subject.getText(), equalTo(subject));
    assertThat(message.content.getText(), equalTo(text));
  }

  private boolean checkNewMail(String folder, int before) throws Exception {
    for (int i = 0; i < 10; i++) {
      mail.checkMail();
      int after = mail.openFolder(folder).countMessages();
      if (after > before) {
        return true;
      } else {
        Thread.sleep(1000); 
      }
    }
    return false;
  }

}
