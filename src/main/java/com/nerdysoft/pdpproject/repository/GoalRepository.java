package com.nerdysoft.pdpproject.repository;

import com.nerdysoft.pdpproject.entity.Goal;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalRepository extends JpaRepository<Goal, Long> {
}
