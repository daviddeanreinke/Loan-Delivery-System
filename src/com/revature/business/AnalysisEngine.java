package com.revature.business;

import java.util.Random;

import com.revature.dataservice.Loan;

public class AnalysisEngine {

	// risk is a number between 1-10 where 10 is high risk
	private static final int RISK_THRESHOLD = 7;
	private static final int CREDIT_SCORE__THRESHOLD = 625;
	private static final int DELINQUENCY__THRESHOLD = 6;

	public Loan getCompliance(Loan loan) {
		// need some kind of logic here to
		// check government compliance
		// are the details of the loan feasible
		// is the tax_id of the borrower legit?

		// this logic could get messy so 
		// randomly approve 19 out of 20 loans
		Random rand = new Random();
		int randInt = rand.nextInt(20 - 1 + 1) + 1;

		String comment = loan.getComments();
		if (randInt < 19) {
			loan.setCompliant(true);
			comment = "\n< compliance check: pass >\n" + comment;
			loan.setComments(comment);
		} else {
			loan.setCompliant(false);
			comment = "\n< compliance check: fail >\nloan found to be not government compliant\n" + comment;
			loan.setComments(comment);
		}
		return loan;
	}

	public Loan calculateRisk(Loan loan) {
		// risk on scale from 1-10 1=low 10=high
		// set risk number and approve if within threshold and compliant
		// Loan variables to use in calculation
		// - compliant, delinquency, amount, duration,
		// interestRate, creditScore, secured, type,
		// monthly_payment
		// this could get really complicated, KEEP IT SIMPLE!

		/**
		 * rules - DO NOT ACCEPT LOANS THAT ARE - non-gov't-compliant risk = 10
		 * - delinquent over 6 months risk = 10 - borrower credit score is too
		 * low risk = 10
		 * 
		 * simple algorithm to calculate risk if secured, risk = 3
		 * 
		 * keep it simple, random number between 1-8
		 */
		String reason = "";
		if (!loan.isCompliant() || loan.getDelinquency() > DELINQUENCY__THRESHOLD
				|| loan.getCreditScore() < CREDIT_SCORE__THRESHOLD) {
			loan.setRisk(10);
			loan.setApproved(false);
			reason = "\n< approval check: fail >\n";
			if(!loan.isCompliant())
				reason += "Loan found to be not government compliant\n";
			if(loan.getDelinquency() > DELINQUENCY__THRESHOLD)
				reason += "Loan found to be above the delinquency threshold\n";
			if(loan.getCreditScore() < CREDIT_SCORE__THRESHOLD)
				reason += "Borrower of loan found to have low credit score\n";
				
			loan.setComments(reason + loan.getComments());
				
		} else {
			if (loan.getSecured().equalsIgnoreCase("yes")) {
				loan.setRisk(3);
			} else {
				// keep it simple, create a random number between 1 and 8
				Random rand = new Random();
				int randInt = rand.nextInt(8 - 1 + 1) + 1;
				loan.setRisk(randInt);
			}

			if (loan.getRisk() < RISK_THRESHOLD){
				loan.setApproved(true);
				reason = "\n< approval check: pass >\n";
			}	
			else{
				loan.setApproved(false);
				reason = "\n< approval check: fail >\nLoan found to be above the risk threshold\n";
			}
			loan.setComments(reason + loan.getComments());
		}
		return loan;
	}

}
