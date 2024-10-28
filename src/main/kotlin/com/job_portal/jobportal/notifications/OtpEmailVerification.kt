package com.job_portal.jobportal.notifications

import jakarta.mail.internet.MimeMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

/**
 *
 * @author Titus Murithi Bundi
 */
@Service
class OtpEmailVerification(private val mailSender: JavaMailSender) {
    fun sendVerificationEmail(to: String, otp: String) {
        val message: MimeMessage = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true)
        helper.setTo(to)
        helper.setSubject("Account Verification")
        helper.setText("Your OTP for account verification is: $otp", true)
        mailSender.send(message)
    }
}
