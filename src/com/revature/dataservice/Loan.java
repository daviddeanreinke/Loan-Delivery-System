package com.revature.dataservice;
 
import java.sql.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.apache.struts.action.ActionForm;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;



@SuppressWarnings("serial")
@Entity
@XmlAccessorType(XmlAccessType.NONE)
@JsonAutoDetect(fieldVisibility = Visibility.NONE,
				getterVisibility = Visibility.NONE,
				isGetterVisibility = Visibility.NONE,
				setterVisibility = Visibility.NONE)
@Table(name = "lds2_loans")
public class Loan  extends ActionForm{

	@Id
	@Column(name="loan_id")
	@GeneratedValue
	@XmlElement
	@JsonProperty
	private int loanID;
	
	@XmlElement
	@JsonProperty
	@Column(name="commit_id", insertable=false, updatable=false)
	private int commitID;
	
	@XmlElement
	@JsonProperty
	@Column(name="borrower_name")
	private String borrowerName;
	
	@XmlElement
	@JsonProperty
	@Column(name="type")
	private String type;
	
	@XmlElement
	@JsonProperty
	@Column(name="secured")
	private String secured;
	
	@XmlElement
	@JsonProperty
	@Column(name="amount")
	private double amount;
	
	@XmlElement
	@JsonProperty
	@Column(name="monthly_payment")
	private double monthly_payment;
	
	@XmlElement
	@JsonProperty
	@Column(name="interest_rate")
	private double interestRate;
	
	@XmlElement
	@JsonProperty
	@Column(name="risk")
	private int risk;
	
	@XmlElement
	@JsonProperty
	@Column(name="issue_date")
	private Date issue_date;
	/**
	 * the number of months left on this loan
	 */
	@XmlElement
	@JsonProperty
	@Column(name="duration")
	private int duration;
	
	/**
	 * the number of months this loan is overdue if any
	 */
	@XmlElement
	@JsonProperty
	@Column(name="delinquency")
	private int delinquency;

	@XmlElement
	@JsonProperty
	@Column(name="borrower_tax_id")
	private String taxID;
	
	@XmlElement
	@JsonProperty
	@Column(name="borrower_credit_score")
	private int creditScore;
	
	@XmlElement
	@JsonProperty
	@Column(name="compliant")
	private boolean compliant;
	
	@XmlElement
	@JsonProperty
	@Column(name="approved")
	private boolean approved;
	
	@XmlElement
	@JsonProperty
	@Column(name="comments")
	private String comments;  // cannot name row "comment"
	
	@ManyToOne
	@JoinColumn(name="commit_id")
	@XmlTransient
	private Commitment commitment;

	
	public int getLoanID() {
		return loanID;
	}

	public void setLoanID(int loanID) {
		this.loanID = loanID;
	}

	public int getRisk() {
		return risk;
	}

	public void setRisk(int risk) {
		this.risk = risk;
	}

	public int getCommitID() {
		return commitID;
	}

	public void setCommitID(int commitID) {
		this.commitID = commitID;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSecured() {
		return secured;
	}

	public void setSecured(String secured) {
		this.secured = secured;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getMonthly_payment() {
		return monthly_payment;
	}

	public void setMonthly_payment(double monthly_payment) {
		this.monthly_payment = monthly_payment;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public void setInterestRate(double interestRate) {
		this.interestRate = interestRate;
	}

	public Date getIssue_date() {
		return issue_date;
	}

	public void setIssue_date(Date issue_date) {
		this.issue_date = issue_date;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public int getDelinquency() {
		return delinquency;
	}

	public void setDelinquency(int delinquency) {
		this.delinquency = delinquency;
	}

	public String getTaxID() {
		return taxID;
	}

	public String getBorrowerName() {
		return borrowerName;
	}

	public void setBorrowerName(String borrowerName) {
		this.borrowerName = borrowerName;
	}
	
	public void setTaxID(String taxID) {
		this.taxID = taxID;
	}

	public int getCreditScore() {
		return creditScore;
	}

	public void setCreditScore(int creditScore) {
		this.creditScore = creditScore;
	}

	public boolean isCompliant() {
		return compliant;
	}

	public void setCompliant(boolean compliant) {
		this.compliant = compliant;
	}

	public boolean isApproved() {
		return approved;
	}

	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@XmlTransient
	public Commitment getCommitment() {
		return commitment;
	}

	public void setCommitment(Commitment commitment) {
		this.commitment = commitment;
	}

	@Override
	public String toString() {
		
		return 	"------Loan Information------" + 
				"Loan ID: " + loanID + 
				"      Government Compliance: " + compliant +
				"      Approved: " + approved + "\n" +
				"Risk: " + risk + "\n" +
				"Commit ID: " + commitID + "\n" +
				"Borrower Name: " + borrowerName + "\n" +
				"Tax ID: " + taxID + "\n" +
				"Credit Score: " + creditScore + "\n" +
				"Type: " + type + "\n" +
				"Secured: " + secured + "\n" +
				"Amount: " + amount + "\n" +
				"Monthly Payment: " + monthly_payment + "\n" +
				"Interest Rate: " + interestRate + "%" + "\n" +
				"Issue Date: " + issue_date + "\n" +
				"Duration: " + duration + "\n" +
				"Comments: " + comments +"\n";
				
	}

	
	
}
