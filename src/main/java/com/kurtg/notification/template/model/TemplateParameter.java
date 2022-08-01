package com.kurtg.notification.template.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TemplateParameter {
    private String notificationParameterName;
    private String notificationParameterValue;
}
