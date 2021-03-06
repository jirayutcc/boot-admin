package com.tutofox.bootadmin.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "projects")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "project_id")
	private Integer id;

	@Column(name = "project_name")
	@Length(min = 3, message = "*Your project name must have at least 3 characters")
	@NotEmpty(message = "*Please provide a project name")
	private String projectName;

	@Column(name = "project_description")
	private String projectDescription;

	@Column(name = "project_leader")
	private String projectLeader;

	@Column(name = "project_process")
	private Integer projectProcess;

	@Column(name = "status")
	private String status;

}
