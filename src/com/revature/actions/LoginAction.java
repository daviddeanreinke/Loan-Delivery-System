package com.revature.actions;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;


import com.revature.business.Delegate;
import com.revature.dataservice.DAO;
import com.revature.dataservice.DAOImpl;
import com.revature.dataservice.Loan;
import com.revature.dataservice.User;
import com.revature.forms.LoginForm;


public class LoginAction extends DispatchAction {

	
	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("entered login action");
		// down cast the ActionForm object to get the form properties
		LoginForm logForm = (LoginForm) form;
		String username = logForm.getUsr();
		String password = logForm.getPwd();

		DAO dao = new DAOImpl();
		User user = dao.login(username, password);
		Calendar cal = Calendar.getInstance();
		Date date = new Date(cal.getTime().getTime());
		// System.out.println(e.getFirstname());
		if (user.getFirstname() != null) {
			HttpSession session = request.getSession();
			session.setAttribute("uid", user.getUid());
			session.setAttribute("email", user.getEmail());
			session.setAttribute("instID", user.getInstID());
			session.setAttribute("firstname", user.getFirstname());	
			session.setAttribute("page", "about");
			session.setAttribute("date", date.toString());
			System.out.println("login success " + user.getUid());
			
			// for user experience and to get rid of use having 
			// to click on a button to get the daily history and 
			// loan marketplace data, load into session
			Delegate delegate = new Delegate();
			List<Loan> approvedLoans = delegate.getApprovedLoans();
			session.setAttribute("approvedLoans", approvedLoans);
			
			int instID = user.getInstID();
			List<Loan> dailyCommittedLoans = delegate.getDailyCommited(instID);
			System.out.println("dailyCommittedLoans: " + dailyCommittedLoans.size());
			session.setAttribute("dailyCommittedLoans", dailyCommittedLoans);
			return mapping.findForward("success");
		} else{
			System.out.println("login failed");
			return mapping.findForward("fail");
		}
			
	}
	
	public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		session.invalidate();
		return mapping.findForward("fail");
		
	}
}
