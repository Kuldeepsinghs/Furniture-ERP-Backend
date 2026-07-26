package com.furniture.FurnitureManagement.sales.service;

import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.furniture.FurnitureManagement.sales.dto.ShowroomSaleProductResponse;
import com.furniture.FurnitureManagement.sales.dto.ShowroomSaleResponse;

/**
 * Sends an email to the admin whenever a new showroom sale is created, so
 * they don't have to keep opening the app to check. This is fire-and-forget
 * on a background thread (@Async) - if sending fails (bad credentials,
 * Gmail hiccup, etc.) it only logs a warning and never affects the actual
 * sale creation, which has already succeeded by the time this runs.
 */
@Service
public class SaleNotificationService {

    private static final Logger log =
            LoggerFactory.getLogger(SaleNotificationService.class);

    private static final DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

    private final JavaMailSender mailSender;

    @Value("${app.notifications.admin-email}")
    private String adminEmail;

    @Value("${app.notifications.enabled}")
    private boolean notificationsEnabled;

    @Value("${spring.mail.username}")
    private String fromAddress;

    public SaleNotificationService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void notifyNewSale(ShowroomSaleResponse sale) {

        if (!notificationsEnabled) {
            return;
        }

        if (adminEmail == null || adminEmail.isBlank()
                || fromAddress == null || fromAddress.isBlank()) {

            log.warn(
                    "Skipping sale-notification email: admin email or "
                    + "sender Gmail credentials are not configured.");
            return;
        }

        try {

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromAddress);
            message.setTo(adminEmail);
            message.setSubject(buildSubject(sale));
            message.setText(buildBody(sale));

            mailSender.send(message);

            log.info(
                    "Sent new-sale notification email for sale #{} to {}",
                    sale.getId(),
                    adminEmail);

        } catch (Exception ex) {

            // Never let a failed email break anything - the sale itself
            // was already saved successfully before this method was called.
            log.warn(
                    "Failed to send new-sale notification email for sale #{}: {}",
                    sale.getId(),
                    ex.getMessage());
        }
    }

    private String buildSubject(ShowroomSaleResponse sale) {

        return "New Sale: ₹" + sale.getTotalAmount()
                + " - " + sale.getCustomerName();
    }

    private String buildBody(ShowroomSaleResponse sale) {

        StringBuilder body = new StringBuilder();

        body.append("A new showroom sale has been recorded.\n\n");
        body.append("Sale ID: #").append(sale.getId()).append("\n");
        body.append("Customer: ").append(sale.getCustomerName()).append("\n");

        if (sale.getCustomerPhone() != null) {
            body.append("Phone: ").append(sale.getCustomerPhone()).append("\n");
        }

        body.append("Location: ").append(sale.getLocation()).append("\n");
        body.append("Date & Time: ")
                .append(sale.getSaleDateTime() != null
                        ? sale.getSaleDateTime().format(DATE_TIME_FORMAT)
                        : "N/A")
                .append("\n");
        body.append("Recorded by: ")
                .append(sale.getCreatedBy() != null ? sale.getCreatedBy() : "N/A")
                .append("\n\n");

        body.append("Products:\n");

        if (sale.getProducts() != null) {

            for (ShowroomSaleProductResponse product : sale.getProducts()) {

                body.append("- ")
                        .append(product.getProductName())
                        .append(" (").append(product.getCategory()).append(")")
                        .append(" | Qty: ").append(product.getQuantity())
                        .append(" | Price: ₹").append(product.getPrice())
                        .append(" | Line Total: ₹").append(product.getLineTotal())
                        .append("\n");
            }
        }

        if (sale.getRemarks() != null && !sale.getRemarks().isBlank()) {
            body.append("\nRemarks: ").append(sale.getRemarks()).append("\n");
        }

        body.append("\nTotal Amount: ₹").append(sale.getTotalAmount()).append("\n");

        return body.toString();
    }
}