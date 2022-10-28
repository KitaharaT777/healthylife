package dao;

import java.sql.Connection;
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
public class UserDAO{
		// データベース関連
		/** JDBCドライバ名 */
		public static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

		/** JDBC接続文字列（文字コードをutf8mb4_general_ciに設定） */
		public static final String JDBC_URL = "jdbc:mysql://localhost/healthylife?connectionCollation=utf8mb4_general_ci";

		/** データベース接続ユーザー名 */
		public static final String DB_USER = "root";

		/** データベース接続パスワード */
		public static final String DB_PASS = "root";
		
		public UserModel findByLogin(Login login){
			UserModel account=null;
			
			try {
				// JDBCドライバを読み込み
				Class.forName(DRIVER_NAME);
			
				//データベースに接続
			try(Connection conn=DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
			
				System.out.println("UserDAOのDBに接続");
				
				//SELECT文の準備
				//String sql="SELECT * FROM USERS WHERE USER_ID= ? AND PASS= ?";
				//String sql="SELECT * FROM USERS WHERE EMAIL= ? AND PASSWORD= ?";
				String sql="SELECT * FROM USERS WHERE MAIL_ADDRESS= ? AND PASSWORD= ?";
				PreparedStatement pStmt=conn.prepareStatement(sql);
				pStmt.setString(1, login.getEmail());
				pStmt.setString(2, login.getPass());
				
				System.out.println("UserSql: "+ sql);
			
				//SELECTを実行
				ResultSet rs=pStmt.executeQuery();
			
				//一致したユーザーが存在した場合そのユーザーを表すAccountインスタンスを生成
				if(rs.next()) {
					//System.out.println("rs: "+rs);
					//結果表からデータを取得
					int userId=rs.getInt("USER_ID");
					//String name=rs.getString("NAME");
					String email=rs.getString("MAIL_ADDRESS");
					String pass=rs.getString("PASSWORD");
					System.out.println("ユーザー結果: " + userId + email + pass);
					//account= new Account(userId, email, pass, name);
					account= new UserModel(userId, email, pass);
								
				}else {
					System.out.println("UserDAOのユーザーが一致しない");
				}
			}
			}catch(SQLException e) {
				System.out.println("UserDAOのfindByLogin失敗");
				e.printStackTrace();
				return null;
			}catch(ClassNotFoundException e) {
				System.out.println("UserDAOのNotFoundfindAll失敗");
				e.printStackTrace();
				return null;
			}
			
			//見つかったユーザーまたはnullを返す
			return account;
		}
		
		public boolean addAccount(UserModel account) {
			try {
				// JDBCドライバを読み込み
				Class.forName(DRIVER_NAME);
			
			//データベースに接続
			try(Connection conn=DriverManager.getConnection(JDBC_URL,DB_USER,DB_PASS)){
				
				//INSERT文の準備（idは自動採番）
				String sql="INSERT INTO USERS (EMAIL, PASSWORD, NAME) VALUES (?, ?, ?)";
				PreparedStatement pStmt=conn.prepareStatement(sql);
				
				//INSERT文中の?に使用する値を設定しSQLを完成
				pStmt.setString(1,account.getEmail());
				pStmt.setString(2,account.getPass());
				pStmt.setString(3,account.getUserName());
			
				
				//INSERT文を実行
				int result=pStmt.executeUpdate();
				if(result!=1) {
					System.out.println("UserDAOの?をINSERT失敗");
					return false;
				}
			}
			}catch(SQLException e) {
				System.out.println("UserDAOのcreate失敗");
				e.printStackTrace();
				return false;
			}catch(ClassNotFoundException e) {
				System.out.println("UserDAOのNotFoundAddAccount失敗");
				e.printStackTrace();
				return false;
			}
			return true;
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
						/*model.setId(rs.getInt("id"));
						model.setEmail(rs.getString("email"));
						model.setPassword(rs.getString("password"));
						model.setName(rs.getString("name"));
						model.setIsDeleted(rs.getInt("is_deleted"));
						*/model.setCreatedAt(rs.getTimestamp("created_at"));
						model.setUpdatedAt(rs.getTimestamp("updated_at"));

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
							/*model.setId(rs.getInt("id"));
							model.setEmail(rs.getString("email"));
							model.setPassword(rs.getString("password"));
							model.setName(rs.getString("name"));
							model.setIsDeleted(rs.getInt("is_deleted"));
							model.setCreatedAt(rs.getTimestamp("created_at"));
							model.setUpdatedAt(rs.getTimestamp("updated_at"));
						*/} else {
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
							/*model.setId(rs.getInt("id"));
							model.setEmail(rs.getString("email"));
							model.setPassword(rs.getString("password"));
							model.setName(rs.getString("name"));
							model.setIsDeleted(rs.getInt("is_deleted"));
							model.setCreatedAt(rs.getTimestamp("created_at"));
							model.setUpdatedAt(rs.getTimestamp("updated_at"));
						*/} else {
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
				String sql = "insert into users (email, password, name, is_deleted) values (?, ?, ?, ?)";

				// SQLを実行する準備をする。
				try (PreparedStatement stmt = connection.prepareStatement(sql)) {
					// パラメータを設定する
					stmt.setString(1, model.getEmail());
					/*stmt.setString(2, model.getPassword());
					stmt.setString(3, model.getName());
					stmt.setInt(4, model.getIsDeleted());
					*/
					
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
		 * @return 実行結果 1:成功、その他:エラーコード
		 */
		public int update(Connection connection, UserModel model) {
			try {
				// SQL文を設定する。
				String sql = "update users set email=?, password=?, name=?, is_deleted=? where id=?";

				// SQLを実行する準備をする
				try (PreparedStatement stmt = connection.prepareStatement(sql)) {
					// パラメータを設定する
					stmt.setString(1, model.getEmail());
					/*stmt.setString(2, model.getPassword());
					stmt.setString(3, model.getName());
					stmt.setInt(4, model.getIsDeleted());
					stmt.setInt(5, model.getId());
					*/
					// SQLを実行する
					stmt.executeUpdate();
				}
			} catch (SQLException e) {
				e.printStackTrace();

				return e.getErrorCode();
			}

			return DatabaseSettings.DB_EXECUTION_SUCCESS;
		}
}