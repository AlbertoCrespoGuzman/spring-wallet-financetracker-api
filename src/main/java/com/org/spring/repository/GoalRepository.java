package com.org.spring.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.org.spring.model.Goal;

@Repository
public interface GoalRepository  extends CrudRepository<Goal, Long> {
	List<Goal> findByUserid(long user_id);
	Goal findById(long goal_id);
	Goal findByUseridAndFirstdateAndCategoryAndIncome(long userid, Date firstdate, int category, boolean income);
}
