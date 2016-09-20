package com.revature.dataservice;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name = "lds2_users")
public class User{

	@Id
	@Column(name="u_id")
	@GeneratedValue
	private int uid;
	
	@Column(name="inst_id", insertable=false, updatable=false)
	private int instID;
	
	@Column(name="firstname")
	private String firstname;
	
	@Column(name="lastname")
	private String lastname;
	
	@Column(name="email")
	private String email;

	@Column(name = "username", unique=true, nullable=false)
	private String username;

	@Column(name = "password", nullable=false)
	private String password;
	
	@ManyToOne
	@JoinColumn(name="inst_id")
	private Institution institution;

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

	public int getInstID() {
		return instID;
	}

	public void setInstID(int instID) {
		this.instID = instID;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

		
}
