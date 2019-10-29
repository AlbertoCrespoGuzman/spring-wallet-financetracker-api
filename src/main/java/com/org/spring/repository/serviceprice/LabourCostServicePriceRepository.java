package com.org.spring.repository.serviceprice;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.org.spring.model.serviceprice.LabourCost;


public interface LabourCostServicePriceRepository  extends CrudRepository<LabourCost, Long> {

	LabourCost findById(long labourcost_id);
}