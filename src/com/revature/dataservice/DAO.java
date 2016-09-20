package com.revature.dataservice;

import java.util.List;

import org.hibernate.Session;

public interface DAO {
	
	public User login(String usr, String pw);

	public Institution getInstitution(int instID, Session session);

	public Commitment createCommitment(List<Loan> sessionLoans, Institution inst, Session session);

	public List<Loan> getApprovedLoans(Session session);

	public List<Loan> getDailyCommittedLoans(int instID, Session session);

	public void purchaseLoan(int loanID, Session session);

	public Loan getLoan(int loanID, Session session);
}
