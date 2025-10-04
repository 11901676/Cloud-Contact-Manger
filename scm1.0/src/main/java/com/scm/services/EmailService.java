package com.scm.services;

public interface EmailService {
    void sendEmail(String to, String subject, String body);

    void sendEmailWithHtml();

    void sendEmailWithAttachment();

    void sendVerificationEmail(String to, String verificationLink);
}
