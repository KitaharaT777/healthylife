package logic;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.ResultItemDAO;
import dao.SearchItemDAO;
import database.DBConnection;
import model.ResultItemModel;
import model.SearchItemModel;

/**
 * 症状検索結果アイテムロジッククラス
 */
public class ResultItemLogic {

	/**
	 * 指定IDの検索結果アイテムを1件取得します
	 *
	 * @param id     検索結果アイテムID
	 * @return 検索結果アイテムモデルのArrayList
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public List<ResultItemModel> find(int id) throws ClassNotFoundException, SQLException {
		try (DBConnection db = new DBConnection()) {
			Connection conn = db.getInstance();
			ResultItemDAO dao = new ResultItemDAO();

			return dao.findOne(conn, id);
		}
	}

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
}
