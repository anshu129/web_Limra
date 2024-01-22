package com.limrainfracon.config;
import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.limrainfracon.model.Role;
import com.limrainfracon.model.User;
import com.limrainfracon.model.Wallet;
import com.limrainfracon.repository.RoleRepository;
import com.limrainfracon.repository.UserRepository;
import com.limrainfracon.repository.WalletRepository;

@Service
public class DatabaseInitializerService {

	 private final RoleRepository roleRepository;
	    private final PasswordEncoder passwordEncoder;
	    private final UserRepository userRepository;
	    private final WalletRepository walletRepository;

	    @Autowired
	    public DatabaseInitializerService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, WalletRepository walletRepository) {
	        this.userRepository = userRepository;
	        this.roleRepository = roleRepository;
	        this.passwordEncoder = passwordEncoder;
	        this.walletRepository = walletRepository;
	    }


	    @PostConstruct
	    @Transactional
	    public void initializeDatabase() {
	        if (userRepository.count() == 0) {
	            User user = new User();
	            user.setReferalId("LD111111");
	            user.setContactNumber("1234567890");
	            user.setEmail("limra.admin@limrainfracon.com");
	            // Encrypt the password before saving
	            user.setPassword(passwordEncoder.encode("12345"));
	            user.setName("Admin");
	            user.setActive(true);
	            user.setLoginId("LD111111");
	            Role role = new Role();
	            role.setRoleName("ADMIN");
	            role.setLoginId("LD111111");
	            user.getRoles().add(role);
	            role.getUsers().add(user);
	            Wallet wallet = new Wallet();
	            wallet.setUser(user);
	            wallet.setLoginId(user.getLoginId());
	            user.setWallet(wallet);
	            userRepository.save(user);
	            walletRepository.save(wallet);
	        
	            User user1 = new User();
	            user1.setReferalId("LD111112");
	            user1.setContactNumber("1234567891");
	            user1.setEmail("azhar.sheikh@limrainfracon.com");
	            // Encrypt the password before saving
	            user1.setPassword(passwordEncoder.encode("12345"));
	            user1.setName("Azhar Sheikh");
	            user1.setActive(true);
	            user1.setLoginId("LD111112");
	            Role role1 = new Role();
	            role1.setRoleName("SUPER_USER");
	            role1.setLoginId("LD111112");
	            user1.getRoles().add(role1);
	            role1.getUsers().add(user1);
	            Wallet wallet1 = new Wallet();
	            wallet1.setLoginId(user1.getLoginId());
	            wallet1.setUser(user1);
	            user1.setWallet(wallet1);
	            userRepository.save(user1);
	            walletRepository.save(wallet1);
	        }
	    }
}
