package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DiseaseItemModel;
import model.SearchItemModel;
import model.TodoItemModel;
import model.UserModel;

/**
 * 検索履歴アイテムDAOクラス
 */
public class SearchItemDAO {
	/** 基本となるSELECT文 */
	private final String BASE_SQL = "select "
			/*+ "t.id,"
			+ "t.user_id,"
			+ "t.registration_date,"
			+ "t.expiration_date,"
			+ "t.finished_date,"
			+ "t.todo_item,"
			+ "t.is_deleted,"
			+ "t.created_at,"
			+ "t.updated_at,"
			+ "u.email,"
			+ "u.password,"
			+ "u.name,"
			+ "u.is_deleted as user_is_deleted,"
			+ "u.created_at as user_created_at,"
			+ "u.updated_at as user_updated_at "
			+ "from todo_items t "
			+ "inner join users u on t.user_id=u.id ";
			 */
			+ "* from user_search ";

	/**
	 * 検索履歴アイテムを全件取得します。
	 *
	 * @param Connection connection データベースコネクションのインスタンス
	 * @return 病名アイテムモデルのArrayList
	 */
	public List<SearchItemModel> findAll(Connection connection) {
		// レコードを格納するArrayListを生成する。
		List<SearchItemModel> list = new ArrayList<SearchItemModel>();

		try {
			// SQL文を設定する。
			String sql = BASE_SQL
					//+ "where t.is_deleted=0 and u.is_deleted=0 "
					//+ "order by t.expiration_date asc, t.id desc";
					;
					
			// SQLを実行する準備をする。
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// SQLを実行する
				try (ResultSet rs = stmt.executeQuery()) {
					// SQLの実行結果をArrayListに格納する。
					while (rs.next()) {
						SearchItemModel model = new SearchItemModel();
						model.setId(rs.getLong("id"));
						model.setUserId(rs.getInt("user_id"));
						model.setMark(rs.getInt("mark"));
						model.setSearchDate(rs.getDate("search_date"));
						model.setWord(rs.getString("word"));
						model.setIsDeleted(rs.getInt("is_deleted"));
						model.setCreatedAt(rs.getTimestamp("create_date_time"));
						model.setUpdatedAt(rs.getTimestamp("update_date_time"));
						model.setResult1Prob(rs.getInt("result1_probability"));
						model.setResult1NameId(rs.getInt("result1_name_id"));
						model.setResult2Prob(rs.getInt("result2_probability"));
						model.setResult2NameId(rs.getInt("result2_name_id"));
						model.setResult3Prob(rs.getInt("result3_probability"));
						model.setResult3NameId(rs.getInt("result3_name_id"));
						
						/*UserModel userModel = new UserModel();
						userModel.setEmail(rs.getString("email"));
						userModel.setPassword(rs.getString("password"));
						userModel.setName(rs.getString("name"));
						userModel.setIsDeleted(rs.getInt("user_is_deleted"));
						userModel.setCreatedAt(rs.getTimestamp("user_created_at"));
						userModel.setUpdatedAt(rs.getTimestamp("user_updated_at"));

						model.setUserModel(userModel);
						*/
						
						// ArrayListにレコードを追加する。
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
	 * 病名アイテムを1件取得します。
	 *
	 * @param Connection connection データベースコネクションのインスタンス
	 * @param id         病名アイテムID
	 * @param userId     ユーザーID
	 * @return 病名アイテムモデル
	 */
	public DiseaseItemModel findOne(Connection connection, int id, int userId) {
		// DiseaseItemModelクラスのインスタンスを生成する。
		DiseaseItemModel model = new DiseaseItemModel();

		try {
			// SQL文を設定する。
			String sql = BASE_SQL
					+ "where t.is_deleted=0 "
					+ "and u.is_deleted=0 "
					+ "and t.id=? "
					+ "and t.user_id=?";

			// SQLを実行する準備をする。
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// パラメータを設定
				stmt.setInt(1, id);
				stmt.setInt(2, userId);

				// SQLを実行する。
				try (ResultSet rs = stmt.executeQuery()) {
					// SQLの実行結果を取得する。
					if (rs.next()) {
						// 実行結果があるとき、モデルに値を設定する。
						model.setId(rs.getInt("id"));
						/*model.setUserId(rs.getInt("user_id"));
						model.setRegistrationDate(rs.getDate("registration_date"));
						model.setExpirationDate(rs.getDate("expiration_date"));
						model.setFinishedDate(rs.getDate("finished_date"));
						model.setTodoItem(rs.getString("todo_item"));
						*/model.setIsDeleted(rs.getInt("is_deleted"));
						model.setCreatedAt(rs.getTimestamp("created_at"));
						model.setUpdatedAt(rs.getTimestamp("updated_at"));

						UserModel userModel = new UserModel();
						userModel.setEmail(rs.getString("email"));
						//userModel.setPassword(rs.getString("password"));
						//userModel.setName(rs.getString("name"));
						userModel.setIsDeleted(rs.getInt("user_is_deleted"));
						userModel.setCreatedAt(rs.getTimestamp("user_created_at"));
						userModel.setUpdatedAt(rs.getTimestamp("user_updated_at"));

						model.setUserModel(userModel);
					} else {
						// 実行結果がないときは、nullを代入する。
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
	 * 指定ユーザーIDの検索履歴アイテムを取得します。
	 *
	 * @param Connection connection データベースコネクションのインスタンス
	 * @param userId     ユーザーID
	 * @param limit      取得するレコード数（リミット値）
	 * @param offset     取得開始する行数（オフセット値）
	 * @return SearchItemModelのArrayList
	 */
	public List<SearchItemModel> findByUserId(Connection connection, int userId, int limit, int offset) {
		// レコードを格納するArrayListを生成する。
		List<SearchItemModel> list = new ArrayList<SearchItemModel>();
		try {
			// SQL文を設定する。
			String sql = BASE_SQL
					+ "where is_deleted=0 "
					+ "and user_id=? "
					//+ "order by t.expiration_date asc, t.id desc "
					+ "limit ? offset ?";
			
			// SQLを実行する準備をする。
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// パラメータを設定する。
				stmt.setInt(1, userId);
				stmt.setInt(2, limit);
				stmt.setInt(3, offset);
				
				System.out.println("findByUserIdSQL:"+ sql);

				// SQLを実行する
				try (ResultSet rs = stmt.executeQuery()) {
					// レコードが存在する間、処理を行う。
					while (rs.next()) {
						// SearchItemModelのインスタンスを生成する。
						SearchItemModel model = new SearchItemModel();

						// フィールドに値を設定する。
						//model.setId(rs.getInt("id"));
						/*model.setUserId(rs.getInt("user_id"));
						model.setRegistrationDate(rs.getDate("registration_date"));
						model.setExpirationDate(rs.getDate("expiration_date"));
						model.setFinishedDate(rs.getDate("finished_date"));
						model.setTodoItem(rs.getString("todo_item"));
						*///model.setIsDeleted(rs.getInt("is_deleted"));
						//model.setCreatedAt(rs.getTimestamp("created_at"));
						//model.setUpdatedAt(rs.getTimestamp("updated_at"));
						
						model.setId(rs.getLong("id"));
						model.setUserId(rs.getInt("user_id"));
						model.setMark(rs.getInt("mark"));
						model.setSearchDate(rs.getDate("search_date"));
						model.setWord(rs.getString("word"));
						model.setIsDeleted(rs.getInt("is_deleted"));
						model.setCreatedAt(rs.getTimestamp("create_date_time"));
						model.setUpdatedAt(rs.getTimestamp("update_date_time"));
						model.setResult1Prob(rs.getInt("result1_probability"));
						model.setResult1NameId(rs.getInt("result1_name_id"));
						model.setResult2Prob(rs.getInt("result2_probability"));
						model.setResult2NameId(rs.getInt("result2_name_id"));
						model.setResult3Prob(rs.getInt("result3_probability"));
						model.setResult3NameId(rs.getInt("result3_name_id"));

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
	 * 指定ユーザーIDの検索履歴アイテムを全件取得します。
	 *
	 * @param Connection connection データベースコネクションのインスタンス
	 * @param userId     ユーザーID
	 * @return SearchItemModelのArrayList
	 */
	public List<SearchItemModel> findByUserId(Connection connection, int userId) {
		System.out.println("Integer.MAX_VALUE:"+ Integer.MAX_VALUE);
		return findByUserId(connection, userId, Integer.MAX_VALUE, 0);
	}

	/**
	 * 指定ユーザーIDのTODOアイテムのレコードの件数を取得します。
	 *
	 * @param Connection connection データベースコネクションのインスタンス
	 * @param userId     ユーザーID
	 * @return レコード数
	 */
	public int countByUserId(Connection connection, int userId) {
		try {
			// SQL文を設定する。
			String sql = "select count(t.id) as cnt "
					+ "from todo_items t "
					+ "inner join users u on t.user_id=u.id "
					+ "where t.is_deleted=0 "
					+ "and t.user_id=? "
					+ "order by t.expiration_date asc, t.id desc";

			// SQLを実行する準備をする。
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// パラメータを設定する
				stmt.setInt(1, userId);

				// SQLを実行する。
				try (ResultSet rs = stmt.executeQuery()) {
					if (rs.next()) {
						return rs.getInt("cnt");
					} else {
						return 0;
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();

			return 0;
		}
	}

	/**
	 * 指定ユーザーIDの病名アイテムをキーワードで検索します。
	 *
	 * @param Connection connection データベースコネクションのインスタンス
	 * @return 病名アイテムモデルのArrayList
	 */
	public List<DiseaseItemModel> findByKeyWord(Connection connection, int userId, String keyWord) {
		// レコードを格納するArrayListを生成する。
		List<DiseaseItemModel> list = new ArrayList<DiseaseItemModel>();

		try {
			// SQL文を設定する。
			String sql = BASE_SQL
					+ "where t.is_deleted=0 "
					+ "and t.user_id=? "
					+ "and t.todo_item like ? "
					+ "order by t.expiration_date asc, t.id desc";

			// SQLを実行する準備をする。
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// パラメータを設定する。
				stmt.setInt(1, userId);
				stmt.setString(2, "%" + keyWord + "%");

				// SQLを実行する。
				try (ResultSet rs = stmt.executeQuery()) {
					// SQLの実行結果をArrayListに格納する
					while (rs.next()) {
						DiseaseItemModel model = new DiseaseItemModel();
						model.setId(rs.getInt("id"));
						/*model.setUserId(rs.getInt("user_id"));
						model.setRegistrationDate(rs.getDate("registration_date"));
						model.setExpirationDate(rs.getDate("expiration_date"));
						model.setFinishedDate(rs.getDate("finished_date"));
						model.setTodoItem(rs.getString("todo_item"));
						*/model.setIsDeleted(rs.getInt("is_deleted"));
						model.setCreatedAt(rs.getTimestamp("created_at"));
						model.setUpdatedAt(rs.getTimestamp("updated_at"));

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
	 * 病名アイテムを1件追加します。
	 *
	 * @param Connection connection データベースコネクションのインスタンス
	 * @param model    SearchItemModel
	 * @return 結果（true:成功、false:失敗）
	 */
	public boolean create(Connection connection, SearchItemModel model) {
		try {
			// SQL文を設定する。
			String sql = "insert into todo_items ("
					+ "user_id ,"
					+ "registration_date,"
					+ "expiration_date,"
					+ "finished_date,"
					+ "todo_item,"
					+ "is_deleted"
					+ ") values ("
					+ "?," // user_id
					+ "?," // registration_date
					+ "?," // expiration_date
					+ "?," // finished_date
					+ "?," // todo_item
					+ "?" // is_deleted
					+ ")";

			// SQLを実行する準備をする。
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// パラメータを設定する。
				/*stmt.setInt(1, model.getUserId());
				stmt.setDate(2, model.getRegistrationDate());
				stmt.setDate(3, model.getExpirationDate());
				stmt.setDate(4, model.getFinishedDate());
				stmt.setString(5, model.getTodoItem());
				*/stmt.setInt(6, model.getIsDeleted());

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
	 * 病名アイテムを1件更新します。
	 *
	 * @param Connection connection データベースコネクションのインスタンス
	 * @param model      TodoItemModel
	 * @return 結果（true:成功、false:失敗）
	 */
	public boolean update(Connection connection, TodoItemModel model) {
		try {
			// SQL文を設定する。
			String sql = "update todo_items set "
					+ "user_id=?,"
					+ "registration_date=?,"
					+ "expiration_date=?,"
					+ "finished_date=?,"
					+ "todo_item=?,"
					+ "is_deleted=? "
					+ "where id=?";

			// SQLを実行する準備をする。
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// パラメータを設定する。
				stmt.setInt(1, model.getUserId());
				stmt.setDate(2, model.getRegistrationDate());
				stmt.setDate(3, model.getExpirationDate());
				stmt.setDate(4, model.getFinishedDate());
				stmt.setString(5, model.getTodoItem());
				stmt.setInt(6, model.getIsDeleted());
				stmt.setInt(7, model.getId());

				// SQLを実行する。
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}
}
