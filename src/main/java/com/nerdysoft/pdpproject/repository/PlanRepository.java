package com.nerdysoft.pdpproject.repository;

import com.nerdysoft.pdpproject.entity.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
