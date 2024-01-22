
package com.limrainfracon.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@Table(name = "project")
@EqualsAndHashCode(callSuper=false)
public class Project  {
	@Id
	@GeneratedValue(generator = "customPrimaryKeyGenerator")
	@GenericGenerator(name = "customPrimaryKeyGenerator", strategy = "com.limrainfracon.primaykey.generator.ProjectPrimaryKeyGenerator")
	@Column(length = 191)
	private String projectId;
	@NotNull(message = " Project Name cannot be null.")
	@Size(min = 3, max = 20, message = "Project Name must be between 3 and 20 characters.")
	private String projectName;
	@NotNull(message = "Project State cannot be null.")
	@Size(min = 3, max = 20, message = "Project State must be between 3 and 20 characters.")
	private String projectState;
	@NotNull(message = "Project City cannot be null.")
	@Size(min = 3, max = 20, message = "Project City must be between 3 and 20 characters.")
	private String projectCity;
	private Boolean isProjectActive;
	@NotNull(message = "Project Start Date  cannot be null")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate projectStartDate;	
	@OneToMany(mappedBy = "project")
	@JsonManagedReference
	@EqualsAndHashCode.Exclude
	@JsonIgnore
	@ToString.Exclude
	private Set<Plot> plots = new HashSet<>();
	@EqualsAndHashCode.Exclude
	@JsonIgnore
	@ToString.Exclude
	@OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Phase> phases;

}
