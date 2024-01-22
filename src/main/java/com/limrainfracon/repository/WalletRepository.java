package com.limrainfracon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.limrainfracon.model.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, String>{
	
	@Query(value = "SELECT * FROM user_wallet w WHERE w.login_id = :login_id", nativeQuery = true)
	Wallet findWalletByLoginId(@Param("login_id") String loginId);
	
	
	

}
