package com.lyz.util;

import com.sun.mail.util.MailSSLSocketFactory;
import com.lyz.pojo.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

//用户体验 3秒未加载出来会关闭
//多线程实现用户体验!  (异步处理)
public class sendEmail extends Thread {

    //用于给用户发送邮件的邮箱
    private String from = "1732781024@qq.com";
    //目标邮箱
    private String target = "";
    //邮箱的密码
    private String password = "gvbmstzfgxmmcahj";
    //发送邮件的服务器地址
    private String host = "smtp.qq.com";

    //发送的内容
    private String info = "";

    public sendEmail(String target) {
        this.target = target;
    }

    public void addInfo(String info){
        this.info = info;
    }

    //重写run方法的实现，在run方法中发送邮件给指定用户
    public void run() {
        try {
            Properties prop = new Properties();
            prop.setProperty("mail.host", "smtp.qq.com");//设置QQ邮件服务器
            prop.setProperty("mail.transport.protocol", "smtp");//邮件发送协议
            prop.setProperty("mail.smtp.auth", "true");//需要验证用户密码

            //关于QQ邮箱，还要设置SSL加密，加上以下代码即可，其他邮箱不需要
            MailSSLSocketFactory sf = null;

            sf = new MailSSLSocketFactory();

            sf.setTrustAllHosts(true);
            prop.put("mail.smtp.ssl.enable", "true");
            prop.put("mail.smtp.ssl.socketFactory", sf);

            //使用JavaMail发送邮件的五个步骤

            //1. 创建定义整个应用程序所需的环境信息的Session对象
            //QQ才有！其他邮箱就不用
            Session session = Session.getDefaultInstance(prop, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    //发件人邮件用户名、授权码
                    return new PasswordAuthentication(from, password);
                }
            });
            //开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
            session.setDebug(true);

            //2. 通过Session得到transport对象
            Transport ts = session.getTransport();

            //3. 使用邮箱的用户名和授权码连上邮件服务器
            ts.connect(host, from, password);

            //4. 创建邮件：写邮件
            //注意需要传递Session
            MimeMessage message = new MimeMessage(session);
            //指明邮件的发件人
            message.setFrom(new InternetAddress(from));
            //指明邮件的收件人，现在发件人和收件人是一样的，那家就是自己给自己发
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(target));
            //邮件的标题
            message.setSubject("用户注册邮件");

            message.setContent(info, "text/html;charset=UTF-8");
            message.saveChanges();

            //发送邮件
            ts.sendMessage(message, message.getAllRecipients());
            ts.close();

        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}