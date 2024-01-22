package com.limrainfracon.service;

import java.util.List;

import com.limrainfracon.model.TransportDetails;

public interface AdminService {

	List<TransportDetails> getAllTransportDetails();

	void updateTransportStatus(String id, String status);

}
