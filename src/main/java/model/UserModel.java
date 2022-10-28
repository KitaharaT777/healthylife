package model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * ユーザーモデルクラス
 */
public class UserModel implements Serializable{
	private int userId;
	private String userName;
	private String pass;
	private String email;
	
	private Date birthday;
	private int sex;
	private String country;
	private int isAISearch;
	private int isWithdrawaled;
	private int isDeleted;
	
	private Timestamp createdAt;
	private Timestamp updatedAt;
	
	private int loginflg;
	
	public UserModel() {
	}
	
	//ログイン用
	public UserModel(int userId, String email, String pass) {
		this.userId=userId;
		this.pass=pass;
		this.email=email;
	}
	
	//ログイン失敗用
	public UserModel(String email, String pass, int loginflg) {
		this.pass=pass;
		this.email=email;
		this.loginflg=loginflg;
	}
	
	//新規登録のエラー用
	public UserModel(String email, String pass, String userName) {
		this.userName=userName;
		this.pass=pass;
		this.email=email;
	}
	
	//ログインセッション用
	public UserModel(int userId, String email, String pass, int loginflg) {
		this.userId=userId;
		this.pass=pass;
		this.email=email;
		this.loginflg=loginflg;
	}
	
	public UserModel(int userId, String email, String pass, String userName) {
		this.userId=userId;
		this.userName=userName;
		this.pass=pass;
		this.email=email;
	}
	
	//ログインセッション用（TODO）
	public UserModel(int userId, String email, String pass, String userName, int loginflg) {
		this.userId=userId;
		this.userName=userName;
		this.pass=pass;
		this.email=email;
		this.loginflg=loginflg;
	}
	
	public int getUserId() {return userId;}
	public String getPass() {return pass;}
	public String getUserName() {return userName;}
	public String getEmail() {return email;}
	public int getLoginflg() {return loginflg;}

	public void setUserId(int userId) {this.userId=userId;}
	public void setUserName(String userName) {this.userName=userName;}
	public void setPass(String pass) {this.pass=pass;}
	public void setEmail(String email) {this.email=email;}
	public void setLoginflg(int loginflg) {this.loginflg=loginflg;}
	
	public Date getBirthday() {
		return this.birthday;
	}
	
	public void setBirthday(Date birthday) {
		this.birthday=birthday;
	}
	
	public int getSex() {
		return this.sex;
	}
	
	public void setSex(int sex) {
		this.sex=sex;
	}
	
	public String getCountry() {
		return this.country;
	}
	
	public void setCountry(String country) {
		this.country=country;
	}
	
	public int getIsAISearch() {
		return this.isAISearch;
	}
	
	public void setIsAISearch(int isAISearch) {
		this.isAISearch=isAISearch;
	}
	
	public int getIsWithdrawaled() {
		return this.isWithdrawaled;
	}
	
	public void setIsWithdrawaled(int isWithdrawaled) {
		this.isWithdrawaled=isWithdrawaled;
	}
	
	public int getIsDeleted() {
		return this.isDeleted;
	}
	
	public void setIsDeleted(int isDeleted) {
		this.isDeleted=isDeleted;
	}
	
	public Timestamp getCreatedAt() {
		return this.createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return this.updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}
}
