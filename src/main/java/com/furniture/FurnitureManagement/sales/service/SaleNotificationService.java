package com.furniture.FurnitureManagement.sales.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.furniture.FurnitureManagement.sales.dto.ShowroomSaleProductResponse;
import com.furniture.FurnitureManagement.sales.dto.ShowroomSaleResponse;

/**
 * Sends an email to the admin whenever a new showroom sale is created, so
 * they don't have to keep opening the app to check.
 *
 * Uses Resend's HTTP API (not raw SMTP) - Render's free tier blocks
 * outbound traffic to SMTP ports (25/465/587) to prevent spam abuse, so a
 * direct Gmail SMTP connection can never succeed there. A plain HTTPS API
 * call on port 443 isn't affected by that block.
 *
 * This is fire-and-forget on a background thread (@Async) - if sending
 * fails (bad API key, Resend hiccup, etc.) it only logs a warning and
 * never affects the actual sale creation, which has already succeeded by
 * the time this runs.
 */
@Service
public class SaleNotificationService {

    private static final Logger log =
            LoggerFactory.getLogger(SaleNotificationService.class);

    private static final DateTimeFormatter DATE_TIME_FORMAT =
            DateTimeFormatter.ofPattern("dd MMM yyyy, hh:mm a");

    private static final String RESEND_API_URL = "https://api.resend.com/emails";

    private final HttpClient httpClient =
            HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(8))
                    .build();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${app.notifications.admin-email}")
    private String adminEmail;

    @Value("${app.notifications.enabled}")
    private boolean notificationsEnabled;

    @Value("${resend.api-key:}")
    private String resendApiKey;

    @Value("${resend.from-email}")
    private String fromEmail;

    @Async
    public void notifyNewSale(ShowroomSaleResponse sale) {

        if (!notificationsEnabled) {
            return;
        }

        if (resendApiKey == null || resendApiKey.isBlank()
                || adminEmail == null || adminEmail.isBlank()) {

            log.warn(
                    "Skipping sale-notification email: Resend API key or "
                    + "admin email is not configured.");
            return;
        }

        try {

            Map<String, Object> payload = Map.of(
                    "from", fromEmail,
                    "to", List.of(adminEmail),
                    "subject", buildSubject(sale),
                    "text", buildBody(sale));

            String jsonBody = objectMapper.writeValueAsString(payload);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(RESEND_API_URL))
                    .timeout(Duration.ofSeconds(8))
                    .header("Authorization", "Bearer " + resendApiKey)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 200 && response.statusCode() < 300) {

                log.info(
                        "Sent new-sale notification email for sale #{} to {}",
                        sale.getId(),
                        adminEmail);

            } else {

                log.warn(
                        "Resend rejected the notification email for sale #{}: "
                        + "HTTP {} - {}",
                        sale.getId(),
                        response.statusCode(),
                        response.body());
            }

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