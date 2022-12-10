package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Login;
import model.UserModel;
import settings.DatabaseSettings;

/**
 * ユーザーDAOクラス
 */
public class UserDAO {
	// データベース関連
	/** JDBCドライバ名 */
	public static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

	/** JDBC接続文字列（文字コードをutf8mb4_general_ciに設定） */
	public static final String JDBC_URL = "jdbc:mysql://localhost/healthylife?connectionCollation=utf8mb4_general_ci";

	/** データベース接続ユーザー名 */
	public static final String DB_USER = "root";

	/** データベース接続パスワード */
	public static final String DB_PASS = "root";

	public UserModel findByLogin(Login login) {
		UserModel account = null;

		try {
			// JDBCドライバを読み込み
			Class.forName(DRIVER_NAME);

			//データベースに接続
			try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

				//SELECT文の準備
				String sql = "SELECT * FROM USERS WHERE MAIL_ADDRESS= ? AND PASSWORD= ?";
				PreparedStatement pStmt = conn.prepareStatement(sql);
				pStmt.setString(1, login.getEmail());
				pStmt.setString(2, login.getPass());

				//SELECTを実行
				ResultSet rs = pStmt.executeQuery();

				//一致したユーザーが存在した場合そのユーザーを表すAccountインスタンスを生成
				if (rs.next()) {
					//結果表からデータを取得
					int id = rs.getInt("ID");
					int userId = rs.getInt("USER_ID");
					String email = rs.getString("MAIL_ADDRESS");
					String pass = rs.getString("PASSWORD");
					Date birthday = rs.getDate("BIRTHDAY");
					int sex = rs.getInt("SEX");
					String country = rs.getString("COUNTRY");

					account = new UserModel(id, userId, email, pass, birthday, sex, country);
				} else {
					System.out.println("UserDAOのユーザーが一致しない");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}

		//見つかったユーザーまたはnullを返す
		return account;
	}

	/**
	 * Usersテーブルの最大IDを取得します。（user_idの採番用で今後は改善したい）
	 *
	 * @param Connection connection データベースコネクションのインスタンス
	 * @return Usersテーブルの最大ID
	 */
	public int maxID(Connection connection) {
		System.out.println("MaxIDを確認");
		// SQL文を設定する。
		String sql = "select MAX(id) from users ";

		try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
			//最大IDが返ってくる
			if (rs.next()) {
				//結果表からデータを取得
				int maxId = rs.getInt("MAX(id)");

				return maxId;
			} else {
				return 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * ユーザーを全件取得します。
	 *
	 * @param Connection connection データベースコネクションのインスタンス
	 * @return 検索結果（ユーザーモデルのリスト）
	 */
	public List<UserModel> findAll(Connection connection) {
		// レコードを格納するArrayListを生成する。
		List<UserModel> list = new ArrayList<UserModel>();

		try {
			// SQL文を設定する。
			String sql = "select * from users order by id";

			// SQLを実行する準備をして、実行する。
			try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
				// レコードが存在する間、処理を行う。
				while (rs.next()) {
					// UserModelのインスタンスを生成する。
					UserModel model = new UserModel();

					// フィールドに値を設定する。
					model.setId(rs.getInt("ID"));
					model.setUserId(rs.getInt("USER_ID"));
					model.setEmail(rs.getString("MAIL_ADDRESS"));
					model.setPass(rs.getString("PASSWORD"));
					model.setBirthday(rs.getDate("birthday"));
					model.setSex(rs.getInt("sex"));
					model.setCountry(rs.getString("country"));
					model.setIsAISearch(rs.getInt("is_ai_search"));
					model.setIsDeleted(rs.getInt("is_withdrawaled"));
					model.setIsDeleted(rs.getInt("is_deleted"));
					model.setCreatedAt(rs.getTimestamp("create_date_time"));
					model.setUpdatedAt(rs.getTimestamp("update_date_time"));

					// レコードをArrayListに追加する。
					list.add(model);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();

			return null;
		}

		return list;
	}

	/**
	 * 指定ユーザーIDのユーザーを1件検索します。
	 *
	 * @param Connection connection データベースコネクションのインスタンス
	 * @param id         ユーザーID
	 * @return 検索結果（ユーザーモデル）
	 */
	public UserModel findOne(Connection connection, int id) {
		// レコードを格納するUserModelのインスタンスを生成する。
		UserModel model = new UserModel();

		try {
			// SQL文を設定する。
			String sql = "select * from users where id=?";

			// SQLを実行する準備をする
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// パラメータを設定する。
				stmt.setInt(1, id);

				// SQLを実行する
				try (ResultSet rs = stmt.executeQuery()) {
					// レコードが存在するとき、
					if (rs.next()) {
						// フィールドに値を設定する。
						model.setId(rs.getInt("ID"));
						model.setUserId(rs.getInt("USER_ID"));
						model.setEmail(rs.getString("MAIL_ADDRESS"));
						model.setPass(rs.getString("PASSWORD"));
						model.setBirthday(rs.getDate("birthday"));
						model.setSex(rs.getInt("sex"));
						model.setCountry(rs.getString("country"));
						model.setIsAISearch(rs.getInt("is_ai_search"));
						model.setIsDeleted(rs.getInt("is_withdrawaled"));
						model.setIsDeleted(rs.getInt("is_deleted"));
						model.setCreatedAt(rs.getTimestamp("create_date_time"));
						model.setUpdatedAt(rs.getTimestamp("update_date_time"));
					} else {
						model = null;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();

			return null;
		}

		return model;
	}

	/**
	 * キーワードのユーザーを検索します。
	 *
	 * @param Connection connection データベースコネクションのインスタンス
	 * @param keyWord         ユーザーID
	 * @return 検索結果（ユーザーモデル）
	 */
	public List<UserModel> findByKeyWord(Connection connection, String keyWord) {
		// レコードを格納するArrayListを生成する。
		List<UserModel> list = new ArrayList<UserModel>();

		try {
			// SQL文を設定する。
			String sql = "select * from users "
					+ "where is_deleted=0 "
					+ "and mail_address like ? "
					+ "order by id asc";

			// SQLを実行する準備をする
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// パラメータを設定する。
				stmt.setString(1, "%" + keyWord + "%");

				// SQLを実行する
				try (ResultSet rs = stmt.executeQuery()) {
					// レコードが存在するとき、
					while (rs.next()) {
						// レコードを格納するUserModelのインスタンスを生成する。
						UserModel model = new UserModel();

						// フィールドに値を設定する。
						model.setId(rs.getInt("ID"));
						model.setUserId(rs.getInt("USER_ID"));
						model.setEmail(rs.getString("MAIL_ADDRESS"));
						model.setPass(rs.getString("PASSWORD"));
						model.setBirthday(rs.getDate("birthday"));
						model.setSex(rs.getInt("sex"));
						model.setCountry(rs.getString("country"));
						model.setIsAISearch(rs.getInt("is_ai_search"));
						model.setIsDeleted(rs.getInt("is_withdrawaled"));
						model.setIsDeleted(rs.getInt("is_deleted"));
						model.setCreatedAt(rs.getTimestamp("create_date_time"));
						model.setUpdatedAt(rs.getTimestamp("update_date_time"));

						// レコードをArrayListに追加する。
						list.add(model);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();

			return null;
		}

		return list;
	}

	/**
	 * 指定ユーザーemailとpasswordのユーザーを1件検索します。
	 *
	 * @param connection データベースコネクションのインスタンス
	 * @param email emailアドレス
	 * @param password パスワード
	 * @return 検索結果（ユーザーモデル）
	 */
	public UserModel findOne(Connection connection, String email, String password) {
		// レコードを格納するUserModelのインスタンスを生成する。
		UserModel model = new UserModel();

		try {
			// SQL文を設定する
			String sql = "select * from users where is_deleted=0 and email=? and password=?";

			// SQLを実行する準備をする。
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// パラメータを設定する。
				stmt.setString(1, email);
				stmt.setString(2, password);

				// SQLを実行する。
				try (ResultSet rs = stmt.executeQuery()) {
					// SQLの実行結果を取得する。
					if (rs.next()) {
						model.setId(rs.getInt("id"));
						model.setEmail(rs.getString("email"));
						model.setPass(rs.getString("password"));
						model.setIsDeleted(rs.getInt("is_deleted"));
						model.setCreatedAt(rs.getTimestamp("created_at"));
						model.setUpdatedAt(rs.getTimestamp("updated_at"));
						} else {
						model = null;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();

			return null;
		}

		return model;
	}

	/**
	 * ユーザーを1件追加します。
	 *
	 * @param Connection connection データベースコネクションのインスタンス
	 * @param model      UserModel
	 * @return 実行結果 1:成功、その他:エラーコード
	 */
	public int create(Connection connection, UserModel model) {
		try {
			// SQL文を設定する。
			String sql = "insert into users (user_id, mail_address, password, birthday, sex, country, is_ai_search, is_withdrawaled, is_deleted) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

			// SQLを実行する準備をする。
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// パラメータを設定する
				stmt.setInt(1, model.getUserId());
				stmt.setString(2, model.getEmail());
				stmt.setString(3, model.getPass());
				stmt.setDate(4, model.getBirthday());
				stmt.setInt(5, model.getSex());
				stmt.setString(6, model.getCountry());
				stmt.setInt(7, model.getIsAISearch());
				stmt.setInt(8, model.getIsWithdrawaled());
				stmt.setInt(9, model.getIsDeleted());

				// SQLを実行する。
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();

			return e.getErrorCode();
		}

		return DatabaseSettings.DB_EXECUTION_SUCCESS;
	}

	/**
	 * 指定ユーザーIDのユーザーを1件更新します。
	 *
	 * @param model
	 * @return 結果（true:成功、false:失敗）
	 */
	public boolean update(Connection connection, UserModel model) {
		try {
			// SQL文を設定する。
			String sql = "update users set "
					+ "user_id=?, "
					+ "mail_address=?, "
					+ "password=?, "
					+ "birthday=?, "
					+ "sex=?, "
					+ "country=? "
					+ "where id=?";

			// SQLを実行する準備をする
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// パラメータを設定する
				stmt.setInt(1, model.getUserId());
				stmt.setString(2, model.getEmail());
				stmt.setString(3, model.getPass());
				stmt.setDate(4, model.getBirthday());
				stmt.setInt(5, model.getSex());
				stmt.setString(6, model.getCountry());
				stmt.setInt(7, model.getId());

				// SQLを実行する
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();

			return false;
		}
		return true;
	}

	/**
	 * 指定ユーザーIDのユーザーを1件更新します。(退会フラグ)
	 *
	 * @param model
	 * @return 結果（true:成功、false:失敗）
	 */
	public boolean updateIsWithdrawaled(Connection connection, UserModel model) {
		try {
			// SQL文を設定する。
			String sql = "update users set "
					+ "is_withdrawaled=? "
					+ "where id=?";

			// SQLを実行する準備をする
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// パラメータを設定する
				stmt.setInt(1, model.getIsWithdrawaled());
				stmt.setInt(2, model.getId());

				System.out.println("ID: " + model.getId());

				// SQLを実行する
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}