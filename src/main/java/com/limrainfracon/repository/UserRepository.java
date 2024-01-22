package com.limrainfracon.repository;

import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.limrainfracon.model.User;

@Repository
public interface UserRepository  extends JpaRepository<User, Long>{

	@Query(value = "SELECT * FROM user u where u.login_id = :login_id", nativeQuery = true)
	User findByLoginId(String login_id);
	
	@Query(value = "SELECT login_id FROM user ORDER BY login_id DESC LIMIT 1", nativeQuery = true)
	String findLastLoginId();

	@Query(value = "SELECT * FROM user u where u.referal_id = :loginId", nativeQuery = true)
	List<User> findAllMyUsers(String loginId);

	boolean existsByLoginId(String loginId);
	Optional<User> findByEmail(String email);
	Optional<User> findByContactNumber(String contactNumber);

	//@Query(value = "SELECT * FROM user where referal_id in (select login_id from user where referal_id=:loginId) union all select * from user where referal_id=:loginId order by login_id asc", nativeQuery = true)
	//List<User> findAllMyUserDetails(String loginId);
	@Query(value = "WITH RECURSIVE user_hierarchy AS (SELECT id, name, login_id, referal_id, contact_number, created_date, email, is_active, password, updated_date FROM user WHERE login_id = :loginId UNION ALL SELECT e.id, e.name, e.login_id, e.referal_id, e.contact_number, e.created_date, e.email, e.is_active, e.password, e.updated_date FROM user AS e INNER JOIN user_hierarchy AS eh ON eh.login_id = e.referal_id ) SELECT id, name, login_id, referal_id, contact_number, created_date, email, is_active, password, updated_date FROM user_hierarchy", nativeQuery = true)
	List<User> findAllMyUserDetails(@Param("loginId") String loginId);

	@Query(value = "WITH RECURSIVE hierarchy AS (" +
            "  SELECT login_id, referal_id, 0 AS level, CONCAT('/', login_id, '/') AS path" +
            "  FROM user WHERE login_id = ?1" +
            "  UNION ALL" +
            "  SELECT u.login_id, u.referal_id, h.level + 1, CONCAT(path, u.login_id, '/')" +
            "  FROM user u" +
            "  JOIN hierarchy h ON h.referal_id = u.login_id" +
            "  WHERE LOCATE(CONCAT('/', u.login_id, '/'), path) = 0" +
            ")" +
            "SELECT MAX(level) AS max_level FROM hierarchy", nativeQuery = true)
    Integer findHierarchyLevelFromDescendant(String loginId);

	@Query(value = "SELECT * FROM user u where u.login_id = :referal_id", nativeQuery = true)
	User findByReferralId(String referal_id);
	
	
	
	
}
