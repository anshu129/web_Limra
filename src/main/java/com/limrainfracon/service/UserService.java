package com.limrainfracon.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.limrainfracon.model.TransportDetails;
import com.limrainfracon.model.User;
import com.limrainfracon.model.Userdocs;
import com.limrainfracon.request.EmailRequest;
import com.limrainfracon.request.MyChartDetails;
import com.limrainfracon.response.CommonResponse;

public interface UserService {

	CommonResponse save(User user);

	User findUser(User user);

	Optional<User> userFindById(Long userId);

	List<User> getAllUsers();

	List<User> getAllMyUsers(String loginId);

	void sendEmailToUs(EmailRequest emailRequest);

	void saveDocsDetails(String filename, long fileSize, String filePath, String loginId, String id_Number, String type);

	void saveImage(String filename, long fileSize, String filePath, String loginId);

	boolean existsById(String id);

	User findByLoginId(String sellerId);

	Userdocs getAllDetailsById(String loginId);

	List<MyChartDetails> getAllMyUsersDetails(String loginId);

	TransportDetails saveTransportDetails(@Valid TransportDetails transportDetails);

	List<TransportDetails> getDetailsByAssociateId(String loginId);

}
