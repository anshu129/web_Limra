
package com.limrainfracon.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "plot_sale")
@Data
public class PlotSale {
	@Id
	@GeneratedValue(generator = "customPrimaryKeyGenerator")
	@GenericGenerator(name = "customPrimaryKeyGenerator", strategy = "com.limrainfracon.primaykey.generator.PlotSalePrimaryKeyGenerator")
	@Column(length = 191)
	private String plotSaleId;
	@NotEmpty(message = "projectName Name cannot be null.")
	@Size(min = 1, max = 20, message = "projectName Name must be between 1 and 20 characters.")
	private String projectName;
	private String plotNumber;
	@NotNull(message = "Plot Size can not be null")
	private Double plotSize;
	@NotNull(message = "Plot Rate can not be null")
	private Double plotRate;
	private String zipCode;
	private String plotDiscription;
	@NotEmpty(message = "Client Name cannot be null.")
	@Size(min = 1, max = 20, message = "Client Name must be between 1 and 20 characters.")
	private String clientName;
	@NotEmpty(message = "Email is required")
	@Email(message = "Invalid email format")
	private String clientEmail;
	@NotEmpty(message = "Mobile number is required")
	@Pattern(regexp = "(^$|[0-9]{10})", message = "Invalid mobile number")
	private String clientPhoneNumber;
	private String sellerId;
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	@ManyToOne
	@JoinColumn(name = "plot_id", nullable = false)
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@JsonIgnore
	private Plot plot;
	@Column(name = "sale_date")
	private LocalDate saleDate;
	@ManyToOne
	@JoinColumn(name = "phase_id")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@JsonIgnore
	private Phase phase;
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "wallet_id")
	private Wallet wallet;

	@Transient
	private boolean sellerIdCheckBox;

}
