package com.org.spring.repository.serviceprice;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.org.spring.model.serviceprice.LabourTax;


@Repository
public interface LabourTaxServicePriceRepository  extends CrudRepository<LabourTax, Long> {
	
	LabourTax findById(long fixedcost_id);
}