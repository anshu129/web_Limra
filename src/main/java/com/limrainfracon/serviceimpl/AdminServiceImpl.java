package com.limrainfracon.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.limrainfracon.model.TransportDetails;
import com.limrainfracon.repository.TransportRepository;
import com.limrainfracon.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {
	
	@Autowired
	private TransportRepository transportRepository;

	@Override
	public List<TransportDetails> getAllTransportDetails() {

		List<TransportDetails> transportDetails = transportRepository.findAll();
		return transportDetails;
	}

	@Override
	public void updateTransportStatus(String id, String status) {
		
		transportRepository.updateTransportStatus(id, status);
		
	}

}
