package com.org.spring.repository.serviceprice;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.org.spring.model.serviceprice.FixedCost;

@Repository
public interface FixedCostServicePriceRepository  extends CrudRepository<FixedCost, Long> {

	FixedCost findById(long fixedcost_id);
}