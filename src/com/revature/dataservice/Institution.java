package com.revature.dataservice;

import java.util.List;

import javax.persistence.*;


@Entity
@Table(name = "lds2_institutions")
public class Institution {

	@Id
	@Column(name="inst_id")
	@GeneratedValue
	private int instID;
	
	@Column(name="tax_id", nullable=false)
	private String taxID;
	
	@Column(name="name")
	private String name;
	
	@Column(name="url")
	private String url;

	/**
	 * calculated accumulated risk 
	 * using number of total loans accepted
	 * average risk of all loans approved today
	 * 1-10 ( 1 is low risk )
	 */
	@Column(name = "accum_risk")
	private int accumulated_risk;

	/**
	 * number of loans approved today
	 */
	@Column(name = "commit_level")
	private double commitment_level;
	
	@OneToMany(mappedBy="institution")
	private List<Commitment> commitments;
	
	@OneToMany(mappedBy="institution")
	private List<User> users;

	public List<Commitment> getCommitments() {
		return commitments;
	}

	public void setCommitments(List<Commitment> commitments) {
		this.commitments = commitments;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public int getInstID() {
		return instID;
	}

	public void setInstID(int instID) {
		this.instID = instID;
	}

	public String getTaxID() {
		return taxID;
	}

	public void setTaxID(String taxID) {
		this.taxID = taxID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getAccumulated_risk() {
		return accumulated_risk;
	}

	public void setAccumulated_risk(int accumulated_risk) {
		this.accumulated_risk = accumulated_risk;
	}

	public double getCommitment_level() {
		return commitment_level;
	}

	public void setCommitment_level(double commitment_level) {
		this.commitment_level = commitment_level;
	}
	
}
