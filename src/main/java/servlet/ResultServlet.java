package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import logic.DiseaseItemLogic;
import logic.ResultItemLogic;
import model.DiseaseItemModel;
import model.ResultItemModel;
import model.SearchItemModel;
import model.UserModel;
import settings.PageSettings;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Result")
public class ResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ResultServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			// セッションスコープからインスタンスを取得
			HttpSession session = request.getSession();
			UserModel loginUser = (UserModel) session.getAttribute("loginUser");
			int user_id = loginUser.getUserId();

			// 検索ワードを取得
			request.setAttribute("key", request.getParameter("key"));
			String key = request.getParameter("key");

			// 症状検索のロジック/////////////////////////////////////////
			// 改修したいので確認用にprintlnありの部分

			// キーワードで条件分岐をさせてresult_idのパターンを増やし精度向上
			// 結果の変数を初期化
			int result_id = 0;

			if (key.contains("熱") || key.contains("以上")) {
				result_id = ((int) (Math.random() * 4) + 1) + 10000; //10001～10005
			}
			if (key.contains("頭")) {
				result_id = ((int) (Math.random() * 4) + 1) + 20000; //20001～20005
			}
			if (key.contains("足")) {
				result_id = ((int) (Math.random() * 4) + 1) + 30000; //30001～30005
			}

			System.out.println("乱数確認: "+ result_id);

			// 結果をランダムで決定（エラーにならないようResultテーブルに登録済みのIDで上書き）
			if (result_id != 0) {
				result_id = (int) (Math.random() * 4) + 1; //1～5
			}

			System.out.println("結果乱数: "+ result_id);

			// 症状検索結果リストを取得する。
			ResultItemLogic logic;
			logic = new ResultItemLogic();

			// 検索結果リストを取得してリクエストスコープに保存
			List<ResultItemModel> resultList = logic.find(result_id);
			request.setAttribute("resultList", resultList);

			// Diseaseリストを取得する。
			DiseaseItemLogic logic_disease;
			logic_disease = new DiseaseItemLogic();
			List<DiseaseItemModel> diseaseList = logic_disease.find();
			request.setAttribute("diseaseList", diseaseList);

			// 検索ワードとTOP3の結果を検索履歴（user_searchテーブル）に追加
			///////////////////////////////////////////////////

			// 今日の日付を取得する（検索日の登録に使う）。
			java.util.Date date = new java.util.Date();
			java.sql.Date today = new java.sql.Date(date.getTime());

			// パラメータを検索履歴モデルに設定する。
			SearchItemModel searchItem = new SearchItemModel();
			searchItem.setUserId(user_id);
			searchItem.setMark(0);
			searchItem.setSearchDate(today);
			searchItem.setWord(key);
			searchItem.setIsDeleted(0);

			for (ResultItemModel saveItem : resultList) {
				//System.out.println("Result_result1_probability: " + saveItem.result1Prob);
				//System.out.println("Result_result1_name_id: " + saveItem.result1NameId);

				// ResultItemModelの変数をpublicにしないとエラーになる
				int result1Prob = saveItem.result1Prob;
				int result1NameId = saveItem.result1NameId;
				int result2Prob = saveItem.result2Prob;
				int result2NameId = saveItem.result2NameId;
				int result3Prob = saveItem.result3Prob;
				int result3NameId = saveItem.result3NameId;

				searchItem.setResult1Prob(result1Prob);
				searchItem.setResult1NameId(result1NameId);
				searchItem.setResult2Prob(result2Prob);
				searchItem.setResult2NameId(result2NameId);
				searchItem.setResult3Prob(result3Prob);
				searchItem.setResult3NameId(result3NameId);

				// 検索ワードを検索履歴に登録する。
				logic.create(searchItem);
			}

			/////////////////////////////////////////////////////

			// フォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/result.jsp");
			dispatcher.forward(request, response);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();

			// エラーページへフォワードする。
			RequestDispatcher dispatcher = request.getRequestDispatcher(PageSettings.PAGE_ERROR);
			dispatcher.forward(request, response);

			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// resultページにリダイレクト
		response.sendRedirect("/WEB-INF/jsp/result.jsp");
	}
}
