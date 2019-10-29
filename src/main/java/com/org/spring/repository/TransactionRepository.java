package com.org.spring.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.org.spring.model.Transaction;

@Repository
public interface TransactionRepository  extends CrudRepository<Transaction, Long> {
	List<Transaction> findByUserid(long user_id);
	Transaction findById(long transaction_id);
}
