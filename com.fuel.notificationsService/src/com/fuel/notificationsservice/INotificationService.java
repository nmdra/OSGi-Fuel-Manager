package com.fuel.notificationsservice;

import java.util.List;

public interface INotificationService {
    void addNotification(String message, String priority);
    List<NotificationModel> getNotifications();
    void markNotificationAsRead(int index);
}
