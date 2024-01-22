package com.limrainfracon.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.limrainfracon.model.TransportDetails;

@Repository
public interface TransportRepository extends JpaRepository<TransportDetails, String>{

	@Query(value = "SELECT * FROM transport_details u where u.associate_flc_no = :loginId", nativeQuery = true)
	List<TransportDetails> getAllTransportByAssId(String loginId);

	@Modifying
	@Query(value="update transport_details td set td.status = :status where td.transport_id = :id", nativeQuery = true)
	@Transactional
	void updateTransportStatus(@Param(value = "id") String id, @Param(value = "status") String status);

}
