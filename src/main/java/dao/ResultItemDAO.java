package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ResultItemModel;

/**
 * 検索結果アイテムDAOクラス
 */
public class ResultItemDAO {
	/** 基本となるSELECT文 */
	private final String BASE_SQL = "select "

			+ "r.id, r.result_id, r.comment, r.is_deleted, "
			+ "r.create_date_time, "
			+ "r.update_date_time, "
			+ "r.result1_probability, r.result1_name_id, d1.name_of_disease, "
			+ "r.result2_probability, r.result2_name_id, d2.name_of_disease, "
			+ "r.result3_probability, r.result3_name_id, d3.name_of_disease "
			//機能拡張に利用のため一部コメントアウト
			/*+ "r.result4_probability, r.result4_name_id, d4.name_of_disease, "
			+ "r.result5_probability, r.result5_name_id, d5.name_of_disease, "
			+ "r.result6_probability, r.result6_name_id, d6.name_of_disease, "
			+ "r.result7_probability, r.result7_name_id, d7.name_of_disease, "
			+ "r.result8_probability, r.result8_name_id, d8.name_of_disease, "
			+ "r.result9_probability, r.result9_name_id, d9.name_of_disease, "
			+ "r.result10_probability, r.result10_name_id, d10.name_of_disease "
			*/ + "from result r "
			+ "inner join name_of_disease d1 on r.result1_name_id=d1.id "
			+ "inner join name_of_disease d2 on r.result2_name_id=d2.id "
			+ "inner join name_of_disease d3 on r.result3_name_id=d3.id "
			/*+ "inner join name_of_disease d4 on r.result4_name_id=d4.name_id "
			+ "inner join name_of_disease d5 on r.result5_name_id=d5.name_id "
			+ "inner join name_of_disease d6 on r.result6_name_id=d6.name_id "
			+ "inner join name_of_disease d7 on r.result7_name_id=d7.name_id "
			+ "inner join name_of_disease d8 on r.result8_name_id=d8.name_id "
			+ "inner join name_of_disease d9 on r.result9_name_id=d9.name_id "
			+ "inner join name_of_disease d10 on r.result10_name_id=d10.name_id "
			*/;

	/**
	 * 検索結果アイテムを1件取得します。
	 *
	 * @param Connection connection データベースコネクションのインスタンス
	 * @param id         結果ID
	 * @param userId     ユーザーID
	 * @return 検索結果アイテムモデル
	 */
	public List<ResultItemModel> findOne(Connection connection, int result_id) {
		// レコードを格納するArrayListを生成する。
		List<ResultItemModel> list = new ArrayList<ResultItemModel>();

		// ResultItemModelクラスのインスタンスを生成する。
		ResultItemModel model = new ResultItemModel();

		try {
			// SQL文を設定する。
			String sql = BASE_SQL
					+ "where r.result_id=? ";

			// SQLを実行する準備をする。
			try (PreparedStatement stmt = connection.prepareStatement(sql)) {
				// パラメータを設定
				stmt.setInt(1, result_id);

				// SQLを実行する。
				try (ResultSet rs = stmt.executeQuery()) {
					// SQLの実行結果を取得する。
					if (rs.next()) {
						// 実行結果があるとき、モデルに値を設定する。
						model.setId(rs.getInt("id"));
						model.setResultId(rs.getInt("result_id"));
						model.setComment(rs.getString("comment"));
						model.setIsDeleted(rs.getInt("is_deleted"));
						model.setCreatedAt(rs.getTimestamp("create_date_time"));
						model.setUpdatedAt(rs.getTimestamp("update_date_time"));

						model.setResult1Prob(rs.getInt("result1_probability"));
						model.setResult1NameId(rs.getInt("result1_name_id"));
						model.setResult2Prob(rs.getInt("result2_probability"));
						model.setResult2NameId(rs.getInt("result2_name_id"));
						model.setResult3Prob(rs.getInt("result3_probability"));
						model.setResult3NameId(rs.getInt("result3_name_id"));
						//機能拡張に利用のため一部コメントアウト
						/*model.setResult4Prob(rs.getInt("result4_probability"));
						model.setResult4NameId(rs.getInt("result4_name_id"));
						model.setResult5Prob(rs.getInt("result5_probability"));
						model.setResult5NameId(rs.getInt("result5_name_id"));
						model.setResult6Prob(rs.getInt("result6_probability"));
						model.setResult6NameId(rs.getInt("result6_name_id"));
						model.setResult7Prob(rs.getInt("result7_probability"));
						model.setResult7NameId(rs.getInt("result7_name_id"));
						model.setResult8Prob(rs.getInt("result8_probability"));
						model.setResult8NameId(rs.getInt("result8_name_id"));
						model.setResult9Prob(rs.getInt("result9_probability"));
						model.setResult9NameId(rs.getInt("result9_name_id"));
						model.setResult10Prob(rs.getInt("result10_probability"));
						model.setResult10NameId(rs.getInt("result10_name_id"));
						*/

						// ArrayListにレコードを追加する。
						list.add(model);
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
		return list;
	}

}
