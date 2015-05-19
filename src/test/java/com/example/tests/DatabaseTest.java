package com.example.tests;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.testng.annotations.Test;

import com.example.fw.MessageData;

public class DatabaseTest {

  @Test
  public void testUntitled() throws Exception {
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    Transaction tr = session.beginTransaction();
    try {
      List<MessageData> messages =
          (List<MessageData>) session.createQuery("from MessageData").list();
      System.out.println(messages);
    } finally {
      tr.commit();
    }
  }

}
