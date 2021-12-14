package com.project.couponsys.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.project.couponsys.entities.Company;

public interface CompanyRepo extends CrudRepository<Company, Integer> {
	
	//findAll - defined method for spring jpa
	
	//findById - defined method for spring jpa
	
	public Optional<Company> findByName (String compname);
	public Optional<Company> findByEmailAndPassword (String email, String password);
	

}
