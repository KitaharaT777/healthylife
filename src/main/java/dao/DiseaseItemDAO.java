package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.DiseaseItemModel;

/**
 * 病名アイテムDAOクラス
 */
public class DiseaseItemDAO {
	/** 基本となるSELECT文 */
	private final String BASE_SQL = "select "
			+ "* from name_of_disease ";

	/**
	 * 病名アイテムを全件取得します。
	 *
	 * @param Connection connection データベースコネクションのインスタンス
	 * @return 病名アイテムモデルのArrayList
	 */
	public List<DiseaseItemModel> findAll(Connection connection) {
		// レコードを格納するArrayListを生成する。
		List<DiseaseItemModel> list = new ArrayList<DiseaseItemModel>();

		try {
			// SQL文を設定する。
			String sql = BASE_SQL
					+ "order by name_id asc"; //AdminMainページでname_id順に表示させる

			// SQLを実行する準備をする。
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// SQLを実行する
				try (ResultSet rs = stmt.executeQuery()) {
					// SQLの実行結果をArrayListに格納する。
					while (rs.next()) {
						DiseaseItemModel model = new DiseaseItemModel(); //exportエクセルでの順番

						model.setId(rs.getInt("id")); //1
						model.setNameId(rs.getInt("name_id")); //2
						model.setNameOfDisease(rs.getString("name_of_disease")); //3
						model.setInfo(rs.getString("information")); //4
						model.setLink(rs.getString("link")); //5
						model.setIsDeleted(rs.getInt("is_deleted")); //6
						model.setCreatedAt(rs.getTimestamp("create_date_time")); //7
						model.setUpdatedAt(rs.getTimestamp("update_date_time")); //8

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
					+ "where id=?";

			// SQLを実行する準備をする。
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// パラメータを設定
				stmt.setInt(1, id);

				// SQLを実行する。
				try (ResultSet rs = stmt.executeQuery()) {
					// SQLの実行結果を取得する。
					if (rs.next()) {
						// 実行結果があるとき、モデルに値を設定する。
						model.setId(rs.getInt("id"));
						model.setNameId(rs.getInt("name_id"));
						model.setNameOfDisease(rs.getString("name_of_disease"));
						model.setInfo(rs.getString("information"));
						model.setLink(rs.getString("link"));
						model.setIsDeleted(rs.getInt("is_deleted"));
						model.setCreatedAt(rs.getTimestamp("create_date_time"));
						model.setUpdatedAt(rs.getTimestamp("update_date_time"));

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
	 * 病名アイテムをキーワードで検索します。
	 *
	 * @param Connection connection データベースコネクションのインスタンス
	 * @return 病名アイテムモデルのArrayList
	 */
	public List<DiseaseItemModel> findByKeyWord(Connection connection, String keyWord) {
		// レコードを格納するArrayListを生成する。
		List<DiseaseItemModel> list = new ArrayList<DiseaseItemModel>();

		try {
			// SQL文を設定する。
			String sql = BASE_SQL
					+ "where is_deleted=0 "
					+ "and name_of_disease like ? "
					+ "order by id asc";

			// SQLを実行する準備をする。
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// パラメータを設定する。
				stmt.setString(1, "%" + keyWord + "%");

				// SQLを実行する。
				try (ResultSet rs = stmt.executeQuery()) {
					// SQLの実行結果をArrayListに格納する
					while (rs.next()) {
						DiseaseItemModel model = new DiseaseItemModel();
						model.setId(rs.getInt("id"));
						model.setNameId(rs.getInt("name_id"));
						model.setNameOfDisease(rs.getString("name_of_disease"));
						model.setInfo(rs.getString("information"));
						model.setLink(rs.getString("link"));
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
	 * 病名アイテムを1件追加します。
	 *
	 * @param Connection connection データベースコネクションのインスタンス
	 * @param model      DiseaseItemModel
	 * @return 結果（true:成功、false:失敗）
	 */
	public boolean create(Connection connection, DiseaseItemModel model) {
		try {
			// SQL文を設定する。
			String sql = "insert into name_of_disease ("
					+ "name_id ,"
					+ "name_of_disease,"
					+ "information,"
					+ "link,"
					+ "is_deleted"
					+ ") values ("
					+ "?," // name_id
					+ "?," // name_of_disease
					+ "?," // information
					+ "?," // link
					+ "?" // is_deleted
					+ ")";

			// SQLを実行する準備をする。
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// パラメータを設定する。
				stmt.setInt(1, model.getNameId());
				stmt.setString(2, model.getNameOfDisease());
				stmt.setString(3, model.getInfo());
				stmt.setString(4, model.getLink());
				stmt.setInt(5, model.getIsDeleted());

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
	public boolean update(Connection connection, DiseaseItemModel model) {
		try {
			// SQL文を設定する。
			String sql = "update name_of_disease set "
					+ "name_id=?,"
					+ "name_of_disease=?,"
					+ "information=?,"
					+ "link=?,"
					+ "is_deleted=? "
					+ "where id=?";

			// SQLを実行する準備をする。
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// パラメータを設定する。
				stmt.setInt(1, model.getNameId());
				stmt.setString(2, model.getNameOfDisease());
				stmt.setString(3, model.getInfo());
				stmt.setString(4, model.getLink());
				stmt.setInt(5, model.getIsDeleted());
				stmt.setInt(6, model.getId());

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
