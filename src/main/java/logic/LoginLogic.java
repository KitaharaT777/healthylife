package logic;

import dao.UserDAO;
import model.Login;
import model.UserModel;

/**
 * ログインロジッククラス
 */
public class LoginLogic {

	/**
	 * ユーザーのユーザー情報を探します。
	 * @param model ユーザーモデル
	 * @return 結果（true:成功、false:失敗）
	 */
	public boolean execute(Login login) {
		UserDAO dao = new UserDAO();
		UserModel account = dao.findByLogin(login);
		return account != null;
	}
}
