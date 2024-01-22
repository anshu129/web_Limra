package com.limrainfracon.service;

import com.limrainfracon.model.Plot;
import com.limrainfracon.model.PlotSale;

public interface PlotSaleService {

	void savePlotSale(PlotSale plotSale);

	void updateAndSavePlotDetails(Plot plot, PlotSale plotSale);

}
