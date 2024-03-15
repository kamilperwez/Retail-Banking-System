package com.retail.banking.controller;

import java.time.DateTimeException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.retail.banking.model.CustomerEntity;
import com.retail.banking.service.CustomerService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;

	/**
	 * Creating a new customer and new account
	 */
	@PostMapping("/createCustomerWithAccount")
	public ResponseEntity<?> createCustomerWithAccount(@Valid @RequestBody CustomerEntity customer)
			throws DateTimeException {
		
		CustomerEntity customerEntity = customerService.createCustomerWithAccount(customer);
		if (customerEntity != null)
			return new ResponseEntity<>(customerEntity, HttpStatus.CREATED);
		else
			return new ResponseEntity<>("Customer Creation is UNSUCCESSFUL", HttpStatus.NOT_ACCEPTABLE);
	}

	/**
	 * Creating a new customer without Account
	 */
	@PostMapping("/createCustomer")
	public ResponseEntity<?> createCustomer(@Valid @RequestBody CustomerEntity customer) {
		CustomerEntity customerEntity = customerService.createCustomer(customer);
		if (customerEntity != null)
			return new ResponseEntity<>(customerEntity, HttpStatus.CREATED);
		else
			return new ResponseEntity<>("Customer Creation is UNSUCCESSFUL", HttpStatus.NOT_ACCEPTABLE);
	}

	/**
	 * Updating existing customer details
	 */
	@PostMapping("/updateCustomer")
	public ResponseEntity<?> updateCustomer(@Valid @RequestBody CustomerEntity customer) {
		Boolean result = customerService.updateCustomer(customer);
		if (result.booleanValue())
			return new ResponseEntity<>(customer, HttpStatus.OK);
		else
			return new ResponseEntity<>("Customer Update is UNSUCCESSFUL, Customer does not exist.", HttpStatus.NOT_ACCEPTABLE);		
	}

	/**
	 * Getting customer details by given customer id
	 */
	@GetMapping("/getCustomerDetails/{id}")
	public ResponseEntity<?> getCustomerDetails(@PathVariable("id") String id) {
		CustomerEntity toReturnCustomerDetails = customerService.getCustomerDetail(id);
		if (toReturnCustomerDetails == null)
			return new ResponseEntity<>("Customer Userid " + id + " DOES NOT EXISTS", HttpStatus.NOT_ACCEPTABLE);
		toReturnCustomerDetails.setPassword(null);
		return new ResponseEntity<>(toReturnCustomerDetails, HttpStatus.OK);
	}

	/**
	 * Deleting customer details with given customer id
	 */
	@DeleteMapping("/deleteCustomer/{id}")
	public ResponseEntity<?> deleteCustomer(@PathVariable("id") String id) {
		CustomerEntity toReturnCustomerDetails = customerService.getCustomerDetail(id);
		if (toReturnCustomerDetails == null)
			return new ResponseEntity<>("Customer Userid " + id + " DOES NOT EXISTS", HttpStatus.NOT_ACCEPTABLE);
		toReturnCustomerDetails.setPassword(null);
		boolean deleteCustomer = customerService.deleteCustomer(id);
		if (deleteCustomer) {

			return new ResponseEntity<>("CUSTOMER DELETED", HttpStatus.OK);
		}
		return new ResponseEntity<>("Customer Userid " + id + " DOES NOT EXISTS", HttpStatus.NOT_ACCEPTABLE);
	}

}
