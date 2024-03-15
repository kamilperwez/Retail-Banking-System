package com.retail.banking.serviceImpl;

import org.springframework.stereotype.Service;

import com.retail.banking.model.MinimumBalanceException;
import com.retail.banking.model.RulesInput;
import com.retail.banking.service.RulesService;

@Service
public class RulesServiceImpl implements RulesService {
	
	private static final String INVALID = "Send Valid Details.";

	@Override
	public boolean evaluate(RulesInput account) {
		if (account.getCurrentBalance() == 0) {
			throw new MinimumBalanceException(INVALID);
		} else {
			int min = 1000;
			double check = account.getCurrentBalance() - account.getAmount();
			if (check >= min)
				return true;
			else
				return false;
		}
	}
	

}
