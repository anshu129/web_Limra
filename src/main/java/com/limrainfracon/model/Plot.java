
  package com.limrainfracon.model;
  
  import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.limrainfracon.enums.PlotStatus;
import com.limrainfracon.enums.PlotType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
  
  @Data 
  @Entity
  @Table(name = "plot")
  @EntityListeners(AuditingEntityListener.class)
  public class Plot {
  @Id
  @GeneratedValue(generator = "customPrimaryKeyGenerator")
  @GenericGenerator(name = "customPrimaryKeyGenerator", strategy = "com.limrainfracon.primaykey.generator.PlotPrimaryKeyGenerator")
  @Column(length = 191)
  private String plotId;
  @NotNull(message = "Plot Number cannot be null.")
  @Size(min = 1, max = 20, message = "Plot Number must be between 1 and 20 characters.")
  private String plotNumber;
	/* @Range(min = 1, message = "Plot size must be greater than 0 .") */
  @NotNull(message = "Plot Size can not be null")
  private Double plotSize;
	/*
	 * @NotNull(message = " Plot Rate cannot be null.")
	 * 
	 * @Min(value = 1, message = "Plot Rate must be Greater than 0")
	 */
  @NotNull(message = "Plot Rate can not be null")
  private Double plotRate;
  private String zipCode;
  private String updatedPlotSize;
  @Enumerated(EnumType.STRING)
  @Column(name = "plot_status")
  private PlotStatus plotStatus;
  @Enumerated(EnumType.STRING)
  @Column(name = "plot_type")
  private PlotType plotType;
  private String plotSold;
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "plot_created_date", nullable = false, updatable = false)
  @CreatedDate
  @DateTimeFormat(pattern="dd-MM-yyyy HH:mm")
  private Date plotCreateddate;
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "plot_updated_date", nullable = false)
  @LastModifiedDate
  private Date plotUpdatedDate;
  @ManyToOne
  @JoinColumn(name = "project_id", nullable = false)
  @JsonBackReference
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  private Project project;
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @JsonIgnore
  @OneToMany(mappedBy = "plot")
  private Set<PlotSale> plotSales = new HashSet<>();
  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "phase_id")
  private Phase phase;

  
  }
 