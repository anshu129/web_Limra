package com.limrainfracon.service;

import com.limrainfracon.model.Wallet;

public interface WalletService {

	Wallet findWalletByLoginId(String loginId);

	void saveUpdatedWallet(Wallet wallet);

}
