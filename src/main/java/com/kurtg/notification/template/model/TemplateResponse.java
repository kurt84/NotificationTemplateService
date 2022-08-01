package com.kurtg.notification.template.model;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TemplateResponse {
    private String status;
    private String statusDescription;
    private String emailContent;
    private String smsContent;
    private String emailSubject;

    public TemplateResponse(String status, String statusDescription, String emailContent, String smsContent, String emailSubject) {
        this.status = status;
        this.statusDescription = statusDescription;
        this.emailContent = emailContent;
        this.smsContent = smsContent;
        this.emailSubject = emailSubject;
    }
}
