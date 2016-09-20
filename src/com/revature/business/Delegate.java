package com.revature.business;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.dataservice.Commitment;
import com.revature.dataservice.DAO;
import com.revature.dataservice.DAOImpl;
import com.revature.dataservice.Institution;
import com.revature.dataservice.Loan;
import com.revature.mail.SendMailClient;
import com.revature.utility.HibernateUtility;

public class Delegate {
	
	private static final String SIGNATURE = 
			"\n------------------------------------------------\n" +
			"     Thank You for your Business\n" +
			"     LDC Loan Delivery Service\n" +
			"     Revature LLC" + 
			"\n------------------------------------------------\n";

	/**
	 * processes a committed loan from an institution Delegates responsibility
	 * of business logic 1. Check that the Commitment level for Institution has
	 * not crossed allowable daily threshold 2. Analyze loan for government
	 * compliance and risk 3. if Approved, recalculate the accumulated risk and
	 * commitment level for the Institution and add to the database 4. email
	 * client?
	 * 
	 * @param loan
	 * @param instID
	 * @param uid
	 * @param email
	 * @return return a processed loan
	 */
	public Loan processLoan(Loan loan, int instID) {
		System.out.println("entered Delegate: processLoan");
		CommitmentManager cm = new CommitmentManager();
		AnalysisEngine engine = new AnalysisEngine();

		if (cm.checkInstitutionLevel(instID) == true) {
			loan = engine.getCompliance(loan);
			if (loan.isCompliant()) {
				loan = engine.calculateRisk(loan);
			}
		}
		return loan;
	}

	public List<Loan> commitLoans(List<Loan> sessionLoans, int instID) {
		// TODO Auto-generated method stub
		System.out.println("entered delegate: commitLoans");
		System.out.println("number of loans: " + sessionLoans.size());
		System.out.println("institution ID: " + instID);

		List<Loan> committedLoans = new ArrayList<>();
		
		Session session = HibernateUtility.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		session.setFlushMode(FlushMode.COMMIT);
		try {

			DAO dao = new DAOImpl();
			Institution inst = dao.getInstitution(instID, session);
			Commitment commitment = dao.createCommitment(sessionLoans, inst, session);
			// need to update the institution levels
			CommitmentManager cm = new CommitmentManager();
			inst = cm.updateInstitutionLevels(inst, commitment);
			committedLoans = commitment.getLoans();
			
			session.save(inst);
			tx.commit();
			
		} catch (Exception e) {
			if (tx != null) {
				System.out.println("Rolling back...");
				tx.rollback();
			}
			e.printStackTrace();
		} finally {
			session.close();
		}
		System.out.println("delegate commit, number of committed loans: " + committedLoans.size());
		return committedLoans;
	}

	public List<Loan> getApprovedLoans() {
		System.out.println("entered getApprovedLoans");
		// retrieves all of the approved loans in the database
		Session session = HibernateUtility.getSessionFactory().openSession();
		DAO dao = new DAOImpl();
		List<Loan> approvedLoans = new ArrayList<>();
		try {
			approvedLoans = dao.getApprovedLoans(session);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return approvedLoans;
	}

	public List<Loan> getDailyCommited(int instID) {
		System.out.println("entered getDailyCommited");
		// retrieves all of the committed loans for the day
		// for the specified institution
		Session session = HibernateUtility.getSessionFactory().openSession();
		DAO dao = new DAOImpl();
		List<Loan> dailyCommittedLoans = new ArrayList<>();
		try {
			dailyCommittedLoans = dao.getDailyCommittedLoans(instID, session);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return dailyCommittedLoans;
	}

	public void purchaseLoan(int loanID, String email) {
		Session session = HibernateUtility.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		DAO dao = new DAOImpl();
		try {
			dao.purchaseLoan(loanID, session);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
	}

	// emails the client a transaction notification
	public void notifyClient(String email, String subject, String mailText) {
		subject = subject + SIGNATURE;
		SendMailClient mailSender = new SendMailClient(email, subject, mailText);
		try {
			mailSender.sendMail();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//
}
