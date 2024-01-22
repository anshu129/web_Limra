package com.limrainfracon.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.limrainfracon.enums.Status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="transport_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class TransportDetails {
	
	@Id
	@GeneratedValue(generator = "customPrimaryKeyGenerator")
	@GenericGenerator(name = "customPrimaryKeyGenerator", strategy = "com.limrainfracon.primaykey.generator.TransportPKGenerator")
	private String transportId;
	
	@Column(name="driver_name")
	private String driverName;
	
	@Column(name="car_model")
	private String carModel;
	
	@Column(name="car_registration")
	private String carRegistration;
	
	@Column(name="visiter_slip")
	private String visitorSlip;
	
	@Column(name="associate_name")
	private String associateName;
	
	@Column(name="flc_number")
	private String flcNumber;
	
	@Column(name="associate_flc_no")
	private String associateFlcNo;
	
	@Column(name="visite_date")
	private Date visiteDate;
	
	@Column(name="visite_time")
	private String time;
	
	@Column(name="status")
	private String status;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false, updatable = false)
	@CreatedDate
	private Date createDate;
	
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name="transport_id")  
	private List<TransportClients> transportClients = new ArrayList<>();

}
