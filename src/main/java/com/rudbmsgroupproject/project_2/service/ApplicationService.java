package com.rudbmsgroupproject.project_2.service;

import com.rudbmsgroupproject.project_2.model.Application;
import com.rudbmsgroupproject.project_2.repository.ApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    public List<Application> getApplicationsWithActionTaken(Short actionTaken) {
        return applicationRepository.findByActionTaken(actionTaken);
    }
}