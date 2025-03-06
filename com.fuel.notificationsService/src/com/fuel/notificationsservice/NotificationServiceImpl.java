package com.fuel.notificationsservice;

import java.util.ArrayList;
import java.util.List;

public class NotificationServiceImpl implements INotificationService {
    private final List<NotificationModel> notifications = new ArrayList<>();

    @Override
    public void addNotification(String message, String priority) {
        notifications.add(new NotificationModel(message, priority));
        System.out.println("📢 New Notification: " + message);
    }

    @Override
    public List<NotificationModel> getNotifications() {
        return notifications;
    }

    @Override
    public void markNotificationAsRead(int index) {
        if (index >= 0 && index < notifications.size()) {
            notifications.get(index).markAsRead();
            System.out.println("✅ Notification marked as read.");
        } else {
            System.out.println("⚠️ Invalid notification index.");
        }
    }
}
