package com.fuel.notificationsservice;

import java.time.LocalDateTime;

public class NotificationModel {
    private String message;
    private String priority;
    private LocalDateTime createdTime;
    private boolean isRead;

    public NotificationModel(String message, String priority) {
        this.message = message;
        this.priority = priority;
        this.createdTime = LocalDateTime.now();
        this.isRead = false;
    }

    public String getMessage() { return message; }
    public String getPriority() { return priority; }
    public LocalDateTime getCreatedTime() { return createdTime; }
    public boolean isRead() { return isRead; }
    public void markAsRead() { this.isRead = true; }

    @Override
    public String toString() {
        return "[Priority: " + priority + "] " + message + " | Created: " + createdTime + " | Read: " + isRead;
    }
}
