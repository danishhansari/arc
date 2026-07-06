package com.arc.service.impl;

import com.arc.dto.EmailDTO;
import com.arc.dto.InvitationWorkspaceDTO;
import com.arc.service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender javaMailSender;

    public void sendVerificationMail(EmailDTO user) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

        mimeMessageHelper.setTo(user.getEmail());
        mimeMessageHelper.setSubject("Login for Arc");

        String html = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                </head>
                <body style="margin:0;padding:0;background-color:#f5f5f5;font-family:-apple-system,BlinkMacSystemFont,'Segoe UI',Roboto,sans-serif;">
                
                    <table width="100%%" cellpadding="0" cellspacing="0" style="background-color:#f5f5f5;padding:40px 0;">
                        <tr>
                            <td align="center">
                                <table width="600" cellpadding="0" cellspacing="0"
                                       style="background:#ffffff;border-radius:12px;padding:40px;">
                                    <tr>
                                        <td>
                                            <h2 style="margin:0;color:#111827;">
                                                Arc
                                            </h2>
                
                                            <p style="margin-top:30px;font-size:24px;font-weight:500;color:#111827;">
                                                Your login code for ARC
                                            </p>
                
                                            <p style="font-size:14px;color:#4b5563;line-height:1.6;">
                                                Login to Arc by clicking the button below.
                                            </p>
                
                                            <div style="text-align:center;margin:30px 0;">
                                                <a href="%s"
                                                   style="
                                                        background:#111827;
                                                        color:white;
                                                        text-decoration:none;
                                                        padding:14px 28px;
                                                        border-radius:8px;
                                                        display:inline-block;
                                                        font-weight:600;
                                                   ">
                                                    Login to Arc
                                                </a>
                                            </div>
                
                                            <p style="font-size:14px;color:#6b7280;">
                                                This link and code will only be valid for the next 5 minutes.
                                                If the button does not work, you can use the verification code directly:
                                            </p>
                
                                            <div style="
                                                background:#f9fafb;
                                                border:1px solid #e5e7eb;
                                                border-radius:8px;
                                                padding:20px;
                                                text-align:center;
                                                margin:25px 0;">
                                                <span style="
                                                    font-size:28px;
                                                    font-weight:700;
                                                    letter-spacing:6px;
                                                    color:#111827;">
                                                    %s
                                                </span>
                                            </div>
                
                                            <hr style="border:none;border-top:1px solid #e5e7eb;margin:30px 0;">
                
                                            <p style="font-size:12px;color:#9ca3af;">
                                                If you didn't request this email, you can safely ignore it.
                                            </p>
                
                                            <p style="font-size:12px;color:#9ca3af;margin-top:24px;">
                                                Arc Technologies
                                            </p>
                
                                        </td>
                                    </tr>
                                </table>
                
                            </td>
                        </tr>
                    </table>
                
                </body>
                </html>
                """.formatted("google.com", user.getCode());

        mimeMessageHelper.setText(html, true);

        javaMailSender.send(mimeMessage);
    }

    public void sendInvitationEmails(InvitationWorkspaceDTO invitationWorkspaceDTO) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
                true, "UTF-8");

        mimeMessageHelper.setBcc(invitationWorkspaceDTO.getEmails().toArray(new String[0]));
        mimeMessageHelper.setSubject("You're invited to join " + invitationWorkspaceDTO.getWorkspaceName() + " on Arc");

        String html = """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
        </head>
        <body style="margin:0;padding:0;background:#f5f5f5;font-family:-apple-system,BlinkMacSystemFont,'Segoe UI',Roboto,sans-serif;">

        <table width="100%%" cellpadding="0" cellspacing="0" style="padding:40px 0;background:#f5f5f5;">
            <tr>
                <td align="center">

                    <table width="600" cellpadding="0" cellspacing="0"
                           style="background:#ffffff;border-radius:12px;padding:40px;">

                        <tr>
                            <td>

                                <h2 style="margin:0;color:#111827;">Arc</h2>

                                <h1 style="margin:30px 0 10px;font-size:28px;color:#111827;">
                                    You're invited to collaborate
                                </h1>

                                <p style="font-size:15px;color:#4b5563;line-height:1.7;">
                                    <strong>%s</strong> has invited you to join the
                                    <strong>%s</strong> workspace on Arc.
                                </p>

                                <p style="font-size:15px;color:#4b5563;line-height:1.7;">
                                    Sign in or create your Arc account to view and accept
                                    your workspace invitation.
                                </p>

                                <div style="text-align:center;margin:35px 0;">
                                    <a href="%s"
                                       style="
                                            background:#111827;
                                            color:#ffffff;
                                            text-decoration:none;
                                            padding:14px 28px;
                                            border-radius:8px;
                                            display:inline-block;
                                            font-weight:600;">
                                        Open Arc
                                    </a>
                                </div>

                                <div style="background:#f9fafb;border:1px solid #e5e7eb;border-radius:8px;padding:18px;margin-top:25px;">
                                    <p style="margin:0;color:#374151;font-size:14px;">
                                        <strong>What happens next?</strong>
                                    </p>

                                    <ul style="color:#4b5563;font-size:14px;line-height:1.8;padding-left:20px;margin:12px 0 0;">
                                        <li>Click <strong>Open Arc</strong>.</li>
                                        <li>Sign in or create your account.</li>
                                        <li>Your pending workspace invitation will automatically appear.</li>
                                        <li>Accept the invitation and start collaborating.</li>
                                    </ul>
                                </div>

                                <hr style="border:none;border-top:1px solid #e5e7eb;margin:35px 0;">

                                <p style="font-size:12px;color:#9ca3af;">
                                    If you weren't expecting this invitation, you can safely ignore this email.
                                </p>

                                <p style="font-size:12px;color:#9ca3af;margin-top:20px;">
                                    Arc Technologies
                                </p>

                            </td>
                        </tr>

                    </table>

                </td>
            </tr>
        </table>

        </body>
        </html>
        """
                .formatted(
                        invitationWorkspaceDTO.getInviterName(),
                        invitationWorkspaceDTO.getWorkspaceName(),
                        "http://localhost:3000/login"
                );

        mimeMessageHelper.setText(html, true);
        javaMailSender.send(mimeMessage);
    }
}
