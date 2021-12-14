package com.project.couponsys.repository;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.project.couponsys.entities.Customer;

public interface CustomerRepo extends CrudRepository<Customer, Integer>{
	
	public Optional<Customer> findByEmail (String email);
	
	public Optional<Customer> findByEmailAndPassword (String email, String password);
	
	//findAll - defined method for spring JPA
	
	//findById - defined method for spring jpa
	

}
