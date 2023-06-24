package com.lyn.mail;

import jakarta.mail.*;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailClient {
    public static void main(String[] args) throws IOException, MessagingException {
        final Properties properties = new Properties();
        InputStream resource = MailClient.class.getClassLoader().getResourceAsStream("mail.properties");
        properties.load(resource);
        /*示例
        final Properties properties = new Properties();
        properties.put("mail.smtp.host","smtp.qq.com");//smtp服务器地址
        properties.put("mail.smtp.port","587");//用户名 qq邮箱端口号是 465/587
        properties.put("mail.smtp.auth","true");//开启验证
        properties.put("mail.smtp.starttls.enable","true");//启动TLS加密
         */
        Session session = Session.getInstance(properties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(
                                properties.getProperty("mail.username"),
                                properties.getProperty("mail.password") //注意密码需要替换成对应邮箱的授权码，可从对应邮箱产品的设置中开启
                        );
                    }
                }
        );
        //设置debug模式便于调试
        session.setDebug(true);

        //发送邮件
        sendMessage(session);
    }

    private static void sendMessage(Session session) throws MessagingException {
        MimeMessage message = new MimeMessage(session);
        //设置发送方地址
        message.setFrom(new InternetAddress(session.getProperty("mail.address.from")));
        //设置接收方地址
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(
                session.getProperty("mail.address.to")
        ));
        //设置邮件主题
        message.setSubject("Hello", "UTF-8");
        //设置邮件正文
        message.setText("有空来玩啊");
        Transport.send(message);
    }
}
