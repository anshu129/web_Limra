package com.limrainfracon.serviceimpl;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.limrainfracon.model.Plot;
import com.limrainfracon.model.PlotSale;
import com.limrainfracon.repository.PlotRepository;
import com.limrainfracon.repository.PlotSaleRepository;
import com.limrainfracon.service.PlotSaleService;

@Service
@Transactional
public class PlotSaleServiceImpl implements PlotSaleService {
	
	@Autowired
	private PlotSaleRepository plotSaleRepository;
	
	@Autowired 
	private PlotRepository plotRepository;

	@Override
	public void savePlotSale(PlotSale plotSale) {
		plotSaleRepository.save(plotSale);
	}

	@Override
	public void updateAndSavePlotDetails(Plot plot, PlotSale plotSale) {
		plot.setPlotSize(plot.getPlotSize() - plotSale.getPlotSize());
		plot.setPlotRate(plot.getPlotRate() - plotSale.getPlotRate());
		plotRepository.save(plot);
		
		
	}

}
