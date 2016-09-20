package com.revature.actions;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.revature.business.Delegate;
import com.revature.dataservice.Loan;

public class CustomerAction extends DispatchAction{

	public ActionForward verify(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		System.out.println("entered CustomerAction: verify");
		
		HttpSession session = req.getSession();
		System.out.println("entered verify action " + req.getSession().getAttribute("uid"));
		List<Loan> sessionLoans = (ArrayList<Loan>)session.getAttribute("sessionLoans"); 
		if(sessionLoans == null){
			System.out.println("making new loan array");
			sessionLoans = new ArrayList<Loan>();
		}
		for(Loan l : sessionLoans)
			System.out.println(l.getBorrowerName());
		
		Loan pointerish = (Loan)form;
		Loan loan = newLoan(pointerish);
		pointerish.setAmount(0);
		pointerish.setType(null);
		pointerish.setSecured(null);
		pointerish.setInterestRate(0);
		pointerish.setBorrowerName(null);
		pointerish.setTaxID(null);
		pointerish.setCreditScore(0);
		pointerish.setMonthly_payment(0);
		pointerish.setDuration(0);
		pointerish.setDelinquency(0);
		pointerish.setIssue_date(null);
		pointerish.setComments(null);
		
		System.out.println("loan form from session: " +loan.getBorrowerName());
		
		int uid = (int)session.getAttribute("uid");
		int instID = (int)session.getAttribute("instID");
		String email = (String)session.getAttribute("email");
		
		Delegate delegate = new Delegate(); 
		Loan result = delegate.processLoan(loan, instID);
		sessionLoans.add(result);
		session.setAttribute("sessionLoans", sessionLoans);
		session.setAttribute("page", "commit");
		// cannot accumlulate the form loans into an array 
		// for some reason it keeps a "pointer" to the last form 
		// ( even though I had added it to an array ) 
		// and will over-write and dupe itself if the page has 
		// not been navigated to other pages?
		return map.findForward("success");
	}
	
	// this seems really redundant but go struts! I think it will work
	private Loan newLoan(Loan loan){
		Loan result = new Loan();
		result.setAmount(loan.getAmount());
		result.setType(loan.getType());
		result.setSecured(loan.getSecured());
		result.setInterestRate(loan.getInterestRate());
		result.setBorrowerName(loan.getBorrowerName());
		result.setTaxID(loan.getTaxID());
		result.setCreditScore(loan.getCreditScore());
		result.setMonthly_payment(loan.getMonthly_payment());
		result.setDuration(loan.getDuration());
		result.setDelinquency(loan.getDelinquency());
		result.setIssue_date(loan.getIssue_date());
		result.setComments(loan.getComments());
		
		return result;
	}
	
	public ActionForward commit(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		System.out.println("entered CustomerAction: commit");
		
		HttpSession session = req.getSession();
		
		// get needed session variables
		List<Loan> sessionLoans = (ArrayList<Loan>)session.getAttribute("sessionLoans");
		int uid = (int)session.getAttribute("uid");
		int instID = (int)session.getAttribute("instID");
		String email = (String)session.getAttribute("email");
		String uname = (String)session.getAttribute("firstname") + " " + (String)session.getAttribute("lastname");
		String date = (String)session.getAttribute("date");
		Delegate delegate = new Delegate(); 
		List<Loan> committedLoans = delegate.commitLoans(sessionLoans, instID);
		// email the client with the results
		String subject = "Loan Commitment at LDS";
		String mailText = 	"Loan Commitment Details at LDS on: " + 
							date + "\t" + "User: " + uname;
		
		// add the committed loans to the daily commit
		// and add to the approved loans on the session market
		List<Loan> dailyLoans = (List<Loan>) session.getAttribute("dailyCommittedLoans");
		List<Loan> approvedLoans = (List<Loan>) session.getAttribute("approvedLoans");
		for(Loan loan: committedLoans){
			dailyLoans.add(loan);
			mailText += loan.toString();
			if(loan.isApproved())
				approvedLoans.add(loan);
		}
		delegate.notifyClient(email, subject, mailText);
		session.setAttribute("dailyCommittedLoans", dailyLoans);
		session.setAttribute("approvedLoans", approvedLoans);
		session.setAttribute("sessionLoans", null);
		session.setAttribute("page", "history");
		// need to this again b/c of an annoying form bug
		// this will clear the form on the commit page
		Loan pointerish = (Loan)form;
		pointerish.setAmount(0);
		pointerish.setType(null);
		pointerish.setSecured(null);
		pointerish.setInterestRate(0);
		pointerish.setBorrowerName(null);
		pointerish.setTaxID(null);
		pointerish.setCreditScore(0);
		pointerish.setMonthly_payment(0);
		pointerish.setDuration(0);
		pointerish.setDelinquency(0);
		pointerish.setIssue_date(null);
		pointerish.setComments(null);
		
		return map.findForward("success");
	}
	
	public ActionForward purchase(ActionMapping map, ActionForm form, HttpServletRequest req, HttpServletResponse resp)
			throws Exception {
		System.out.println("entered CustomerAction: purchase");
		
		HttpSession session = req.getSession();
		List<Loan> newList = new ArrayList<>();
		// get needed session variables
		List<Loan> approvedLoans = (ArrayList<Loan>)session.getAttribute("approvedLoans");
		Loan hiddenloan = (Loan)form;
		int loanID = hiddenloan.getLoanID();
		int instID = (int)session.getAttribute("instID");
		String email = (String)session.getAttribute("email");
		String uname = (String)session.getAttribute("firstname") + " " + (String)session.getAttribute("lastname");
		String date = (String)session.getAttribute("date");
		String subject = "Loan Purchase at LDS";
		String mailText = 	"Loan Purchase Details at LDS on: " + 
							date + "\t" + "User: " + uname;
		Delegate delegate = new Delegate(); 
		String loanDetails = "";
		delegate.purchaseLoan(loanID, email);
		for(Loan loan: approvedLoans){
			if(loan.getLoanID() != loanID){
				newList.add(loan);
			}else{
				loanDetails = loan.toString();
			}
		}
		mailText = mailText + loanDetails;
		System.out.println("notify client: " + email);
		delegate.notifyClient(email, subject, mailText);
		session.setAttribute("approvedLoans", newList);
		session.setAttribute("page", "browse");
		
		return map.findForward("success");
	}
	
}
