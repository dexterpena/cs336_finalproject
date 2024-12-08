package com.rudbmsgroupproject.project_2.controller;

import com.rudbmsgroupproject.project_2.model.Application;
import com.rudbmsgroupproject.project_2.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    // Endpoint to get all applications, default actionTaken = 1
    @GetMapping("/action-taken")
    public List<Application> getApplicationsByActionTaken(
            @RequestParam(value = "actionTaken", defaultValue = "1") Short actionTaken) {
        return applicationService.getApplicationsWithActionTaken(actionTaken);
    }
}
