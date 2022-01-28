package com.nerdysoft.pdpproject.repository;

import com.nerdysoft.pdpproject.entity.SuccessCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SuccessCriteriaRepository extends JpaRepository<SuccessCriteria, Long> {
}
