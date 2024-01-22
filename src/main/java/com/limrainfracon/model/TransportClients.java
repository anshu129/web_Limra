package com.limrainfracon.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.data.annotation.CreatedDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="transport_clients")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransportClients {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	//@GenericGenerator(name = "customPrimaryKeyGenerator", strategy = "com.limrainfracon.primaykey.generator.TransportClientsPKGenerator")
	private long transClientId;
	
	@Column(name="name")
	private String name;
	
	@Column(name="mobile_number")
	private String mobileNumber;
	
	@Column(name="pickup_location")
	private String location;
		
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_date", nullable = false, updatable = false)
	@CreatedDate
	private Date createDate;
}
