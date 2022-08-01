package com.kurtg.notification.template.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class TemplateRequest {
    private List<TemplateParameter> notificationParameters;
    private String notificationTemplateName;
    private String notificationMode;
}
