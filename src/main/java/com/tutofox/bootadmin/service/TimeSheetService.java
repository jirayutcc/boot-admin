package com.tutofox.bootadmin.service;

import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Date;

import com.tutofox.bootadmin.model.TimeSheet;
import com.tutofox.bootadmin.repository.TimeSheetRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class TimeSheetService {

    private TimeSheetRepository timeSheetRepository;

    @Autowired
    public TimeSheetService(TimeSheetRepository timeSheetRepository) {
        this.timeSheetRepository = timeSheetRepository;
    }

    public TimeSheet findTimeSheetById(int timeSheet) {
        return timeSheetRepository.findById(timeSheet);
    }
    
    public Page<TimeSheet> listAll(int pageNum) {
        int pageSize = 10;
        
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        
        return timeSheetRepository.findAll(pageable);
    }

    public TimeSheet saveTimesheet(TimeSheet timesheet) {
        return timeSheetRepository.save(timesheet);
    }

}
