package com.chat.model;

import java.time.LocalDateTime;

public class Message {
   private String sender;
   private String receiver;
    private String content;
    private LocalDateTime timestamp;

    public Message(){
      this.timestamp = LocalDateTime.now();
    }
public Message(String sender, String receiver, String content){
    this.sender= sender;
    this.receiver = receiver;
    this.content = content;
    this.timestamp = LocalDateTime.now();
}
public String getSender(){
    return sender;
}
public void setSender(String sender){
    this.sender =sender;
}
public String getReceiver(){
    return receiver;
}
public void setReceiver(String receiver){
    this.receiver= receiver;
}
public String getContent(){
    return content;
}

public void setContent(String content){
    this.content = content;
}

public LocalDateTime getTimestamp(){
    return timestamp;
}
public void setTimestamp(LocalDateTime timestamp){
    this.timestamp = timestamp;
}
@Override
public String toString(){
return "Message{" + "sender='" + sender + '\'' +", receiver='" + receiver + '\'' +
                ", content='" + content + '\'' +", timestamp=" + timestamp +'}';
}
}
