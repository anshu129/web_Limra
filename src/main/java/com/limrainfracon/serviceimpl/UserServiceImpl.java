package com.limrainfracon.serviceimpl;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import com.limrainfracon.common.EmailService;
import com.limrainfracon.enums.Status;
import com.limrainfracon.exception.RefrencedUserDoesNotExist;
import com.limrainfracon.model.Role;
import com.limrainfracon.model.TransportDetails;
import com.limrainfracon.model.User;
import com.limrainfracon.model.Userdocs;
import com.limrainfracon.model.Wallet;
import com.limrainfracon.repository.UserdocsRepository;
import com.limrainfracon.repository.TransportRepository;
import com.limrainfracon.repository.WalletRepository;
import com.limrainfracon.repository.UserRepository;
import com.limrainfracon.request.EmailRequest;
import com.limrainfracon.request.MyChartDetails;
import com.limrainfracon.response.CommonResponse;
import com.limrainfracon.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
	private final UserRepository userRepository;
	private final EmailService emailService;
	private final PasswordEncoder passwordEncoder;
	private final UserdocsRepository userDocs;
	private final TransportRepository transportRepository;
	private final WalletRepository walletRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, EmailService emailService,PasswordEncoder passwordEncoder,UserdocsRepository userDocs, WalletRepository walletRepository, TransportRepository transportRepository) {
	    this.userRepository = userRepository;
	    this.emailService = emailService;
		this.passwordEncoder = passwordEncoder;
		this.userDocs = userDocs;
		this.transportRepository=transportRepository;
		this.walletRepository = walletRepository;
	}

	@Override
	public CommonResponse save(User user) {
		CommonResponse response = new CommonResponse();
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            response.setSuccess(false);
            response.setMessage("Email already exists");
            return response;
        }
        if (userRepository.findByContactNumber(user.getContactNumber()).isPresent()) {
            response.setSuccess(false);
            response.setMessage("Contact number already exists");
            return response;
        }
	    if(!userRepository.existsByLoginId(user.getReferalId())) {
	        throw new RefrencedUserDoesNotExist("ReferralId: " + user.getReferalId() + " does not exist.");
	    } else {
	       log.info("Exist");
	    }

	    String lastLoginId = userRepository.findLastLoginId();
	    if (lastLoginId == null) {
	        user.setLoginId("LD111111");
	    } else {
	        String prefix = lastLoginId.substring(0, 2);
	        int numericPart = Integer.parseInt(lastLoginId.substring(2));
	        numericPart++;
	        log.info("new ID " + prefix + String.format("%06d", numericPart));
	        user.setLoginId( prefix + String.format("%06d", numericPart));
	        user.setActive(true);
	        Role role = new Role();
	        role.setRoleName("L1");
	        role.setLoginId(prefix + String.format("%06d", numericPart));
	        // Add the role to user and user to role
	        user.getRoles().add(role);
	        role.getUsers().add(user);

	        user.setPassword(passwordEncoder.encode(user.getPassword()));
	    }
	    Wallet wallet = new Wallet();
	    wallet.setUser(user);
	    wallet.setLoginId(user.getLoginId());
	    user.setWallet(wallet);
	    userRepository.save(user);
	    walletRepository.save(wallet);
	    emailService.sendEmail(user);
	    user.setConfirmPassword(null);
	    response.setSuccess(true);
        response.setMessage("User ID successfully created");
		return response;
	}

	@Override
	public User findUser(User user) {
		 User user1 = null;
		try {
		    user1 = userRepository.findByLoginId(user.getLoginId());
		}catch(Exception e) {
		}
		return user1;
	}
	
	@Override
	public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
	    User user = userRepository.findByLoginId(loginId);
	    log.info("User========" +user);
	    if (user == null) {
	        throw new UsernameNotFoundException("User not found");
	    }
	    // Create the Spring Security User object
	    org.springframework.security.core.userdetails.User springUser =
	        new org.springframework.security.core.userdetails.User(
	            user.getName(),
	            user.getPassword(),
	            user.getRoles().stream()
	                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
	                .collect(Collectors.toList())
	        );

	    // Store the user in the session
	    RequestContextHolder.currentRequestAttributes().setAttribute("user", user, RequestAttributes.SCOPE_SESSION);

	    return springUser;
	}

	public Optional<User> userFindById(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		return user;
	}
	@Override
	public List<User> getAllUsers() {
		Sort sortBy = Sort.by(Sort.Direction.DESC, "id");
		List<User> usrs = userRepository.findAll(sortBy);
		return usrs;
	}

	@Override
	public List<User> getAllMyUsers(String loginId) {
		
		List<User> usrs = userRepository.findAllMyUsers(loginId);
		return usrs;
	}

	@Override
	public void sendEmailToUs(EmailRequest emailRequest) {
		
		emailService.sendEmailToUs(emailRequest);
	}

	@Override
	public void saveDocsDetails(String filename, long fileSize, String filePath, String loginId, String idNumber, String type) {
		
		Userdocs userdocs = new Userdocs();
		userdocs.setFileName(filename);
		userdocs.setFilePath(filePath);
		userdocs.setFileSize(fileSize);
		userdocs.setLoginId(loginId);
		userdocs.setId_number(idNumber);
		userdocs.setType(type);
		//user.saveUserDocs(filename, filePath, fileSize, loginId);
		userDocs.save(userdocs);
		
	}

	@Override
	public void saveImage(String filename, long fileSize, String filePath, String loginId) {

		Userdocs userdocs = new Userdocs();
		userdocs.setFileName(filename);
		userdocs.setFilePath(filePath);
		userdocs.setFileSize(fileSize);
		userdocs.setLoginId(loginId);
		//user.saveUserDocs(filename, filePath, fileSize, loginId);
		userDocs.save(userdocs);
	}

	public boolean existsById(String id) {
		Boolean status;
		User user = userRepository.findByLoginId(id);
		if (user != null) {
			status = true;
		} else {
			status = false;
		}
		return status;
	}

	@Override
	public User findByLoginId(String sellerId) {
		 User user = null;
			try {
			    user = userRepository.findByLoginId(sellerId);
			}catch(Exception e) {
			}
			return user;
	}

	@Override
	public Userdocs getAllDetailsById(String loginId) {
		
		Userdocs userdocs = userDocs.getByLoginId(loginId);
		return userdocs;
	}

	@Override
	public List<MyChartDetails> getAllMyUsersDetails(String loginId) {
		List<User> usrs = userRepository.findAllMyUserDetails(loginId);
		//User usr = userRepository.findByLoginId(loginId);
		List<MyChartDetails> myChartDetails = new ArrayList<>();
		//myChartDetails.add(new MyChartDetails(usr.getId(), 0, usr.getName()+"("+usr.getLoginId()+")"));
		for(User ur: usrs) {
			User usr1 = userRepository.findByLoginId(ur.getReferalId());
			myChartDetails.add(new MyChartDetails(ur.getId(), usr1.getId(), ur.getName()+"("+ur.getLoginId()+")"));
			
		}
		return myChartDetails;
	}

	@Override
	public TransportDetails saveTransportDetails(@Valid TransportDetails transportDetails) {
		
		transportDetails.setStatus(Status.PENDING.name());
		TransportDetails transportDetails2 = transportRepository.save(transportDetails);
		
		return transportDetails2;
		
	}

	@Override
	public List<TransportDetails> getDetailsByAssociateId(String loginId) {
		List<TransportDetails> transportDetails2 = transportRepository.getAllTransportByAssId(loginId);
		return transportDetails2;
	}
	
}
