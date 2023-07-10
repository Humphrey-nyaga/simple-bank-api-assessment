package com.example.simplebankapi.notifications;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

@Entity
public class Notification {
    @Id
    private Long  id;
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;



}
