package com.revature.forms;

import org.apache.struts.validator.ValidatorForm;

public class LoginForm extends ValidatorForm{

	private String usr, pwd;

	public String getUsr() {
		return usr;
	}

	public void setUsr(String usr) {
		this.usr = usr;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
}
