package com.rudbmsgroupproject.project_2.service;

import com.rudbmsgroupproject.project_2.model.Msamd;
import com.rudbmsgroupproject.project_2.repository.MsamdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MsamdService {

    @Autowired
    private MsamdRepository msamdRepository;

    public List<Msamd> getAllMsamd() {
        return msamdRepository.findAll();
    }

    public boolean compareUserValue(String userValue) {
        List<Msamd> msamdList = getAllMsamd();
        for (Msamd msamd : msamdList) {
            if (msamd.getName().equals(userValue)) {
                return true;
            }
        }
        return false;
    }
}