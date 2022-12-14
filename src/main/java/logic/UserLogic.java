package logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.UserDAO;
import database.DBConnection;
import model.UserModel;

/**
 * ユーザーロジッククラス
 */
public class UserLogic {

	/**
	 * ユーザーを1件追加します。
	 * @param model ユーザーモデルクラス
	 * @return 実行結果 1:成功、0:失敗、その他:エラーコード(SQLSTATE)
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int create(UserModel model) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			UserDAO dao = new UserDAO();

			return dao.create(conn, model);
		}
	}

	/**
	 * ユーザーを全件取得します。
	 * @return 検索結果（ユーザーモデルのリスト）
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<UserModel> find() throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			UserDAO dao = new UserDAO();

			return dao.findAll(conn);
		}
	}

	/**
	 * キーワードのユーザーを取得します。
	 * @return 検索結果（ユーザーモデルのリスト）
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<UserModel> find(String keyWord) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			UserDAO dao = new UserDAO();

			return dao.findByKeyWord(conn, keyWord);
		}
	}

	/**
	 * Usersテーブルの最大IDを取得します。
	 * @return Usersテーブルの最大ID
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */

	public int maxID() throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			UserDAO dao = new UserDAO();

			return dao.maxID(conn);
		}
	}

	/**
	 * 指定ユーザーIDのユーザーを取得します。
	 * @param userId ユーザーID
	 * @return 検索結果（ユーザーモデル）
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public UserModel find(int userId) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			UserDAO dao = new UserDAO();

			return dao.findOne(conn, userId);
		}
	}

	/**
	 * 指定E-mailアドレスとパスワードのユーザーを取得します。
	 * @param email E-mailアドレス
	 * @param password パスワード
	 * @return 検索結果（ユーザーモデル）
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public UserModel find(String email, String password) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			UserDAO dao = new UserDAO();

			return dao.findOne(conn, email, password);
		}
	}

	/**
	 * 指定ユーザーIDのユーザーを1件更新します。
	 * @param model ユーザーモデル
	 * @return 結果（true:成功、false:失敗）
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean update(UserModel model) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			UserDAO dao = new UserDAO();

			return dao.update(conn, model);
		}
	}

	/**
	 * 指定ユーザーIDのユーザーを1件更新します。(退会フラグ)
	 * @param model ユーザーモデル
	 * @return 結果（true:成功、false:失敗）
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean updateIsWithdrawaled(UserModel model) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			UserDAO dao = new UserDAO();

			return dao.updateIsWithdrawaled(conn, model);
		}
	}
}
