package org.dobex.sound_crafters.provider;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import org.dobex.sound_crafters.mail.Mailable;
import org.dobex.sound_crafters.util.EnvVariables;

import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class MailServiceProvider {
    private ThreadPoolExecutor executor;
    private Authenticator authenticator;
    private static MailServiceProvider mailServiceProvider;
    private final BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue<>();
    private final Properties properties = new Properties();

    private MailServiceProvider() {
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls", true);
        properties.put("mail.smtp.host", EnvVariables.getProperty("mail.host"));
        properties.put("mail.smtp.port", EnvVariables.getProperty("mail.port"));
    }

    public static MailServiceProvider getInstance() {
        if (mailServiceProvider == null) mailServiceProvider = new MailServiceProvider();
        return mailServiceProvider;
    }

    public void start() {
        authenticator = new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EnvVariables.getProperty("mail.username"), EnvVariables.getProperty("mail.password"));
            }
        };
        executor = new ThreadPoolExecutor(2, 5, 5,
                TimeUnit.SECONDS, blockingQueue, new ThreadPoolExecutor.AbortPolicy());
        executor.prestartCoreThread();
        System.out.println("Starting Mail Service provider...");
    }

    public Properties getProperties() {
        return properties;
    }

    public void shutDown() {
        if (executor != null) executor.shutdown();
    }

    public Authenticator getAuthenticator() {
        return authenticator;
    }

    public void sendMail(Mailable mailable) {
        blockingQueue.offer(mailable);
    }
}
