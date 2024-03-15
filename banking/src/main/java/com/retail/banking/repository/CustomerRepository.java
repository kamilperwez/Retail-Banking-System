package com.retail.banking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.retail.banking.model.CustomerEntity;


@Repository
public interface CustomerRepository extends CrudRepository<CustomerEntity, String> {

}
