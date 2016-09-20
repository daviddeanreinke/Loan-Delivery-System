package com.revature.webservice;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.revature.business.Delegate;
import com.revature.dataservice.DAO;
import com.revature.dataservice.DAOImpl;
import com.revature.dataservice.Institution;
import com.revature.dataservice.Loan;
import com.revature.utility.HibernateUtility;

@Singleton
@Path("Loans")
public class LoanResource {
	
	// Helper method
	public List<Loan> verifyLoans(Map<Integer, List<Loan>> map){  // need to fix this to return a response but problem in commit
		if(map.isEmpty()) return null;
		// iterate through the map to get the key(instID) and loan list
		Iterator it = map.entrySet().iterator();
		Map.Entry<Integer, List<Loan>> pair = (Map.Entry<Integer, List<Loan>>)it.next();
		int instID = pair.getKey();
		List<Loan> loans = pair.getValue();
		Session session = HibernateUtility.getSessionFactory().openSession();
		DAO dao = new DAOImpl();
		// first need to approve the loans,
		// then able to commit
		List<Loan> verifiedLoans = new ArrayList<>();
		Delegate del = new Delegate();
		try{
			for(Loan loan: loans){
				verifiedLoans.add(del.processLoan(loan, instID));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			session.close();
		}
		return verifiedLoans;
	}
	
	@POST
	@Path("verifyLoans")
	@Produces(value={MediaType.APPLICATION_JSON})
	@Consumes(value={MediaType.APPLICATION_JSON})
	public Response verifyLoans2(Map<Integer, List<Loan>> map){	
		if(map.isEmpty()) return null;
		
		List<Loan> verifiedLoans = verifyLoans(map);
		
		return Response.ok(verifiedLoans).build();
	}
	
	
	@POST
	@Path("commitLoans")
	@Consumes(value={MediaType.APPLICATION_JSON})
	@Produces(value={MediaType.APPLICATION_JSON})
	public Response commitLoans(Map<Integer, List<Loan>> map){
		if(map.isEmpty()) return null;
		// iterate through the map to get the key(instID) and loan list
		Iterator it = map.entrySet().iterator();
		Map.Entry<Integer, List<Loan>> pair = (Map.Entry<Integer, List<Loan>>)it.next();
		int instID = pair.getKey();
		List<Loan> loans = pair.getValue();
		Session session = HibernateUtility.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		List<Loan> verifiedLoans = new ArrayList<>();
		Delegate del = new Delegate();
		try{
			// first need to approve/verify the loans,
			// then able to commit
			verifiedLoans =  verifyLoans(map);   
			del.commitLoans(verifiedLoans, instID);
		}catch(Exception e){
			if (tx != null) {
				tx.rollback();
			}
			System.out.println("Rolling back...");
			e.printStackTrace();
		}finally{
			session.close();
		}
		return Response.ok(verifiedLoans).build();
	}
	
	@POST
	@Path("purchaseLoan")
	@Consumes(value={MediaType.APPLICATION_JSON})
	public Loan purchaseLoan(int loanID){
		Session session = HibernateUtility.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		DAO dao = new DAOImpl();
		Loan loan = new Loan();
		try {
			loan = dao.getLoan(loanID, session);
			dao.purchaseLoan(loanID, session);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		return loan;
	}
	
	@GET
	@Path("/getApprovedLoans")
	@Produces(value={MediaType.APPLICATION_JSON})
	public List<Loan> getApprovedLoans(){
		Session session = HibernateUtility.getSessionFactory().openSession();
		DAO dao = new DAOImpl();
		List<Loan> loans = dao.getApprovedLoans(session);
		loans.size();
		session.close();
		return loans;
	}
	
	private boolean checkInstitution(int instID){
		Session session = HibernateUtility.getSessionFactory().openSession();
		DAO dao = new DAOImpl();
		Institution inst = dao.getInstitution(instID, session);
		if(inst == null)
			return false;
		return true;
	}
	
}
