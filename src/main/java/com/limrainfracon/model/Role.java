package com.limrainfracon.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "role")
@Data
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String roleName;
	private String loginId;
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	@JsonIgnore
	@ManyToMany(mappedBy = "roles")
	private Set<User> users = new HashSet<>();
}
