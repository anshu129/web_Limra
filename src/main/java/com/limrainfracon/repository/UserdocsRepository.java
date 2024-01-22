package com.limrainfracon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.limrainfracon.model.Userdocs;

@Repository
public interface UserdocsRepository extends JpaRepository<Userdocs, Long>{

	@Query(value = "SELECT * FROM user_docs u where u.login_id = :loginId and u.type != 'IMG'", nativeQuery = true)
	Userdocs getByLoginId(String loginId);

}
