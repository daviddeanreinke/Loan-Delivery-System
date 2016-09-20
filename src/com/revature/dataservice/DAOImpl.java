package com.revature.dataservice;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.utility.HibernateUtility;

public class DAOImpl implements DAO {

	public User login(String usr, String pw) {
		System.out.println("entered DAO login: " + usr + " : " + pw);

		Session session = HibernateUtility.getSessionFactory().openSession();

		User user = new User();
		// query the database for valid login credentials
		try {
			List<User> ulist = session.createQuery("FROM User WHERE username =:username AND password=:password")
					.setString("username", usr).setString("password", pw).list();
			user = ulist.get(0);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}

		return user;
	}

	@Override
	public Institution getInstitution(int instID, Session session) {
		Institution inst = new Institution();
		List<Institution> iList = session.createQuery("From Institution where instID = :instID")
				.setInteger("instID", instID).list();
		if(iList.isEmpty()) return null;
		inst = iList.get(0);
		System.out.println("retrieved institution ID: " + inst.getInstID());
		return inst;
	}

	@Override
	public Loan getLoan(int loanID, Session session) {
		Loan loan = new Loan();
		List<Loan> loanList = session.createQuery("From Loan where loanID = :loanID")
				.setInteger("loanID", loanID).list();
		loan = loanList.get(0);
		System.out.println("retrieved Loan ID: " + loan.getLoanID());
		return loan;
	}
	@Override
	public Commitment createCommitment(List<Loan> sessionLoans, Institution inst, Session session) {
		double amount = 0;
		double commit_level = 0;
		double acc_risk = 0;
		// create a new commitment row
		Commitment commitment = new Commitment();
		Calendar cal = Calendar.getInstance();
		Date date = new Date(cal.getTime().getTime());
		System.out.println("today's date: " + date);
		commitment.setCommitdate(date);
		commitment.setInstitution(inst);

		session.save(commitment);
		// need to update the total_amount of the commitment
		// and recalculate the accum_risk and commit_level of the institution

		for (Loan loan : sessionLoans) {
			amount += loan.getAmount();
			acc_risk += loan.getRisk();
			commit_level++;
			loan.setCommitment(commitment);
			session.save(loan);
		}
		commitment.setTotalAmount(amount);
		commitment.setLoans(sessionLoans);
		session.update(commitment);
		return commitment;
	}

	@Override
	public List<Loan> getApprovedLoans(Session session) {
		List<Loan> loans = (ArrayList<Loan>) session.createQuery("From Loan where approved = :approved")
				.setBoolean("approved", true).list();
		return loans;
	}

	@Override
	public List<Loan> getDailyCommittedLoans(int instID, Session session) {
		List<Loan> loans = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		Date date = new Date(cal.getTime().getTime());
		List<Commitment> commits = (ArrayList<Commitment>) session
				.createQuery("From Commitment where instID = :instID")
				.setInteger("instID", instID).list();
		for (Commitment c : commits) {
			if( c.getCommitdate().toString().equals(date.toString()))
				loans.addAll(c.getLoans());
		}
		return loans;

	}

	@Override
	public void purchaseLoan(int loanID, Session session) {
		// add purchase details to log table
		Calendar cal = Calendar.getInstance();
		Date date = new Date(cal.getTime().getTime());
		Loan loan = getLoan(loanID, session);
		String details = loan.toString();
		PurchaseLog  pl = new PurchaseLog();
		pl.setPurchase_date(date);
		pl.setLoanID(loanID);
		pl.setDetails(details);
		session.save(pl);

		Query q = session.createQuery("delete Loan where loanID =:loanID").setInteger("loanID", loanID);
		q.executeUpdate();
	}

}
