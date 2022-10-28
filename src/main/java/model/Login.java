package model;

public class Login {
	private int userId;
	private String pass;
	private String email;
	private String userName;
	/*private int loginflg;
	
	public Login(int loginflg) {
		this.loginflg=loginflg;
	}*/
	
	/*
	public Login(int userId, String pass) {
		this.userId=userId;
		this.pass=pass;
	}
	*/
	
	public Login(String email, String pass) {
		this.email=email;
		this.pass=pass;
	}
	
	public int getUserId() {return userId;}
	public String getPass() {return pass;}
	public String getEmail() {return email;}
	public String getUserName() {return userName;}
	//public int getLoginflg() {return loginflg;}
}
