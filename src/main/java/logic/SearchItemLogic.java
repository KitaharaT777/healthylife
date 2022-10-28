package logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.DiseaseItemDAO;
import dao.SearchItemDAO;
import dao.TodoItemDAO;
import database.DBConnection;
import model.DiseaseItemModel;
import model.SearchItemModel;
import model.TodoItemModel;
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
	 * 指定ユーザーIDの病名アイテムを取得します。
	 *
	 * @param userId ユーザーID
	 * @param limit  取得するレコード数（リミット値）
	 * @param offset 取得開始する行数（オフセット値）
	 * @return DiseaseItemModelのArrayList
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<DiseaseItemModel> find(int userId, int limit, int offset) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			DiseaseItemDAO dao = new DiseaseItemDAO();

			return dao.findByUserId(conn, userId, limit, offset);
		}
	}

	/**
	 * 指定ID、指定ユーザーの病名アイテムを1件取得します
	 *
	 * @param id     TODOアイテムID
	 * @param userId ユーザーID
	 * @return TODOアイテムモデル
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public DiseaseItemModel find(int id, int userId) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			DiseaseItemDAO dao = new DiseaseItemDAO();

			return dao.findOne(conn, id, userId);
		}
	}

	/**
	 * 指定ユーザーIDの病名アイテムをキーワードで検索します。
	 *
	 * @param userId  ユーザーID
	 * @param keyWord 検索キーワード
	 * @return TODOアイテムモデルのArrayList
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<DiseaseItemModel> find(int userId, String keyWord) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			DiseaseItemDAO dao = new DiseaseItemDAO();

			return dao.findByKeyWord(conn, userId, keyWord);
		}
	}

	/**
	 * 病名アイテムを1件更新します。
	 *
	 * @param model 病名アイテムモデル
	 * @return 結果（true:成功、false:失敗）
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean update(TodoItemModel model) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			TodoItemDAO dao = new TodoItemDAO();

			return dao.update(conn, model);
		}
	}
}
