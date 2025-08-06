package com.alrussy_dev.notification_service.service;

import com.alrussy_dev.notification_service.ApplicationProperties;
import com.alrussy_dev.notification_service.entity.EventType;
import com.alrussy_dev.notification_service.entity.NotificationOrderState;
import com.alrussy_dev.notification_service.entity.SummaryOrder;
import com.alrussy_dev.notification_service.entity.UserInfo;
import com.alrussy_dev.notification_service.repository.NotificationOrderStateRepository;
import jakarta.mail.internet.MimeMessage;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotifiOrderStateService {

    private final NotificationOrderStateRepository repository;
    private final ApplicationProperties properties;

    @Autowired
    JavaMailSender mailSender;

    public void sendAndSaveOrderState(SummaryOrder order) {
        UserInfo userInfo = getUserInfo(order.userId());
        String content = setContentNotificationOrderState(userInfo.getFullname(), order.orderNumber(), order.state());
        var notifi = NotificationOrderState.builder()
                .orderNumper(order.orderNumber())
                .userInfo(userInfo)
                .orderState(order.state())
                .content(content)
                .build();
        sendAndSave(notifi);
    }

    public String reSend() {

        List<NotificationOrderState> notifications = findAllIsNotSent();
        if (notifications == null || notifications.isEmpty()) {
            return "not found elements";
        } else {
            notifications.stream().forEach(notifi -> sendAndSave(notifi));
            return "send successfully";
        }
    }

    public void save(NotificationOrderState notifi) {
        repository.save(notifi);
    }

    public void sendAndSave(NotificationOrderState notifi) {
        log.info("\n{}", notifi.getContent());
        if (sendEmail(
                notifi.getUserInfo().getEmail(),
                "Order " + notifi.getOrderState().toString().toLowerCase() + " notification",
                notifi.getContent())) {
            notifi.setIsSent(true);
            notifi.setSendDateAt(Instant.now());
        } else {
            notifi.setIsSent(false);
            log.warn("Error while sending email ");
        }
        ;

        save(notifi);
    }

    private boolean sendEmail(String to, String subject, String content) {
        log.info("statr sending...");
        var isSent = false;
        try {

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);

            helper.setFrom(properties.supportEmail());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content);
            mailSender.send(mimeMessage);
            log.info("Email sent to {}", to);
            isSent = true;

        } catch (Exception e) {
            isSent = false;
        }
        return isSent;
    }

    private String setContentNotificationOrderState(String fullname, String orderNumber, EventType orderState) {
        return """
				==============================================================
				%s successfully
				--------------------------------------------------------------
				Dear %s ,
				Your order with number: %s has been %s successfully
				
				Thanks
				Ecommerce App Team
				==============================================================
							"""
                .formatted(
                        orderState.toString().toLowerCase(),
                        fullname,
                        orderNumber,
                        orderState.toString().toLowerCase());
    }

    private UserInfo getUserInfo(String userId) {
        return UserInfo.builder()
                .userId(userId)
                .fullname("Ahmed Alrussy")
                .email("a_alrussy@alrussydev.con")
                .build();
    }

    public List<NotificationOrderState> findAll() {
        return repository.findAll();
    }

    public List<NotificationOrderState> findAllIsNotSent() {
        return repository.findByIsSent(false);
    }

    public List<NotificationOrderState> findByUserId(String userId) {
        return repository.findByUserInfoUserId(userId);
    }
}
