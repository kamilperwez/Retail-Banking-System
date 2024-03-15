package com.retail.banking.service;

import com.retail.banking.model.RulesInput;

public interface RulesService {
	
	public boolean evaluate(RulesInput account);


}
