package com.tower.service.config.utils;

import java.util.HashMap;
import java.util.Map;

public class MailSender implements Sender{

    public void send(Object receiver,Map msg){
        /**
         String subject = (String)msg.get("subject");
         String body = (String)msg.get("body");
         String mailTo = (String)receiver;
         Properties prop = new Properties();

         String mailServer = config.getString("mailServer","smtp.126.com");
         String mailPort = config.getString("mailPort","25");
         String mailUserName = config.getString("mailUserName","arch_level@126.com");
         String mailPassword = config.getString("mailPassword","jcl7744781");
         String mailAuth = config.getString("mailAuth","false");
         prop.setProperty("mail.smtp.host", mailServer);
         prop.setProperty("mail.smtp.port", mailPort);
         prop.setProperty("mail.smtp.auth", mailAuth);
         prop.setProperty("smtpUsername",mailUserName);
         prop.setProperty("smtpPassword", mailPassword);
         try{
         MailHelper.send(prop, mailTo, subject, body);
         }catch(Exception e){
         }
         */
    }

    static MailSender ms = new MailSender();

    public static void send(String[] receiverArray, String subject,String body){

        Map msg = new HashMap();
        msg.put("subject", subject);
        msg.put("body", body);
        StringBuffer receivers = new StringBuffer();
        for(String receiver : receiverArray){
            receivers.append(receiver);
            receivers.append(";");
        }

        ms.send(receivers.toString(),msg);

    }
}