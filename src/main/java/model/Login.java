package model;

/**
 * ログインアイテムモデルクラス
 */
public class Login {
	private int userId;
	private String pass;
	private String email;
	private String userName;

	public Login(String email, String pass) {
		this.email = email;
		this.pass = pass;
	}

	public int getUserId() {
		return userId;
	}

	public String getPass() {
		return pass;
	}

	public String getEmail() {
		return email;
	}

	public String getUserName() {
		return userName;
	}
}
