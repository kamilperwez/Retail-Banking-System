package com.retail.banking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.retail.banking.model.Statement;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {
	@Query(nativeQuery = true,
			value = "SELECT * from STATEMENT s WHERE " +
					"(s.source_Id = :accountId or s.target_Id = :accountId) " +
					"and (date between :startDate and :endDate) order by date desc ")
	List<Statement> findStatementByAccountId(@Param("accountId") long accountId,
			@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate);
}
