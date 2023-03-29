package com.jobis.repository;

import com.jobis.domain.SalaryMapping;
import com.jobis.domain.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface SalaryRepository extends JpaRepository<Salary, Long> {
    List<SalaryMapping> findByMemberUserId(String userId);
}
