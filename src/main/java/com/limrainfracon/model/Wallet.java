package com.limrainfracon.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name = "user_wallet")
@Data
@EntityListeners(AuditingEntityListener.class)
public class Wallet {
	@Id
	@GeneratedValue(generator = "customPrimaryKeyGenerator")
	@GenericGenerator(name = "customPrimaryKeyGenerator", strategy = "com.limrainfracon.primaykey.generator.WalletPrimaryKeyGenerator")
	@Column(length = 191)
	private String walletId;
	private Double directSaleBalance;
	private String loginId;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, updatable = false)
	@CreatedDate
	@DateTimeFormat(pattern="dd-MM-yyyy HH:mm")
	private Date balanceUpdatedDate;
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	@JsonIgnore
	@OneToMany(mappedBy = "wallet", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<PlotSale> plotSales = new ArrayList<>();
	

}
