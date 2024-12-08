package com.rudbmsgroupproject.project_2.controller;

import com.rudbmsgroupproject.project_2.service.MsamdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/msamd")
public class MsamdController {

    @Autowired
    private MsamdService msamdService;

    @GetMapping("/compare")
    public boolean compareUserValue(@RequestParam String value) {
        return msamdService.compareUserValue(value);
    }
}