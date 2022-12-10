package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.SearchItemModel;

/**
 * 検索履歴アイテムDAOクラス
 */
public class SearchItemDAO {
	/** 基本となるSELECT文 */
	private final String BASE_SQL = "select "
			+ "* from user_search ";

	/**
	 * 検索履歴アイテムを全件取得します。
	 *
	 * @param Connection connection データベースコネクションのインスタンス
	 * @return 検索履歴アイテムモデルのArrayList
	 */
	public List<SearchItemModel> findAll(Connection connection) {
		// レコードを格納するArrayListを生成する。
		List<SearchItemModel> list = new ArrayList<SearchItemModel>();

		try {
			// SQL文を設定する。
			String sql = BASE_SQL;

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
					+ "order by mark desc, search_date asc  "
					+ "limit ? offset ?";

			// SQLを実行する準備をする。
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// パラメータを設定する。
				stmt.setInt(1, userId);
				stmt.setInt(2, limit);
				stmt.setInt(3, offset);

				//System.out.println("findByUserIdSQL:"+ sql);

				// SQLを実行する
				try (ResultSet rs = stmt.executeQuery()) {
					// レコードが存在する間、処理を行う。
					while (rs.next()) {
						// SearchItemModelのインスタンスを生成する。
						SearchItemModel model = new SearchItemModel();

						// フィールドに値を設定する。
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
		//System.out.println("Integer.MAX_VALUE:"+ Integer.MAX_VALUE);
		return findByUserId(connection, userId, Integer.MAX_VALUE, 0);
	}

	/**
	 * 検索履歴アイテムを1件追加します。
	 *
	 * @param Connection connection データベースコネクションのインスタンス
	 * @param model    SearchItemModel
	 * @return 結果（true:成功、false:失敗）
	 */
	public boolean create(Connection connection, SearchItemModel model) {
		try {
			// SQL文を設定する。
			String sql = "insert into user_search ("
					+ "user_id, "
					+ "mark, "
					+ "search_date, "
					+ "word, "
					+ "is_deleted, "
					+ "result1_probability, result1_name_id, "
					+ "result2_probability, result2_name_id, "
					+ "result3_probability, result3_name_id "
					+ ") values ("
					+ "?," // user_id
					+ "?," // mark
					+ "?," // search_date
					+ "?," // word
					+ "?," // is_deleted
					+ "?, ?, " // 1
					+ "?, ?, " // 2
					+ "?, ? " // 3
					+ ")";

			// SQLを実行する準備をする。
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// パラメータを設定する。
				stmt.setInt(1, model.getUserId());
				stmt.setInt(2, model.getMark());
				stmt.setDate(3, model.getSearchDate());
				stmt.setString(4, model.getWord());
				stmt.setInt(5, model.getIsDeleted());
				stmt.setInt(6, model.getResult1Prob());
				stmt.setInt(7, model.getResult1NameId());
				stmt.setInt(8, model.getResult2Prob());
				stmt.setInt(9, model.getResult2NameId());
				stmt.setInt(10, model.getResult3Prob());
				stmt.setInt(11, model.getResult3NameId());

				// SQLを実行する
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("SearchItemCreate失敗");
			e.printStackTrace();

			return false;
		}
		System.out.println("SearchItemCreate成功");
		return true;
	}

	/**
	 * 検索履歴アイテムを1件更新します。(削除フラグ)
	 *
	 * @param Connection connection データベースコネクションのインスタンス
	 * @param model      SearchItemModel
	 * @return 結果（true:成功、false:失敗）
	 */
	public boolean update(Connection connection, SearchItemModel model) {
		try {
			// SQL文を設定する。
			String sql = "update user_search set "
					+ "is_deleted=1 "
					+ "where id=?";

			// SQLを実行する準備をする。
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// パラメータを設定する。
				stmt.setLong(1, model.getId());

				// SQLを実行する。
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * 検索履歴アイテムを1件更新します。(気になるフラグ)
	 *
	 * @param Connection connection データベースコネクションのインスタンス
	 * @param model      SearchItemModel
	 * @return 結果（true:成功、false:失敗）
	 */
	public boolean updateMark(Connection connection, SearchItemModel model) {
		try {
			// SQL文を設定する。
			String sql = "update user_search set "
					+ "mark=1 "
					+ "where id=?";

			// SQLを実行する準備をする。
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// パラメータを設定する。
				stmt.setLong(1, model.getId());

				// SQLを実行する。
				stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();

			return false;
		}

		return true;
	}

	/**
	 * 検索履歴アイテムのレコードの件数を取得します。
	 *
	 * @param Connection connection データベースコネクションのインスタンス
	 * @return レコード数
	 */
	public int countAll(Connection connection) {
		try {
			// SQL文を設定する。
			String sql = "select count(id) as cnt "
					+ "from user_search ";

			// SQLを実行する準備をする。
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {

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
}
