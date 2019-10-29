package com.org.spring.repository.productprice;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.org.spring.model.productprice.FixedVariableCost;


public interface FixedVariableCostProductPriceRepository  extends CrudRepository<FixedVariableCost, Long> {

	FixedVariableCost findById(long fixedvariablecost_id);
}
