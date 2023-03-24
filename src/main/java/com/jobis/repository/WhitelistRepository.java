package com.jobis.repository;

import com.jobis.domain.entity.Whitelist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WhitelistRepository extends JpaRepository<Whitelist, String> {

    @Query("SELECT w FROM Whitelist w WHERE w.name = :name AND w.regNo = :regNo")
    Optional<Whitelist> findByUserIdAndRegNo(@Param("name") String name, @Param("regNo") String regNo);

}
