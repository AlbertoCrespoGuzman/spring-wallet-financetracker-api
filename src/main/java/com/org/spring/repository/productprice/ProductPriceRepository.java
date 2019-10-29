package com.org.spring.repository.productprice;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.org.spring.model.productprice.ProductPrice;


@Repository
public interface ProductPriceRepository  extends CrudRepository<ProductPrice, Long> {
	List<ProductPrice> findByUserid(long user_id);
	ProductPrice findById(long productprice_id);
}
