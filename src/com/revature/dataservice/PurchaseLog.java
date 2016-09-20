package com.revature.dataservice;

import java.sql.Date;

import javax.persistence.*;

@Entity
@Table(name="lds2_loan_purchases")
public class PurchaseLog {
	
	@Id
	@Column(name="p_id")
	@GeneratedValue
	private int pid;
	
	@Column(name="inst_id")
	private int instID;
	
	@Column(name="loan_id")
	private int loanID;
	
	@Column(name="purchase_date")
	private Date purchase_date;
	
	@Column(name="purchase_details")
	private String details;

	public int getPid() {
		return pid;
	}

	private void setPid(int pid) {
		this.pid = pid;
	}

	public int getInstID() {
		return instID;
	}

	public void setInstID(int instID) {
		this.instID = instID;
	}

	public int getLoanID() {
		return loanID;
	}

	public void setLoanID(int loanID) {
		this.loanID = loanID;
	}

	public Date getPurchase_date() {
		return purchase_date;
	}

	public void setPurchase_date(Date purchase_date) {
		this.purchase_date = purchase_date;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}
	
	
}
