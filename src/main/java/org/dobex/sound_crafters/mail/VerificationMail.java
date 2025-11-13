package org.dobex.sound_crafters.mail;

import io.rocketbase.mail.EmailTemplateBuilder;
import io.rocketbase.mail.model.HtmlTextEmail;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import org.dobex.sound_crafters.util.EnvVariables;

public class VerificationMail extends Mailable {

    private final String to;
    private final String customerName;
    private final String verificationCode;

    public VerificationMail(String to, String verificationCode, String customerName) {
        this.to = to;
        this.verificationCode = verificationCode;
        this.customerName = customerName;
    }

    @Override
    public void build(Message message) throws MessagingException {
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
        message.setSubject("Sound Crafters Account Verification");
        io.rocketbase.mail.model.HtmlTextEmail htmlTextEmail = getEmailTemplateConfigBuilder()
                .header()
                .logo("https://i.ibb.co/Q3FwMzMw/Text-Logo.jpg").logoHeight(40).and()
                .text("Hi, " + customerName).h3().and()
                .text("Your account, " + to).and()
                .text("Welcome to Sound Crafters! We're excited to have you join our community of audio enthusiasts.").and()
                .text("To complete your registration and start exploring our collection of premium audio equipment, please verify your email address by entering the code below:").and()
                .text("Verification Code").h2().bold().and()
                .attribute()
                .keyValue("YOUR CODE ", verificationCode).and()
                .text("If you didn't create an account with Sound Crafters, please ignore this email or contact our support team.").and()
                .text("Cheers,\n" + "The Sound Crafters Support Team").and()
                .text("Need Help? \n"
                        + "Email:" + EnvVariables.getProperty("app.support.mail") + "\n"
                        + "Phone:" + "+94 71 987 7812").and()
                .copyright("Sound Crafters").url("http://localhost:5173/").suffix(". All rights reserved.").and()
                .footerText("Sound Crafters \n" + "Colombo Street Rd.\n" + "Suite 1234")
                .build();
        message.setContent(htmlTextEmail.getHtml(), "text/html; charset=utf-8");
    }
}
