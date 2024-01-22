package com.limrainfracon.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.limrainfracon.model.User;
import com.limrainfracon.model.Wallet;
import com.limrainfracon.repository.UserRepository;
import com.limrainfracon.service.WalletService;

@Service
public class CommisionService {
	
	@Autowired
	private 
	UserRepository userRepository;
	
	@Autowired 
	private WalletService walletService;

	public void calculateAndDistributeCommission(User user, double saleAmount) {
		 int level = 0;
		 int numberOfHirarchy = userRepository.findHierarchyLevelFromDescendant(user.getLoginId());
		  while(user != null && level <= numberOfHirarchy) {
	            double commissionRate = getCommissionRate(level);
	            double commissionAmount = saleAmount * commissionRate;
	            updateCommissionForCurrentSeller(user,commissionAmount);
				user =userRepository.findByReferralId(user.getReferalId());
	            level++;
	        }
	    }

	private void updateCommissionForCurrentSeller(User user, double commissionAmount) {
	Wallet wallet =	walletService.findWalletByLoginId(user.getLoginId());
	 double updatedWalletAmount = commissionAmount+wallet.getDirectSaleBalance();
	 wallet.setDirectSaleBalance(updatedWalletAmount);
	 walletService.saveUpdatedWallet(wallet);
	}

	private double getCommissionRate(int level) {
	    switch(level) {
	        case 0: return 0.09; // 9%
	        case 1: return 0.02; // 2%
	        case 2: return 0.01; // 1%
	        case 3: return 0.01; // 1%
	        case 4: return 0.006; // 0.6%
	        case 5: return 0.005; // 0.5%
	        case 6: return 0.005; // 0.5%
	        case 7: return 0.005; // 0.5%
	        case 8: return 0.005; // 0.5%
	        case 9: return 0.004; // 0.4%
	        default: return 0.0; // 0%
	    }
	}

	

}
