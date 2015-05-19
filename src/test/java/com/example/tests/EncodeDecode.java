package com.example.tests;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;

import com.example.fw.MessageData;

public class EncodeDecode {

  @Test
  public void testUntitled() throws Exception {
    byte[] encoded = Base64.encodeBase64("Проверочный текст".getBytes());
    System.out.println(new String(encoded));

    byte[] decoded = Base64.decodeBase64(encoded);
    System.out.println(new String(decoded));
  }

}
