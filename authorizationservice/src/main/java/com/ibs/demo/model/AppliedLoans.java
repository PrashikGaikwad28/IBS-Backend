package com.ibs.demo.model;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@JsonInclude(Include.NON_NULL)
@Table(name = "applied_loans")
@Data
@NoArgsConstructor
public class AppliedLoans {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private long amount;
	
	private int duration;
	
	@JsonProperty("applyDate")
	private Date apply_date;
	
	@JsonProperty("endDate")
	private Date end_date;
	
	@OneToOne
	@JoinColumn(name = "loan_id", referencedColumnName = "id")
	private LoanType loan_id;
	
	@JsonProperty("courseName")
	private String course_name;
	
	@JsonProperty("courseFee")
	private Integer course_fee;
	
	@JsonProperty("annualIncome")
	private long annual_income;
	
	@JsonProperty("monthlyEmi")
	private long monthly_emi;
	
//	@JsonProperty("companyName")
//	private String company_name;
	
	private String designation;
	
	@JsonProperty("totalExperience")
	private Integer total_experience;
	
//	@JsonProperty("panNo")
//	private String pan_no;
	
	@JsonProperty("permanentAddress")
	private String permanent_address;
	
	@OneToOne//(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user_id;
	
	
}
