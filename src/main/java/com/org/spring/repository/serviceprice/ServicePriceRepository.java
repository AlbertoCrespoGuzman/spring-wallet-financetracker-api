package com.org.spring.repository.serviceprice;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.org.spring.model.serviceprice.ServicePrice;

@Repository
public interface ServicePriceRepository   extends CrudRepository<ServicePrice, Long> {
	List<ServicePrice> findByUserid(long user_id);
	ServicePrice findById(long serviceprice_id);
}
