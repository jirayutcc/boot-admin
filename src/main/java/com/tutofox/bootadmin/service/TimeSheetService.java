package com.tutofox.bootadmin.service;

import com.tutofox.bootadmin.model.TimeSheet;
import com.tutofox.bootadmin.repository.TimeSheetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;

@Service
public class TimeSheetService {

    private TimeSheetRepository timeSheetRepository;

    @Autowired
    public TimeSheetService(TimeSheetRepository TimeSheetRepository) {
        this.timeSheetRepository = timeSheetRepository;
    }

    public TimeSheet findTimeSheetById(int timeSheet) {
        return timeSheetRepository.findById(timeSheet);
    }

}
