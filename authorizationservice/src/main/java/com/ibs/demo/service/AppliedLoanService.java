package com.ibs.demo.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ibs.demo.exception.InvalidRequestException;
import com.ibs.demo.model.AppliedLoans;
import com.ibs.demo.model.User;
import com.ibs.demo.repository.AppliedLoanRepository;

@Service
public class AppliedLoanService {

	@Autowired
	private AppliedLoanRepository appliedLoanRepository;

	@Autowired
	private UsersService userService;

	public List<AppliedLoans> findAll(String userName) {
		User user = userService.findByUserName(userName);
		List<AppliedLoans> loans = appliedLoanRepository.findLoansByUser(user.getId());
		loans.stream().forEach(loan -> loan.setUser_id(null));
		return loans;
	}

	@Transactional
	public AppliedLoans applyLoan(String userName, AppliedLoans appliedLoans) {
		User user = userService.findByUserName(userName);
		appliedLoans.setUser_id(user);
		// Calculate end date
		Date applyDate = appliedLoans.getApply_date();
		int duration = appliedLoans.getDuration();
		LocalDate localDate = applyDate.toLocalDate();
		LocalDate updatedLocalDate = localDate.plusYears(duration);
		Date updatedSqlDate = Date.valueOf(updatedLocalDate);
		appliedLoans.setEnd_date(updatedSqlDate);
		//
//		AppliedLoanService service = new AppliedLoanService();		//changes
		List<AppliedLoans> loans = appliedLoanRepository.findLoansByUser(user.getId());
		long totalEmi = appliedLoans.getMonthly_emi();
		Iterator<AppliedLoans> iterator = loans.listIterator();
		while(iterator.hasNext()) {
			if(appliedLoans.getLoan_id().getId() == iterator.next().getLoan_id().getId() && appliedLoans.getId()==0) {
				throw new InvalidRequestException("Cannot apply for the loan. You have already applied for the "+appliedLoans.getLoan_id().getName()+".");
			}
		}
		for(AppliedLoans loan : loans) {
			if(appliedLoans.getId()==loan.getId()) {
				continue;
			}
			totalEmi += loan.getMonthly_emi();
		}
		System.out.println("\nTotal EMI: " + totalEmi);
		long monthlySalary = appliedLoans.getAnnual_income() / 12;
		if (totalEmi <= monthlySalary * 2 / 3) {
			return appliedLoanRepository.save(appliedLoans);
		} else {
			throw new InvalidRequestException("Loan amount exceeds the maximum limit!! Please enter valid amount.");
		} // end of changes
	}

	public List<AppliedLoans> getAllLoanDetails() {
		List<AppliedLoans> list = appliedLoanRepository.findAll();
		System.out.println(list);
		return list;
	}

}
