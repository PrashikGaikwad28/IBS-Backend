package com.ibs.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ibs.demo.model.AppliedLoans;

@Repository
public interface AppliedLoanRepository extends JpaRepository<AppliedLoans, Integer> {

	@Query(value = "SELECT a.id, a.amount, a.duration, a.apply_date, a.loan_id, a.course_name, a.course_fee, a.annual_income, a.monthly_emi, a.designation, a.total_experience, a.permanent_address, a.user_id, l.id, l.name, l.interest FROM applied_loans a INNER JOIN loan_type l ON a.loan_id = l.id WHERE a.user_id = :userId", nativeQuery = true)
	public List<AppliedLoans> findLoansByUser(@Param("userId") long userId);
	
	@Query(value = "SELECT a.id, a.amount, a.duration, a.apply_date, a.loan_id, a.course_name, a.course_fee, a.annual_income, a.monthly_emi, a.designation, a.total_experience, a.permanent_address, a.user_id, l.id, l.name, l.interest FROM applied_loans a INNER JOIN loan_type l ON a.loan_id = l.id", nativeQuery = true)
	public List<AppliedLoans> findAllLoans();
}
