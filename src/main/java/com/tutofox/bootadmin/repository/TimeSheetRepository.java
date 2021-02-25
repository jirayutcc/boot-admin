package com.tutofox.bootadmin.repository;

import java.util.List;
import java.util.Optional;

import com.tutofox.bootadmin.model.TimeSheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeSheetRepository extends JpaRepository<TimeSheet, Integer> {

    List<TimeSheet> findAll();
    TimeSheet findById(int timeSheet);

}
