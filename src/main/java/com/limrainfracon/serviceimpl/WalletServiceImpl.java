package com.limrainfracon.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.limrainfracon.model.Wallet;
import com.limrainfracon.repository.WalletRepository;
import com.limrainfracon.service.WalletService;

@Service
public class WalletServiceImpl implements WalletService {
	
	@Autowired
	private WalletRepository  walletRepository;

	@Override
	public Wallet findWalletByLoginId(String loginId) {
		
		return walletRepository.findWalletByLoginId(loginId);
	}

	@Override
	public void saveUpdatedWallet(Wallet wallet) {
		walletRepository.save(wallet);
		
	}

}
