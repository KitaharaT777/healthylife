package logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.SearchItemDAO;
import database.DBConnection;
import model.SearchItemModel;

/**
 * 病名アイテムロジッククラス
 */
public class SearchItemLogic {

	/**
	 * 検索履歴アイテムを1件追加します。
	 *
	 * @param model 検索履歴アイテムモデル
	 * @return 結果（true:成功、false:失敗）
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean create(SearchItemModel model) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			SearchItemDAO dao = new SearchItemDAO();

			return dao.create(conn, model);
		}
	}

	/**
	 * 検索履歴アイテムを全件取得します。
	 *
	 * @return 検索履歴アイテムモデルのArrayList
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<SearchItemModel> find() throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			SearchItemDAO dao = new SearchItemDAO();

			return dao.findAll(conn);
		}
	}

	/**
	 * 指定ユーザーIDの検索履歴アイテムを全件取得します。
	 *
	 * @param userId ユーザーID
	 * @return 検索履歴アイテムモデルのArrayList
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<SearchItemModel> find(int userId) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			SearchItemDAO dao = new SearchItemDAO();

			return dao.findByUserId(conn, userId);
		}
	}

	/**
	 * 検索履歴アイテムを1件更新します。（削除フラグ）
	 *
	 * @param model 検索履歴アイテムモデル
	 * @return 結果（true:成功、false:失敗）
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean update(SearchItemModel model) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			SearchItemDAO dao = new SearchItemDAO();

			return dao.update(conn, model);
		}
	}

	/**
	 * 検索履歴アイテムを1件更新します。（気になるフラグ）
	 *
	 * @param model 検索履歴アイテムモデル
	 * @return 結果（true:成功、false:失敗）
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean updateMark(SearchItemModel model) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			SearchItemDAO dao = new SearchItemDAO();

			return dao.updateMark(conn, model);
		}
	}

	/**
	 * 検索履歴アイテムのレコードの件数を取得します。
	 *
	 * @param model 検索履歴アイテムモデル
	 * @return レコード数
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int countAll() throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			SearchItemDAO dao = new SearchItemDAO();

			return dao.countAll(conn);
		}
	}
}
