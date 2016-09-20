package com.revature.dataservice;

import java.sql.Date;
import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "lds2_commitments")
public class Commitment {

	@Id
	@Column(name="commit_id")
	@GeneratedValue
	private int commitID;
	
	@Column(name="inst_id", insertable=false, updatable=false)
	private int instID;
	
	@Column(name="commit_date")
	private Date commitdate;
	
	@Column(name="total_amount")
	private double totalAmount;
	
	@ManyToOne
	@JoinColumn(name="inst_id")
	private Institution institution;
	
	@OneToMany(mappedBy="commitment")
	private List<Loan> loans;

	public List<Loan> getLoans() {
		return loans;
	}

	public void setLoans(List<Loan> loans) {
		this.loans = loans;
	}

	public int getCommitID() {
		return commitID;
	}

	public void setCommitID(int commitID) {
		this.commitID = commitID;
	}

	public int getInstID() {
		return instID;
	}

	public void setInstID(int instID) {
		this.instID = instID;
	}

	public Date getCommitdate() {
		return commitdate;
	}

	public void setCommitdate(Date commitdate) {
		this.commitdate = commitdate;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	
}
