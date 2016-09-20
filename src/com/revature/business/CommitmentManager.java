package com.revature.business;

import java.sql.Date;
import java.util.Calendar;

import org.hibernate.Session;

import com.revature.dataservice.Commitment;
import com.revature.dataservice.DAO;
import com.revature.dataservice.DAOImpl;
import com.revature.dataservice.Institution;
import com.revature.dataservice.Loan;
import com.revature.utility.HibernateUtility;

public class CommitmentManager {

	// only allow Institution to submit 30 approved loans a day
	// private static final int MAX_NUMBER_OF_DAILY_LOANS = 30;
	private static final double MAX_DAILY_COMMITMENT_AMOUNT = 1000000.00;
	
	public boolean checkInstitutionLevel(int instID) {
		// check to see if institution is at an allowable
		// level to commit loans
		boolean result = true;
		Session session = HibernateUtility.getSessionFactory().openSession();
		DAO dao = new DAOImpl();
		try {
			if (dao.getInstitution(instID, session).getCommitment_level() > MAX_DAILY_COMMITMENT_AMOUNT)
				result = false;
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return result;
	}

	public Institution updateInstitutionLevels(Institution inst, Commitment commitment) {
		// commit_level= number of loans approved today or at last commit day
		// accumulated_risk = average risk of all risks today or at last commit day
		// accum_amount = total dollar amount of all loans toady or at last commit day
		int numLoans = 0;
		int accum_risk = 0;
		double accum_amount = 0;
		Calendar cal = Calendar.getInstance();
		Date date = new Date(cal.getTime().getTime());
		System.out.println("commitment manager: update Institution levels: number of commitments in the institution: "
				+ inst.getCommitments().size());
		for (Commitment c : inst.getCommitments()) {
			System.out.println("today: "+ date + "  commitment date: "+ c.getCommitdate());
			if (c.getCommitdate().toString().equals(date.toString())) {
				System.out.println("number of loans: " +c.getLoans().size());
				for (Loan loan : c.getLoans()) {
					if (loan.isApproved()) {
						numLoans++;
						accum_risk += loan.getRisk();
						accum_amount += loan.getAmount();
					}
				}
			}
		}
		if(numLoans != 0)
			accum_risk = accum_risk / numLoans;
		inst.setAccumulated_risk(accum_risk);
		inst.setCommitment_level(accum_amount);
		return inst;
	}
}
