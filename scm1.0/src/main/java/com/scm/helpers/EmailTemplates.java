package com.scm.helpers;

public class EmailTemplates {
    
    /**
     * Generate HTML email template for account verification
     * @param userName Name of the user
     * @param verificationLink The verification URL with token
     * @return HTML string for email
     */
   
    public static String getEmailVerificationTemplate() {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <title>Verify Account</title>
            </head>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
                <div style="max-width: 600px; margin: 0 auto; background-color: white; padding: 40px; border-radius: 8px;">
                    <h1 style="color: #333; text-align: center;">Verify Your Email</h1>
                    <p style="color: #666; font-size: 16px;">Hello,</p>
                    <p style="color: #666; font-size: 16px;">
                        Thank you for registering! Please click the button below to verify your email address.
                    </p>
                    <div style="text-align: center; margin: 30px 0;">
                        <a href="{{VERIFICATION_LINK}}" style="background-color: #9933FF; color: white; padding: 15px 30px; text-decoration: none; border-radius: 5px; display: inline-block; font-size: 16px;">
                            Verify Email
                        </a>
                    </div>
                    <p style="color: #999; font-size: 14px;">
                        If the button doesn't work, copy and paste this link: <br>
                        <span style="color: #4CAF50;">{{VERIFICATION_LINK}}</span>
                    </p>
                    <hr style="border: 1px solid #eee; margin: 30px 0;">
                    <p style="color: #999; font-size: 12px;">
                        This link will expire in 24 hours. If you didn't request this, please ignore this email.
                    </p>
                </div>
            </body>
            </html>
            """;
    }
}