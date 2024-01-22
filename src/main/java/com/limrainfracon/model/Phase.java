package com.limrainfracon.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Table(name="phase")
@Data
public class Phase {

	@Id
	@GeneratedValue(generator = "customPrimaryKeyGenerator")
	@GenericGenerator(name = "customPrimaryKeyGenerator", strategy = "com.limrainfracon.primaykey.generator.PhasePrimaryKeyGenerator")
	@Column(length = 191)
    private String phaseId;
	@NotNull(message = " Phase Name cannot be null.")
	@Size(min = 3, max = 20, message = "Phase Name must be between 3 and 20 characters.")
    private String phaseName;
    private Boolean isPhaseActive;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    @NotNull(message = "Number of plots cannot be null")
    @Min(value = 1, message = "Number of plots must be greater than 0")
    private int numberOfPlots;
    @EqualsAndHashCode.Exclude
	@JsonIgnore
	@ToString.Exclude
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plot> plots = new ArrayList<>();
    @EqualsAndHashCode.Exclude
	@JsonIgnore
	@ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    @OneToMany(mappedBy = "phase", cascade = CascadeType.ALL, orphanRemoval = true)
    @EqualsAndHashCode.Exclude
   	@JsonIgnore
   	@ToString.Exclude
    private List<PlotSale> plotSales = new ArrayList<>();
   
}
