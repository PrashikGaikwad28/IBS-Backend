package com.ibs.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
//		AppliedLoanService service = new AppliedLoanService();		//changes
		List<AppliedLoans> loans = appliedLoanRepository.findLoansByUser(user.getId());
		long totalEmi = appliedLoans.getMonthly_emi();
		Iterator<AppliedLoans> iterator = loans.listIterator();
		while(iterator.hasNext()) {
			totalEmi += iterator.next().getMonthly_emi();
		}
		System.out.println("\nTotal EMI: "+totalEmi);				
		long monthlySalary = appliedLoans.getAnnual_income()/12;	
		if(totalEmi <= monthlySalary*2/3) {
			return appliedLoanRepository.save(appliedLoans);
		}else {
			throw new InvalidRequestException("Loan amount exceeds the maximum limit!! Please enter valid amount.");
		}								//end of changes
	}
	
	public List<AppliedLoans> getAllLoanDetails(){
		List<AppliedLoans> list = appliedLoanRepository.findAll();
		System.out.println(list);
		return list;
	}
	
}
