package com.tutofox.bootadmin.repository;

import java.util.List;
import java.util.Optional;

import com.tutofox.bootadmin.model.TimeSheet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TimeSheetRepository extends JpaRepository<TimeSheet, Integer> {

    TimeSheet findById(int timeSheet);
}
