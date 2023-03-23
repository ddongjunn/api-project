package com.jobis.repository;

import com.jobis.domain.entity.IncomeDeduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeDeductionRepository extends JpaRepository<IncomeDeduction, Long> {
}
