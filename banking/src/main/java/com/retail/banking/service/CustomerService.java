package com.retail.banking.service;

import com.retail.banking.model.CustomerEntity;

public interface CustomerService {

	public CustomerEntity createCustomerWithAccount(CustomerEntity customer);

	public CustomerEntity getCustomerDetail(String id);
	
	public CustomerEntity createCustomer(CustomerEntity customer);

	public Boolean updateCustomer(CustomerEntity customer);

	public boolean deleteCustomer(String id);

}
