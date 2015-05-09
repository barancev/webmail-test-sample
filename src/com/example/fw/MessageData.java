package com.example.fw;

public class MessageData {
  
  private String id;
  private String from;
  private String to;
  private String subject;
  private String content;

  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }
  public String getFrom() {
    return from;
  }
  public void setFrom(String from) {
    this.from = from;
  }
  public String getTo() {
    return to;
  }
  public void setTo(String to) {
    this.to = to;
  }
  public String getSubject() {
    return subject;
  }
  public void setSubject(String subject) {
    this.subject = subject;
  }
  public String getContent() {
    return content;
  }
  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "MessageData [id=" + id + ", from=" + from + ", to=" + to
        + ", subject=" + subject + ", content=" + content + "]\n";
  }

}
