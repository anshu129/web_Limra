package com.limrainfracon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.limrainfracon.model.PlotSale;

@Repository
public interface PlotSaleRepository extends JpaRepository<PlotSale, String> {

}
