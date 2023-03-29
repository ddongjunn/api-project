package com.jobis.repository;

import com.jobis.domain.entity.IncomeDeduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface IncomeDeductionRepository extends JpaRepository<IncomeDeduction, Long> {

    @Query("SELECT new map(id as id, incomeType as incomeType, amount as amount) FROM IncomeDeduction i WHERE i.member.userId = :userId")
    List<Map<String, Object>> findByUserId(@Param("userId") String userId);
}
