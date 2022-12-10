package logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.DiseaseItemDAO;
import database.DBConnection;
import model.DiseaseItemModel;

/**
 * 病名アイテムロジッククラス
 */
public class DiseaseItemLogic {

	/**
	 * 病名アイテムを1件追加します。
	 *
	 * @param model 病名アイテムモデル
	 * @return 結果（true:成功、false:失敗）
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public boolean create(DiseaseItemModel model) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			DiseaseItemDAO dao = new DiseaseItemDAO();

			return dao.create(conn, model);
		}
	}

	/**
	 * 病名アイテムを全件取得します。
	 *
	 * @return 病名アイテムモデルのArrayList
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<DiseaseItemModel> find() throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			DiseaseItemDAO dao = new DiseaseItemDAO();

			return dao.findAll(conn);
		}
	}

	/**
	 * 指定ID、指定ユーザーの病名アイテムを1件取得します
	 *
	 * @param id     DiseaseアイテムID
	 * @param userId ユーザーID
	 * @return Diseaseアイテムモデル
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
	 * 病名アイテムをキーワードで検索します。
	 *
	 * @param keyWord 検索キーワード
	 * @return TODOアイテムモデルのArrayList
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<DiseaseItemModel> find(String keyWord) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			DiseaseItemDAO dao = new DiseaseItemDAO();

			return dao.findByKeyWord(conn, keyWord);
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
	public boolean update(DiseaseItemModel model) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			DiseaseItemDAO dao = new DiseaseItemDAO();

			return dao.update(conn, model);
		}
	}
}
