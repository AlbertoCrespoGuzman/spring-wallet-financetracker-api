package com.org.spring.repository.serviceprice;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.org.spring.model.serviceprice.VariableCost;


public interface VariableCostServicePriceRepository  extends CrudRepository<VariableCost, Long> {
	
	VariableCost findById(long variablecost_id);
}