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
	 * @return 実行結果 1:成功、0:失敗、その他:エラーコード
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int update(UserModel model) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			UserDAO dao = new UserDAO();

			return dao.update(conn, model);
		}
	}
}
