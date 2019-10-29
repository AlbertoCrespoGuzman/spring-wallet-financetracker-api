package com.org.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.org.spring.model.Installment;

@Repository
public interface InstallmentRepository  extends CrudRepository<Installment, Long> {
	List<Installment> findByUserid(long user_id);
	Installment findById(long installment_id);
}
