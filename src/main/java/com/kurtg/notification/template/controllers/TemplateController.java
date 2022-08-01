package com.kurtg.notification.template.controllers;

import com.kurtg.notification.template.model.TemplateRequest;
import com.kurtg.notification.template.model.TemplateResponse;
import com.kurtg.notification.template.services.TemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path="/api/notifications/templates")
@Api("Templates")
public class TemplateController {

    @Autowired
    private TemplateService serv;

    @PostMapping
    @ApiOperation(value="Get a template populated with the provided parameters", response = TemplateResponse.class)
    public TemplateResponse post(@RequestBody TemplateRequest req, Model model){
        return serv.handleRequest(req);
    }

}
