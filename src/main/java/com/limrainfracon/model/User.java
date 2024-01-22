package com.limrainfracon.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "user")
@Data
@EntityListeners(AuditingEntityListener.class)

public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column( nullable = false)
	@NotNull(message = "Please enter a referral ID.")
	@Size(min = 8, max = 8, message = "Please enter a valid referral ID.")
	private String referalId;
	@Column( nullable = false)
	@NotNull(message = "Contact number cannot be null.")
	@Pattern(regexp="^[0-9]{10}$", message="Contact number must be exactly 10 digits.")
	private String contactNumber;
	@Column( nullable = false)
	@NotNull(message = "Please enter an email.")
	@Email(message = "Please enter a valid email address.")
	private String email;
	@Column(name = "password", nullable = false)
	@NotNull(message = "Please enter a password.")
	//@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$", message = "Password must be at least 8 characters and include one uppercase letter, one lowercase letter, one digit, and one special character.")
	private String password;
	@Column(name = "name", nullable = false)
	@NotNull(message = "Name cannot be null.")
	@Size(min = 3, max = 20, message = "Name must be between 3 and 20 characters.")
	private String name;
	@Transient
	private String confirmPassword;
	@Column( nullable = false)
	private boolean isActive;
	@Column( nullable = false)
	private String loginId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false, updatable = false)
	@CreatedDate
	private Date createdDate;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated_date", nullable = false)
	@LastModifiedDate
	private Date updatedDate;
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
		name = "user_roles",
		joinColumns = @JoinColumn(name = "user_id"),
		inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles = new HashSet<>();
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private Set<PlotSale> plotSales = new HashSet<>();
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@JsonIgnore
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Wallet wallet;

}
