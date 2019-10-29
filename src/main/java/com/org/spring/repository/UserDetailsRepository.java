package com.org.spring.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.org.spring.model.UserDetails;

@Repository
public interface UserDetailsRepository extends CrudRepository<UserDetails, Long> {
	UserDetails findByUserid(long user_id);
}