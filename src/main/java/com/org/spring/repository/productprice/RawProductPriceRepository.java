package com.org.spring.repository.productprice;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.org.spring.model.productprice.Raw;


public interface RawProductPriceRepository  extends CrudRepository<Raw, Long> {

	Raw findById(long raw_id);
}
