package com.retail.banking.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retail.banking.model.Account;
import com.retail.banking.model.AppUser;
import com.retail.banking.model.CustomerEntity;
import com.retail.banking.repository.CustomerRepository;
import com.retail.banking.repository.UserRepository;
import com.retail.banking.service.AccountService;
import com.retail.banking.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);

	private static final String CUSTOMER = "CUSTOMER";

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	AccountService accountService;

	/**
	 * Creating new customer and storing it in the database
	 */
	@Override
	public CustomerEntity createCustomerWithAccount(CustomerEntity customer) {

		CustomerEntity checkCustomerExists = getCustomerDetail(customer.getUserid());
		if (checkCustomerExists == null) {
			AppUser user = new AppUser(customer.getUserid(), customer.getUsername(), customer.getPassword(), null,
					CUSTOMER);
			userRepo.save(user);
		}

		for (Account acc : customer.getAccounts()) {
			accountService.createAccount(customer.getUserid(), acc);
		}

		customerRepo.save(customer);
		LOGGER.info("Customer details saved.");
		return customer;
	}

	/**
	 * Getting the customer details based on the customer id
	 */
	@Override
	public CustomerEntity getCustomerDetail(String id) {
		Optional<CustomerEntity> customer = customerRepo.findById(id);
		if (!customer.isPresent())
			return null;
		LOGGER.info("Customer details fetched.");
		List<Account> list = accountService.getCustomerAccount(id);
		customer.get().setAccounts(list);
		return customer.get();
	}

	/**
	 * Deleting the customer details associated with the given customer id
	 */
	@Override
	public boolean deleteCustomer(String id) {
		CustomerEntity customer = customerRepo.findById(id).get();
		if (customer != null)
			customerRepo.deleteById(id);
		else
			return false;
		LOGGER.info("Customer details deleted.");
		return true;
	}

	/**
	 * Updating the customer details based on customer id
	 */
	@Override
	public Boolean updateCustomer(CustomerEntity customer) {
		try {
			customerRepo.findById(customer.getUserid()).get();

			customerRepo.save(customer);
			return true;

		} catch (Exception e) {
			return false;

		}
//		toUpdate.setAccounts(customer.getAccounts());
//		for (Account acc : customer.getAccounts()) {
//			accountService.createAccount(customer.getUserid(), acc);
//		}

	}

	/**
	 * Saving customer record in the database
	 */
	@Override
	public CustomerEntity createCustomer(CustomerEntity customer) {
		CustomerEntity checkCustomerExists = getCustomerDetail(customer.getUserid());
		if (checkCustomerExists == null) {
			AppUser user = new AppUser(customer.getUserid(), customer.getUsername(), customer.getPassword(), null,
					CUSTOMER);
			userRepo.save(user);
		}
		LOGGER.info("Customer details saved.");
		return customerRepo.save(customer);
	}

}
