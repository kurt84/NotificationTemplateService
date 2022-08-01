package com.kurtg.notification.template.services;

import com.kurtg.notification.template.model.TemplateParameter;
import com.kurtg.notification.template.model.TemplateRequest;
import com.kurtg.notification.template.model.TemplateResponse;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TemplateService {

    @Autowired
    private SpringTemplateEngine t;

    private String success = "SUCCESS";
    private String successMsg = "Successfully merged the template with the template parameters";
    private String error = "ERROR";
    private String errorMsg = "An error occurred";
    private String subject = "Subject Placeholder";

    private TemplateResponse mapRequest2Response(TemplateRequest req){
        TemplateResponse res = null;

        if(req.getNotificationMode().equals("EMAIL")){
            res = buildEmailResponse(req);
        } else if(req.getNotificationMode().equals("SMS")){
            res = buildSMSResponse(req);
        } else {
            res = getFailureResponse();
        }

        return res;
    }

    private Map<String, Object> getParameterMap(List<TemplateParameter> params){
        return params.stream().collect(
                Collectors.toMap(TemplateParameter::getNotificationParameterName, TemplateParameter::getNotificationParameterValue)
            );
    }

    private String processTemplate(String template, Map<String, Object> params){
        Context context = new Context();
        context.setVariables(params);
        return t.process(template, context);
    }

    private TemplateResponse buildEmailResponse(TemplateRequest req) {
//        TemplateEngine t = new TemplateEngine();
        TemplateResponse res = null;
        File emailTemplateFile = new File("./src/main/resources/templates/email/"+req.getNotificationTemplateName() + ".html");
//        Map<String, Object> params = getParameterMap(req.getNotificationParameters());

        if(emailTemplateFile.exists()) {
            String content = processTemplate("./email/" + req.getNotificationTemplateName() + ".html",getParameterMap(req.getNotificationParameters()));
            res = new TemplateResponse(success, successMsg, content, null, req.getNotificationTemplateName());
        } else {
            res = getFailureResponse();
        }

        return res;
    }

    private TemplateResponse buildSMSResponse(TemplateRequest req){
        TemplateResponse res = null;
        Map<String, Object> params = getParameterMap(req.getNotificationParameters());
        String template = "";

        if(req.getNotificationTemplateName().equalsIgnoreCase("ViewBalance")){
            template = getBalanceSMSTemplate();
        } else if(req.getNotificationTemplateName().equalsIgnoreCase("PhoneNumberChanged")){
            template = getPhoneNumberChanged();
        }

        if(template.isEmpty()){
            res = getFailureResponse();
        } else {
            String content = StringSubstitutor.replace(template,getParameterMap(req.getNotificationParameters()));
            res = new TemplateResponse(success, successMsg, null, content, null);
        }

        return res;
    }

    public static String getBalanceSMSTemplate() {
        return "Hello ${name}!\nYour current balance is ${balance}\nThanks.";
    }

    private String getPhoneNumberChanged() {
        return "Hello ${name}!\nYour phone number is changed from ${oldPhoneNumber} to ${newPhoneNumber}.";
    }



    private TemplateResponse getFailureResponse(){
        return new TemplateResponse(error, errorMsg, null, null, null);
    }

    public TemplateResponse handleRequest(TemplateRequest req){
        return mapRequest2Response(req);
    }
}
