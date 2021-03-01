package com.tutofox.bootadmin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "timesheet")
public class TimeSheet {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "timesheetDate")
	private Date timesheetDate;

	@Column(name = "detail")
	private String detail;

	@Column(name = "team")
	private String team;
}
